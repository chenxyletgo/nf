package vip.self.cliservice.other.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import vip.self.cliservice.other.Interceptor.LoginInterceptor;

@Configuration
public class MyWebConfig implements WebMvcConfigurer {

    /**
     * 配置登录权限拦截
     * @param registry 注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());
        registration.addPathPatterns("/cliservice/*/log/**"); // 拦截路径
    }
}
