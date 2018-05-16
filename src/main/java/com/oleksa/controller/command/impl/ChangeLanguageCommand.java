package com.oleksa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;

import com.oleksa.controller.command.Command;
import static com.oleksa.controller.constants.MessagesConstants.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.oleksa.controller.constants.MessagesConstants.*;

public class ChangeLanguageCommand implements Command {
	
    @Override
    public String execute(HttpServletRequest request) {
        String langParam = request.getParameter(PARAM_LANG);
        if(Objects.nonNull(langParam) && LanguageParamHolder.LANGS.contains(langParam)) {
        	request.getSession().setAttribute(PARAM_LANG, langParam);
        }
        return PAGE_REDIRECT + URL_INDEX;
    }

	private static class LanguageParamHolder {
		static final Set<String> LANGS = new HashSet<>();
		static {
			LANGS.add(PARAM_LANG_EN);
			LANGS.add(PARAM_LANG_UA);
		}
	}
}
