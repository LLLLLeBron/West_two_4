package cn.zcx.westtwo.config;

//配置文件

//import cn.zcx.west.controller.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfig implements WebMvcConfigurer
{

  //视图跳转
  @Override
  public void addViewControllers(ViewControllerRegistry registry)
  {
    registry.addViewController("/main").setViewName("BackEnd");   // /main返回BackEnd页面
  }

  //登录拦截器
  @Override
  public void addInterceptors(InterceptorRegistry registry)
  {
    //未登录的情况下只允许/login /enrol /upload的访问
    registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/","/login","/enrol","/upload","/css/*","/js/**","/img/**");
  }
}
