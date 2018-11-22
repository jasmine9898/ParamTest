package com.paramsTest;

import javax.servlet.annotation.WebServlet;
import java.awt.print.PrinterGraphics;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

@WebServlet(name = "GetParamServlet", urlPatterns = "/getParams")
public class GetParamServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        PrintWriter writer = response.getWriter();
        String result = Util.paramOption(request);
        int time = 50;
        if (request.getParameter("time") != null) {
            time = Integer.valueOf(request.getParameter("time"));
        }
        String sleep=Util.longProcessing(time);
        writer.write(sleep+ "<br>"
                + result);
    }

}

