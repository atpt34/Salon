package com.oleksa.controller.command.impl;

import java.util.Set;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oleksa.model.entity.User;
import com.oleksa.model.entity.UserRole;
import static com.oleksa.controller.constants.MessagesConstants.*;

/**
 * Utility class to manage user's logging
 * session and context data.  
 * 
 * @author atpt34
 *
 */
public final class CommandUtil {

    private static final Logger LOGGER = LogManager.getLogger(CommandUtil.class);

    private CommandUtil() { }

    public static boolean setLoggedUser(HttpServletRequest request, User user) {
        if(getLoggedUsers(request).add(user.getName())) {
            HttpSession session = request.getSession();
            session.setAttribute(PARAM_USER, Optional.of(user));
            LOGGER.info("login user: " + user);
            LOGGER.info("logged users: " + getLoggedUsers(request));
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static void unsetUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Optional<User> user = (Optional<User>) session.getAttribute(PARAM_USER);
        if (user.isPresent()) {
            getLoggedUsers(request).remove(user.get().getName());
            session.setAttribute(PARAM_USER, Optional.empty());
            session.removeAttribute(ATTRIBUTE_RECORDS);
            session.removeAttribute(ATTRIBUTE_SCHEDULES);
            session.removeAttribute(ATTRIBUTE_USERS);
            LOGGER.info("logout user: " + user.get());
            LOGGER.info("logged users: " + getLoggedUsers(request));
        }
    }

    @SuppressWarnings("unchecked")
    private static Set<String> getLoggedUsers(HttpServletRequest request) {
        return (Set<String>) request.getSession().getServletContext()
                .getAttribute(PARAM_LOGGED_USERS);
    }

    @SuppressWarnings("unchecked")
    public static boolean isUserLogged(HttpServletRequest request) {
        return ((Optional<User>) request.getSession().getAttribute(PARAM_USER)).isPresent();
    }
}
