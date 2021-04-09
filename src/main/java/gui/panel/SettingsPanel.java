package gui.panel;

import gui.listener.SettingsListener;
import util.CenterPanel;
import util.GUIUtil;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JSplitPane {
    // 获取屏幕的宽度和高度
    private final static int g_nWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final static int g_nHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static SettingsPanel instance=null;
    public JPanel pLeft= new JPanel();
    public JButton bFtpSetting = new JButton("FTP上传下载配置");

    public CenterPanel pRight ;

    private SettingsPanel(){
        pLeft.setBounds(0, 0, 500, 60);
        pLeft.setLayout(new GridLayout(20,1));
        pLeft.add(bFtpSetting);

        pRight=new CenterPanel(0.7D);
        this.setLeftComponent(pLeft);
        this.setRightComponent(pRight);
        pLeft.setPreferredSize(new Dimension(g_nWidth/9,g_nHeight));
        this.setDividerLocation(80);

        addListener();


    }

    private void addListener(){
        SettingsListener listener = new SettingsListener();
        this.bFtpSetting.addActionListener(listener);
    }

    public static void main(String args[]){
        JFrame f = new JFrame("LoL");
        f.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        f.setLocation(0,0);

        f.setLayout(null);
        f.setContentPane(instance);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.setVisible(true);
//        GUIUtil.showPanel(instance,1.0D);
    }

    static{
        instance = new SettingsPanel();
    }
}
