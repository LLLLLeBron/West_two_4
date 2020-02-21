package cn.zcx.westtwo.controller;

import cn.zcx.westtwo.dao.FileTool;
import cn.zcx.westtwo.dao.HomeworkDao;
import cn.zcx.westtwo.pojo.Assess;
import cn.zcx.westtwo.pojo.Homework;
import cn.zcx.westtwo.service.AssessService;
import cn.zcx.westtwo.service.HomeworkService;
import cn.zcx.westtwo.service.ToolService;
import org.apache.commons.io.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import cn.zcx.westtwo.controller.AssessController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.tools.Tool;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class HomeworkController
{

  @RequestMapping("/upload")
  public String upload(@RequestParam("file") MultipartFile file, @RequestParam("id") int id, @RequestParam("number") long number,
                       @RequestParam("name") String name, HttpServletRequest request, Model model) throws Exception
  {
      String fileName = FileTool.fileNameCreate(file, number, id);    //获取文件名
      String filePath = FileTool.filePathCreate(request, "/upload");   //获取文件目录

      Homework homework = new Homework(name, number, filePath, fileName);
      //将作业信息导入数据库
      if(HomeworkService.insertHomework(homework, AssessService.getAssessById(id)))   //如果作业信息能导入数据库（在截止日期前提交）
      {
        FileTool.uploadFile(file.getBytes(), filePath, fileName);     //上传文件
        model.addAttribute("upload","上传成功!!!");
      }
      else
      {
        model.addAttribute("upload","截止时间已过，上传失败!!!");
      }
      model.addAttribute("assesses",AssessService.getAssesses());
      return "index";      //返回首页
  }


  //下载文件
  @RequestMapping("/download/{assId}/{homId}")
  public String download(HttpServletRequest request, HttpServletResponse response,
                         @PathVariable("assId") Integer assId, @PathVariable("homId") Integer homId) throws Exception
  {
    Homework homework=HomeworkService.getHomework(assId,homId);   //获取要下载的作业信息
    String filePath = homework.getFilePath();   //获取文件路径
    String fileName = homework.getFileName();    //获取文件名
    FileTool.downloadFile(response,filePath,fileName);    //下载文件
    return "redirect:/assess/{assId}";      //重定向至考核详情页
  }

  //删除作业信息
  @RequestMapping("/homeworkDelete/{assId}/{homId}")
  public String delete(@PathVariable("assId") Integer assId,@PathVariable("homId") Integer homId)
  {
    HomeworkService.deleteHomework(assId,homId);
    return "redirect:/assess/{assId}";    //重定向至考核详情页
  }


  //批量删除作业信息
  @PostMapping("/deleteAll/{assId}")
  public String deleteHomeworks(String checkList,@PathVariable("assId") int assId)
  {
    HomeworkService.deleteHomeworks(assId, ToolService.getIds(checkList));
    return "redirect:/assess/{assId}";  //重定向至考核详情页
  }



  //将作业标记为审核中
  @RequestMapping("/homeworkUnderView/{assId}/{homId}")
  public String UnderView(@PathVariable("assId") Integer assId,@PathVariable("homId") Integer homId)
  {
    HomeworkService.updateFlag(assId,homId,0);
    return "redirect:/assess/{assId}";    //重定向至考核详情页
  }

  //将作业标记为通过
  @RequestMapping("/homeworkPass/{assId}/{homId}")
  public String Pass(@PathVariable("assId") Integer assId,@PathVariable("homId") Integer homId)
  {
    HomeworkService.updateFlag(assId,homId,1);
    return "redirect:/assess/{assId}";    //重定向至考核详情页
  }


  //将作业标记为未通过
  @RequestMapping("/homeworkCull/{assId}/{homId}")
  public String Cull(@PathVariable("assId") Integer assId,@PathVariable("homId") Integer homId)
  {
    HomeworkService.updateFlag(assId,homId,2);
    return "redirect:/assess/{assId}";    //重定向至考核详情页
  }
}

