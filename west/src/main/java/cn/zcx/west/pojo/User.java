package cn.zcx.west.pojo;



import lombok.Data;

@Data
//账号类
public class User
{
  private String username;
  private String password;



//无参构造函数
  public User()
  {
  }

  //有参构造函数
  public User(String username, String password)
  {
    this.username = username;
    this.password = password;
  }



//判断账号密码是否正确
  public boolean equals(User user)
  {
    return this.username==user.getUsername()&&this.password==user.getPassword();
  }



//get set函数
  public String getUsername()
  {
    return username;
  }

  public void setUsername(String username)
  {
    this.username = username;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }
}
