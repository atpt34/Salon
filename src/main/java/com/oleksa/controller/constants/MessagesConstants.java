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
    
    String PARAM_SCHEDULES = "schedules";
    String PARAM_RECORDS = "records";
    String PARAM_USERS = "users";
    
    String URL_CHANGE_LANGUAGE = "/changeLanguage";
    String URL_REGISTER = "/register";
    String URL_LOGOUT = "/logout";
    String URL_LOGIN = "/login";
    String URL_INDEX = "/index";
    
    String URL_SCHEDULES = "/schedules";
    String URL_ADMIN_USERS = "/admin/users";
    String URL_CLIENT_RECORDS = "/client/records";
    String URL_MASTER_SCHEDULES = "/master/schedules";
    
    String PAGE_REDIRECT = "redirect:";
    String PAGE_LOGIN = "/login_page";
    String PAGE_REGISTER = "/register_page";
    String PAGE_ADMIN = "/admin_page";
    String PAGE_MASTER = "/master_page";
    String PAGE_CLIENT = "/client_page";

    String WEBINF_JSP = "./WEB-INF/jsp";
    String SERVERPAGE_INDEX = WEBINF_JSP + "/index.jsp";
    String SERVERPAGE_LOGIN = WEBINF_JSP + "/login.jsp";
    String SERVERPAGE_REGISTER = WEBINF_JSP + "/register.jsp";
    String SERVERPAGE_ADMIN = WEBINF_JSP + "/admin/admin.jsp";
    String SERVERPAGE_CLIENT = WEBINF_JSP + "/client/client.jsp";
    String SERVERPAGE_MASTER = WEBINF_JSP + "/master/master.jsp";
    String SERVERPAGE_404 = WEBINF_JSP + "/404.jsp";
    
    String SERVERPAGE_SCHEDULES = WEBINF_JSP + "/schedules.jsp";
    String PARENT_DIR = "../";
    String SERVERPAGE_ADMIN_COMMENTS = PARENT_DIR + WEBINF_JSP + "/admin/comments.jsp";
    String SERVERPAGE_ADMIN_USERS = PARENT_DIR + WEBINF_JSP + "/admin/users.jsp";
    String SERVERPAGE_ADMIN_RECORDS = PARENT_DIR + WEBINF_JSP + "/admin/records.jsp";
    String SERVERPAGE_MASTER_SCHEDULES = PARENT_DIR + WEBINF_JSP + "/master/schedules.jsp";
    String SERVERPAGE_CLIENT_COMMENTS = PARENT_DIR + WEBINF_JSP + "/client/make_comment.jsp";
    String SERVERPAGE_CLIENT_RECORDS = PARENT_DIR + WEBINF_JSP + "/client/records.jsp";

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
