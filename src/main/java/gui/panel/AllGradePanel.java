package gui.panel;

import ch.qos.logback.core.joran.event.BodyEvent;
import dao.StuInfoDao;
import gui.listener.AllGradeListener;
import service.GradeDaoService;
import service.GradeDaoServiceImpl;
import service.StuInfoDaoService;
import service.StuInfoDaoServiceImpl;
import util.CenterPanel;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.util.*;

public class AllGradePanel extends JPanel {
    public static AllGradePanel instance ;

    public JPanel jp=new JPanel();
    public JLabel warningL = new JLabel("各项目成绩占比");
    public JPanel jp_input = new JPanel();
    public Map<JLabel,JTextField> inputG = new LinkedHashMap<>();
    public JButton calculateB=new JButton("计算");

    public JPanel jp_out = new JPanel();
    public JLabel label_out = new JLabel("导出路径： ");
    public JTextField text_out = new JTextField();
    public JFileChooser file_ch = new JFileChooser();
    public JButton fcB = new JButton("...");
    public JButton saveB = new JButton("保存并导出");

    public CenterPanel centerPanel = new CenterPanel(1.0D);
    public JScrollPane sp = new JScrollPane();
    public JTable jTable;
    public AllGradeTableModel aTModel=new AllGradeTableModel();

    public void addListener(){
        AllGradeListener listener = new AllGradeListener();
        this.calculateB.addActionListener(listener);
        this.fcB.addActionListener(listener);
        this.saveB.addActionListener(listener);
    }
    private AllGradePanel(){
        this.setLayout(new BorderLayout());

        this.jp.setLayout(new BorderLayout());
        String[] strs = {"RegularGrade","experiment01","experiment02","experiment03","experiment04","experiment05"};
        jp_input.setLayout(new GridLayout(24,2));
        for(int i = 0;i < strs.length;i++){
            JLabel aLabel = new JLabel(strs[i]);
            JTextField jText = new JTextField();
            inputG.put(aLabel,jText);
            jp_input.add(aLabel);
            jp_input.add(jText);
        }
        jp.add(warningL,BorderLayout.NORTH);
        jp.add(jp_input,BorderLayout.CENTER);
        jp.add(calculateB,BorderLayout.SOUTH);
        this.add(jp,BorderLayout.WEST);

        file_ch.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if(f.isDirectory()){
                    return  true;
                }
                return false;
            }

            @Override
            public String getDescription() {
                return null;
            }
        });
        file_ch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        file_ch.setDialogTitle("选取文件目录");
        jp_out.add(label_out);
        jp_out.add(text_out);
        jp_out.add(fcB);
        jp_out.add(saveB);
        this.add(jp_out, BorderLayout.SOUTH);
        text_out.setPreferredSize(new Dimension(500,40));

        StuInfoDaoService stuInfoDaoService = new StuInfoDaoServiceImpl();
        HashSet<StuInfoDao> stuInfos = stuInfoDaoService.getAllStuIfo("","");
        stuInfos.stream().forEach(System.out::println);
//        this.aTModel.data.addAll(stuInfos);
        Iterator<StuInfoDao> iterator = stuInfos.iterator();
        for(;iterator.hasNext();){
            StuInfoDao s = iterator.next();
            this.aTModel.data.add(s);
        }
        GradeDaoService gradeDaoService = new GradeDaoServiceImpl();
        //逐条获取学生某项目成绩
        StuInfoDao student = new StuInfoDao();
        String project_name = "";
        for(int i = 0;i < aTModel.data.size();i++){
            student = aTModel.data.get(i);
            Integer[] arr_grade = new Integer[7];
            for(int j = 0;j < 6;j++){
                project_name = aTModel.columnNames[3+j];
                arr_grade[j] = gradeDaoService.selectOneGrade(student.getNumber(),project_name);
                if(arr_grade[j] == -1)arr_grade[j] = 0;
            }
            arr_grade[6] = -1;
            aTModel.grades.add(arr_grade);
        }
        jTable = new JTable(aTModel);
        sp.setViewportView(jTable);
        this.add(centerPanel,BorderLayout.CENTER);
        centerPanel.show(sp);

        addListener();

    }
    static {
        instance = new AllGradePanel();
    }
}
