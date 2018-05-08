package com.oleksa.controller.filters;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oleksa.controller.exception.AccessDeniedException;
import com.oleksa.model.entity.User;
import com.oleksa.model.entity.UserRole;
import static com.oleksa.controller.constants.MessagesConstants.*;

public final class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("AuthFilter doFilter");

        final HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        ServletContext context = request.getServletContext();
        String path = req.getRequestURI();
        System.out.println(path);
        System.out.println(context.getAttribute(PARAM_LOGGED_USERS));
        Optional<User> user = (Optional<User>) session.getAttribute(PARAM_USER);
        if(user.isPresent()) {
            UserRole role = user.get().getRole();
            PathToRoleMapper.map
            .forEach((k, v) -> { if (path.contains(k) && role != v)
              throw new AccessDeniedException(MSG_ACCESS_DENIED); } );
        } else {
            PathToRoleMapper.map
            .forEach((k, v) -> { if (path.contains(k))
              throw new AccessDeniedException(MSG_ACCESS_DENIED); } );
        }
        chain.doFilter(request,response);
    }
    
    private static class PathToRoleMapper {
        static Map<String, UserRole> map = new HashMap<>();
        static {
            map.put(FOLDER_ADMIN, UserRole.ADMIN);
            map.put(FOLDER_CLIENT, UserRole.CLIENT);
            map.put(FOLDER_MASTER, UserRole.MASTER);
        }
    }

    @Override
    public void destroy() {
        
    }

}
