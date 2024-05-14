package com.example.demopj.aboutInterview.contextInitializer;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MyApplicationContextInitializer implements org.springframework.context.ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
//        在容器中注册一些环境属性
//        第一步：准备好我们要加入的属性
        Map<String, Object> myMap = new HashMap<>();
        myMap.put("applicationName", "demopj");
//        第二步：获取环境资源对象管理器
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();
//        第三步：完成注册
        propertySources.addLast(new MapPropertySource("myMap", myMap));
    }
}
