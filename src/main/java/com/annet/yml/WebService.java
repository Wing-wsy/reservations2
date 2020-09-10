package com.annet.yml;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 加载yaml配置文件的方法
 * spring-boot更新到1.5.2版本后locations属性无法使用
 * @PropertySource注解只可以加载proprties文件,无法加载yaml文件
 *故现在把数据放到application.yml文件中,spring-boot启动时会加载
 * @author
 */
@Component
@ConfigurationProperties(prefix = "webservice")
public class WebService {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
