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
import com.oleksa.model.exception.NotUniqueEmailException;
import com.oleksa.model.exception.NotUniqueNameException;
import com.oleksa.model.logger.Loggable;
import com.oleksa.model.service.UserService;

public class AdminUpdateUserCommand implements Command, Loggable {

	private UserService userService;

	public AdminUpdateUserCommand(UserService userService) {
		this.userService = userService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String execute(HttpServletRequest request) {
		String idParam = request.getParameter(PARAM_ID);
		try {
			int id = ValidatorUtil.parseIdParameter(idParam);
			List<User> found = (List<User>) request.getSession().getAttribute(ATTRIBUTE_USERS);
			if (Objects.isNull(found)) {
				getLogger().error(MSG_NO_PREV_SEARCH);
				return SERVERPAGE_ADMIN_USERS;
			}
			getLogger().debug(found);
			Optional<User> first = found.stream().filter(k -> k.getId() == id).findFirst();
			if(!first.isPresent()) {
				getLogger().error(MSG_NO_PREV_SEARCH);
				return SERVERPAGE_ADMIN_USERS;
			}
			userService.updateToMaster(first.get());
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
