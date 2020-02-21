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
    registry.addViewController("/main").setViewName("BackEnd");
  }

  //登录拦截器
  @Override
  public void addInterceptors(InterceptorRegistry registry)
  {
    registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/","/login","/upload","/css/*","/js/**","/img/**");
  }
}
