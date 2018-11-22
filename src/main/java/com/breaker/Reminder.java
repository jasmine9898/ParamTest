package com.breaker;

import com.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class Reminder {
    Timer timer;
    public Reminder(int ms) {
        timer = new Timer();
       // timer.schedule(new RemindTask(), 0, 1000);
    //    timer.scheduleAtFixedRate(new RemindTask(), 5000, ms);
      //  timer.scheduleAtFixedRate(new RemindTask2(), 1000, 1000*60*4);
    }
    class RemindTask extends TimerTask {
        public void run() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(sdf.format(this.scheduledExecutionTime()));
            HttpClientGetServlet httpclient=new HttpClientGetServlet();
            try {
                String url="http://"+Test.getValue("host")+":"+Test.getValue("port")+"/"+Test.getValue("ContextPath");
                System.out.println( httpclient.get(url+"/gc?size=50"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //timer.cancel(); // Terminate the timer thread
        }
    }
    class RemindTask2 extends TimerTask {
        public void run() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(sdf.format(this.scheduledExecutionTime())+"  clear list");
            MemServlet.testObjectList.clear();
            //timer.cancel(); // Terminate the timer thread
        }
    }
}