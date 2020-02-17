package cn.zcx.westtwo.controller;

import cn.zcx.westtwo.dao.AssessDao;
import cn.zcx.westtwo.pojo.Assess;
import cn.zcx.westtwo.service.AssessService;
import cn.zcx.westtwo.service.HomeworkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

@Controller
public class AssessController
{
  //考核列表显示
  @RequestMapping("/assessList")
  String assessList(Model model)
  {
    ArrayList<Assess> assesses=new ArrayList<>();
    assesses=AssessService.getAssesses();
    model.addAttribute("assesses", assesses);        //将考核信息列表传给前台*/

    return "list";        //返回考核列表展示页面
  }

  //进入考核发布页面
  @GetMapping("/assessAdd")
  String assessAdd()
  {
    return "add";      //返回考核发布页面
  }

  //发布考核信息
  @PostMapping("/assessAdd")
  String add(@RequestParam("assName") String assName, @RequestParam("content")
      String content, Model model)
  {


    Assess assess=new Assess(assName,content,new Date(new Date().getTime()+1000000000));
    AssessService.insertAssess(assess);
    return "redirect:/assessList";      //重定向至考核列表展示
  }

  //进入考核详情页
  @GetMapping("/assess/{id}")
  String assess(@PathVariable("id") int id,Model model)
  {
    model.addAttribute("assess", AssessService.getAssessById(id));   //将该id的考核信息传给前台

    model.addAttribute("homeworks", HomeworkService.getHomeworks(id));      //将该考核下的所有作业信息传给前端
    return "assess";        //返回考核详情页
  }

  //进入信息修改页面
  @GetMapping("/assessEdit/{id}")
  String assessEdit(@PathVariable("id") int id,Model model)
  {
    model.addAttribute("assess", AssessService.getAssessById(id));   //将该条考核信息传给前台
    return "edit";          //返回考核信息修改页面
  }

  //修改考核信息
  @PostMapping("/assessEdit/{id}")
  String edit(@PathVariable("id") int id,@RequestParam("assName") String assName,
              @RequestParam("content") String content)
  {
    Assess assess=new Assess(id, assName, content, new Date(new Date().getTime()+1000000000));       //修改后的考核信息

    AssessService.updateAssess(assess);       //更新信息
    return "redirect:/assess/{id}";    //重定向至考核详情页
  }

  //删除考核
  @GetMapping("/assessDelete/{id}")
  String delete(@PathVariable("id") int id)
  {
    AssessService.deleteAssess(id);        //删除考核信息
    return "redirect:/assessList";      //重定向至考核列表展示
  }
}
