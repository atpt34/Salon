package com.oleksa.controller.command.impl;

import static com.oleksa.controller.constants.MessagesConstants.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.oleksa.controller.command.Command;
import com.oleksa.controller.exception.UnparsableIdException;
import com.oleksa.controller.validator.ValidatorUtil;
import com.oleksa.model.entity.User;
import com.oleksa.model.logger.Loggable;
import com.oleksa.model.service.UserService;

public class AdminDeleteUserCommand implements Command, Loggable {

	private UserService userService;

	public AdminDeleteUserCommand(UserService userService) {
		this.userService = userService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String execute(HttpServletRequest request) {
		List<User> found = (List<User>) request.getSession().getAttribute(ATTRIBUTE_USERS);
		if (Objects.isNull(found)) {
			getLogger().error(MSG_NO_PREV_SEARCH);
			request.setAttribute(PARAM_ERROR, MSG_RETRY_SEARCH);
			return PARENT_DIR + SERVERPAGE_ADMIN;
		}
		String idParam = request.getParameter(PARAM_ID);
		try {
			int id = ValidatorUtil.parseIdParameter(idParam);
			Optional<User> first = found.stream().filter(k -> k.getId() == id).findFirst();
			if (!first.isPresent()) {
				getLogger().error(MSG_NO_PREV_SEARCH);
				request.setAttribute(PARAM_ERROR, MSG_RETRY_SEARCH);
				return PARENT_DIR + SERVERPAGE_ADMIN;
			}
			userService.delete(first.get());
			request.getSession().removeAttribute(ATTRIBUTE_USERS);
		} catch (UnparsableIdException e) {
			getLogger().error(e);
			request.setAttribute(PARAM_ERROR, MSG_RETRY_SEARCH);
			return PARENT_DIR + SERVERPAGE_ADMIN;
		}
		return PAGE_REDIRECT + PAGE_ADMIN;
	}

}
