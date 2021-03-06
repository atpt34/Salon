package com.oleksa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;

import com.oleksa.controller.command.Command;
import static com.oleksa.controller.constants.MessagesConstants.*;

/**
 * Performs log out of user in application.
 * 
 * @author atpt34
 *
 */
public final class LogoutCommand implements Command {

    @Override
    public final String execute(HttpServletRequest request) {
        if (CommandUtil.isUserLogged(request)) {
            CommandUtil.unsetUser(request);
        }
        return PAGE_REDIRECT + URL_INDEX;
    }

}
