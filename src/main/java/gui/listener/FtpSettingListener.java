package gui.listener;

import gui.panel.FtpSettingPanel;
import util.FtpUtil;
import util.PropertiesUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class FtpSettingListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        FtpSettingPanel fsp = FtpSettingPanel.instance;
        JButton jb = (JButton) e.getSource();
        fsp.warningL.setText("...");

        if (fsp.emptyExist()) {
            JOptionPane.showMessageDialog(fsp, "所填内容不能为空！");
            return;
        }
        //测试
        if (jb == fsp.testButton) {

            boolean bDown = false, bUp = false;
            try {
                bDown = FtpUtil.connectFtp(fsp.getDownFtp());
                bUp = FtpUtil.connectFtp(fsp.getUpFtp());
                FtpUtil.closeFtp();
            } catch (Exception exception) {
                exception.printStackTrace();
                bDown = false;
                bUp = false;
            }

            String tip = "";
            if (bDown&&bUp) {
                tip = "FTP连接测试成功！";
            } else {
                tip = "FTP连接测试失败！";
            }

            fsp.warningL.setText(tip);


        }

        //保存配置
        if (jb == fsp.startButton) {
            Properties properties = new Properties();

            try {
                OutputStream fos = new FileOutputStream("src/ftp.properties");
                properties.setProperty("ftp.ipAdress", fsp.ipText.getText());
                properties.setProperty("ftp.port", fsp.portText.getText());
                properties.setProperty("ftp.usename", fsp.userText.getText());
                properties.setProperty("ftp.password", new String(fsp.passwordText.getPassword()));
                properties.setProperty("ftp.ftpDownPath", fsp.ftpDownPathText.getText());
                properties.setProperty("ftp.localDownPath", fsp.localDownPathText.getText());
                properties.setProperty("ftp.ftpUpPath", fsp.ftpUpPathText.getText());
                properties.setProperty("ftp.localUpPath", fsp.localUpPathText.getText());
                properties.store(fos,"update");
                fos.close();
                fsp.warningL.setText("保存成功！");
            } catch (IOException ioException) {
                ioException.printStackTrace();
                fsp.warningL.setText("保存失败！");
            }


        }


    }
}
