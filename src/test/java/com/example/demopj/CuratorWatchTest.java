package com.example.demopj;

import io.lettuce.core.ScriptOutputType;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent.Type.CHILD_UPDATED;

public class CuratorWatchTest {

    private CuratorFramework client;

    @BeforeEach
    public void buildCConnect(){

        // 重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(300, 1);

        client = CuratorFrameworkFactory.builder()
                .connectString("103.152.132.148:2181")
                .sessionTimeoutMs(60000)
                .connectionTimeoutMs(150)
                .namespace("itheima")
                .retryPolicy(retryPolicy).build();
        client.start();
    }

    @Test
    public void nodeCache() throws Exception {
        // 1. 创建NodeCache对象
        NodeCache nodeCache = new NodeCache(client, "/app1");
        // 2. 注册监听
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.print("节点变化，当前数据为：");
                byte[] data = nodeCache.getCurrentData().getData();
                System.out.println(new String(data));
            }
        });
        // 3. 开启监听，如果设置为true，则开启监听池，加载缓冲数据
        nodeCache.start(true);
        System.out.println(new String(nodeCache.getCurrentData().getData()));

        while(true){

        }

    }

    @Test
    public void testPathChildrenCache() throws Exception {
        // 1. 创建PathChildrenCache对象
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, "/app2", true);
        // 2. 注册监听
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                System.out.println("监听到了变化~");
                System.out.println(pathChildrenCacheEvent);
                if (pathChildrenCacheEvent.getType().equals(CHILD_UPDATED)){
                    System.out.println("数据更新了！");
                    byte[] data = pathChildrenCacheEvent.getData().getData();
                    System.out.print("新数据为：");
                    System.out.println(new String(data));
                }
                System.out.println("==================");
            }
        });

        // 3. 开启监听，如果设置为true，则开启监听池，加载缓冲数据
        pathChildrenCache.start();

        while(true){

        }

    }

    @Test
    public void testTreeCache() throws Exception {
        // 1. 创建TreeCache对象
        TreeCache treeCache = new TreeCache(client, "/app2");

        // 2. 注册监听
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                System.out.println("节点变化了！");
                System.out.println(treeCacheEvent);
                System.out.println("=======================");
            }
        });

        // 3. 开启监听，如果设置为true，则开启监听池，加载缓冲数据
        treeCache.start();

        while(true){

        }

    }

    @AfterEach
    public void closeConnect(){
        if (client != null){
            client.close();
        }
    }
}
