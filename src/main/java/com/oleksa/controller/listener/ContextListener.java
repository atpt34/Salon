package com.oleksa.controller.listener;

import static com.oleksa.controller.constants.MessagesConstants.PARAM_LOGGED_USERS;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.oleksa.model.entity.User;

public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Set<String> set = ConcurrentHashMap.newKeySet();
        sce.getServletContext().setAttribute(PARAM_LOGGED_USERS, set);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }

}
