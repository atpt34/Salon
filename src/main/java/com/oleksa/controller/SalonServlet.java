package com.oleksa.controller;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oleksa.controller.command.Command;
import com.oleksa.controller.command.impl.ChangeLanguageCommand;
import com.oleksa.controller.command.impl.IndexCommand;
import com.oleksa.controller.command.impl.LoginCommand;
import com.oleksa.controller.command.impl.LogoutCommand;
import com.oleksa.controller.command.impl.RegisterCommand;
import com.oleksa.controller.command.impl.admin.AdminCommentsCommand;
import com.oleksa.controller.command.impl.admin.AdminDeleteUserCommand;
import com.oleksa.controller.command.impl.admin.AdminUpdateUserCommand;
import com.oleksa.controller.command.impl.admin.AdminUsersCommand;
import com.oleksa.controller.command.impl.client.ClientCommentCommand;
import com.oleksa.controller.command.impl.client.ClientCreateRecordCommand;
import com.oleksa.controller.command.impl.client.ClientDeleteCommand;
import com.oleksa.controller.command.impl.client.ClientRecordsCommand;
import com.oleksa.controller.command.impl.client.ClientSearchScheduleCommand;
import com.oleksa.controller.command.impl.master.MasterCreateScheduleCommand;
import com.oleksa.controller.command.impl.master.MasterDeleteScheduleCommand;
import com.oleksa.controller.command.impl.master.MasterSchedulesCommand;
import com.oleksa.model.service.ServiceFactory;
import com.oleksa.model.service.impl.DefaultServiceFactory;

import static com.oleksa.controller.constants.MessagesConstants.*;

/**
 * Controller of web application.
 * 
 * @author atpt34
 *
 */
public final class SalonServlet extends HttpServlet {
    private static final long serialVersionUID = 4389426352834378054L;

    private static final Logger LOGGER = LogManager.getLogger(SalonServlet.class);

    private final Map<String, Command> commands;
    private final ServiceFactory serviceFactory;

    public SalonServlet() {
        LOGGER.info("SalonServlet()");
        commands = new ConcurrentHashMap<>();
        this.serviceFactory = new DefaultServiceFactory();
    }

    public SalonServlet(ServiceFactory serviceFactory, Map<String, Command> commands) {
        this.serviceFactory = serviceFactory;
        this.commands = commands;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        LOGGER.info("SalonServlet init()");
        commands.put(URL_LOGIN, new LoginCommand(serviceFactory.getUserService()));
        commands.put(URL_LOGOUT, new LogoutCommand());
        commands.put(URL_REGISTER, new RegisterCommand(serviceFactory.getUserService()));
        commands.put(URL_CHANGE_LANGUAGE, new ChangeLanguageCommand());
        commands.put(URL_ADMIN_USERS, new AdminUsersCommand(serviceFactory.getUserService()));
        commands.put(URL_ADMIN_UPDATE_USER, new AdminUpdateUserCommand(serviceFactory.getUserService()));
        commands.put(URL_ADMIN_DELETE_USER, new AdminDeleteUserCommand(serviceFactory.getUserService()));
        commands.put(URL_ADMIN_COMMENTS, new AdminCommentsCommand(serviceFactory.getRecordService()));
        commands.put(URL_CLIENT_RECORDS, new ClientRecordsCommand(serviceFactory.getRecordService()));
        commands.put(URL_CLIENT_CREATE_RECORD, new ClientCreateRecordCommand(serviceFactory.getRecordService()));
        commands.put(URL_CLIENT_DELETE_RECORD, new ClientDeleteCommand(serviceFactory.getRecordService()));
        commands.put(URL_CLIENT_CREATE_COMMENT, new ClientCommentCommand(serviceFactory.getRecordService()));
        commands.put(URL_CLIENT_SEARCH_SCHEDULE, new ClientSearchScheduleCommand(serviceFactory.getScheduleService()));
        commands.put(URL_MASTER_SCHEDULES, new MasterSchedulesCommand(serviceFactory.getScheduleService()));
        commands.put(URL_MASTER_CREATE_SCHEDULE, new MasterCreateScheduleCommand(serviceFactory.getScheduleService()));
        commands.put(URL_MASTER_DELETE_SCHEDULE, new MasterDeleteScheduleCommand(serviceFactory.getScheduleService()));

        commands.put(PAGE_LOGIN, r -> SERVERPAGE_LOGIN);
        commands.put(PAGE_REGISTER, r -> SERVERPAGE_REGISTER);
        commands.put(PAGE_ADMIN, r -> SERVERPAGE_ADMIN);
        commands.put(PAGE_CLIENT, r -> SERVERPAGE_CLIENT);
        commands.put(PAGE_CLIENT_SEARCH_SCHEDULE, r -> SERVERPAGE_CLIENT_SEARCH_SCHEDULE);
        commands.put(PAGE_MASTER, r -> SERVERPAGE_MASTER);
        commands.put(PAGE_MASTER_CREATE_SCHEDULE, r -> SERVERPAGE_MASTER_CREATE_SCHEDULE);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        LOGGER.info("request uri: " + path);
        Command command = commands.getOrDefault(path.replaceAll(URL_CONTEXT, ""), new IndexCommand(serviceFactory.getScheduleService()));
        String page = command.execute(request);
        if (page.contains(PAGE_REDIRECT)) {
            response.sendRedirect(page.replace(PAGE_REDIRECT, ""));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
