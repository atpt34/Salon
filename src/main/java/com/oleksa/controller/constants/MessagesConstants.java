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
    String PARAM_LANG_UA = "ua";
    String PARAM_LANG_EN = "en";
    String PARAM_PAGE = "page";
    String PARAM_TEXT = "text";
    String PARAM_STARS = "stars";
    String PARAM_TIME = "time";
    String PARAM_START_TIME = "start_time";
    String PARAM_FINISH_TIME = "finish_time";
    String PARAM_DATE = "date";
    String PARAM_ID = "id";
    String PARAM_TOTAL_PAGES = "totalPages";
    String PARAM_ERROR = "error";
    
    String PARAM_SCHEDULES = "schedules";
    String PARAM_RECORDS = "records";
    String PARAM_USERS = "users";
    
    String URL_CHANGE_LANGUAGE = "/changeLanguage";
    String URL_REGISTER = "/register";
    String URL_LOGOUT = "/logout";
    String URL_LOGIN = "/login";
    String URL_INDEX = "/index";
    
    String URL_ADMIN_USERS = "/admin/users";
    String URL_ADMIN_UPDATE_USER = "/admin/update_user";
    String URL_ADMIN_DELETE_USER = "/admin/delete_user";
    String URL_ADMIN_COMMENTS = "/admin/comments";
    String URL_CLIENT_RECORDS = "/client/records";
    String URL_CLIENT_CREATE_RECORD = "/client/create_record";
    String URL_CLIENT_DELETE_RECORD = "/client/delete_record";
    String URL_CLIENT_CREATE_COMMENT = "/client/create_comment";
    String URL_CLIENT_SEARCH_SCHEDULE = "/client/search_schedule";
    String URL_MASTER_SCHEDULES = "/master/schedules";
    String URL_MASTER_CREATE_SCHEDULE = "/master/create_schedule";
    String URL_MASTER_DELETE_SCHEDULE = "/master/delete_schedule";
    
    String PAGE_REDIRECT = "redirect:";
    String PAGE_LOGIN = "/login_page";
    String PAGE_REGISTER = "/register_page";
    String PAGE_ADMIN = "/admin_page";
    
    String PAGE_MASTER = "/master_page";
    String PAGE_MASTER_CREATE_SCHEDULE = "/master/create_schedule_page";
    String PAGE_CLIENT_CREATE_COMMENT = "/client/create_comment_page";
    String PAGE_CLIENT_SEARCH_SCHEDULE = "/client/search_schedule_page";
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
    String SERVERPAGE_MASTER_SCHEDULES = PARENT_DIR + WEBINF_JSP + "/master/schedules.jsp";
    String SERVERPAGE_MASTER_CREATE_SCHEDULE = PARENT_DIR + WEBINF_JSP + "/master/create_schedule.jsp";
    String SERVERPAGE_CLIENT_CREATE_COMMENT = PARENT_DIR + WEBINF_JSP + "/client/create_comment.jsp";
    String SERVERPAGE_CLIENT_SEARCH_SCHEDULE = PARENT_DIR + WEBINF_JSP + "/client/search_schedule.jsp";
    String SERVERPAGE_CLIENT_RECORDS = PARENT_DIR + WEBINF_JSP + "/client/records.jsp";

    String ATTRIBUTE_SCHEDULES = "attribute_schedules";
    String ATTRIBUTE_RECORDS = "attribute_records";
    String ATTRIBUTE_USERS = "attribute_users";
    
    String FOLDER_ADMIN = "admin";
    String FOLDER_CLIENT = "client";
    String FOLDER_MASTER = "master";
    
    String MSG_ACCESS_DENIED = "error.access.denied";
    String MSG_ALREADY_LOGIN = "error.already.login";
    String MSG_INVALID_INPUT = "error.invalid.input";
    String MSG_INVALID_CREDENTIALS = "error.invalid.credentials";
    String MSG_NAME_IN_USE = "error.inuse.name";
    String MSG_EMAIL_IN_USE = "error.inuse.email";
    String MSG_RETRY_SEARCH = "error.retry.search";
    String MSG_NO_PREV_SEARCH = "error.no.prev.search";
    String MSG_NO_CHOSEN_SCHEDULES = "error.no.chosen.schedules";
    String MSG_NO_CHOSEN_RECORDS = "error.no.chosen.records";
    String MSG_NO_CHOSEN_USERS = "error.no.chosen.users";
    
}
