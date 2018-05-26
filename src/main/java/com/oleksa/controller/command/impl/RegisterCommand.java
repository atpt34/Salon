package com.oleksa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;

import com.oleksa.controller.command.Command;
import com.oleksa.controller.validator.ValidatorUtil;
import com.oleksa.model.entity.User;
import com.oleksa.model.entity.UserRole;
import com.oleksa.model.exception.NotUniqueEmailException;
import com.oleksa.model.exception.NotUniqueNameException;
import com.oleksa.model.logger.Loggable;
import com.oleksa.model.service.ServiceFactory;
import com.oleksa.model.service.UserService;

import static com.oleksa.controller.constants.MessagesConstants.*;

/**
 * Tries to register new user of application.
 * If successful new user registered with client role and 
 * client's home page returned.
 * 
 * @author atpt34
 *
 */
public final class RegisterCommand implements Command, Loggable {

    private UserService service;

    public RegisterCommand(UserService userService) {
        this.service = userService;
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
            String email = request.getParameter(PARAM_EMAIL);
            String fullname = request.getParameter(PARAM_FULLNAME);
            if (!ValidatorUtil.isValidName(name)
                    || !ValidatorUtil.validPassword(pass)
                    || !ValidatorUtil.validEmail(email)
                    || !ValidatorUtil.validFullname(fullname)) {
                request.setAttribute(PARAM_ERROR, MSG_INVALID_INPUT);
                return PAGE_REGISTER;
            }
            getLogger().info(name + " " + pass + " " + email + " " + fullname);
            User user = new User(null, name, email, pass, UserRole.CLIENT, fullname);
            user = service.create(user);
            if (!CommandUtil.setLoggedUser(request, user)) {
                request.setAttribute(PARAM_ERROR, MSG_ALREADY_LOGIN);
                return PAGE_LOGIN;
            }
            return PAGE_REDIRECT + PAGE_CLIENT;
        } catch (NotUniqueNameException e) {
            request.setAttribute(PARAM_ERROR, MSG_NAME_IN_USE);
            return PAGE_REGISTER;
        } catch (NotUniqueEmailException e) {
            request.setAttribute(PARAM_ERROR, MSG_EMAIL_IN_USE);
            return PAGE_REGISTER;
        }
    }

}
