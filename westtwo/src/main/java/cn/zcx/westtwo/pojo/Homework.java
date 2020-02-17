package cn.zcx.westtwo.pojo;

import lombok.Data;

import java.util.Date;


@Data
//作业类
public class Homework
{
  private static int homeworkId=10000;      //作业的id，从10000开始

  private int id;         //作业编号，每个作业都有唯一且不重复的编号

  private int assId;     //所属考核的编号
  private String name;    //姓名
  private long number;  //学号
  private Date time;      //提交时间
  private int flag;       //标记
  private String filePath;    //文件路径
  private String fileName;    //文件名


  public  static final String[] flags={"审核中","通过","未通过"};      //三种审核状态 0：审核中 1：通过 2：未通过


//构造函数
  public Homework(int id,int assId,String name,long number, Date time, int flag,String filePath,String fileName)
  {
    this.id=id;
    this.assId=assId;
    this.name = name;
    this.number = number;
    this.time = time;
    this.flag = flag;
    this.filePath=filePath;
    this.fileName=fileName;
  }

//按照系统时间生成作业
  public Homework(String name, long number,String filePath,String fileName)
  {
    this.id=homeworkId++;

    this.assId=0;
    this.name = name;
    this.number = number;
    this.filePath=filePath;
    this.fileName=fileName;
    this.time = new Date();    //作业提交时间为系统时间
    this.flag = 0;              //标记为审核中
  }

//无参构造函数
  public Homework()
  {
    this.id=homeworkId++;
  }


//将作业提交至某个考核下
  public boolean addAssessment(Assess assessment)
  {
    if(time.getTime()<=assessment.getDeadline().getTime())    //判断提交是否超时
    {
        this.assId=assessment.getId();   //未超时返回true
        return true;
    }
    else                //超时返回false
      return false;
  }



  //get 函数
  public int getId() {
    return id;
  }

  public int getAssId() {
    return assId;
  }

  public String getName() {
    return name;
  }

  public long getNumber() {
    return number;
  }

  public Date getTime() {
    return time;
  }

  public int getFlag() {
    return flag;
  }

  public String getFilePath() {
    return filePath;
  }

  public String getFileName() {
    return fileName;
  }
}
