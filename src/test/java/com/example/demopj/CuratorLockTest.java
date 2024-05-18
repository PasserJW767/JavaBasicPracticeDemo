package com.example.demopj;

public class CuratorLockTest {

    public static void main(String[] args) {
        Ticket12306 ticket12306 = new Ticket12306();

        Thread thread = new Thread(ticket12306, "飞猪");
        Thread thread1 = new Thread(ticket12306, "携程");
        Thread thread2 = new Thread(ticket12306, "去哪儿");

        thread.start();
        thread1.start();
        thread2.start();
    }

}
