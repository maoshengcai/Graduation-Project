package gui.panel;

import gui.listener.StudentGetListener;
import util.CenterPanel;
import util.GUIUtil;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

public class StudentGetPanel extends JPanel {
    public static StudentGetPanel instance ;

    public JPanel jp_select = new JPanel();
    public JLabel label_path = new JLabel("学生信息导入路径：");
    public JLabel label_file = new JLabel();
    public JFileChooser fc  = new JFileChooser();
    public JButton jb_path = new JButton("...");
    public JButton jb_get = new JButton("导入");

    public CenterPanel centerPanel = new CenterPanel(1.0D);
    public JScrollPane jsp = new JScrollPane();
    public JTable jTable  = new JTable();

    public JPanel jp_sourth = new JPanel();
    public JButton jb_in = new JButton("录入");
    public JButton jb_cancel = new JButton("重置");

    public void addListener(){
        StudentGetListener listener = new StudentGetListener();
        jb_path.addActionListener(listener);
        jb_get.addActionListener(listener);
        jb_in.addActionListener(listener);
        jb_cancel.addActionListener(listener);
    }
    private StudentGetPanel(){
        this.setLayout(new BorderLayout());

        jp_select.add(label_path);
        jp_select.add(label_file);
        jp_select.add(jb_path);
        jp_select.add(jb_get);
        fc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".xls")||f.isDirectory();
            }

            @Override
            public String getDescription() {
                return ".xls";
            }
        });
        this.add(jp_select,BorderLayout.NORTH);
        label_file.setPreferredSize(new Dimension(500,40));

        jsp.setViewportView(jTable);
        this.add(centerPanel,BorderLayout.CENTER);
        centerPanel.show(jsp);

        jp_sourth.add(jb_in);
        jp_sourth.add(jb_cancel);
        this.add(jp_sourth,BorderLayout.SOUTH);
        addListener();
    }
    static {
        instance = new StudentGetPanel();
    }
    public static void main(String[] args){
        GUIUtil.showPanel(instance,1.0D);
    }
}
