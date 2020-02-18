package cn.zcx.westtwo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BackEndController
{
  @RequestMapping("/BackEnd")
  String BackEnd()
  {
    return "BackEnd";
  }
}
