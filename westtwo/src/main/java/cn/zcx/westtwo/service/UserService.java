package cn.zcx.westtwo.service;


import cn.zcx.westtwo.dao.UserDao;
import cn.zcx.westtwo.pojo.User;

import java.util.ArrayList;

//实现账号相关业务
public class UserService
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

  //注册账号
  public static String enrol(String username,String password1,String password2)
  {
    UserDao userDao=new UserDao();
    if(!password1.equals(password2))
      return "两次密码输入不一致!!!";
    else if(password1.length()>16||password1.length()<6)
      return "密码长度为6~16位!!!";
    else if(userDao.isExist(username))
    {
      return "该用户名已存在!!!";
    }
    else
    {
      userDao.insert(new User(username,password1));
      return "注册成功!!!";
    }
  }
}
