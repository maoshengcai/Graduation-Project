package util;

import dao.FtpDao;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class FtpUtil {
    private static final Logger logger = Logger.getLogger(FtpUtil.class);
    private static FTPClient ftp;
    private static String encoding = new Properties(System.getProperties()).getProperty("file.encoding");
    private static String LOCAL_CHARSET = "GBK";

    // FTP协议里面，规定文件名编码为iso-8859-1
    private static String SERVER_CHARSET = "ISO-8859-1";

    /**
     * 获取ftp连接
     *
     * @param f
     * @return
     * @throws Exception
     */
    public static boolean connectFtp(FtpDao f) throws Exception {
        ftp = new FTPClient();
        boolean flag = false;
        if (f.getPort() == null) {
            ftp.connect(f.getIpAddr(), 21);
        } else {
            ftp.connect(f.getIpAddr(), f.getPort());
        }
        boolean fLogin=ftp.login(f.getUserName(), f.getPwd());
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        int reply = ftp.getReplyCode();
        System.out.println("reply"+reply+"  "+fLogin);
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            return flag;
        }
        //登录失败时，返回false
        if(!fLogin){
            ftp.disconnect();
            return false;
        }
        if(FTPReply.isPositiveCompletion(ftp.sendCommand("OPTS UTF8", "ON"))){
            LOCAL_CHARSET = "UTF-8";
            System.out.println("support utf-8");
        }
        ftp.changeWorkingDirectory(new String(f.getPath().getBytes(encoding), SERVER_CHARSET));
        flag = true;
        return flag;
    }

    /**
     * 关闭ftp连接
     */
    public static void closeFtp() {
        if (ftp != null && ftp.isConnected()) {
            try {
                ftp.logout();
                ftp.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ftp上传文件
     *
     * @param f
     * @throws Exception
     */
    public static void upload(File f) throws Exception {
        String path = new String(f.getName().getBytes(encoding), StandardCharsets.ISO_8859_1);
        if (f.isDirectory()) {
            ftp.makeDirectory(path);
            ftp.changeWorkingDirectory(path);
            String[] fnames = ftp.listNames();
            String[] files = f.list();
            for (String fstr : files) {
                File file1 = new File(f.getPath() + "/" + fstr);
                fstr = new String(fstr.getBytes(encoding), StandardCharsets.ISO_8859_1);

                if (file1.isDirectory()) {
                    upload(file1);
                    ftp.changeToParentDirectory();
                } else {
                    FileInputStream input = new FileInputStream(file1);
                    //判断文件名是否与ftp服务器该目录下某一文件名上相同，若相同，则在上传文件名后面添加01
                    String upFileName = file1.getName();
                    for (String fname : fnames) {
                        if (fname.equals(upFileName)) {
                            String[] names = fname.split("[.]");
                            int len = names.length;
                            names[len - 2] = names[len - 2] + "_01";
                            upFileName = String.join(".", names);
                        }
                    }
                    //pdf文件名要求iso-8859-1的编码，目录或文件名要转码
                    boolean flag1 = ftp.storeFile(new String(upFileName.getBytes(encoding), StandardCharsets.ISO_8859_1), input);
                    System.out.println("成功上传" + f.getName()+"  location:"+flag1);
                    String string1 = new String(upFileName.getBytes(encoding),StandardCharsets.ISO_8859_1);
                    System.out.println(string1);
                    input.close();
                }
            }
        } else {
            FileInputStream input = new FileInputStream(f);

            //判断文件名是否与ftp服务器该目录下某一文件名上相同，若相同，则在上传文件名后面添加01
            String[] fnames = ftp.listNames();
            String upFileName = f.getName();
            upFileName=new String(upFileName.getBytes(encoding), StandardCharsets.ISO_8859_1);
            for (String fname : fnames) {
                if (fname.equals(upFileName)) {
                    String[] names = fname.split("[.]");
                    int len = names.length;
                    names[len - 2] = names[len - 2] + "_01";
                    upFileName = String.join(".", names);
                }
            }
            ftp.enterLocalPassiveMode();
            String string1 = new String(upFileName.getBytes(encoding),"GBK");
            boolean flag1 = ftp.storeFile(new String(upFileName.getBytes(encoding), StandardCharsets.ISO_8859_1), input);
            System.out.println("成功上传" + f.getName()+"  location:"+flag1);

            System.out.println(string1);
            input.close();
        }
    }


    /**
     * 下载链接配置
     *
     * @param f
     * @param localBaseDir  本地目录
     * @param remoteBaseDir 远程目录
     * @throws Exception
     */
    public static boolean startDown(FtpDao f, String localBaseDir, String remoteBaseDir) throws Exception {
        boolean b = false;
        remoteBaseDir = new String(remoteBaseDir.getBytes(encoding), StandardCharsets.ISO_8859_1);
        if (FtpUtil.connectFtp(f)) {
            try {
                FTPFile[] files = null;
                boolean changedir = ftp.changeWorkingDirectory(remoteBaseDir);
                //单文件下载没有实现
                boolean loacldir = new File(localBaseDir).exists();
                System.out.println("localidr= "+loacldir+"  "+changedir);
                if (changedir && loacldir) {
                    ftp.setControlEncoding(encoding);
                    files = ftp.listFiles();

                    for (int i = 0; i < files.length; i++) {
                        try {
                            downloadFile(files[i], localBaseDir, remoteBaseDir);

                        } catch (Exception e) {
                            logger.error(e);
                            logger.error("<" + files[i].getName() + ">下载失败");
                        }
                    }
                    b = true;
                }

            } catch (Exception e) {
                logger.error(e);
                logger.error("下载过程中出现异常");
            }
        } else {
            logger.error("链接失败！");
        }
        return b;
    }

    /**
     * 下载FTP文件
     * 当你需要下载FTP文件的时候，调用此方法
     * 根据<b>获取的文件名，本地地址，远程地址</b>进行下载
     *
     * @param ftpFile
     * @param relativeLocalPath
     * @param relativeRemotePath
     */
    private static void downloadFile(FTPFile ftpFile, String relativeLocalPath, String relativeRemotePath) throws Exception{
        relativeRemotePath = new String(relativeRemotePath.getBytes(encoding), StandardCharsets.ISO_8859_1);

        if (ftpFile.isFile()) {
            if (ftpFile.getName().indexOf("?") == -1) {
                OutputStream outputStream = null;
                try {
                    File locaFile = new File(relativeLocalPath +'/'+ ftpFile.getName());
                    //判断文件是否存在，存在则返回
                    if (locaFile.exists()) {
                        return;
                    } else {
                        outputStream = new FileOutputStream(relativeLocalPath +'/'+ ftpFile.getName());
                        ftp.retrieveFile(new String(ftpFile.getName().getBytes(encoding), StandardCharsets.ISO_8859_1), outputStream);
                        outputStream.flush();
                        outputStream.close();
                    }
                } catch (Exception e) {
                    logger.error(e);
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        logger.error("输出文件流异常");
                    }
                }
            }
        } else {
            String newlocalRelatePath = relativeLocalPath +'/'+ ftpFile.getName();
            String newRemote = relativeRemotePath + ftpFile.getName();
            File fl = new File(newlocalRelatePath);
            if (!fl.exists()) {
                fl.mkdirs();
            }
            try {
//                newlocalRelatePath = newlocalRelatePath +'/';
                newRemote = newRemote + "/";
                String currentWorkDir = ftpFile.getName();
                boolean changedir = ftp.changeWorkingDirectory(currentWorkDir);   //进入子文件夹
                if (changedir) {
                    FTPFile[] files = null;
                    files = ftp.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        downloadFile(files[i], newlocalRelatePath, newRemote);
                    }

                    //返回父目录
                    ftp.changeToParentDirectory();
                }

            } catch (Exception e) {
                logger.error(e);
            }
        }
    }

    public static void main(String[] args)  {
        // TODO Auto-generated method stub
        Properties initProp = new Properties(System.getProperties());
        System.out.println("当前系统编码:" + initProp.getProperty("file.encoding"));
        System.out.println("当前系统语言:" + initProp.getProperty("user.language"));

        FtpDao f = new FtpDao();
        f.setIpAddr("172.20.33.101");
        f.setPort(21);
        f.setUserName("test");
        f.setPwd("test");
        f.setPath("/");
        boolean test=false;
        try{
            test = FtpUtil.connectFtp(f);
            File file = new File("G:/test/test/计组实验2.pdf");

            FtpUtil.upload(file);
            FtpUtil.closeFtp();
        }catch(Exception e){
            e.printStackTrace();
            test=false;
        }

        System.out.println("connect success:" + test);


//		FtpUtil.startDown(f, "G:/test", "/");

        System.out.println("success!");

    }

}
