package com.oleksa.controller;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.oleksa.controller.command.impl.CommandUtil;
import com.oleksa.model.service.ServiceFactory;
import com.oleksa.model.service.UserService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static com.oleksa.controller.constants.MessagesConstants.*;

public class SalonServletTest  {

    private SalonServlet servlet;
    
    @Before
    public void setUp() throws ServletException {
        servlet = new SalonServlet();
        ServletConfig config = mock(ServletConfig.class);
        when(config.getServletContext()).thenReturn(mock(ServletContext.class));
        servlet.init(config);
    }
    
    @Test
    public void defaultPage() throws IOException, ServletException {
        String[] stupidUris = new String[] {"/", "/home", "/index.jsp", "/index.jspx", "/welcome", "",
                "/index.htm", "/error", "/index", "/money", "/users", "/data"};
        for (String uri : stupidUris) {
            HttpServletRequest request = mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
            HttpServletResponse response = mock(HttpServletResponse.class);
            when(request.getRequestURI()).thenReturn(uri);
            servlet.doGet(request, response);
            ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
            verify(request, atLeastOnce()).getRequestDispatcher(captor.capture());
            verify(request.getRequestDispatcher(anyString()), atLeastOnce()).forward(request, response);
            assertEquals(1, captor.getAllValues().size());
            assertEquals(SERVERPAGE_INDEX, captor.getValue());
        }
        
    }
    
    @Test
    public void logoutRedirect() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getRequestURI()).thenReturn("/logout");
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute(PARAM_USER)).thenReturn(Optional.empty());
        when(request.getSession()).thenReturn(session);
        servlet.doGet(request, response);
        verify(response, atLeastOnce()).sendRedirect(anyString());
    }
}
