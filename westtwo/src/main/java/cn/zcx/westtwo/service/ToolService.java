package cn.zcx.westtwo.service;


import java.util.ArrayList;

//实现一些其他业务
public class ToolService
{
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
}
