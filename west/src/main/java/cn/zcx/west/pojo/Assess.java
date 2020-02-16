package cn.zcx.west.pojo;


import lombok.Data;

import java.util.Date;


@Data
//考核类
public class Assess
{
    private static int assessId=10000;          //考核的id，从10000开始

    private int id;             //考核的编号，每个考核都有唯一且不重复的编号

    private String assName;     //考核名称
    private String content;     //考核内容
    private Date deadline;      //截止时间

//无参构造函数
    public Assess()
    {
        this.id=assessId++;
    }

//有参构造函数
    public Assess(int id, String assName, String content, Date deadline)
    {
        this.id=id;
        this.assName = assName;
        this.content = content;
        this.deadline = deadline;
    }


    //生成考核
    public Assess(String assName, String content, Date deadline)
    {
        this.id=assessId++;

        this.assName=assName;
        this.content=content;
        this.deadline=deadline;
    }


//get set函数
    public int getId()
    {
        return id;
    }

    public String getAssName()
    {
        return assName;
    }

    public void setAssName(String assName)
    {
        this.assName = assName;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Date getDeadline()
    {
        return deadline;
    }

    public void setDeadline(Date deadline)
    {
        this.deadline = deadline;
    }
}
