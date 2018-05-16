package com.oleksa.controller.command.impl;

import static com.oleksa.controller.constants.MessagesConstants.*;

import javax.servlet.http.HttpServletRequest;

import com.oleksa.controller.command.Command;
import com.oleksa.controller.exception.UnparsableIdException;
import com.oleksa.controller.validator.ValidatorUtil;
import com.oleksa.model.entity.User;
import com.oleksa.model.exception.NotUniqueEmailException;
import com.oleksa.model.exception.NotUniqueNameException;
import com.oleksa.model.logger.Loggable;
import com.oleksa.model.service.UserService;

public class AdminUpdateUserCommand implements Command, Loggable {

	private UserService userService;

	public AdminUpdateUserCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request) {
		String idParam = request.getParameter(PARAM_ID);
		try {
			int id = ValidatorUtil.parseIdParameter(idParam);
			User user = null;
			userService.updateToMaster(user);
		} catch (UnparsableIdException e) {
			e.printStackTrace();
			getLogger().error(e);
		} catch (NotUniqueNameException e) {
			e.printStackTrace();
			getLogger().error(e);
		} catch (NotUniqueEmailException e) {
			e.printStackTrace();
			getLogger().error(e);
		}
		return SERVERPAGE_ADMIN_USERS;
	}

}
