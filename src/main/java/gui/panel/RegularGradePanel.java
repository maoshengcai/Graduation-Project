package gui.panel;

import util.CenterPanel;
import util.GUIUtil;

import javax.swing.*;
import java.awt.*;

public class RegularGradePanel extends JPanel {
    public static RegularGradePanel instance ;
    public JPanel jpOption = new JPanel();
    public JLabel label_class = new JLabel("年级：");
    public JComboBox cb_class ;
    public JLabel label_academy = new JLabel("学院：");
    public JComboBox cb_academy ;
    public JButton searchB = new JButton("查询");

    public JPanel jp=new JPanel();
    public JButton saveB=new JButton("提交");
    public JButton cancelB = new JButton("重置");
    public JLabel jLabel = new JLabel();

    public JScrollPane sp = new JScrollPane();
    public JTable jTable;
    public RegularTableModel rTModel;

    private RegularGradePanel(){
        this.setLayout(new BorderLayout());
        cb_class = new JComboBox();
        cb_academy = new JComboBox();
        jpOption.add(label_class);
        jpOption.add(cb_class);
        jpOption.add(label_academy);
        jpOption.add(cb_academy);
        jpOption.add(searchB);
        this.add(jpOption,BorderLayout.NORTH);

        jp.add(saveB);
        jp.add(cancelB);
        jp.add(jLabel);
        this.add(jp,BorderLayout.SOUTH);
//        saveB.setPreferredSize(new Dimension(80,30));
//        cancelB.setPreferredSize(new Dimension(80,30));


        rTModel = new RegularTableModel();
        jTable = new JTable(rTModel);
        sp.setViewportView(jTable);
        this.add(sp,BorderLayout.CENTER);

    }
    static {
        instance = new RegularGradePanel();
    }
    public static void main(String[] args){

        GUIUtil.showPanel(instance,1.0D);
    }



}
