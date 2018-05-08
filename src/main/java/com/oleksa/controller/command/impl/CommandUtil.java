package com.oleksa.controller.command.impl;

import java.util.Set;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oleksa.model.entity.User;
import com.oleksa.model.entity.UserRole;
import static com.oleksa.controller.constants.MessagesConstants.*;

public final class CommandUtil {

    private CommandUtil() { }

    public static boolean setLoggedUser(HttpServletRequest request, User user) {
        if(getLoggedUsers(request).add(user.getName())) {
            HttpSession session = request.getSession();
            session.setAttribute(PARAM_USER, Optional.of(user));
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
