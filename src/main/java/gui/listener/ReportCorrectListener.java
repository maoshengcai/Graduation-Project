package gui.listener;

import gui.panel.ReportCorrectPanel;
import service.ReportService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportCorrectListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        ReportCorrectPanel rcl = ReportCorrectPanel.instance;
        JButton jb = (JButton) e.getSource();
        rcl.warningL.setText("");
        if(jb == rcl.submit){
            boolean flag = true;
            //获得实验名和对应学号的成绩，提交数据库

            //提交成功，更改文档，将该文件路径加入已批改报告列表中
            //设置提示，重新显示报告
            if(flag){
                int grade = Integer.parseInt(rcl.jTextGrade.getText());
                new ReportService().setGradeInPdf(rcl.currentFilePath,grade);
                rcl.setImageShow(rcl.currentFilePath);
                rcl.warningL.setText("更改成功！");
            }

        }

        if(jb == rcl.nextReport){
            //判断是否还有未批改报告

            //有则取出第一个，重新渲染面板

            //无则提示该目录中报告已批改完毕

        }

    }
}
