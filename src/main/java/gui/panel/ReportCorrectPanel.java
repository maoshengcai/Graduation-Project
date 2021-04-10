package gui.panel;

import dao.GradeDao;
import gui.listener.ReportCorrectListener;
import service.Sources;
import util.CenterPanel;
import util.GUIUtil;
import util.PdfToImage;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class ReportCorrectPanel extends JPanel {
    public static ReportCorrectPanel instance;

    private final static int g_nWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final static int g_nHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

    public String filePath = "G:/test";
    public ArrayList<String> reportList = new ArrayList<>();   //当前所点击文件夹或点击的pdf文件所在父目录下所有未批改pdf文件绝对路径列表
    public String currentFilePath="";
    public JTree jTree ;
    public JScrollPane jSPane_Tree;

    public CenterPanel centerPanel;

    public JPanel jpImage = new JPanel();
    public JLabel showPath = new JLabel("当前文档为：");
    public JLabel jLabel;
    public JScrollPane jSPane;
    public ImageIcon imageIcon;

    public JPanel jPanel;
    public JLabel label1= new JLabel("实验名称：");
    public JLabel label2 = new JLabel();
    public JLabel labelStu1 = new JLabel();
    public JTextField jTextStu1 = new JTextField();
    public JLabel labelStu2 = new JLabel();
    public JTextField jTextStu2 = new JTextField();
    public JLabel labelStu3 = new JLabel();
    public JTextField jTextStu3 = new JTextField();
    public JLabel labelStu4 = new JLabel();
    public JTextField jTextStu4 = new JTextField();
    public JLabel labelGrade = new JLabel("报告成绩：");
    public JTextField jTextGrade = new JTextField();
    public JButton nextReport = new JButton("下一份");
    public JButton submit = new JButton("提交");
    public JLabel warningL = new JLabel();


    //获取面板中输入成绩,返回一个列表
    public ArrayList<GradeDao> getGrades(){
        ArrayList<GradeDao> result = new ArrayList<>();
        String project_name = "experiment"+label2.getText();
        String[] strs = new String[]{labelStu1.getText(),labelStu2.getText(),labelStu3.getText(),labelStu4.getText()};
        String[] vals  = new String[]{jTextStu1.getText(),jTextStu2.getText(),jTextStu3.getText(),jTextStu4.getText()};
        int allGrade = 0;
        if(jTextGrade.getText().equals("")){
            JOptionPane.showMessageDialog(instance,"报告成绩不能为空！");
            return null;
        }else{
            allGrade = Integer.valueOf(jTextGrade.getText());
            if(allGrade<0 || allGrade>100){
                JOptionPane.showMessageDialog(instance,"成绩只能是大于0小于100的整数！");
                return null;
            }
        }
        for(int i= 0;i < strs.length;i++){
            System.out.println("string    "+strs[i]);
            if(strs[i].equals("")){

                break;
            }
            int val = allGrade;
            if(!vals[i].equals("")){
                val = Integer.valueOf(vals[i]);
                if(val <0 || val >100){
                    JOptionPane.showMessageDialog(instance,"成绩只能是大于0小于100的整数！");
                    return null;
                }
            }
            GradeDao grade = new GradeDao(project_name,strs[i],val);
            result.add(grade);
        }

        return result;
    }
    //生成文件树结构
    public static void produceTree(String filePath, DefaultMutableTreeNode pNode){

        File file= new File(filePath);
        if(file.isDirectory()){
            String[] paths = file.list();
            boolean test = true;
            for(int i = 0;i < paths.length;i++){
                DefaultMutableTreeNode node=new DefaultMutableTreeNode(paths[i]);
                pNode.add(node);
                String strPath = filePath+"/"+paths[i];
                if(new File(strPath).isDirectory()){
                    test = false;
                    produceTree(strPath,node);
                }
            }
            if(test){
                return;
            }
        }else{
            String[] str = filePath.split("[/]");
            DefaultMutableTreeNode child=new DefaultMutableTreeNode(str[str.length-1]);
            pNode.add(child);
            return;
        }
        System.out.println("Tree produce succeed!");
    }
    //生成预览pdf文件的图像
    public void setImageShow(String path){
        if(path.endsWith(".pdf")){

            String[] strA = path.split("[/]");
            String fileNames[] = strA[strA.length-1].split("[._]");
            String cond[] = Arrays.copyOfRange(fileNames,1,fileNames.length-1);
            instance.showPath.setText("加载中......");
            instance.centerPanel.show(instance.jpImage);
            System.out.println("生成imageIcon");
            instance.currentFilePath = path;
            instance.imageIcon.setImage( PdfToImage.pdfToImage(path));
            instance.showPath.setText("当前文档为："+path);
            instance.centerPanel.show(instance.jpImage);

            int len =  cond.length;
            if(len>=2){
                instance.jPanel.setVisible(true);
                instance.label2.setText(cond[len-1]);
                instance.labelStu1.setText(cond[0]);
            }
            if(len>=3){
                instance.labelStu2.setVisible(true);
                instance.labelStu2.setText(cond[1]);
            }else{
                instance.labelStu2.setVisible(false);
            }
            if(len >= 4){
                instance.labelStu3.setVisible(true);
                instance.labelStu3.setText(cond[2]);
            }else{
                instance.labelStu3.setVisible(false);
            }
            if(len >= 5){
                instance.labelStu4.setVisible(true);
                instance.labelStu4.setText(cond[3]);
            }else{
                instance.labelStu3.setVisible(false);
            }
            System.out.println(path);
        }
    }
    public void setReportList(String path){
        HashSet<String> correctedList = Sources.correctedReport;
        File file = new File(path);
        if(file.isFile()){
            path = file.getParent();
            String[] strs = path.split("[\\\\]");
            path = String.join("/",strs);
            file = new File(path);
        }
        System.out.println("setReportList"+path);

        if(file.isDirectory()){
            String[] fileStrs = file.list();
            for(int i = 0;i< fileStrs.length;i++){
                String str = fileStrs[i];
                str = path+"/"+str;
                if(str.endsWith(".pdf")&&(!correctedList.contains(str))){
                    reportList.add(str);
                }
            }
        }

    }
    public void addListener(){
        ReportCorrectListener rcl = new ReportCorrectListener();
        this.submit.addActionListener(rcl);
        this.nextReport.addActionListener(rcl);
    }
    public TreeSelectionListener getListener(){
        return new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                ReportCorrectPanel rcp = ReportCorrectPanel.instance;
                rcp.jPanel.setVisible(false);
                JTree tree = (JTree) e.getSource();
                DefaultMutableTreeNode node= (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                Object object[] = node.getUserObjectPath();
                String str = object[0].toString();
                for(int i = 1;i<object.length;i++){
                    str = str + "/"+object[i].toString();
                }
                setReportList(str);
                System.out.println("所有未批改报告：");
                reportList.stream().forEach(System.out::println);
                if(!reportList.isEmpty()){
                    if(new File(str).isDirectory()){
                        str = reportList.get(0);
                    }
                    if(reportList.contains(str)){
                        setImageShow(str);
                    }else{
                        //提示该报告已批改过
                        JOptionPane.showMessageDialog(instance,"该报告已经被批改！");
                    }
                }else{
                    JOptionPane.showMessageDialog(instance,"该目录中所有报告已被批改！");
                }

            }
        };
    }
    private ReportCorrectPanel(){
        this.setLayout(new BorderLayout());
        //west
        DefaultMutableTreeNode pNode = new DefaultMutableTreeNode(filePath);
        produceTree(filePath,pNode);
        jTree = new JTree(pNode);
        jTree.addTreeSelectionListener(getListener());
        jSPane_Tree = new JScrollPane();
        jSPane_Tree.setViewportView(jTree);
        this.add(this.jSPane_Tree,"West");
        //center
        imageIcon = new ImageIcon();
        jLabel = new JLabel();
        jLabel.setIcon(imageIcon);
        jLabel.setBounds(50,50,imageIcon.getIconWidth(),imageIcon.getIconHeight());
        centerPanel = new CenterPanel(1.0D);
        jSPane = new JScrollPane(jLabel);
        jpImage.setLayout(new BorderLayout());
        jpImage.add(showPath,BorderLayout.NORTH);
        jpImage.add(jSPane,BorderLayout.CENTER);
        centerPanel.setPreferredSize(new Dimension(50,50));
        this.add(this.centerPanel,"Center");
        //east
        jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(20,2));
        jPanel.add(label1);jPanel.add(label2);
