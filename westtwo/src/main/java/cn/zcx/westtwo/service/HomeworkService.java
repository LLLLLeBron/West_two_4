package cn.zcx.westtwo.service;

import cn.zcx.westtwo.dao.HomeworkDao;
import cn.zcx.westtwo.pojo.Assess;
import cn.zcx.westtwo.pojo.Homework;

import javax.servlet.http.HttpServletResponse;
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

  //批量删除作业信息
  public static void deleteHomeworks(int assId,ArrayList<Integer> homIds)
  {
    for(int i=0;i<homIds.size();i++)
      deleteHomework(assId,homIds.get(i));      //删除ID列表中所有对应的作业信息
  }

  //修改作业的审核状态
  public static boolean updateFlag(int assId,int homId,int flag)
  {
    HomeworkDao homeworkDao=new HomeworkDao();
    homeworkDao.getAll(assId);
    Homework homework=homeworkDao.getHomeworkById(homId);
    homework.setFlag(flag);
    return homeworkDao.update(assId,homework);
  }

  //批量下载作业信息
  public static void downloadHomeworks(HttpServletResponse response,int assId,ArrayList<Integer> homIds) throws Exception
  {
    HomeworkDao homeworkDao=new HomeworkDao();
    homeworkDao.getAll(assId);
    for(int i=0;i<homIds.size();i++)
    {
      String fileName=homeworkDao.getHomeworkById(homIds.get(i)).getFileName();
      String filePath=homeworkDao.getHomeworkById(homIds.get(i)).getFilePath();
      FileService.downloadFile(response,filePath,fileName);
    }
    return;
  }

}
