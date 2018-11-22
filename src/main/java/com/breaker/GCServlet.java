package com.breaker;

import com.Test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name = "GCServlet",urlPatterns="/gc")
public class GCServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int addMemSize= Integer.valueOf(Test.getValue("MaxNewSize"))/2*3;
        if(!request.getParameterMap().isEmpty()){
            addMemSize=Integer.valueOf(request.getParameter("size"));
        }
        byte[] bufOne = new byte[1024 * 1024 * addMemSize];
        //Runtime.getRuntime().gc();
        response.getWriter().write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS").format(Calendar.getInstance().getTime())+"-------addsize:" +addMemSize);
    }

}
