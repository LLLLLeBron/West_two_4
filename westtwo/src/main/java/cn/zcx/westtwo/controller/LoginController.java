package cn.zcx.westtwo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController
{
  //登录控制器
  @RequestMapping("/login")
  public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession session)
  {
    /*
    User user=new User(username,password);
    UserDao userDao = new UserDao();
    ArrayList<User> users = new ArrayList<>();
    users = userDao.getAll();         //将数据库user表中所有的账号信息取出
    for (int i = 0; i < users.size(); i++)
    {
      if(user.equals(users.get(i)))     //判断输入的账号是否能够登录
      {
        session.setAttribute("loginUser",username);     //登录成功
        return "2.html";      //登陆成功，页面跳转
      }
    }
    model.addAttribute("msg","用户名或密码错误!!!");    //登录失败，生成错误信息
    return "index";     //登陆失败，返回首页
     */

    if(!StringUtils.isEmpty(username)&&"123456".equals(password))     //密码为123456即可登录成功
    {
      session.setAttribute("loginUser",username);
      return "BackEnd";      //重定向
    }
    else
    {
      model.addAttribute("msg","用户名或者密码错误!!!");
      return "index";
    }
  }

  //注销控制器
  @RequestMapping("/logout")
  public String logout(HttpSession session)
  {
    session.invalidate();
    return "index";       //注销成功，返回首页
  }
}
