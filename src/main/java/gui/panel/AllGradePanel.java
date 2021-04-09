package gui.panel;

import javax.swing.*;

public class AllGradePanel extends JPanel {
    public static AllGradePanel instance = new AllGradePanel();
    JPanel jp=new JPanel();
    public JButton saveB=new JButton("保存");
    public JButton cancelB = new JButton("取消");
    public JLabel jLabel = new JLabel();

    public JScrollPane sp = new JScrollPane();
    public JTable jTable;
    public RegularTableModel rTModel;
}
