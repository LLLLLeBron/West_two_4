package cn.zcx.westtwo.controller;

import cn.zcx.westtwo.dao.HomeworkDao;
import cn.zcx.westtwo.service.FileService;
import cn.zcx.westtwo.pojo.Homework;
import cn.zcx.westtwo.service.AssessService;
import cn.zcx.westtwo.service.HomeworkService;
import cn.zcx.westtwo.service.ToolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;


//实现作业信息相关的controller
@Controller
public class HomeworkController
{

  /*上传文件
  url:/upload
  通过前台提供的id号确定要提交考核信息，将前台输入的作业信息存入数据库，并将文件上传至对应路径
  返回首页
   */
  @RequestMapping("/upload")
  public String upload(@RequestParam("file") MultipartFile file, @RequestParam("id") int id, @RequestParam("number") long number,
                       @RequestParam("name") String name, HttpServletRequest request, Model model) throws Exception
  {
      String fileName = FileService.fileNameCreate(file, number, id,new Date());    //获取文件名
      String filePath = FileService.filePathCreate(request, "/upload");   //获取文件目录


      Homework homework = new Homework(name, number, filePath, fileName);
      //将作业信息导入数据库
      if(HomeworkService.insertHomework(homework, AssessService.getAssessById(id)))   //如果作业信息能导入数据库（在截止日期前提交）
      {
        FileService.uploadFile(file.getBytes(), filePath, fileName);     //上传文件
        model.addAttribute("upload","上传成功!!!");         //向前台发出上传成功信息
      }
      else
      {
        model.addAttribute("upload","截止时间已过，上传失败!!!");      //向前台发出错误信息
      }
      model.addAttribute("assesses",AssessService.getAssesses());
      return "index";      //返回首页
  }


  /*下载文件
    url:/download/assId/homId assId,homId为前台提供的参数
    通过assId和homId确定要下载的作业文件
   */
  @RequestMapping("/download/{assId}/{homId}")
  public String download(HttpServletRequest request, HttpServletResponse response,
                         @PathVariable("assId") Integer assId, @PathVariable("homId") Integer homId) throws Exception
  {
    Homework homework=HomeworkService.getHomework(assId,homId);   //获取要下载的作业信息
    String filePath = homework.getFilePath();   //获取文件路径
    String fileName = homework.getFileName();    //获取文件名
    FileService.downloadFile(response,filePath,fileName);    //下载文件
    return null;
  }

  /*删除作业信息
    url:/homeworkDelete/assId/homId assId,homId为前台提供的参数
    通过assId和homId确定要删除的作业信息
   */
  @RequestMapping("/homeworkDelete/{assId}/{homId}")
  public String delete(@PathVariable("assId") Integer assId,@PathVariable("homId") Integer homId)
  {
    HomeworkService.deleteHomework(assId,homId);      //删除该作业信息
    return "redirect:/assess/{assId}";    //重定向至考核详情页
  }


  /*批量删除作业信息
    url:/deleteAll/assId assId为前台提供的参数
    通过前台传的assId列表字符串，将该字符串解析后批量删除所有的作业
    重定向至考核详情页
   */
  @PostMapping("/deleteAll/{assId}")
  public String deleteHomeworks(String checkList,@PathVariable("assId") int assId)
  {
    HomeworkService.deleteHomeworks(assId, ToolService.getIds(checkList));    //批量删除全部的作业信息
    return "redirect:/assess/{assId}";  //重定向至考核详情页
  }

  /*批量下载作业信息
    url:/downloadAll/assId assId为前台提供的参数
    通过前台传的assId列表字符串，将该字符串解析后批量下载所有的作业
   */
  @PostMapping("/downloadAll/{assId}")
  public String downloadHomeworks(HttpServletRequest request, HttpServletResponse response,
                                  String checkList,@PathVariable("assId") int assId)  throws Exception
  {
    HomeworkService.downloadHomeworks(response,assId, ToolService.getIds(checkList));   //批量下载全部的作业信息
    return null;
  }


  /*将作业标记为通过
      url:/homeworkPass/assId/homId assId,homId为前台提供的参数
      通过assId和homId确定要修改状态的作业信息
      重定向至考核详情页
   */
  @RequestMapping("/homeworkPass/{assId}/{homId}")
  public String Pass(@PathVariable("assId") Integer assId,@PathVariable("homId") Integer homId)
  {
    HomeworkService.updateFlag(assId,homId,1);    //将该作业状态修改为通过
    return "redirect:/assess/{assId}";    //重定向至考核详情页
  }


  /*将作业标记为未通过
      url:/homeworkCull/assId/homId assId,homId为前台提供的参数
      通过assId和homId确定要修改状态的作业信息
      重定向至考核详情页
   */
  @RequestMapping("/homeworkCull/{assId}/{homId}")
  public String Cull(@PathVariable("assId") Integer assId,@PathVariable("homId") Integer homId)
  {
    HomeworkService.updateFlag(assId,homId,2);    //将该作业状态修改为未通过
    return "redirect:/assess/{assId}";    //重定向至考核详情页
  }
}

