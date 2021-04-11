package service;

import dao.StuInfoDao;

import java.util.HashSet;

public interface StuInfoDaoService {
    //添加一个学生信息
    public boolean addOneStudent(StuInfoDao stuInfoDao);

    //获取年级数目
    public HashSet<String> getAllClass();
    //获取所有学院名称
    public HashSet<String> getAllAcademy();
    //查询某个年级某个学院所有学生信息
    public HashSet<StuInfoDao> getAllStuIfo(String className,String academyName);

}
