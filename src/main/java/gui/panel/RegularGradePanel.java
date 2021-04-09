package gui.panel;

import util.CenterPanel;
import util.GUIUtil;

import javax.swing.*;
import java.awt.*;

public class RegularGradePanel extends JPanel {
    public static RegularGradePanel instance = new RegularGradePanel();
    JPanel jp=new JPanel();
    public JButton saveB=new JButton("保存");
    public JButton cancelB = new JButton("取消");
    public JLabel jLabel = new JLabel();

    public JScrollPane sp = new JScrollPane();
    public JTable jTable;
    public RegularTableModel rTModel;

    private RegularGradePanel(){
        this.setLayout(new BorderLayout());

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
    public static void main(String[] args){

        GUIUtil.showPanel(instance,1.0D);
    }



}
