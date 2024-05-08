package com.example.demopj.aboutInterview.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;

public class Student implements BeanNameAware, BeanClassLoaderAware, BeanFactoryAware {
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("Student...setBeanClassLoader");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("Student...setBeanFactory");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("Student...setBeanName");
    }
}
