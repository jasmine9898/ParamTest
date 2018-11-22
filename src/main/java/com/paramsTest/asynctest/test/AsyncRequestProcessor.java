package com.paramsTest.asynctest.test;

import com.paramsTest.Util;
import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
import static com.paramsTest.Util.nowtime;

public class AsyncRequestProcessor implements Runnable {

    private AsyncContext asyncContext;
    private int time;

    public AsyncRequestProcessor(AsyncContext asyncCtx, int secs) {
        this.asyncContext = asyncCtx;
        this.time = secs;
    }

    public void run() {

        ServletResponse response=asyncContext.getResponse();
//        System.out.println("Async Supported? "
//                + asyncContext.getRequest().isAsyncSupported() + "  !!!  ");
        String params="async-"+Util.paramOption(asyncContext.getRequest());
        String sleep=Util.longProcessing(time);
        Util.writeToResponse(response, sleep + " ,异步线程取得参数 ： " + params);
        Util.writeToResponse(response," 异步线程 "+Thread.currentThread().getId()+"  complete 时间是："+nowtime());
        asyncContext.complete();

        System.out.println(nowtime()+" 异步线程 "+Thread.currentThread().getId()+"  complete!!!!!!!!!!!!!!!!!!!!!!!");
    }
}
