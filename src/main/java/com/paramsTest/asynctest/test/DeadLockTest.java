package com.paramsTest.asynctest.test;

/**
 * Created by admin on 2016/9/9.
 */
public class DeadLockTest implements Runnable {
    public int flag = 1;
    static Object o1 = new Object(), o2 = new Object();
    public void run() {
        System.out.println("flag=" + flag);
        if(flag == 1) {
            synchronized(o1) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized(o2) {
                    System.out.println("1");
                }
            }
        }
        if(flag == 0) {
            synchronized(o2) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized(o1) {
                    System.out.println("0");
                }
            }
        }
    }
    public static void main(String[] args) {
        DeadLockTest td1 = new DeadLockTest();
        DeadLockTest td2 = new DeadLockTest();
        td1.flag = 1;
        td2.flag = 0;
        new Thread(td1).start();
        new Thread(td2).start();

    }
}