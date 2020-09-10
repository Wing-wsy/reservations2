package com.annet.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类：注册拦截器，过滤器，监听器
 * @author ：xy
 * @date ：Created in 2019/6/21 9:26
 * @description：
 * @modified By：
 * @version: $
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 配置拦截器
     * @param registry 参数
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器
        // 跨域拦截器需放在最上面
        //registry.addInterceptor(new CorsInterceptor()).addPathPatterns("/**");
        /*registry.addInterceptor(new Interceptor())
                //拦截所有请求
                .addPathPatterns("/**")
                //对应的不拦截的请求
                .excludePathPatterns("/","/tbUser/login","/tbDepttable/finAll","/bespoke","/tbBespoke/YJReservation");*/
    }
}
