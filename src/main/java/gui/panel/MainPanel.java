package gui.panel;

import gui.listener.MenuBarListener;
import util.CenterPanel;
import util.GUIUtil;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel{
    public static MainPanel instance ;
//    public JToolBar tb = new JToolBar();
//    public JButton bFtpDownload = new JButton("报告下载");
//    public JButton bFtpUpload = new JButton("报告上传");
//    public JButton bReportCorrect = new JButton("报告批改");
//    public JButton bRegularGrade = new JButton("平时成绩计算");
//    public JButton bAllGrade = new JButton("总成绩预览");
    public JMenuBar mb= new JMenuBar();

    public JMenu  mFile=new JMenu("文件");
    public JMenu  mCorrect=new JMenu("报告批改");
    public JMenu  mRegularGrade=new JMenu("平时成绩录入");
    public JMenu  mAllGrade=new JMenu("总成绩");
    public JMenu  mSettings=new JMenu("设置");

    public JMenuItem jStuInfoGet = new JMenuItem("学生信息录入");
    public JMenuItem jFileDownload=new JMenuItem("文件下载");
    public JMenuItem jFileUpload=new JMenuItem("文件上传");
    public JMenuItem jReportCorrect=new JMenuItem("实验报告批改");
    public JMenuItem jRegularGrade = new JMenuItem("平时成绩录入");
    public JMenuItem jAllGrade = new JMenuItem("总成绩录入");
    public JMenuItem jFtpSetting = new JMenuItem("Ftp服务器设置");


    public CenterPanel jPanel;

    private MainPanel() {
//        tb.add(bFtpDownload);
//        tb.add(bFtpUpload);
//        tb.add(bReportCorrect);
//        tb.add(bRegularGrade);
//        tb.add(bAllGrade);

        mFile.add(jStuInfoGet);
        mFile.add(jFileDownload);
        mFile.add(jFileUpload);
        mCorrect.add(jReportCorrect);
        mRegularGrade.add(jRegularGrade);
        mAllGrade.add(jAllGrade);
        mSettings.add(jFtpSetting);

        mb.add(mFile);
        mb.add(mCorrect);
        mb.add(mRegularGrade);
        mb.add(mAllGrade);
        mb.add(mSettings);

        jPanel = new CenterPanel(1.0D);
        this.setLayout(new BorderLayout());
        this.add(this.mb,"North" );
        this.add(this.jPanel,"Center");
        this.addListener();

    }

    private  void addListener(){
        MenuBarListener listener=new MenuBarListener();
        this.jFtpSetting.addActionListener(listener);
        this.jFileDownload.addActionListener(listener);
        this.jFileUpload.addActionListener(listener);
        this.jReportCorrect.addActionListener(listener);
        this.jRegularGrade.addActionListener(listener);
        this.jAllGrade.addActionListener(listener);
        this.jStuInfoGet.addActionListener(listener);
//        this.bFtpDownload.addActionListener(listener);
//        this.bFtpUpload.addActionListener(listener);
    }



    public static void main(String[] args){
        GUIUtil.showPanel(instance,1.0D);
    }
    static{
//        GUIUtil.useLNF();
        instance = new MainPanel();
    }
}
