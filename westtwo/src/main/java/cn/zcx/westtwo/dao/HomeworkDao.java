package cn.zcx.westtwo.dao;

import cn.zcx.westtwo.pojo.Assess;
import cn.zcx.westtwo.pojo.Homework;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//实现作业信息在mysql数据库上的处理
@Repository
public class HomeworkDao
{

  ArrayList<Homework> homeworks;      //存放作业
  Connection connection;                //连接数据库

  //无参构造函数
  public HomeworkDao()
  {
    homeworks=new ArrayList<Homework>();
  }

  //有参构造函数
  public HomeworkDao(ArrayList<Homework> homeworks)
  {
    this.homeworks = homeworks;
  }

  //判断表是否存在
  public boolean tableJudgment(String tableName)
  {
    connectionOpen();     //连接数据库

    ResultSet resultSet=null;
    try
    {
      DatabaseMetaData metaData = connection.getMetaData();
      String[] type = {"TABLE"};
      resultSet = metaData.getTables("LITTLESTAR", null, tableName, type);
      if (resultSet.next())              //判断表是否已经存在
      {
        connectionClose();      //关闭连接
        ConnectionTool.close(resultSet);
        return true;
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    ConnectionTool.close(resultSet);
    connectionClose();      //关闭连接
    return false;
  }

  //创建作业表
  public void createTable(int id)
  {
    String tableName=id+"_homework";   //表名
    if(!tableJudgment(tableName))       //判断表是否存在
    {
      connectionOpen();       //连接数据库
      PreparedStatement preparedStatement = null;
      try
      {
        String sql = "create table " + tableName + "(考核编号 int,编号 int,学号 bigint,姓名 varchar(40)," +
            "审核状态 varchar(40),文件路径 varchar(200),文件名 varchar(40),提交时间 TIMESTAMP)";
        //执行语句
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
      }
      catch (SQLException e)        //异常处理
      {
        e.printStackTrace();
      }
      finally
      {
        ConnectionTool.close(preparedStatement);    //关闭连接
        connectionClose();
      }
    }
  }


  //将全部作业导入表中
  public void homeworksAllInsert(Assess assess)
  {
    String tableName=assess.getId()+"_homework";   //表名
    if(tableJudgment(tableName))       //判断表是否存在
    {
      for (int i = 0; i < homeworks.size(); i++)        //遍历作业列表
      {
        insert(homeworks.get(i), assess);             //插入作业数据
      }
    }
  }

  //向表中插入一条作业数据
  public boolean insert(Homework homework, Assess assess)
  {
    String tableName=assess.getId()+"_homework";   //表名
    if(tableJudgment(tableName))       //判断表是否存在
    {
      connectionOpen();     //连接数据库
      PreparedStatement preparedStatement = null;
      try
      {
        if (!homework.addAssessment(assess))
          return false;
        else
        {
          String flag;
          //将表示审核状态的int值转化为字符串
          flag = Homework.flags[homework.getFlag()];

          //sql语句
          String sql = "insert into " + tableName + "(考核编号,编号,学号,姓名,审核状态,文件路径,文件名,提交时间) values(?,?,?,?,?,?,?,?)";
          preparedStatement = connection.prepareStatement(sql);
          preparedStatement.setInt(1, assess.getId());
          preparedStatement.setInt(2, homework.getId());
          preparedStatement.setLong(3, homework.getNumber());
          preparedStatement.setString(4, homework.getName());
          preparedStatement.setString(5, flag);
          preparedStatement.setString(6, homework.getFilePath());
          preparedStatement.setString(7,homework.getFileName());
          preparedStatement.setTimestamp(8, new Timestamp(homework.getTime().getTime()));

          preparedStatement.executeUpdate();      //执行语句
        }
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
      finally
      {
        ConnectionTool.close(preparedStatement);    //关闭连接
        connectionClose();
        return true;
      }
    }
    return false;
  }

  //删除表中某条信息
  public void delete(int assId,int homId)
  {
    String tableName=assId+"_homework";   //表名
    if(tableJudgment(tableName))       //判断表是否存在
    {
      connectionOpen();       //连接数据库
      PreparedStatement preparedStatement = null;
      try
      {
        //sql语句
        String sql = "delete from " + tableName + " where 编号=?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,homId);

        preparedStatement.executeUpdate();    //执行语句
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
      finally
      {
        connectionClose();        //关闭连接
        ConnectionTool.close(preparedStatement);
      }
    }
  }


  //将表中所有数据导出
  public ArrayList<Homework> getAll(int assId)
  {
    homeworks.clear();        //清空Arraylist中的内容
    String tableName=assId+"_homework";   //表名
    if(tableJudgment(tableName))       //判断表是否存在
    {
      connectionOpen();       //连接数据库
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;
      try
      {
        //sql语句
        String sql = "select * from " + tableName;
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();   //执行语句，并将结果返回ResultSet中
        while (resultSet.next())   //遍历
        {
          int Flag = 0;
          int AssId = resultSet.getInt("考核编号");
          int id = resultSet.getInt("编号");
          long number = resultSet.getLong("学号");
          String name = resultSet.getString("姓名");
          String flag = resultSet.getString("审核状态");
          String filePath = resultSet.getString("文件路径");
          String fileName=resultSet.getString("文件名");
          for (int i = 0; i < Homework.flags.length; i++)
          {
            if (flag.equals(Homework.flags[i]))      //将字符串形式的审核状态转化为标记
              Flag = i;
          }
          Date date = new Date(resultSet.getTimestamp("提交时间").getTime());//将timestamp转化为date

          homeworks.add(new Homework(id, AssId, name, number, date, Flag, filePath,fileName));       //将每条作业信息都存入Arraylist中
        }
        ConnectionTool.close(resultSet);
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
      finally
      {
        connectionClose();    //关闭连接
        ConnectionTool.close(preparedStatement);
      }
    }
    return homeworks;
  }


//输出所有的作业信息
  public void printAll()
  {
    for(int i=0;i<homeworks.size();i++)
    {
      String flag;
      flag= Homework.flags[homeworks.get(i).getFlag()];
      System.out.print("考核编号:"+homeworks.get(i).getAssId()+"   "+"作业编号:"+homeworks.get(i).getId());
      System.out.print("  "+homeworks.get(i).getNumber() + "  "+homeworks.get(i).getName() + "  ");
      System.out.print(flag+" "+homeworks.get(i).getFilePath()+"   "+homeworks.get(i).getFileName()+"   ");
      SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      System.out.println(simpleDateFormat.format(homeworks.get(i).getTime()));
    }
  }




  //通过ID在列表中查找作业
  public Homework getHomeworkById(int id)
  {
    Homework homework=new Homework();
    for(int i=0;i<homeworks.size();i++)
    {
      if(id==homeworks.get(i).getId())
        homework=homeworks.get(i);
    }
    return homework;
  }


//关闭数据库连接
  public void connectionClose()
  {
    ConnectionTool.close(connection);
  }

//连接数据库
  public Connection connectionOpen()
  {
    connection= ConnectionTool.getConnection();
    return connection;
  }




  //get set函数

  public ArrayList<Homework> getHomeworks()
  {
    return homeworks;
  }

  public void setHomeworks(ArrayList<Homework> homeworks)
  {
    this.homeworks = homeworks;
  }

  public Connection getConnection()
  {
    return connection;
  }

  public void setConnection(Connection connection)
  {
    this.connection = connection;
  }
}

