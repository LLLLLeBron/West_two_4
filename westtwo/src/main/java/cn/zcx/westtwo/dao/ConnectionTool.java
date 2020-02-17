package cn.zcx.westtwo.dao;


//与数据库进行连接的工具类

import java.sql.*;

public class ConnectionTool
{
  private static String URL = "jdbc:mysql://127.0.0.1:3306/littlestar?useSSL=false&useUnicode=true&characterEncoding=UTF8&serverTimezone=GMT";
  private static String USER = "LittleStar";
  private static String PASSWORD = "321123klklkl";
  private static String DRIVER = "com.mysql.cj.jdbc.Driver";
  public ConnectionTool()
  {
  }

  public static Connection getConnection()
  {
    Connection connection = null;
    try
    {
      Class.forName(DRIVER);
      connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }
    catch (ClassNotFoundException cnfe)
    {
      cnfe.printStackTrace();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return connection;
  }

  public static void close(PreparedStatement preparedStatement)
  {
    if (preparedStatement != null)
    {
      try
      {
        preparedStatement.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }

  }

  public static void close(Connection connection)
  {
    if (connection != null)
    {
      try
      {
        connection.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
  }

  public static void close(ResultSet resultSet)
  {
    if (resultSet != null)
    {
      try
      {
        resultSet.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
  }
}

