package com.paramsTest.asynctest;

import com.paramsTest.Util;
import com.paramsTest.asynctest.test.AppAsyncListener;
import com.paramsTest.asynctest.test.AsyncRequestProcessor;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.concurrent.ThreadPoolExecutor;

import static com.paramsTest.Util.nowtime;

@WebServlet(urlPatterns = "/asyncCom", asyncSupported = true)
public class AsyncCompleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final PrintWriter writer = response.getWriter();
        writer.println("主线程 "+Thread.currentThread().getId()+"  进入时间是：" +nowtime() + "<br>");
        String getparam = Util.getParamstr(request);
        writer.write("主线程取得参数 ： "+getparam);
      //  writer.flush();
        AsyncContext asyncCtx = request.startAsync();
        asyncCtx.addListener(new AppAsyncListener());

        asyncCtx.setTimeout(1000L);
        System.out.println("getTimeout "+asyncCtx.getTimeout()+"!!!!!!!!!!!!!!!!!!!!!");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int time = 50;
        if (request.getParameter("time") != null) {
            time = Integer.valueOf(request.getParameter("time"));
        }
        ThreadPoolExecutor executor = (ThreadPoolExecutor) request
                .getServletContext().getAttribute("executor");
        executor.execute(new AsyncRequestProcessor(asyncCtx, time));
        // new Thread(new AsyncRequestProcessor(asyncCtx, time)).start();
        //response.getWriter().write(Util.getStackTrace());
        writer.println("主线程 "+Thread.currentThread().getId()+"  完成时间是：" + nowtime() + "<br>");

    }
}
