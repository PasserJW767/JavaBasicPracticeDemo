package com.example.demopj.aboutInterview.listener.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void createOrder(){
//        1. 创建订单
        System.out.println("创建订单，生成订单信息，保存订单...");

//        2. 发布事件
        OrderCreateEvent orderCreateEvent = new OrderCreateEvent(this, "orderNo: 1566798");
//        applicationContext.publishEvent(orderCreateEvent);
        applicationEventPublisher.publishEvent(orderCreateEvent);
    }

}
