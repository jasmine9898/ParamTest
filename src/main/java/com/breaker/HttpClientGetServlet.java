package com.breaker;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


@WebServlet(name="HttpclientServlet",urlPatterns = "/httpclient")
public class HttpClientGetServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        StringBuffer stringBuffer = new StringBuffer();
        String urlString = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/gc";
        if (!req.getParameterMap().isEmpty()) {
            urlString =  URLDecoder.decode(req.getParameter("urlparam"), "UTF-8");
        }
        resp.getWriter().write(get(urlString));
    }
    public String get(String urlString) throws IOException {
        String responseString="";
        AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
        Future<String> f = asyncHttpClient.prepareGet(urlString).execute(
                new AsyncCompletionHandler<String>() {
                    public String onCompleted(Response response) throws Exception {
                        return response.getResponseBody();
                    }
                    public void onThrowable(Throwable t) {
                        t.printStackTrace();
                    }
                });
        try {
            responseString = f.get(6000000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            responseString=e.getMessage();
            e.printStackTrace();
        }
        asyncHttpClient.close();
        return responseString;
    }

}
