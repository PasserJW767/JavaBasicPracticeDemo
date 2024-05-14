package com.example.demopj.aboutInterview.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPosterProcessor implements BeanPostProcessor {
//    初始化前前执行
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyBeanPosterProcessor...postProcessBeforeInitialization..." + beanName);
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

//    初始化后执行
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyBeanPosterProcessor...postProcessAfterInitialization..." + beanName);
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
