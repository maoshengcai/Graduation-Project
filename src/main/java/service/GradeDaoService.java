package service;

import dao.GradeDao;

public interface GradeDaoService {
    //通过学号、实验名、成绩将一条数据录入到grade表中
    public boolean addOneGrade(GradeDao gradeDao);
    //查询某一学生某一项目成绩是否录入
    public boolean checkOneGrade(String stu_number,String project_name);

    //查询某一学生某一项目成绩
    public int selectOneGrade(String stu_number,String project_name);

}
