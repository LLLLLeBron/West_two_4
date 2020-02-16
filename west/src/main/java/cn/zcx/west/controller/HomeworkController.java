package cn.zcx.west.controller;

import cn.zcx.west.dao.FileTool;
import cn.zcx.west.dao.HomeworkDao;
import cn.zcx.west.pojo.Assess;
import cn.zcx.west.pojo.Homework;
import cn.zcx.west.service.AssessService;
import cn.zcx.west.service.HomeworkService;
import org.apache.commons.io.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;

@Controller
public class HomeworkController
{

  @RequestMapping("/upload")
  public String upload(@RequestParam("file") MultipartFile file, @RequestParam("id") int id, @RequestParam("number") long number,
                       @RequestParam("name") String name, HttpServletRequest request) throws Exception
  {
    String fileName = FileTool.fileNameCreate(file, number, id);    //获取文件名
    String filePath = FileTool.filePathCreate(request, "/upload");   //获取文件目录

    Homework homework = new Homework(name, number, filePath, fileName);
    HomeworkService.insertHomework(homework, AssessService.getAssessById(id)); //将作业信息导入数据库

    FileTool.uploadFile(file.getBytes(), filePath, fileName);
    return "redirect:/";      //重定向至首页
  }


  //下载文件
  @RequestMapping("/download")
  public String download(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam("assId") int assId,@RequestParam("homId") int homId) throws Exception
  {
    Homework homework=HomeworkService.getHomework(assId,homId);   //获取要下载的作业信息
    String filePath = homework.getFilePath();   //获取文件路径
    String fileName = homework.getFileName();    //获取文件名
    FileTool.downloadFile(response,filePath,fileName);    //下载文件
    return "";      //返回下载成功页面
  }

  //删除作业信息
  @RequestMapping("/homeworkDelete")
  public String delete(@RequestParam("assId") int assId,@RequestParam("homId") int homId)
  {
    HomeworkService.deleteHomework(assId,homId);
    return "redirect:/assess/{assId}";    //重定向至考核详情页
  }

}

