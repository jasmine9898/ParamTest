package com.paramsTest.asynctest.examples;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(urlPatterns = "/asyncDispatch",asyncSupported = true)
public class AsyncDispatchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final AsyncContext context = req.startAsync();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                context.dispatch("/index.jsp");
            }
        }
        );
        t.start();

    }
}
