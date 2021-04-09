package gui.panel;

import dao.FtpDao;
import gui.listener.FtpSettingListener;
import util.GUIUtil;
import util.PropertiesUtil;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

public class FtpSettingPanel extends JPanel {
    public static FtpSettingPanel instance = null;
    public JLabel ipL = new JLabel("ip地址：");
    public JTextField ipText = new JTextField("");
    public JLabel portL = new JLabel("端口号：");
    public JTextField portText = new JTextField("");
    public JLabel userL = new JLabel("用户名：");
    public JTextField userText = new JTextField("");
    public JLabel passwordL = new JLabel("密码：");
    public JPasswordField passwordText = new JPasswordField("");
    public JLabel ftpDownPathL = new JLabel("ftp服务器文件下载目录地址（根地址为“/”）：");
    public JTextField ftpDownPathText = new JTextField("");
    public JLabel localDownPathL = new JLabel("下载文件本地目录地址：");
    public JTextField localDownPathText = new JTextField("");
    public JLabel ftpUpPathL = new JLabel("ftp服务器文件上传目录地址（根地址为“/”）：");
    public JTextField ftpUpPathText = new JTextField("");
    public JLabel localUpPathL = new JLabel("上传文件本地地址：");
    public JTextField localUpPathText = new JTextField("");
    public JButton testButton = new JButton("连接测试");
    public JButton startButton = new JButton("保存");
    public JLabel warningL = new JLabel("...");

    private FtpSettingPanel(){
        this.setPreferredSize(new Dimension(10, 40));
        this.setLayout(new GridLayout(10, 2));

        Dimension dSize = new Dimension(5, 5);
//        ipText.setText("172.20.33.101");
//        portText.setText("21");
//        userText.setText("test");
//        passwordText.setText("test");
//        ftpDownPathText.setText("/");
//        localDownPathText.setText("G:/test");
//        ftpUpPathText.setText("/");
//        localUpPathText.setText("G:/test");
        //读取ftp.properties到JTextField中
        Properties properties=PropertiesUtil.getProperties("src/ftp.properties");
        ipText.setText(properties.getProperty("ftp.ipAdress"));
        portText.setText(properties.getProperty("ftp.port"));
        userText.setText(properties.getProperty("ftp.usename"));
        passwordText.setText(properties.getProperty("ftp.password"));
        ftpDownPathText.setText(properties.getProperty("ftp.ftpDownPath"));
        localDownPathText.setText(properties.getProperty("ftp.localDownPath"));
        ftpUpPathText.setText(properties.getProperty("ftp.ftpUpPath"));
        localUpPathText.setText(properties.getProperty("ftp.localUpPath"));

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
        this.add(ftpDownPathL);
        this.add(ftpDownPathText);
        this.add(localDownPathL);
        this.add(localDownPathText);
        this.add(ftpUpPathL);
        this.add(ftpUpPathText);
        this.add(localUpPathL);
        this.add(localUpPathText);
        this.add(testButton);
        this.add(startButton);
        this.add(warningL);

        FtpSettingListener listener=new FtpSettingListener();
        this.testButton.addActionListener(listener);
        this.startButton.addActionListener(listener);
    }
    public boolean emptyExist() {
        boolean ip_b = ipText.getText().isEmpty();
        boolean port_b = portText.getText().isEmpty();
        boolean user_b = userText.getText().isEmpty();
        boolean password_b = passwordText.getPassword().toString().isEmpty();
        boolean ftpDownPath_b = ftpDownPathText.getText().isEmpty();
        boolean localDownPath_b = localDownPathText.getText().isEmpty();
        boolean ftpUpPath_b = ftpUpPathText.getText().isEmpty();
        boolean localUpPath_b = localUpPathText.getText().isEmpty();
        return ip_b || port_b || user_b || password_b || ftpDownPath_b || localDownPath_b ||ftpUpPath_b ||localUpPath_b;
    }

    public FtpDao getDownFtp() {
        FtpDao ftpDao = new FtpDao();
        if (!this.emptyExist()) {
            ftpDao.setIpAddr(ipText.getText());
            ftpDao.setPort(Integer.valueOf(portText.getText()));
            ftpDao.setUserName(userText.getText());
            ftpDao.setPwd(new String(passwordText.getPassword()));
            ftpDao.setPath(ftpDownPathText.getText());
        }
        return ftpDao;

    }

    public FtpDao getUpFtp() {
        FtpDao ftpDao = new FtpDao();
        if (!this.emptyExist()) {
            ftpDao.setIpAddr(ipText.getText());
            ftpDao.setPort(Integer.valueOf(portText.getText()));
            ftpDao.setUserName(userText.getText());
            ftpDao.setPwd(new String(passwordText.getPassword()));
            ftpDao.setPath(ftpUpPathText.getText());
        }
        return ftpDao;

    }

    public static void main(String[] args){
        GUIUtil.showPanel(instance);
    }
    static {
        instance = new FtpSettingPanel();
    }

}
