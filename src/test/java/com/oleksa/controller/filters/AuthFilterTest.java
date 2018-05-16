package com.oleksa.controller.filters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.oleksa.model.entity.User;
import com.oleksa.model.entity.UserRole;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.oleksa.controller.constants.MessagesConstants.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthFilterTest {

    private AuthFilter authFilter;
    
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;
    @Mock
    private User user;
    
    @Before
    public void setUp() {
        authFilter = new AuthFilter();
        
    }
    
    @Test
    public void testDoFilterOnIndex() throws IOException, ServletException {
        for(UserRole role : UserRole.values()) {
            User user = mock(User.class);
//            when(user.getRole()).thenReturn(role); // should not be called on index page
            commonForRoles(Optional.of(user));
        }
        commonForRoles(Optional.empty());
    }

    private void commonForRoles(Optional<User> user) throws IOException, ServletException {
//        HttpServletRequest request = mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);       
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        FilterChain chain = mock(FilterChain.class);
        when(request.getSession().getAttribute(PARAM_USER)).thenReturn(user);
        when(request.getRequestURI()).thenReturn(SERVERPAGE_INDEX);
        authFilter.doFilter(request, response, chain);
        verify(chain, atLeastOnce()).doFilter(request, response);
    }
    
    @Test
    public void testDoFilterOnAdminPages() throws IOException, ServletException {
        commonTestBody(UserRole.ADMIN, SERVERPAGE_ADMIN);
    }
    
    @Test
    public void testDoFilterOnClientPages() throws IOException, ServletException {
        commonTestBody(UserRole.CLIENT, SERVERPAGE_CLIENT);
    }
    
    @Test
    public void testDoFilterOnMasterPages() throws IOException, ServletException {
        commonTestBody(UserRole.MASTER, SERVERPAGE_MASTER);
    }

    private void commonTestBody(UserRole forRole, String rolePage) throws IOException, ServletException {
        for(UserRole role : UserRole.values()) {
            when(user.getRole()).thenReturn(role);
            Optional<User> optUser = Optional.of(user);
            when(request.getSession().getAttribute(PARAM_USER)).thenReturn(optUser);
            when(request.getRequestURI()).thenReturn(rolePage);
            if (optUser.isPresent() && optUser.get().getRole() == forRole) {
                authFilter.doFilter(request, response, chain);
                verify(chain, atLeastOnce()).doFilter(request, response);
            } else {
                try {
                    authFilter.doFilter(request, response, chain);
                    fail();
                } catch (RuntimeException e) {
                    assertNotNull(e);
                }
            }
        }
        Optional<User> optUser = Optional.empty();
        when(request.getSession().getAttribute(PARAM_USER)).thenReturn(optUser);
        when(request.getRequestURI()).thenReturn(rolePage);
        try {
            authFilter.doFilter(request, response, chain);
            fail();
        } catch (RuntimeException e) {
            assertNotNull(e);
        }
    }
}
