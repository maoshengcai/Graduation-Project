package gui.listener;

import gui.panel.RegularGradePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegularGradeListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        RegularGradePanel rgp = RegularGradePanel.instance;
        JButton jb = (JButton) e.getSource();
        if(jb == rgp.searchB){
            //获取所要查询的年级和学院信息

            //查询某个年级某个学院的所有为提交平时成绩的学生信息

            //将信息显示到表中

        }
        if(jb == rgp.saveB){
            //将表中所有平时成绩数据提交到数据库中

            //提示提交成功

        }
        if(jb == rgp.cancelB){
            //重置所有已填平时成绩信息

            //提示重置成功

        }
    }
}
