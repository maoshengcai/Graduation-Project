package service;

import dao.GradeDao;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GradeDaoServiceImpl implements GradeDaoService{
    @Override
    public boolean addOneGrade(GradeDao gradeDao) {
        String sql = "insert into grade values(?,?,?)";
        String sql2 = "select id from project where name=?";
        int update_number = 0;
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            PreparedStatement pst2 = conn.prepareStatement(sql2);
        ){
            pst2.setString(1,gradeDao.getProject_name());
            ResultSet resultSet = pst2.executeQuery();
            if(resultSet.next()){
                gradeDao.setProject_id(resultSet.getInt(1));
            }
            pst.setString(1,gradeDao.getStudent_id());
            pst.setInt(2,gradeDao.getProject_id());
            pst.setInt(3,gradeDao.getGrade());
            update_number = pst.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println("更新了"+update_number);
        System.out.println(gradeDao.toString());
        if(update_number == 0){
            return false;
        }
        return true;
    }

    @Override
    public boolean checkOneGrade(String stu_number,String  project_name) {
        String sql= "select * from grade g,project p where g.student_id = ? and p.name = ? and g.project_id = p.id";
        Boolean flag = false;
        ResultSet resultSet;
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
        ){
            pst.setString(1,stu_number);
            pst.setString(2,project_name);
            resultSet = pst.executeQuery();
            if(resultSet.next()){
                flag = true;
            }
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flag;
    }


    public static void main(String[] args){
//        GradeDao gradeDao = new GradeDao("experiment01","2017051712",100);
//        new GradeDaoServiceImpl().addOneGrade(gradeDao);
        boolean flag = new GradeDaoServiceImpl().checkOneGrade("2017051712","experiment01");
        System.out.println(flag);
    }
}
