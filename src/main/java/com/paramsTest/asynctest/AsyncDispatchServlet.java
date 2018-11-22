package com.paramsTest.asynctest;

import com.paramsTest.Util;
import com.paramsTest.asynctest.test.AppAsyncListener;
import com.paramsTest.asynctest.test.AsyncDispatchProcess;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static com.paramsTest.Util.nowtime;

@WebServlet(urlPatterns = "/asyncDis",asyncSupported = true)
public class AsyncDispatchServlet extends HttpServlet {
    ScheduledThreadPoolExecutor userExecutor = new ScheduledThreadPoolExecutor(5);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("acceptedTime",nowtime());
        String getparam = Util.getParamstr(request);
        request.setAttribute("mainparam",getparam);

        AsyncContext act = request.startAsync();
        act.setTimeout(1000L);
        System.out.println("getTimeout "+act.getTimeout()+"!!!!!!!!!!!!!!!!!!!!!");

        act.addListener(new AppAsyncListener());
        AsyncDispatchProcess paramHandler = new AsyncDispatchProcess(act);
        userExecutor.execute(paramHandler);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        request.setAttribute("endTime",nowtime());
        request.setAttribute("mainThreadID",Thread.currentThread().getId());

        //writer.println("主线程 "+Thread.currentThread().getId()+"  sleep 2000 ms 后时间是：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS").format(Calendar.getInstance().getTime()) + "<br>");
        //writer.flush();
    }

}
