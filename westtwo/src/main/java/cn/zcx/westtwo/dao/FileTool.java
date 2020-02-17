package cn.zcx.westtwo.dao;


import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

//进行文件操作的工具类
public class FileTool
{
  //根据提供的文件夹名称生成目录
  public static String filePathCreate(HttpServletRequest request,String name)
  {
    return request.getSession().getServletContext().getRealPath(name);
  }

  //根据学号和作业号生成文件名
  public static String fileNameCreate(MultipartFile file,long number, int id)
  {
    return number+"_"+id+"_"+file.getOriginalFilename();
  }

  //上传文件,将文件写入指定路径
  public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception
  {
    File targetFile = new File(filePath);
    if(!targetFile.exists())      //若该文件不存在，则创建该目录
    {
      targetFile.mkdirs();
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
        bufferedInputStream.close();
      }
      if (fileInputStream!= null)
      {
        fileInputStream.close();
      }
    }
  }



}
