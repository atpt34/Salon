package com.oleksa.controller.filters;

import javax.servlet.*;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Sets encoding to UTF-8.
 * @author atpt34
 *
 */
public final class EncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        servletResponse.setContentType("text/html");
        servletResponse.setCharacterEncoding("UTF-8");
        servletRequest.setCharacterEncoding("UTF-8");
        servletRequest.setAttribute("today", LocalDate.now());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
