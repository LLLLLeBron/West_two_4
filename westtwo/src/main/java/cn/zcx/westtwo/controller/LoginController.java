package cn.zcx.westtwo.controller;

import cn.zcx.westtwo.pojo.User;
import cn.zcx.westtwo.service.AssessService;
import cn.zcx.westtwo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController
{
  //登录控制器
  @RequestMapping("/login")
  public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession session)
  {
    User user=new User(username,password);
    if(UserService.login(user))
    {
      session.setAttribute("loginUser",username);
      return "redirect:/main";      //重定向至后台主页
    }
    else
    {
      model.addAttribute("msg","用户名或者密码错误!!!");

      model.addAttribute("assesses", AssessService.getAssesses());
      return "index";        //重定向至首页
    }
  }

  //注销控制器
  @RequestMapping("/logout")
  public String logout(HttpSession session)
  {
    session.invalidate();
    return "redirect:/";       //注销成功，返回首页
  }

  @RequestMapping("/enrol")
  public String enrol(@RequestParam("username1") String username1,@RequestParam("password1") String password1,
                      @RequestParam("password2") String password2,Model model)
  {
    model.addAttribute("enrol",UserService.enrol(username1,password1,password2));       //向前台发送注册结果

    model.addAttribute("assesses", AssessService.getAssesses());
    return "index";     //返回首页
  }

}
