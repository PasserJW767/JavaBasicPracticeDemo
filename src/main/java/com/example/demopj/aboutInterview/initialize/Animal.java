package com.example.demopj.aboutInterview.initialize;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class Animal implements InitializingBean, DisposableBean {
    @Override
    public void destroy() throws Exception {
        System.out.println("Animal...destroy...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Animal...afterPropertiesSet...");
    }
}
