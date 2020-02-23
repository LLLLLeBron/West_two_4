package cn.zcx.westtwo.dao;

import cn.zcx.westtwo.pojo.Assess;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


//实现考核信息在mysql数据库上的处理
@Repository
public class AssessDao
{

  ArrayList<Assess> assesses;      //存放考核
  Connection connection;                //连接数据库

  //无参构造函数
  public AssessDao()
  {
    assesses=new ArrayList<Assess>();
  }

  //有参构造函数
  public AssessDao(ArrayList<Assess> assesses)
  {
    this.assesses=assesses;
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

  //创建考核表
  public void createTable()
  {
    if(!tableJudgment("assess"))       //判断表是否存在
    {
      connectionOpen();       //连接数据库
      PreparedStatement preparedStatement = null;
      try
      {
        String sql = "create table assess(编号 int,考核名称 varchar(40),考核内容 longtext,截止时间 timestamp)";
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

  //将全部考核导入表中
  public void assessmentsAllInsert()
  {
    if(tableJudgment("assess"))       //判断表是否存在
    {
      for (int i = 0; i < assesses.size(); i++)        //遍历作业列表
      {
        insert(assesses.get(i));             //插入作业数据
      }
    }
  }

  //向表中插入一条考核数据
  public boolean insert(Assess assess)
  {
    if(tableJudgment("assess"))       //判断表是否存在
    {
      connectionOpen();   //连接数据库
      PreparedStatement preparedStatement = null;
      try
      {
        //sql语句
        String sql = "insert into assess(编号,考核名称,考核内容,截止时间) values(?,?,?,?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, assess.getId());
        preparedStatement.setString(2, assess.getAssName());
        preparedStatement.setString(3, assess.getContent());
        preparedStatement.setTimestamp(4, new Timestamp(assess.getDeadline().getTime()));

        preparedStatement.executeUpdate();      //执行语句


        //插入一条考核信息后，创建一个该考核信息下的作业表
        HomeworkDao homeworkDao = new HomeworkDao();
        homeworkDao.createTable(assess.getId());
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
      connectionClose();        //关闭连接
      ConnectionTool.close(preparedStatement);
      return true;
    }
    return false;
  }

  //修改表中某条信息
  public boolean update(Assess assess)
  {
    if(tableJudgment("assess"))       //判断表是否存在
    {
      connectionOpen();       //连接数据库
      PreparedStatement preparedStatement = null;
      try
      {
        //sql语句
        String sql = "update assess set 考核名称=?,考核内容=?,截止时间=? where 编号=?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, assess.getAssName());
        preparedStatement.setString(2,assess.getContent());
        preparedStatement.setTimestamp(3, new Timestamp(assess.getDeadline().getTime()));
        preparedStatement.setInt(4,assess.getId());
        preparedStatement.executeUpdate();    //执行语句

      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
      connectionClose();        //关闭连接
      ConnectionTool.close(preparedStatement);
      return true;
    }
    return false;
  }

  //删除表中某条信息
  public void delete(int id)
  {
    if(tableJudgment("assess"))       //判断表是否存在
    {
      connectionOpen();       //连接数据库
      PreparedStatement preparedStatement = null;
      try
      {
        //sql语句
        String sql = "delete from assess where 编号=?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        preparedStatement.executeUpdate();    //执行语句


        //删除完考核后并将该考核下的所有作业信息删除
        sql = "drop table " + id + "_homework";
        preparedStatement = connection.prepareStatement(sql);
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
  public ArrayList<Assess> getAll()
  {
    assesses.clear();          //清空Arraylist中的内容
    if(tableJudgment("assess"))       //判断表是否存在
    {
      connectionOpen();     //连接数据库
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;
      try
      {
        //sql语句
        String sql = "select * from assess";
        preparedStatement = connection.prepareStatement(sql);

        resultSet = preparedStatement.executeQuery(); //执行语句并将结果返回ResultSet
        while (resultSet.next())//遍历
        {
          int id = resultSet.getInt("编号");
          String assName = resultSet.getString("考核名称");
          String content = resultSet.getString("考核内容");
          Date date = new Date(resultSet.getTimestamp("截止时间").getTime());

          assesses.add(new Assess(id, assName, content, date));   //将每条考核信息都存入Arraylist中
        }
        ConnectionTool.close(resultSet);
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
      finally
      {
        connectionClose();      //关闭连接
        ConnectionTool.close(preparedStatement);
      }
    }
    return assesses;
  }



  //输出所有的考核信息
  public void printAll()
  {
    for(int i=0;i<assesses.size();i++)
    {
      System.out.print(assesses.get(i).getId()+"   ");
      System.out.print(assesses.get(i).getAssName()+ "  " +assesses.get(i).getContent()+ "  ");
      SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      System.out.println(simpleDateFormat.format(assesses.get(i).getDeadline()));
    }
  }

  //通过ID在列表中查找考核
  public Assess getAssessById(int id)
  {
    Assess assessment=null;
    for(int i=0;i<assesses.size();i++)
    {
      if(id==assesses.get(i).getId())
        assessment=assesses.get(i);
    }
    return assessment;
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
  public ArrayList<Assess> getAssesses()
  {
    return assesses;
  }

  public void setAssesses(ArrayList<Assess> assesses)
  {
    this.assesses = assesses;
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





