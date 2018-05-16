package com.oleksa.controller.listener;

import java.util.Set;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oleksa.model.entity.User;

import static com.oleksa.controller.constants.MessagesConstants.*;

public class SessionListener implements HttpSessionListener {

	private static final Logger LOGGER = LogManager.getLogger(SessionListener.class);
	
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        session.setAttribute(PARAM_USER, Optional.empty());
        
    }

    @SuppressWarnings("unchecked")
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        Optional<User> user = (Optional<User>) httpSessionEvent.getSession()
                .getAttribute(PARAM_USER);
        if (user.isPresent()) {
            ((Set<String>) httpSessionEvent
                    .getSession().getServletContext()
                    .getAttribute(PARAM_LOGGED_USERS)).remove(user.get().getName());
            LOGGER.info("session expired " + user.get());
        }
    }

}
