package cn.zcx.west.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReleaseController
{
  @RequestMapping("/release")
  String release()
  {
    return "release";
  }
}
