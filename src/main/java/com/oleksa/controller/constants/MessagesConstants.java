package com.oleksa.controller.constants;

public interface MessagesConstants {

    String PARAM_PASS = "pass";
    String PARAM_NAME = "name";
    String PARAM_FULLNAME = "fullname";
    String PARAM_EMAIL = "email";
    String PARAM_ROLE = "role";
    String PARAM_USER = "user";
    String PARAM_LOGGED_USERS = "loggedUsers";
    String PARAM_LANG = "lang";
    String PARAM_ERROR = "error";
    
    String URL_CHANGE_LANGUAGE = "/changeLanguage";
    String URL_REGISTER = "/register";
    String URL_LOGOUT = "/logout";
    String URL_LOGIN = "/login";
    String URL_INDEX = "/index";
    
    String PAGE_REDIRECT = "redirect:";
    String PAGE_LOGIN = "/login_page";
    String PAGE_REGISTER = "/register_page";
    String PAGE_ADMIN = "/admin_page";
    String PAGE_MASTER = "/master_page";
    String PAGE_CLIENT = "/client_page";

    String PARENT_DIR = "./WEB-INF/jsp";
    String SERVERPAGE_INDEX = PARENT_DIR + "/index.jsp";
    String SERVERPAGE_LOGIN = PARENT_DIR + "/login.jsp";
    String SERVERPAGE_REGISTER = PARENT_DIR + "/register.jsp";
    String SERVERPAGE_ADMIN = PARENT_DIR + "/admin/admin.jsp";
    String SERVERPAGE_CLIENT = PARENT_DIR + "/client/client.jsp";
    String SERVERPAGE_MASTER = PARENT_DIR + "/master/master.jsp";
    String SERVERPAGE_404 = PARENT_DIR + "/404.jsp";

    String FOLDER_ADMIN = "admin";
    String FOLDER_CLIENT = "client";
    String FOLDER_MASTER = "master";
    
    String MSG_ACCESS_DENIED = "error.access.denied";
    String MSG_ALREADY_LOGIN = "error.already.login";
    String MSG_INVALID_INPUT = "error.invalid.input";
    String MSG_INVALID_CREDENTIALS = "error.invalid.credentials";
    String MSG_NAME_IN_USE = "error.inuse.name";
    String MSG_EMAIL_IN_USE = "error.inuse.email";
    
}
