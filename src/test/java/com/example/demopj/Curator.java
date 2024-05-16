package com.example.demopj;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
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

//    ==========================================================create==================================================

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

//    ==========================================================get=====================================================
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

//    ==========================================================update==================================================
    /*
     * 修改节点
     * 1. 基本修改数据: setData().forPath(...)
     * 2. 根据版本修改: setData().withVersion(...).forPath(...)
     *      version是通过查询获得的，目的是为了让其他客户端或者线程不干扰
     */
    @Test
    public void testSet() throws Exception{
        client.setData().forPath("/app1", "lala".getBytes());
    }

    @Test
    public void testSetWithVesion() throws Exception{
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath("/app1");
        int version = stat.getVersion();
        client.setData().withVersion(version).forPath("/app1", "wen".getBytes());
    }

//    ==========================================================delete==================================================
    /*
     * 删除节点
     * 1. 删除单个节点: delete().forPath(...)
     * 2. 删除带有子节点的节点: delete().deletingchildrenIfNeeded().forPath(...);
     * 3. 必须成功的删除: 为了防止网络抖动。本质就是重试。 client.delete().guaranteed().forPath(...);
     * 4. 回调: inBackground
     */
    @Test
    public void delete1() throws Exception{
        client.delete().forPath("/app1");
    }

    @Test
    public void delete2() throws Exception{
        client.delete().deletingChildrenIfNeeded().forPath("/app4");
    }

    @Test
    public void delete3() throws Exception{
        client.delete().guaranteed().forPath("/app2");
    }

    @Test
    public void delet4() throws Exception{
        client.delete().inBackground(new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                System.out.println("成功删除");
                System.out.println(curatorEvent);
                System.out.println("===============分割线==================");
                System.out.println(curatorFramework);
            }
        }).forPath("/app1");
    }


    @AfterEach
    public void closeConnect(){
        if (client != null){
            client.close();
        }
    }

}
