package com.oleksa.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.oleksa.controller.command.Command;
import com.oleksa.model.service.ServiceFactory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


import java.io.IOException;
import java.util.Map;


import static com.oleksa.controller.constants.MessagesConstants.*;

@RunWith(MockitoJUnitRunner.class)
public class SalonServletTest  {

    private SalonServlet servlet;
    
	@Mock(answer=Answers.RETURNS_DEEP_STUBS)
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	ServiceFactory serviceFactory;
	@Mock
	Map<String, Command> commands;
	@Mock
	Command command;
    
	@Before
    public void setUp() throws ServletException {
		servlet = new SalonServlet(serviceFactory, commands);
    }
    
    @Test
    public void testProcessForward() throws ServletException, IOException {
    	String uri = "/uri";
		when(request.getRequestURI()).thenReturn(uri);
		String value = "/hello";
		when(command.execute(notNull())).thenReturn(value);
		when(commands.getOrDefault(anyString(), notNull())).thenReturn(command);

		servlet.processRequest(request, response);
		
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(request, atLeastOnce()).getRequestDispatcher(captor.capture());
		verify(request.getRequestDispatcher(anyString()), atLeastOnce()).forward(request, response);
		assertEquals(1, captor.getAllValues().size());
        assertEquals(value, captor.getValue());
    }
    
    @Test
    public void testProcessRedirect() throws ServletException, IOException {
    	String uri = "/uri";
		when(request.getRequestURI()).thenReturn(uri);
		String hello = "/hello";
		String value = PAGE_REDIRECT + hello;
		when(command.execute(notNull())).thenReturn(value);
		when(commands.getOrDefault(anyString(), notNull())).thenReturn(command);

		servlet.processRequest(request, response);

		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(response, atLeastOnce()).sendRedirect(captor.capture());
		assertEquals(1, captor.getAllValues().size());
        assertEquals(hello, captor.getValue());
    }
    
    @Test 
    public void testReplacePath() {
    	String uri = "/index";
    	String context = "/BeautySalon";
    	assertEquals(uri, (uri + context).replaceAll(context, ""));
    	assertEquals(uri, (context + uri).replaceAll(context, ""));
    	assertEquals(uri, (context + context + uri).replaceAll(context, ""));
    	assertEquals(uri, (context + context + context + uri).replaceAll(context, ""));
    }
}
