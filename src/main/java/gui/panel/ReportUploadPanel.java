package gui.panel;

import dao.FtpDao;
import gui.listener.UploadListener;
import util.FtpUtil;
import util.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ReportUploadPanel extends JPanel{
    public static ReportUploadPanel instance;
    public JLabel ipL = new JLabel("ip地址：");
    public JTextField ipText = new JTextField("");
    public JLabel portL = new JLabel("端口号：");
    public JTextField portText = new JTextField("");
    public JLabel userL = new JLabel("用户名：");
    public JTextField userText = new JTextField("");
    public JLabel passwordL = new JLabel("密码：");
    public JPasswordField passwordText = new JPasswordField("");
    public JLabel ftpPathL = new JLabel("ftp服务器文件上传目录地址（根地址为“/”）：");
    public JTextField ftpPathText = new JTextField("");
    public JLabel localPathL = new JLabel("上传文件本地地址：");
    public JTextField localPathText = new JTextField("");
    public JButton testButton = new JButton("连接测试");
    public JButton startButton = new JButton("上传");
    public JLabel warningL = new JLabel("...");

    private ReportUploadPanel() {
        this.setPreferredSize(new Dimension(10, 40));
        this.setLayout(new GridLayout(8, 2));

        Dimension dSize = new Dimension(5, 5);
        ipText.setText("172.20.33.101");
        portText.setText("21");
        userText.setText("test");
        passwordText.setText("test");
        ftpPathText.setText("/");
        localPathText.setText("G:/test");
        testButton.setPreferredSize(dSize);
        startButton.setPreferredSize(dSize);
        this.add(ipL);
        this.add(ipText);
        this.add(portL);
        this.add(portText);
        this.add(userL);
        this.add(userText);
        this.add(passwordL);
        this.add(passwordText);
        this.add(ftpPathL);
        this.add(ftpPathText);
        this.add(localPathL);
        this.add(localPathText);
        this.add(testButton);
        this.add(startButton);
        this.add(warningL);

        UploadListener listener=new UploadListener();
        this.testButton.addActionListener(listener);
        this.startButton.addActionListener(listener);
//        //创建测试按钮监听器
//        testButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (emptyExist()) {
//                    JOptionPane.showMessageDialog(instance, "所填内容不能为空！");
//                } else {
//                    boolean b = false;
//                    try {
//                        b = FtpUtil.connectFtp(getFtp());
//                        FtpUtil.closeFtp();
//                    } catch (Exception exception) {
//                        exception.printStackTrace();
//                    }
//                    if (b) {
//                        warningL.setText("连接测试成功");
//                    } else {
//
//                        warningL.setText("连接测试失败");
//                    }
//                }
//            }
//        });
//        //创建下载按钮监听器
//        startButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (emptyExist()) {
//                    JOptionPane.showMessageDialog(instance, "所填内容不能为空！");
//                } else {
//                    warningL.setText("上传中......");
//                    boolean b = false;
//                    File file=new File(localPathText.getText());
//                    if(file.exists()) {
//                        try {
//                            b = FtpUtil.connectFtp(getFtp());
//                            FtpUtil.upload(file);
//                            FtpUtil.closeFtp();
//                        } catch (Exception exception) {
//                            exception.printStackTrace();
//                            warningL.setText("上传失败");
//                        }
//                    }else{
//                        JOptionPane.showMessageDialog(instance, "本地文件为空！");
//                        return;
//                    }
//                    if (b) {
//                        warningL.setText("上传成功");
//                        System.out.println(getFtp().toString());
//                    } else {
//                        warningL.setText("上传失败");
//                    }
//                }
//            }
//        });



    }


    public boolean emptyExist() {
        boolean ip_b = ipText.getText().isEmpty();
        boolean port_b = portText.getText().isEmpty();
        boolean user_b = userText.getText().isEmpty();
        boolean password_b = passwordText.getPassword().toString().isEmpty();
        boolean ftpPath_b = ftpPathText.getText().isEmpty();
        boolean localPath_b = localPathText.getText().isEmpty();
        return ip_b || port_b || user_b || password_b || ftpPath_b || localPath_b;
    }

    public FtpDao getFtp() {
        FtpDao ftpDao = new FtpDao();
        if (!this.emptyExist()) {
            ftpDao.setIpAddr(ipText.getText());
            ftpDao.setPort(Integer.valueOf(portText.getText()));
            ftpDao.setUserName(userText.getText());
            ftpDao.setPwd(new String(passwordText.getPassword()));
            ftpDao.setPath(ftpPathText.getText());
        }
        return ftpDao;

    }

    public String getLocalPath() {
        return localPathText.getText();
    }

    public static void main(String[] args) {
        GUIUtil.showPanel(instance);
    }
    static {
//        GUIUtil.useLNF();
        instance = new ReportUploadPanel();
    }

}
