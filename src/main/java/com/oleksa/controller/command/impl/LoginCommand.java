package com.oleksa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;


import com.oleksa.controller.command.Command;
import com.oleksa.controller.validator.ValidatorUtil;
import com.oleksa.model.entity.User;
import com.oleksa.model.entity.UserRole;
import com.oleksa.model.exception.InvalidCredentialsException;
import com.oleksa.model.service.UserService;

import static com.oleksa.controller.constants.MessagesConstants.*;

import java.util.EnumMap;
import java.util.Map;

public final class LoginCommand implements Command {
    
    private UserService service;
    
    public LoginCommand(UserService service) {
    	this.service = service;
	}

    @Override
    public String execute(HttpServletRequest request) {
        try {
        	if (CommandUtil.isUserLogged(request)) {
                request.setAttribute(PARAM_ERROR, MSG_ALREADY_LOGIN);
                return PAGE_LOGIN;
            }
            String name = request.getParameter(PARAM_NAME);
            String pass = request.getParameter(PARAM_PASS);
            if (!ValidatorUtil.isValidName(name)
                    || !ValidatorUtil.validPassword(pass)){
                request.setAttribute(PARAM_ERROR, MSG_INVALID_INPUT);
                return PAGE_LOGIN;
            }
            User user = service.findUserByCredentials(name, pass);
            if (!CommandUtil.setLoggedUser(request, user)) {
                request.setAttribute(PARAM_ERROR, MSG_ALREADY_LOGIN);
                return PAGE_LOGIN;
            }
            return RoleToPageMapper.map.get(user.getRole());
        } catch (InvalidCredentialsException e) {
            request.setAttribute(PARAM_ERROR, MSG_INVALID_CREDENTIALS);
            return PAGE_LOGIN;
        }
    }

    private static class RoleToPageMapper {
        static Map<UserRole, String> map = new EnumMap<>(UserRole.class);
        static {
            map.put(UserRole.ADMIN, PAGE_REDIRECT + PAGE_ADMIN);
            map.put(UserRole.CLIENT, PAGE_REDIRECT + PAGE_CLIENT);
            map.put(UserRole.MASTER, PAGE_REDIRECT + PAGE_MASTER);
        }
    }
}
