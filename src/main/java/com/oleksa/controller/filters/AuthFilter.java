package com.oleksa.controller.filters;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oleksa.controller.exception.AccessDeniedException;
import com.oleksa.model.entity.User;
import com.oleksa.model.entity.UserRole;
import com.oleksa.model.logger.Loggable;

import static com.oleksa.controller.constants.MessagesConstants.*;

/**
 * Filters requests of users to resources that they have no access rights.
 *   
 * @author atpt34
 *
 */
public final class AuthFilter implements Filter, Loggable {

    private static final Map<String, UserRole> MAP = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        MAP.put(FOLDER_ADMIN, UserRole.ADMIN);
        MAP.put(FOLDER_CLIENT, UserRole.CLIENT);
        MAP.put(FOLDER_MASTER, UserRole.MASTER);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        String path = req.getRequestURI();
        getLogger().info("path: " + path);
        Optional<User> user = (Optional<User>) session.getAttribute(PARAM_USER);
        checkAccess(path, user);
        chain.doFilter(request,response);
    }

    private void checkAccess(String path, Optional<User> user) {
        for (Entry<String, UserRole> entry : MAP.entrySet()) {
            if (path.contains(entry.getKey()) && (!user.isPresent() || user.get().getRole() != entry.getValue())) {
                throw new AccessDeniedException(MSG_ACCESS_DENIED); 
            }
        }
    }

    @Override
    public void destroy() {

    }

}
