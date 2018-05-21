package com.oleksa.controller.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.oleksa.controller.command.impl.CommandUtil;
import com.oleksa.model.entity.User;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

@RunWith(MockitoJUnitRunner.class)
public class CommandUtilTest {

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request;
	@Mock
    private User user;
	
	private String username = "oleksa";
	
	@Test
	public void testSetLoggedUserTrue() {
		Set<String> set = new HashSet<>();
		when(user.getName()).thenReturn(username);
		when(request.getSession().getServletContext()
				.getAttribute(anyString())).thenReturn(set);
		
		assertTrue(CommandUtil.setLoggedUser(request, user));
	}
	
	@Test
	public void testSetLoggedUserFalse() {
		Set<String> set = new HashSet<>();
		set.add(username);
		when(user.getName()).thenReturn(username);
		when(request.getSession().getServletContext()
        .getAttribute(anyString())).thenReturn(set);
		
		assertFalse(CommandUtil.setLoggedUser(request, user));
	}
	
	@Test
	public void testSetLoggedUserAgain() {
		Set<String> set = new HashSet<>();
		when(user.getName()).thenReturn(username);
		when(request.getSession().getServletContext()
        .getAttribute(anyString())).thenReturn(set);
		
		assertTrue(CommandUtil.setLoggedUser(request, user));
		assertFalse(CommandUtil.setLoggedUser(request, user));
	}
}
