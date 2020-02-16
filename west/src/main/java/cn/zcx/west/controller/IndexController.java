package cn.zcx.west.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController
{
  //启动首页
  @RequestMapping("/index")
  String index()
  {
    return "index";         //返回首页
  }
}
