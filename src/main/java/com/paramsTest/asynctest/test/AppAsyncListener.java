package com.paramsTest.asynctest.test;

import com.paramsTest.Util;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.paramsTest.Util.nowtime;

@WebListener
public class AppAsyncListener implements AsyncListener, ServletContextListener, ServletRequestListener {
    public void onComplete(AsyncEvent asyncEvent) throws IOException {
        System.out.println(nowtime() + " 异步线程  onComplete!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    public void onTimeout(AsyncEvent asyncEvent) throws IOException {
        System.out.println(nowtime() + " 异步线程 onTimeout!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        asyncEvent.getAsyncContext().dispatch("/timeout.jsp");
    }

    public void onError(AsyncEvent asyncEvent) throws IOException {
        ServletResponse response=asyncEvent.getAsyncContext().getResponse();
        Util.writeToResponse(response, asyncEvent.getThrowable().getMessage());
        System.out.println(Thread.currentThread().getId() + "  onError!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + asyncEvent.getThrowable().getMessage());

    }

    public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
        ServletResponse response=asyncEvent.getAsyncContext().getResponse();
        Util.writeToResponse(response, "start....");
        System.out.println(Thread.currentThread().getId() + "  onStartAsync!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {

        // create the thread pool
        ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 100, 50000L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(100));
        servletContextEvent.getServletContext().setAttribute("executor", executor);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) servletContextEvent
                .getServletContext().getAttribute("executor");
        executor.shutdown();
    }

    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        System.out.println(nowtime() + " Listener - 线程 " + Thread.currentThread().getId() + "   uri: " + request.getRequestURI() + "   , request Destroyed !!!  ");
    }

    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        System.out.println(nowtime() + " Listener - 线程 " + Thread.currentThread().getId() + "   uri: " + request.getRequestURI() + "   , request Initialized !!!  ");
    }


}
