package com.breaker;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class WebListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        new Reminder(200);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
