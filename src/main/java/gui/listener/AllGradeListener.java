package gui.listener;

import dao.GradeDao;
import dao.StuInfoDao;
import gui.panel.AllGradePanel;
import service.GradeDaoService;
import service.GradeDaoServiceImpl;
import util.ExcelUtil;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class AllGradeListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        AllGradePanel agp = AllGradePanel.instance;
        JButton jb = (JButton) e.getSource();
        if(jb == agp.calculateB){
            //从界面获取各个项目成绩占比
            LinkedList<Double> relate = new LinkedList<>();
            Set<JLabel>  keySet =  agp.inputG.keySet();
            Iterator<JLabel> iterator = keySet.iterator();
            Double result = 0D;
            for(;iterator.hasNext();){
                JLabel label = iterator.next();
                JTextField text = agp.inputG.get(label);
                if(text.getText().equals("")){
                    JOptionPane.showMessageDialog(agp,"占比不能为空！");
                    return;
                }
                Double num = Double.valueOf(text.getText());
                DecimalFormat df = new DecimalFormat("0.000");
                result =Double.valueOf(df.format(result+num));
                relate.add(num);
            }
            System.out.println("显示获取占比,result="+result);
            relate.stream().forEach(System.out::println);
            //判断占比和是否为1
            //1，计算并显示出结果到界面上
            //不为1，提示出错，返回
            if(result != 1){
                JOptionPane.showMessageDialog(agp,"占比之和不等于1！");
                return;
            }else{
                System.out.println("in 1");
                for(int i = 0;i< agp.aTModel.grades.size();i++){
                    System.out.println("in 2");
                    double tmp = 0D;
                    Integer[] tmp_int = agp.aTModel.grades.get(i);
                    for(int j = 0;j<tmp_int.length-1;j++){
                        System.out.println("int 3");

                        tmp = tmp + tmp_int[j] * relate.get(j);
                    }
                    int tmp1 = (int)tmp;
                    if(tmp*10%10>=5){
                        tmp1+=1;
                    }
                    agp.aTModel.grades.get(i)[tmp_int.length-1] = tmp1;
                    System.out.println("tmp = "+tmp+"  grades="+agp.aTModel.grades.get(i)[tmp_int.length-1] );
                }
                agp.jTable.setModel(agp.aTModel);
                agp.sp.setViewportView(agp.jTable);
                agp.centerPanel.show(agp.sp);
            }


        }

        if(jb == agp.fcB){
            //进入面板的文件选择
            //获取选择的文件夹，并将路径显示在面板中
            int returnval = agp.file_ch.showOpenDialog(agp);
            File file = agp.file_ch.getSelectedFile();
            if(returnval == JFileChooser.APPROVE_OPTION){
                String str = file.getAbsolutePath()+"\\grade.xls";
                agp.text_out.setText(str);
            }


        }

        if(jb == agp.saveB){
            //获取选择文件夹路径，路径不存在则提示并返回
            String path = agp.text_out.getText();
            if(path.equals("")){
                JOptionPane.showMessageDialog(agp,"该文件不能为空!");
                return;
            }
            File file = new File(path);
            if(file.exists()){
                JOptionPane.showMessageDialog(agp,"该文件已存在!");
                return;
            }
            //获取面板学号、项目名称、总成绩，逐条将数据录入数据库
            GradeDao gradeDao = new GradeDao();
            GradeDaoService gradeDaoService = new GradeDaoServiceImpl();
            boolean flag = true;
            System.out.println("输出grades：");
            agp.aTModel.grades.stream().forEach(a->System.out.println(a[6]));
            for(int i = 0;i < agp.aTModel.getRowCount();i++){
                gradeDao.setGrade(agp.aTModel.grades.get(i)[6]);
                StuInfoDao stu = agp.aTModel.data.get(i);
                gradeDao.setStudent_id(stu.getNumber());
                gradeDao.setProject_name("AllGrade");
                if(!gradeDaoService.addOneGrade(gradeDao)){
                    flag = false;
                }
            }
            System.out.println("录入成功 ： "+flag);
            JOptionPane.showMessageDialog(agp,"保存成功！");
            //将面板中数据导出为excel表格并保存到所选路径中

            try {
                ExcelUtil.exportTable(agp.jTable,file);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            JOptionPane.showMessageDialog(agp,"导出成功！");
            System.out.println("成功！！！！");
        }

    }
}
