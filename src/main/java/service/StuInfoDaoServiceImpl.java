package service;

import dao.StuInfoDao;
import util.DBUtil;

import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;

public class StuInfoDaoServiceImpl implements StuInfoDaoService{
    @Override
    public boolean addOneStudent(StuInfoDao stuInfoDao) {
        String sql = "insert into stuinfo values(?,?,?)";
        int num = 0;
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
        ){
            pst.setString(1,stuInfoDao.getNumber());
            pst.setString(2,stuInfoDao.getName());
            pst.setString(3,stuInfoDao.getAcademy());
            num = pst.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("插入了"+num+"条：  "+stuInfoDao.toString());
        if(num == 0)return false;
        return true;
    }

    @Override
    public HashSet<String> getAllClass() {
        String sql = "select number from stuinfo ";
        HashSet<String> result = new HashSet<>();
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
        ){
            while(resultSet.next()){
                String str = resultSet.getString("number");
                char[] chars = Arrays.copyOf(str.toCharArray(),4);
                str = String.valueOf(chars);
                result.add(str);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public HashSet<String> getAllAcademy() {
        String sql = "select academy from stuinfo";
        HashSet<String> result = new HashSet<>();
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
        ){
            while(resultSet.next()){
                String str = resultSet.getString("academy");
                result.add(str);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public HashSet<StuInfoDao> getAllStuIfo(String className, String academyName) {
        String sql = "select * from stuinfo where  academy = ? and number like ?";
        if(academyName.equals("")){
            sql = "select * from stuinfo where  number like ?";
        }

        className = className+"%";
        HashSet<StuInfoDao> result = new HashSet<>();
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
        ){
            if(academyName.equals("")){
                pst.setString(1,className);
            }else{
                pst.setString(1,academyName);
                pst.setString(2,className);
            }

            ResultSet resultSet = pst.executeQuery();
            while(resultSet.next()){
                StuInfoDao tmp = new StuInfoDao(resultSet.getString("number"),resultSet.getString("name"),resultSet.getString("academy"));
                result.add(tmp);
            }
            resultSet.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args){
//        StuInfoDao stuInfoDao = new StuInfoDao("2017051711","he","计科");
//        new StuInfoDaoServiceImpl().addOneStudent(stuInfoDao);
        HashSet<String> re = new StuInfoDaoServiceImpl().getAllAcademy();
        re.stream().forEach(System.out::println);

        HashSet<StuInfoDao> sd = new StuInfoDaoServiceImpl().getAllStuIfo("","");
        sd.stream().forEach(System.out::println);
    }
}
