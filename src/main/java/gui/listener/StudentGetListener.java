package gui.listener;


import dao.StuInfoDao;
import gui.panel.StudentGetPanel;
import service.StuInfoDaoService;
import service.StuInfoDaoServiceImpl;
import util.ExcelUtil;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class StudentGetListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        StudentGetPanel sgp = StudentGetPanel.instance;
        JButton jb = (JButton) e.getSource();
        if(jb == sgp.jb_path){
            //选择excel文件路径，并显示在面板中
            int returnval = sgp.fc.showOpenDialog(sgp);
            File file = sgp.fc.getSelectedFile();
            if(returnval == JFileChooser.APPROVE_OPTION){
                String filename = file.getAbsolutePath();
                sgp.label_file.setText(filename);
            }

        }

        if(jb == sgp.jb_get){
            //获取文件路径，用该文件更新表格
            String filename = sgp.label_file.getText();
            sgp.jTable = ExcelUtil.excelToTable(new File(filename));
            boolean column1 = sgp.jTable.getColumnName(0).equals("学院");
            boolean column2 = sgp.jTable.getColumnName(1).equals("学号");
            boolean column3 = sgp.jTable.getColumnName(2).equals("姓名");
            if(column1 == false || column2==false || column3==false){
                JOptionPane.showMessageDialog(sgp,"导入失败！excel表格前三列应依次是学院、学号和姓名");
                sgp.jTable = new JTable();
            }
            sgp.jsp.setViewportView(sgp.jTable);
            sgp.centerPanel.show(sgp.jsp);
            JOptionPane.showMessageDialog(sgp,"导入成功！");
        }

        if(jb == sgp.jb_in) {
            //将表格内容逐条加入数据库
            TableModel model = sgp.jTable.getModel();
            String[] columnNames = {model.getColumnName(0),model.getColumnName(1),model.getColumnName(2)};
            ArrayList<String[]> datas = new ArrayList<>();
            StuInfoDaoService stuInfoDaoService = new StuInfoDaoServiceImpl();
            for(int i = 0;i<model.getRowCount();i++){
                String[] strs = new String[3];
                String academy = String.valueOf(model.getValueAt(i,0));
                String number = String.valueOf(model.getValueAt(i,1));
                String name = String.valueOf(model.getValueAt(i,2));
                strs[0] = academy;
                strs[1] = number;
                strs[2] = name;
                if(academy.equals("")||number.equals("")||name.equals("")){
                    datas.add(strs);
                    continue;
                }
                StuInfoDao student = new StuInfoDao(number,name,academy);
                boolean flag = stuInfoDaoService.addOneStudent(student);
                if(flag == false){
                    String str = "学号为"+number+"学生成绩录入出错！";
                    datas.add(strs);
                    JOptionPane.showMessageDialog(sgp,str);
                }
            }
            JOptionPane.showMessageDialog(sgp,"录入结束！");
            sgp.jsp.setViewportView(new JTable(datas.toArray(new String[0][0]),columnNames));
            sgp.centerPanel.show(sgp.jsp);
        }

        if(jb == sgp.jb_cancel){
            //重置表格内容
            sgp.jsp.setViewportView(new JTable());
            sgp.centerPanel.show(sgp.jsp);
        }
    }
}
