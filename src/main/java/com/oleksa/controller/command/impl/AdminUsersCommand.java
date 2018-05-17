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
		System.out.println("admin users command");
		List<User> findAll = service.findAll();
		System.out.println(findAll);
		request.getSession().setAttribute(ATTRIBUTE_USERS, findAll);
		request.setAttribute(PARAM_USERS, findAll);
		return SERVERPAGE_ADMIN_USERS;
	}

}
