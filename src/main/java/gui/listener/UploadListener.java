package gui.listener;

import gui.panel.ReportUploadPanel;
import util.FtpUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class UploadListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        ReportUploadPanel rup=ReportUploadPanel.instance;
        JButton jb= (JButton) e.getSource();
        if(jb == rup.testButton){
            if (rup.emptyExist()) {
                JOptionPane.showMessageDialog(rup, "所填内容不能为空！");
            } else {
                boolean b = false;
                try {
                    b = FtpUtil.connectFtp(rup.getFtp());
                    FtpUtil.closeFtp();
                } catch (Exception exception) {
                    exception.printStackTrace();
                    b=false;
                }
                if (b) {
                    rup.warningL.setText("连接测试成功");
                } else {

                    rup.warningL.setText("连接测试失败");
                }
            }
        }
        if(jb == rup.startButton){
            if (rup.emptyExist()) {
                JOptionPane.showMessageDialog(rup, "所填内容不能为空！");
            } else {
                rup.warningL.setText("上传中......");
                boolean b = false;
                File file=new File(rup.localPathText.getText());
                if(file.exists()) {
                    try {
                        b = FtpUtil.connectFtp(rup.getFtp());
                        FtpUtil.upload(file);
                        FtpUtil.closeFtp();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        rup.warningL.setText("上传失败");
                    }
                }else{
                    JOptionPane.showMessageDialog(rup.instance, "本地文件为空！");
                    return;
                }
                if (b) {
                    rup.warningL.setText("上传成功");
                    System.out.println(rup.getFtp().toString());
                } else {
                    rup.warningL.setText("上传失败");
                }
            }
        }
    }
}