//        jPanel.add(label3);jPanel.add(label4);
        jPanel.add(labelStu1);jPanel.add(jTextStu1);
        jPanel.add(labelStu2);jPanel.add(jTextStu2);jPanel.add(labelStu3);
        jPanel.add(jTextStu3);jPanel.add(labelStu4);jPanel.add(jTextStu4);
        jPanel.add(labelGrade);jPanel.add(jTextGrade);
        jPanel.add(submit);jPanel.add(nextReport);jPanel.add(warningL);
        jPanel.setVisible(false);
        this.add(this.jPanel,"East");


        int panelWidth = g_nWidth;
        int panelHeigth = g_nHeight;
        jSPane_Tree.setPreferredSize(new Dimension(panelWidth/6,panelHeigth/6));
//        centerPanel.setPreferredSize(new Dimension(panelWidth/2,panelHeigth/2));
        jPanel.setPreferredSize(new Dimension(panelWidth/6,panelHeigth/6));
        Dimension dimension = new Dimension(panelWidth/300,panelHeigth/24);
        nextReport.setPreferredSize(dimension);

        addListener();



    }
    static {
        instance = new ReportCorrectPanel();
    }
    public static void main(String[] args){
//        instance.setVisible(true);
//        GUIUtil.showPanel(instance,1.0D);
        String str = "";
        System.out.println(Integer.valueOf(null));
    }




}
