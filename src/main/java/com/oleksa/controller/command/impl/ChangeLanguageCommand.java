package com.oleksa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;

import com.oleksa.controller.command.Command;
import static com.oleksa.controller.constants.MessagesConstants.*;
import static com.oleksa.controller.constants.MessagesConstants.*;

public class ChangeLanguageCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String temp = request.getParameter(PARAM_LANG);
        request.getSession().setAttribute(PARAM_LANG, temp);
        return PAGE_REDIRECT + URL_INDEX;
    }

}
