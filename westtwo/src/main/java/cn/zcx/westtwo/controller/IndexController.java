package cn.zcx.westtwo.controller;

import cn.zcx.westtwo.pojo.Assess;
import cn.zcx.westtwo.service.AssessService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class IndexController
{
  //启动首页
  @RequestMapping("/")
  String index(Model model)
  {
    model.addAttribute("assesses",AssessService.getAssesses());
    return "index";         //返回首页
  }
}
