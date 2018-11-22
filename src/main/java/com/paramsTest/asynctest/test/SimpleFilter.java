package com.paramsTest.asynctest.test;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SimpleFilter implements Filter {
    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        // String filtername=this.getClass().getSimpleName();
        // System.out.println("Entering " +filtername  + ".doFilter().");
        // 将请求和响应强制转换成Http形式
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        // 处理响应乱码
        response.setContentType("text/html;charset=utf-8");

       /* if (req.isAsyncSupported() && req.isAsyncStarted()) {
            AsyncContext context = req.getAsyncContext();
            System.out.println("Leaving " + filtername + ".doFilter(),async" +
                    "context holds wrapped req/resp = " +
                    !context.hasOriginalRequestAndResponse());
        } else {
            System.out.println("leaving " + filtername + ".doFilter().");
        }*/
        // 自定义一个request对象：MyRequest，对服务器原来的requset进行增强，使用装饰设计模式
        // 要增强原来的request对象，必须先获取到原来的request对象
        RequestWrapper myRequest = new RequestWrapper(request);
        chain.doFilter(myRequest, response);

     /*   ResponseWrapper responseWrapper = new ResponseWrapper(response);

        // 注意：放行的时候应该传入增强后的request对象
        chain.doFilter(myRequest, responseWrapper);
        String result = responseWrapper.getResult();
        if(result.contains("Error-500")){
            response.sendError(500,result);
        }*/
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

}