package com.example.demopj;

import com.example.demopj.aboutInterview.listener.business.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ListenerBusinessTest {

    @Autowired
    OrderService orderService;

    @Test
    public void orderTest(){
        orderService.createOrder();
    }

}
