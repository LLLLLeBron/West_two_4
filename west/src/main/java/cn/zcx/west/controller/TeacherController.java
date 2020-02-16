package cn.zcx.west.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TeacherController
{
  //启动教师端页面
  @RequestMapping("/teacher")
  String teacher()
  {
    return "teacher";
  }
}
