package cn.zcx.westtwo.dao;

import cn.zcx.westtwo.pojo.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;


//实现账号信息在mysql数据库上的处理
@Repository
public class UserDao
{
  ArrayList<User> users;                //存放账号
  Connection connection;                //连接数据库


  //无参构造函数
  public UserDao()
  {
    users=new ArrayList<User>();
  }

  //有参构造函数
  public UserDao(ArrayList<User> users)
  {
    this.users = users;
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


  //创建账号表
  public void createTable()
  {
    if(!tableJudgment("user"))       //判断表是否存在
    {
      connectionOpen();     //连接数据库
      PreparedStatement preparedStatement = null;
      try
      {
        String sql = "create table user(用户名 varchar(40),密码 varchar(40))";
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

  //将全部账号导入表中
  public void usersAllInsert()
  {
    if(tableJudgment("user"))       //判断表是否存在
    {
      for (int i = 0; i < users.size(); i++)        //遍历作业列表
      {
        insert(users.get(i));             //插入作业数据
      }
    }
  }

  //判断该用户名是否存在
  public boolean isExist(String username)
  {
    if (tableJudgment("user"))        //判断表是否存在
    {
      connectionOpen();     //连接数据库
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;
      try
      {
        //sql语句
        String sql = "select * from user where 用户名=?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,username);

        resultSet = preparedStatement.executeQuery(); //执行语句并将结果返回ResultSet
        if (resultSet.next())       //判断数据库中是否有和该用户名相同的账号信息
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
      return false;
    }
    else
    {
      System.out.println(3333333);
      return false;
    }
  }

  //向表中插入一条账号数据
  public boolean insert(User user)
  {
    if(tableJudgment("user"))       //判断表是否存在
    {
      connectionOpen();     //连接数据库
      PreparedStatement preparedStatement = null;
      try
      {
        //sql语句
        String sql = "insert into user(用户名,密码) values(?,?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());

        preparedStatement.executeUpdate();      //执行语句

      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
      connectionClose();      //关闭连接
      ConnectionTool.close(preparedStatement);
      return true;
    }
    return false;
  }


  //将表中所有数据导出
  public ArrayList<User> getAll()
  {
    users.clear();          //清空Arraylist中的内容
    if (tableJudgment("user"))        //判断表是否存在
    {
      connectionOpen();     //连接数据库
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;
      try
      {
        //sql语句
        String sql = "select * from user";
        preparedStatement = connection.prepareStatement(sql);

        resultSet = preparedStatement.executeQuery(); //执行语句并将结果返回ResultSet
        while (resultSet.next())//遍历
        {
          String username = resultSet.getString("用户名");
          String password = resultSet.getString("密码");

          users.add(new User(username, password));   //将每条考核信息都存入Arraylist中
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
    return users;
  }



  //输出所有的账号信息
  public void printAll()
  {
    for(int i=0;i<users.size();i++)
    {
      System.out.println("用户名:"+users.get(i).getUsername()+"  密码:"+users.get(i).getPassword());
    }
  }


  //关闭数据库连接
  public void connectionClose()
  {
    ConnectionTool.close(connection);
  }

  //连接数据库
  public Connection connectionOpen()
  {
    connection=ConnectionTool.getConnection();
    return connection;
  }



  //get set函数
  public ArrayList<User> getUsers()
  {
    return users;
  }

  public void setUsers(ArrayList<User> users)
  {
    this.users = users;
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
