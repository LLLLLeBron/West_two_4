package cn.zcx.westtwo.service;


import cn.zcx.westtwo.dao.UserDao;
import cn.zcx.westtwo.pojo.User;

import java.util.ArrayList;

//实现登录业务
public class LoginService
{
  //登录验证
  public static boolean login(User user)
  {
    UserDao userDao=new UserDao();
    ArrayList<User> users=userDao.getAll();
    for(int i=0;i<users.size();i++)
    {
      if(user.equals(users.get(i)))     //如果在数据库中找到对应的账号密码
        return true;        //登录验证成功
    }
    return false;     //否则登录验证失败
  }
}
