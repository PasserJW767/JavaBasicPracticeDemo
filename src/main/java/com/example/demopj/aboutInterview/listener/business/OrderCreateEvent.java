package com.example.demopj.aboutInterview.listener.business;

import org.springframework.context.ApplicationEvent;

public class OrderCreateEvent extends ApplicationEvent {

    private String orderInfo; // 表示订单信息

    public OrderCreateEvent(Object source, String orderInfo) {
        super(source);
        this.orderInfo = orderInfo;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }
}
