package com.example.demopj;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest // 在测试类上加上该注解，在单元测试执行前会先启动Spring容器
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testSet(){
//        在Redis中存储一个键值对
//        .opsForValue()是Spring Data Redis 提供的一个操作字符串类型数据的工具方法
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();

        stringStringValueOperations.set("username", "zhangsan");
        stringStringValueOperations.set("id", "1", 15, TimeUnit.SECONDS);

    }

    @Test
    public void testGet(){
    //        在Redis中获取一个键值对
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();

        System.out.println(stringStringValueOperations.get("username"));

    }

}
