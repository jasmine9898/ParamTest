package com.breaker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@WebServlet(name = "MemCleanServlet", urlPatterns = "/memoryClean")
public class MemCleanServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MemServlet.testObjectList.clear();
        Runtime.getRuntime().gc();
        int freemem= (int) (Runtime.getRuntime().freeMemory()/1024/1024);
        int totalmem= (int) (Runtime.getRuntime().totalMemory()/1024/1024);
        int maxmem= (int) (Runtime.getRuntime().maxMemory()/1024/1024);
        int usedMemory=totalmem-freemem;

        float result= (float) (usedMemory*1.0/maxmem*100);
        response.getWriter().write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS").format(Calendar.getInstance().getTime())+"-------used memory clean,and gc!!!-------used Memory:"+usedMemory+"-------freepercent:"+(100-result));
    }
}
