package gui.listener;

import gui.panel.AllGradePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AllGradeListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        AllGradePanel agp = AllGradePanel.instance;
        JButton jb = (JButton) e.getSource();
        if(jb == agp.calculateB){
            //从界面获取各个项目成绩占比

            //判断占比和是否为1

            //1，计算并显示出结果到界面上

            //不为1，提示出错，返回

        }

        if(jb == agp.fcB){
            //进入面板的文件选择

            //获取选择的文件夹，并将路径显示在面板中

        }

        if(jb == agp.saveB){
            //获取选择文件夹路径，路径不存在则提示并返回

            //获取面板学号、项目名称、总成绩，逐条将数据录入数据库

            //将面板中数据导出为excel表格并保存到所选路径中

        }

    }
}
