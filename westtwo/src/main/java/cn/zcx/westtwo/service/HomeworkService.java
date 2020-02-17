package cn.zcx.westtwo.service;

import cn.zcx.westtwo.dao.HomeworkDao;
import cn.zcx.westtwo.pojo.Assess;
import cn.zcx.westtwo.pojo.Homework;

import java.util.ArrayList;

//实现作业信息相关的业务
public class HomeworkService
{
  //通过考核ID获取该考核下的所有作业信息
  public static ArrayList<Homework> getHomeworks(int id)
  {
    HomeworkDao homeworkDao=new HomeworkDao();
    homeworkDao.getAll(id);
    return homeworkDao.getHomeworks();
  }

  //通过作业ID获取作业信息
  public static Homework getHomework(int assId,int homId)
  {
    HomeworkDao homeworkDao=new HomeworkDao();
    homeworkDao.getAll(assId);        //先获取作业列表
    return homeworkDao.getHomeworkById(homId);    //根据作业ID获取作业信息
  }

  //把作业信息存入指定考核表下
  public static boolean insertHomework(Homework homework, Assess assess)
  {
    HomeworkDao homeworkDao=new HomeworkDao();
    return homeworkDao.insert(homework,assess);
  }

  //删除作业信息
  public static void deleteHomework(int assId,int homId)
  {
    HomeworkDao homeworkDao=new HomeworkDao();
    homeworkDao.delete(assId,homId);
  }
}
