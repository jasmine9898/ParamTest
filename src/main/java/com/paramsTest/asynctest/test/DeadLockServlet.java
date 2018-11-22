package com.paramsTest.asynctest.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by admin on 2016/9/6.
 */
@WebServlet(urlPatterns = "/deadlock")
public class DeadLockServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        DeadTest deadTest=new DeadTest();
        deadTest.run();

        DeadLockTest td1 = new DeadLockTest();
        DeadLockTest td2 = new DeadLockTest();
        td1.flag = 1;
        td2.flag = 0;
        new Thread(td1).start();
        new Thread(td2).start();

        resp.getWriter().write("ok");
    }
    static class DeadTest {
        public void run() {
            MyThread mt = new MyThread();
            new Thread(mt, "张三").start();
            new Thread(mt, "李四").start();
        }

        class MyThread implements Runnable {
            private Object o1 = new Object();
            private Object o2 = new Object();
            private boolean flag = true;
            public void run() {
                if (flag) {
                    flag = false;
                    synchronized (o1) {
                        System.out.println(Thread.currentThread().getName() + " have o1");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        synchronized (o2) {
                            System.out.println(Thread.currentThread().getName() + " have o2");
                        }
                    }
                } else {
                    flag = true;
                    synchronized (o2) {
                        System.out.println(Thread.currentThread().getName() + " have o2");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        synchronized (o1) {
                            System.out.println(Thread.currentThread().getName() + " have o1");
                        }
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
       DeadTest deadTest=new DeadTest();
       deadTest.run();
    }
}