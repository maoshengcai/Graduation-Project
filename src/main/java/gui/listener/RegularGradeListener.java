package gui.listener;

import dao.GradeDao;
import dao.StuInfoDao;
import gui.frame.MainFrame;
import gui.panel.MainPanel;
import gui.panel.RegularGradePanel;
import service.GradeDaoService;
import service.GradeDaoServiceImpl;
import service.StuInfoDaoService;
import service.StuInfoDaoServiceImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class RegularGradeListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        RegularGradePanel rgp = RegularGradePanel.instance;
        JButton jb = (JButton) e.getSource();
        if(jb == rgp.searchB){
            //获取所要查询的年级和学院信息
            String studentClass = (String) rgp.cb_class.getSelectedItem();
            String studentAcademy = (String) rgp.cb_academy.getSelectedItem();
            //查询某个年级某个学院的所有未提交平时成绩的学生信息
            StuInfoDaoService stuInfoDaoService = new StuInfoDaoServiceImpl();
            GradeDaoService gradeDaoService = new GradeDaoServiceImpl();
            HashSet<StuInfoDao> students = stuInfoDaoService.getAllStuIfo(studentClass,studentAcademy);
            Iterator<StuInfoDao> iterator = students.iterator();
            for(;iterator.hasNext();){
                StuInfoDao student = iterator.next();
                boolean flag = gradeDaoService.checkOneGrade(student.getNumber(),"RegularGrade");
                if(flag == true){
                    iterator.remove();
                }else{
                    rgp.rTModel.grades.add(null);
                }

            }
            //将信息显示到表中

            rgp.rTModel.data.clear();
            students.stream().forEach(rgp.rTModel.data::add);
            rgp.jTable.setModel(rgp.rTModel);
            rgp.sp.setViewportView(rgp.jTable);
            rgp.centerPanel.show(rgp.sp);
            if(students.isEmpty()){
                JOptionPane.showMessageDialog(rgp,"无学生平时成绩未录入！");
            }

        }
        if(jb == rgp.saveB){
            //将表中所有平时成绩数据提交到数据库中
            GradeDaoService gradeDaoService = new GradeDaoServiceImpl();
            boolean flag = true;
            int num = rgp.rTModel.getRowCount();
            GradeDao regularGrade = new GradeDao();
            for(int i = 0;i < num;i++){
                if(rgp.rTModel.grades.get(i) == null){
                    continue;
                }
                regularGrade.setProject_name("RegularGrade");
                regularGrade.setStudent_id(rgp.rTModel.data.get(i).getNumber());
                regularGrade.setGrade(rgp.rTModel.grades.get(i));
                rgp.rTModel.data.remove(i);
                rgp.rTModel.grades.remove(i);
                flag = gradeDaoService.addOneGrade(regularGrade);
                i--;
                num--;
            }

            //提示提交成功
            JOptionPane.showMessageDialog(rgp,"提交成功！");
            rgp.jTable.setModel(rgp.rTModel);
            rgp.sp.setViewportView(rgp.jTable);
            rgp.centerPanel.show(rgp.sp);
        }
        if(jb == rgp.cancelB){
            //重置所有已填平时成绩信息
            int num = rgp.rTModel.getRowCount();
            for(int i = 0;i < num;i++){
                rgp.rTModel.grades.set(i,null);
            }
            //提示重置成功
            JOptionPane.showMessageDialog(rgp,"重置成功！");
            rgp.jTable.setModel(rgp.rTModel);
            rgp.sp.setViewportView(rgp.jTable);
            rgp.centerPanel.show(rgp.sp);
        }
    }
}
