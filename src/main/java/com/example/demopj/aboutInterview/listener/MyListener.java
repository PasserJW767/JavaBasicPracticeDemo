package com.example.demopj.aboutInterview.listener;

import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class MyListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
//        event有很多种类型，ApplicationEvent对应发生的事件。如ApplicationReadyEvent，ApplicationFailedEvent
        if (event instanceof ApplicationReadyEvent){
//            容器初始化成功
            System.out.println("MyListener...容器初始化成功...");
        } else if (event instanceof ApplicationFailedEvent) {
//            容器初始化失败
            System.out.println("MyListener...容器初始化失败...");
        }
    }
}
