package gui.listener;

import dao.FtpDao;
import gui.panel.*;
import service.GradeDaoService;
import service.GradeDaoServiceImpl;
import service.StuInfoDaoService;
import service.StuInfoDaoServiceImpl;
import util.FtpUtil;
import util.PropertiesUtil;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashSet;
import java.util.Properties;

public class MenuBarListener implements ActionListener {
    public boolean flag;
    public MenuBarListener(){

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        MainPanel p=MainPanel.instance;
        JMenuItem b= (JMenuItem) e.getSource();
        System.out.println("in Listener");
        if(b == p.jFtpSetting){
            System.out.println("in in Listener");
            p.jPanel.show(SettingsPanel.instance);
        }
        if(b == p.jFileDownload){
            System.out.println("in jFileDownload Listener");
            FileDownAndUpPanel dup = FileDownAndUpPanel.instance;
            dup.warningT.setText("下载中......");

            p.jPanel.show(dup);

            flag = true;
            Properties properties= PropertiesUtil.getProperties("src/ftp.properties");
            FtpDao ftpDao=new FtpDao(properties,true);
            String localDownload = properties.getProperty("ftp.localDownPath");
            System.out.println("下载中......"+localDownload);
            SwingWorker<Void,Void> worker = new SwingWorker<Void,Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    try {
                        flag = FtpUtil.startDown(ftpDao, localDownload, ftpDao.getPath());
                        FtpUtil.closeFtp();
                    } catch (Exception exception) {
//                exception.printStackTrace();
                        flag=false;
//                        Thread.sleep(100);
                        dup.warningT.setText("下载失败!");
                        dup.warningT.setForeground(Color.RED);
                    }
                    System.out.println("执行这个SwingWorder的线程是：" + Thread.currentThread().getName()+"flag = "+flag);
                    if (flag) {
                        dup.warningT.setText("下载成功!");

                    } else {
                        dup.warningT.setText("下载失败!");
                        dup.warningT.setForeground(Color.RED);
                    }
                    return null;

                }
            };
            worker.execute();

            p.jPanel.show(dup);
        }

        if(b == p.jFileUpload){
            FileDownAndUpPanel dup = FileDownAndUpPanel.instance;
            dup.warningT.setText("上传中......");
            p.jPanel.show(dup);
            flag = true;
            Properties properties= PropertiesUtil.getProperties("src/ftp.properties");
            FtpDao ftpDao=new FtpDao(properties,false);
            String localUp = properties.getProperty("ftp.localUpPath");
            SwingWorker<Void,Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    File file=new File(localUp);
                    if(file.exists()) {
                        try {
                            flag = FtpUtil.connectFtp(ftpDao);
                            FtpUtil.upload(file);
                            FtpUtil.closeFtp();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                            flag = false;
                            dup.warningT.setText("上传失败!");
                            dup.warningT.setForeground(Color.RED);
                        }
                    }else{
                        JOptionPane.showMessageDialog(dup, "本地文件为空！");
                    }
                    if (flag) {
                        dup.warningT.setText("上传成功!");

                    } else {
                        dup.warningT.setText("上传失败!");
                        dup.warningT.setForeground(Color.RED);
                    }
                    return null;
                }
            };
            worker.execute();
            p.jPanel.show(dup);
        }

        if(b == p.jReportCorrect){
            ReportCorrectPanel rup = ReportCorrectPanel.instance;
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(rup.filePath);
            rup.produceTree(rup.filePath,node);
            rup.jTree = new JTree(node);
            System.out.println("in Listener and jReportCorrect");
            p.jPanel.show(ReportCorrectPanel.instance);
        }

        if(b == p.jRegularGrade){
            RegularGradePanel rgp = RegularGradePanel.instance;
            StuInfoDaoService stuInfoDaoService = new StuInfoDaoServiceImpl();
            //重置所有选项
            rgp.cb_class.removeAllItems();
            rgp.cb_academy.removeAllItems();
            //获取所有年级和学院
            HashSet<String> classes = stuInfoDaoService.getAllClass();
            HashSet<String> academys = stuInfoDaoService.getAllAcademy();
            rgp.cb_class.addItem("");
            rgp.cb_academy.addItem("");
            classes.stream().forEach(rgp.cb_class::addItem);
            academys.stream().forEach(rgp.cb_academy::addItem);
            p.jPanel.show(rgp);
        }

        if(b == p.jAllGrade){
            AllGradePanel agp = AllGradePanel.instance;
            p.jPanel.show(agp);
        }
        if(b == p.jStuInfoGet){
            p.jPanel.show(StudentGetPanel.instance);
        }
    }
}
