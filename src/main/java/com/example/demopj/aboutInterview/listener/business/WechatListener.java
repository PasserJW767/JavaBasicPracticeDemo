package com.example.demopj.aboutInterview.listener.business;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class WechatListener implements ApplicationListener<OrderCreateEvent> {
    @Override
    public void onApplicationEvent(OrderCreateEvent event) {
        System.out.println("监听到了订单创建事件，发送微信通知..." + event.getOrderInfo());
    }
}
