package cn.zcx.westtwo.controller;

import cn.zcx.westtwo.dao.AssessDao;
import cn.zcx.westtwo.dao.HomeworkDao;
import cn.zcx.westtwo.pojo.Assess;
import cn.zcx.westtwo.pojo.Homework;
import cn.zcx.westtwo.service.AssessService;
import cn.zcx.westtwo.service.HomeworkService;
import cn.zcx.westtwo.service.ToolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
//实现考核信息相关的controller
@Controller
public class AssessController
{
  /*考核列表显示
      url:/assessList
      通过AssessService.getAssesses()方法从数据库中获取全部考核信息，传给前台
      返回考核列表展示页面
   */
  @RequestMapping("/assessList")
  String assessList(Model model)
  {
    model.addAttribute("assesses", AssessService.getAssesses());        //将考核信息列表传给前台*/
    return "list";        //返回考核列表展示页面
  }

  /*进入考核发布页面
      url:/assessAdd
      返回考核发布页面
   */
  @GetMapping("/assessAdd")
  String assessAdd()
  {
    return "add";      //返回考核发布页面
  }

  /*发布考核信息
    url:/assessAdd
    从前台接收输入的考核信息相关变量，构造新的考核并插入数据库中
    重定向至考核列表展示
   */
  @PostMapping("/assessAdd")
  String add(@RequestParam("assName") String assName, @RequestParam("content") String content,
             @RequestParam("date") String date,@RequestParam("time") String time, Model model)
  {
    //调用ToolService中的方法讲前端传的date和time构造成日期，并用这个日期构造考核信息
    Assess assess=new Assess(assName,content, ToolService.setDate(date+" "+time));
    AssessService.insertAssess(assess);   //向数据库中插入考核信息
    return "redirect:/assessList";      //重定向至考核列表展示
  }

  /*进入考核详情页
      url:/assess/id id为前端传给的参数
      将考核详情页所需要展示的参数全部传给前台
      返回考核详情页
   */
  @RequestMapping("/assess/{id}")
  String assess(@PathVariable("id") int id,Model model)
  {
    model.addAttribute("assess", AssessService.getAssessById(id));   //将该id的考核信息传给前台
    model.addAttribute("number",AssessService.homeworksNumber(id));   //将该考核下的已提交作业数传给前台

    //将审核状态字符串数组传给前台
    model.addAttribute("status0", Homework.flags[0]);
    model.addAttribute("status1", Homework.flags[1]);
    model.addAttribute("status2", Homework.flags[2]);

    model.addAttribute("homeworks", HomeworkService.getHomeworks(id));      //将该考核下的所有作业信息传给前台
    return "assess";        //返回考核详情页
  }


  /*进入考核修改页面
    url:/assessEdit/id id为前端传给的参数
    将需要修改的考核信息传给前台
    返回考核修改页面
   */
  @GetMapping("/assessEdit/{id}")
  String editPage(@PathVariable("id") int id,Model model)
  {
    model.addAttribute("assess",AssessService.getAssessById(id)); //将需要修改的考核信息传给前台
    return "edit";      //返回考核修改页面
  }


  /*修改考核信息
    url:/assessEdit/id   id为前端传给的参数
    通过前端传给的id确定需要修改的考核,用从前端输入的信息更新考核信息
    重定向至考核详情页
   */
  @PostMapping("/assessEdit/{id}")
  String edit(@PathVariable("id") int id,@RequestParam("assName") String assName, @RequestParam("content") String content,
              @RequestParam("date") String date,@RequestParam("time") String time, Model model)
  {
    //调用ToolService中的方法讲前端传的date和time构造成日期，并用这个日期构造考核信息
    Assess assess=new Assess(id,assName,content,ToolService.setDate(date+" "+time));
    AssessService.updateAssess(assess);     //在数据库中更新该考核信息
    return "redirect:/assess/{id}";    //重定向至考核详情页
  }

  /*删除考核
    url；/assessDelete/id    id为前端传给的参数
    通过前端传给的id确定需要删除的考核，删除对应考核
    重定向至考核列表展示
   */
  @GetMapping("/assessDelete/{id}")
  String delete(@PathVariable("id") int id)
  {
    AssessService.deleteAssess(id);        //删除考核信息
    return "redirect:/assessList";      //重定向至考核列表展示
  }
}
