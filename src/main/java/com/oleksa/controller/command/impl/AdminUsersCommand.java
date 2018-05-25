package com.oleksa.controller.command.impl;

import static com.oleksa.controller.constants.MessagesConstants.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.oleksa.controller.command.Command;
import com.oleksa.model.entity.User;
import com.oleksa.model.service.UserService;

public class AdminUsersCommand implements Command {

	private UserService service;
    
    public AdminUsersCommand(UserService service) {
    	this.service = service;
	}
    
	@Override
	public String execute(HttpServletRequest request) {
		List<User> find = service.findAll();
		request.getSession().setAttribute(ATTRIBUTE_USERS, find);
		request.setAttribute(PARAM_USERS, find);
		return SERVERPAGE_ADMIN_USERS;
	}

}
