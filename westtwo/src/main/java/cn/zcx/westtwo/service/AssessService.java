package cn.zcx.westtwo.service;


import cn.zcx.westtwo.dao.AssessDao;
import cn.zcx.westtwo.dao.HomeworkDao;
import cn.zcx.westtwo.pojo.Assess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//实现考核信息相关的业务
public class AssessService
{
  //从数据库中获取全部考核信息
  public static ArrayList<Assess> getAssesses()
  {
    AssessDao assessDao=new AssessDao();
    ArrayList<Assess> assesses=assessDao.getAll();        //导出考核列表
    return assesses;
  }

  //从数据库中获取指定ID的考核信息
  public static Assess getAssessById(int id)
  {
    AssessDao assessDao=new AssessDao();
    assessDao.getAll();       //导出考核列表
    return assessDao.getAssessById(id);   //返回对应ID的考核信息
  }

  //向数据库中插入一条考核信息
  public static boolean insertAssess(Assess assess)
  {
    AssessDao assessDao=new AssessDao();
    assessDao.createTable();
    return assessDao.insert(assess);      //将该信息插入数据库
  }

  //修改数据库中的指定信息
  public static boolean updateAssess(Assess assess)
  {
    AssessDao assessDao=new AssessDao();
    return assessDao.update(assess);        //更新信息
  }

  //根据ID删除指定信息
  public static void deleteAssess(int id)
  {
    AssessDao assessDao=new AssessDao();
    assessDao.delete(id);         //删除考核信息
  }

  //通过ID查询考核信息是否存在
  public static boolean findAssess(int id)
  {
    AssessDao assessDao=new AssessDao();
    assessDao.getAll();
    if(assessDao.getAssessById(id)==null)
      return false;
    else
      return true;
  }


  //获取该考核下作业信息的数量
  public static int homeworksNumber(int assId)
  {
    HomeworkDao homeworkDao=new HomeworkDao();
    homeworkDao.getAll(assId);        //获取该考核下的所有作业信息
    return homeworkDao.getHomeworks().size();     //返回作业信息的数量
  }


}
