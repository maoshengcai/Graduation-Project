package gui.panel;

import gui.listener.RegularGradeListener;
import util.CenterPanel;
import util.GUIUtil;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;

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
    public CenterPanel centerPanel = new CenterPanel(1.0D);

    public void addListener(){
        RegularGradeListener rgl = new RegularGradeListener();
        this.searchB.addActionListener(rgl);
        this.saveB.addActionListener(rgl);
        this.cancelB.addActionListener(rgl);
    }

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
        jTable.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
//                System.out.println("lost focus");
//                System.out.println(String.valueOf(e.getSource()));
//
//                jTable.getCellEditor().stopCellEditing();
            }
        });
        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {


                if(instance.jTable.getEditingRow() != -1){
                    System.out.println("mouseClick  close editor:"+instance.jTable.getCellEditor().stopCellEditing());


                }
                super.mouseClicked(e);

            }
        });
        jTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println("in listener");
                int row = e.getLastIndex();
                int column = 3;
                int row_first = e.getFirstIndex();
                int row_real = jTable.getSelectedRow();
                if(row_real != row){
                    row_first = row;
                    row = row_real;
                }
                System.out.println("row = "+row +"  "+"row_first:"+row_first);
                String str = String.valueOf(jTable.getValueAt(row_first,3));
                if(!str.equals("null")){
                    int num = Integer.valueOf(str);
                    System.out.println("num ="+String.valueOf(str));
                    if(num >100 || num < 0){
                        JOptionPane.showMessageDialog(instance,"平时成绩不能小于0或大于100！");
                        jTable.editCellAt(row_first,3);
                        jTable.changeSelection(row_first,3,false,false);
                        return;
                    }
                }
                System.out.println("in editor");
                jTable.editCellAt(row,3);
            }
        });
        sp.setViewportView(jTable);
        this.add(centerPanel,BorderLayout.CENTER);
        centerPanel.show(sp);
        addListener();

    }
    static {
        instance = new RegularGradePanel();
    }
    public static void main(String[] args){

        GUIUtil.showPanel(instance,1.0D);
    }



}
