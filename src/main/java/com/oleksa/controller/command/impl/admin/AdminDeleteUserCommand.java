package com.oleksa.controller.command.impl.admin;

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

/**
 * Deletes user of application
 * 
 * @author atpt34
 *
 */
public class AdminDeleteUserCommand implements Command, Loggable {

    private UserService userService;

    public AdminDeleteUserCommand(UserService userService) {
        this.userService = userService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String execute(HttpServletRequest request) {
        try {
            List<User> found = (List<User>) request.getSession().getAttribute(ATTRIBUTE_USERS);
            if (Objects.isNull(found)) {
                return handleNoPreviousSearch(request);
            }
            String idParam = request.getParameter(PARAM_ID);
            int id = ValidatorUtil.parseIdParameter(idParam);
            Optional<User> first = found.stream().filter(k -> k.getId() == id).findFirst();
            if (!first.isPresent()) {
                return handleNoPreviousSearch(request);
            }
            userService.delete(first.get());
            request.getSession().removeAttribute(ATTRIBUTE_USERS);
            return PAGE_REDIRECT + PAGE_ADMIN;
        } catch (UnparsableIdException e) {
            return handleNoPreviousSearch(request);
        }
    }

    private String handleNoPreviousSearch(HttpServletRequest request) {
        getLogger().error(MSG_NO_PREV_SEARCH);
        request.setAttribute(PARAM_ERROR, MSG_RETRY_SEARCH);
        return PARENT_DIR + SERVERPAGE_ADMIN;
    }

}
