package com.breaker;

import com.Test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@WebServlet(name = "MemoryServlet", urlPatterns = "/memoryAdd")
public class MemServlet extends HttpServlet {
    public static List<TestObject> testObjectList = new ArrayList<>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Runtime.getRuntime().gc();
        int addMemSize = Integer.valueOf(Test.getValue("MaxHeapSize"))-Integer.valueOf(Test.getValue("MaxHeapSize"))*Integer.valueOf(Test.getValue("heap_threshold"))/100;
        if (!request.getParameterMap().isEmpty()) {
            addMemSize = Integer.valueOf(request.getParameter("size"));
        }
        List list = new ArrayList();
        for (int j = 0; j < addMemSize; j++) {
            testObjectList.add(new TestObject());
        }
        int freemem = (int) (Runtime.getRuntime().freeMemory() / 1024 / 1024);
        int totalmem = (int) (Runtime.getRuntime().totalMemory() / 1024 / 1024);
        int maxmem = (int) (Runtime.getRuntime().maxMemory() / 1024 / 1024);
        int usedMemory = totalmem - freemem;

        float result = (float) (usedMemory * 1.0 / maxmem * 100);
        response.getWriter().write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS").format(Calendar.getInstance().getTime()) + "--static object----addsize:" + addMemSize + " M,-------used Memory:" + usedMemory + "-------freepercent:" + (100 - result) + "%");
    }
}
