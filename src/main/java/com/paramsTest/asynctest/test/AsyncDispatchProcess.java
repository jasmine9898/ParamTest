package com.paramsTest.asynctest.test;

import com.paramsTest.Util;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.paramsTest.Util.nowtime;

public class AsyncDispatchProcess implements Runnable {
    private AsyncContext act = null;

    public AsyncDispatchProcess(AsyncContext act) {
        this.act = act;
    }

    public void run() {
        HttpServletRequest request = (HttpServletRequest) act.getRequest();
        HttpServletResponse response = (HttpServletResponse) act.getResponse();
        int time = 50;
        if (request.getParameter("time") != null) {
            time = Integer.valueOf(request.getParameter("time"));
        }
        String params = Util.paramOption(request);
        request.setAttribute("asyncThreadID", Thread.currentThread().getId());
        request.setAttribute("params", "async-"+params);
        Util.longProcessing(time);
        request.setAttribute("completedTime", nowtime());
        System.out.println(nowtime() + " 异步线程 " + Thread.currentThread().getId() + "  dispatch!!!!!!!!!!!!!!!!!!!!!!!"+nowtime());
        act.dispatch("/complete.jsp");
    }
}

