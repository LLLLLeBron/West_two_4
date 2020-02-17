package cn.zcx.westtwo.controller;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//登录拦截器

public class LoginHandlerInterceptor implements HandlerInterceptor
{
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception
  {

    //登录成功后应该有用户的session
    Object loginUser=request.getSession().getAttribute("loginUser");

    if(loginUser==null)   //未登录
    {
      request.setAttribute("msg","没有权限，请先登录");      //未登录，没有权限
      request.getRequestDispatcher("/").forward(request,response);
      return false;
    }
    else
    {
      return true;
    }
  }
}


