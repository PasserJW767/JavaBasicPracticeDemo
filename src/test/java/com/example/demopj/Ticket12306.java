package com.example.demopj;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

public class Ticket12306 implements Runnable{
    private int tickets = 20;

    private InterProcessMutex interProcessMutex;

    public Ticket12306(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(300, 1);

        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("103.152.132.148:2181")
                .sessionTimeoutMs(60000)
                .connectionTimeoutMs(150)
                .retryPolicy(retryPolicy).build();

        client.start();

        interProcessMutex = new InterProcessMutex(client, "/lock");
    }


    @Override
    public void run() {
        while (true){
            try{
                // 获取锁
                interProcessMutex.acquire(3, TimeUnit.SECONDS);

                if (tickets > 0) {
                    System.out.println(Thread.currentThread() + ":" + tickets);
                    tickets--;
                }

                Thread.sleep(1000);

            } catch (Exception e){
                e.printStackTrace();
            } finally {
                // 释放锁
                try {
                    interProcessMutex.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
