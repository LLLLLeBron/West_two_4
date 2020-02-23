package cn.zcx.westtwo.service;


import cn.zcx.westtwo.dao.HomeworkDao;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;

//进行文件操作的工具类
public class FileService
{
  //根据提供的文件夹名称生成目录
  public static String filePathCreate(HttpServletRequest request,String name)
  {
    return request.getSession().getServletContext().getRealPath(name);    //返回生成的目录
  }

  //根据学号、作业号、提交时间生成文件名
  public static String fileNameCreate(MultipartFile file, long number, int id, Date date)
  {
    return number+"_"+id+"_"+"_"+date.getTime()+"_"+file.getOriginalFilename();   //返回生成的文件名
  }

  //上传文件,将文件写入指定路径
  public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception
  {
    File targetFile = new File(filePath);
    if(!targetFile.exists())      //若该文件不存在，则创建该目录
    {
      targetFile.mkdirs();      //创建该目录
    }
    FileOutputStream out = new FileOutputStream(filePath+fileName);     //写入的路径为给出的目录加上文件名
    out.write(file);
    out.flush();
    out.close();
  }


  //从指定路径处下载文件
  public static void downloadFile(HttpServletResponse response,String filePath, String fileName)  throws Exception
  {
    File file = new File(filePath+fileName);
    if (file.exists())
    {
      response.setContentType("application/force-download");// 设置强制下载不打开            
      response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
      byte[] buffer = new byte[1024];
      FileInputStream fileInputStream = null;
      BufferedInputStream bufferedInputStream = null;
      fileInputStream = new FileInputStream(file);
      bufferedInputStream = new BufferedInputStream(fileInputStream);
      OutputStream outputStream = response.getOutputStream();
      int i = bufferedInputStream.read(buffer);
      while (i != -1)
      {
        outputStream.write(buffer, 0, i);
        i = bufferedInputStream.read(buffer);
      }
      if (bufferedInputStream != null)
      {
        bufferedInputStream.close();  //关闭
      }
      if (fileInputStream!= null)
      {
        fileInputStream.close();    //关闭
      }
    }
    return;
  }
}
