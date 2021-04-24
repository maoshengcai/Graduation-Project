package gui.listener;

import dao.GradeDao;
import gui.panel.ReportCorrectPanel;
import service.GradeDaoService;
import service.GradeDaoServiceImpl;
import service.ReportService;
import service.Sources;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class ReportCorrectListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        ReportCorrectPanel rcl = ReportCorrectPanel.instance;
        JButton jb = (JButton) e.getSource();
        rcl.warningL.setText("");

        if(jb == rcl.submit){
            SwingWorker<Void,Void> worker= new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {

                    boolean flag = true;
                    LinkedList<GradeDao> gradeDao_test= new LinkedList<>();
                    //获得实验名和对应学号的成绩，提交数据库
                    GradeDaoService gradeDaoService=new GradeDaoServiceImpl();
                    ArrayList<GradeDao> grades = rcl.getGrades();
                    if(grades == null){
                        return null;
                    }
                    Iterator<GradeDao> iterator = grades.iterator();
                    for(;iterator.hasNext();){
                        GradeDao gradeDao = iterator.next();
                        if(!gradeDaoService.addOneGrade(gradeDao)){
                            flag = false;
                            gradeDao_test.add(gradeDao);
                        }
                    }
                    //提交成功，更改文档，将该文件路径加入已批改报告列表中
                    //设置提示，重新显示报告
                    if(flag){
                        rcl.reportList.remove(rcl.currentFilePath);
                        Sources.nowCorrected.add(rcl.currentFilePath);
                        Sources.correctedReport.add(rcl.currentFilePath);

                        int grade = Integer.parseInt(rcl.jTextGrade.getText());
                        new ReportService().setGradeInPdf(rcl.currentFilePath,grade);
                        rcl.setImageShow(rcl.currentFilePath);
                        rcl.warningL.setText("提交成功！");
                    }else{
                        String warnStr = "提交失败！有学生该实验成绩已经存在";
                        rcl.warningL.setText(warnStr);
                    }
                    return null;
                }
            };
            worker.execute();


        }

        if(jb == rcl.nextReport){
            //判断是否还有未批改报告
            if(!rcl.reportList.isEmpty()){
                //有则取出第一个，重新渲染面板
                String filName = rcl.reportList.get(0);
                rcl.setImageShow(filName);
            }else{
                //无则提示该目录中报告已批改完毕
                JOptionPane.showMessageDialog(rcl,"该目录中所有报告均已批改！");
            }
        }

    }
}
