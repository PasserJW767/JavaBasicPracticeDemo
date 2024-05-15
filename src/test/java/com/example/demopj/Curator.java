package com.example.demopj;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Curator {

    private CuratorFramework client;

    @BeforeEach
    public void buildCConnect(){

        // 重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(300, 1);

        // 方法1：newClient
//        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("103.152.132.148:2181", 60000, 15000, retryPolicy);
//        curatorFramework.start();

        // 方法2：builder
        client = CuratorFrameworkFactory.builder()
                .connectString("103.152.132.148:2181")
                .sessionTimeoutMs(60000)
                .connectionTimeoutMs(150)
                .namespace("itheima")
                .retryPolicy(retryPolicy).build();
        client.start();
    }

    /*
     * 创建节点 create 持久 临时 顺序 数据
     * 1. 基本创建: .forPath(...)
     * 2. 创建节点 带有数据: .forPath(..., str.getBytes())
     * 3. 设置节点类型: withMode(CreateMode.xxx).forPath(...)
     * 4. 创建多级节点 /app1/p1: creatingParentContainersIfNeeded().forPath(...)
     */
    @Test
    public void testCreate1() throws Exception {
        // 1. 基本创建，若不指定具体数据，则默认将ip地址作为数据
        String path = client.create().forPath("/app1");
        System.out.println(path);
    }

    @Test
    public void testCreate2() throws Exception{
        // 2. 创建带有数据的节点
        String path = client.create().forPath("/app2", "haha".getBytes());
        System.out.println(path);
    }

    @Test
    public void testCreate3() throws Exception{
        // 3. 设置节点类型
        String path = client.create().withMode(CreateMode.EPHEMERAL).forPath("/app3");
        System.out.println(path);
    }

    @Test
    public void testCreate4() throws Exception{
        // 4. 创建多级节点
        String path = client.create().creatingParentContainersIfNeeded().forPath("/app4/p1");
        System.out.println(path);
    }


    /*
     * 查询节点 get: getData().forPath()
     * 1. 查询数据: get(): getData().forPath(..)
     * 2. 查询子节点: ls: getChildren.forPath(...)
     * 3. 查询节点状态信息: ls -s: getData().storingStatIn(状态对象Stat).forPath(...)
     */
    @Test
    public void testGet1() throws Exception {
        // 1. 获取节点数据
        byte[] bytes = client.getData().forPath("/app1");
        System.out.println(new String(bytes));
    }

    @Test
    public void testGet2() throws Exception{
        // 2. 获取子节点
        List<String> strings = client.getChildren().forPath("/app4");
        System.out.println(strings);
    }

    @Test
    public void testGet3() throws Exception{
        // 3. 获取状态信息
        Stat stat = new Stat();
        byte[] bytes = client.getData().storingStatIn(stat).forPath("/");
        System.out.println(stat);
        System.out.println(new String(bytes));
    }

    @AfterEach
    public void closeConnect(){
        if (client != null){
            client.close();
        }
    }

}
