package cn.zcx.westtwo.service;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//实现一些其他业务
public class ToolService
{
  //通过日期字符串生成date
  public static Date setDate(String date)
  {
    Date newDate=new Date();
    try
    {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");     //设置时间格式为 "yyyy-MM-dd HH:mm:ss"
      newDate= format.parse(date+":00");     //生成时间
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      return newDate;  //返回生成的时间
    }
  }

  //将ID列表字符串变为ID列表
  public static ArrayList<Integer> getIds(String Ids)
  {
    ArrayList<Integer> ids=new ArrayList<>();
    String[] strs = Ids.split(",");
    for(String str:strs)
    {
      ids.add(Integer.parseInt(str));
    }
    return ids;
  }
 /*
  //判断一个日期字符串是否合法
  public static boolean is_right(String date)
  {

  }
  */
}
