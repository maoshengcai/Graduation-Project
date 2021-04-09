package gui.frame;

import gui.panel.MainPanel;
import gui.panel.ReportDownloadPanel;
import gui.panel.ReportUploadPanel;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class MainFrame extends  JFrame{
    public static MainFrame instance = new MainFrame();
    // 获取屏幕的宽度和高度
    private final static int g_nWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final static int g_nHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    private MainFrame(){
        InitGlobalFont(new Font("宋体", Font.PLAIN, 18)); // 统一设置字体
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setLocation(0,0);
        this.setTitle("计组实验成绩管理系统");
        this.setContentPane(MainPanel.instance);
        this.setLocationRelativeTo((Component)null);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * 统一设置字体，父界面设置之后，所有由父界面进入的子界面都不需要再次设置字体
     */
    private static void InitGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }

    public static void main(String[] args) {

        instance.setVisible(true);

    }

}
