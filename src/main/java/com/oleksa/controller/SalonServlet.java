package com.oleksa.controller;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oleksa.controller.command.Command;
import com.oleksa.controller.command.impl.AdminUsersCommand;
import com.oleksa.controller.command.impl.ChangeLanguageCommand;
import com.oleksa.controller.command.impl.ClientRecordsCommand;
import com.oleksa.controller.command.impl.LoginCommand;
import com.oleksa.controller.command.impl.LogoutCommand;
import com.oleksa.controller.command.impl.MasterSchedulesCommand;
import com.oleksa.controller.command.impl.RegisterCommand;
import com.oleksa.controller.command.impl.ScheduleCommand;
import com.oleksa.model.service.ServiceFactory;

import static com.oleksa.controller.constants.MessagesConstants.*;

public final class SalonServlet extends HttpServlet {
    
    private final Map<String, Command> commands;
    private final ServiceFactory serviceFactory;

    public SalonServlet() {
        commands = new ConcurrentHashMap<>();
        this.serviceFactory = new ServiceFactory();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        commands.put(URL_LOGIN, new LoginCommand(serviceFactory.getUserService()));
        commands.put(URL_LOGOUT, new LogoutCommand());
        commands.put(URL_REGISTER, new RegisterCommand(serviceFactory.getUserService()));
        commands.put(URL_CHANGE_LANGUAGE, new ChangeLanguageCommand());
        commands.put(URL_SCHEDULES, new ScheduleCommand(serviceFactory.getScheduleService()));
        commands.put(URL_ADMIN_USERS, new AdminUsersCommand(serviceFactory.getUserService()));
        commands.put(URL_CLIENT_RECORDS, new ClientRecordsCommand());
        commands.put(URL_MASTER_SCHEDULES, new MasterSchedulesCommand(serviceFactory.getScheduleService()));
        
        commands.put(PAGE_LOGIN, r -> SERVERPAGE_LOGIN);
        commands.put(PAGE_REGISTER, r -> SERVERPAGE_REGISTER);
        commands.put(PAGE_ADMIN, r -> SERVERPAGE_ADMIN);
        commands.put(PAGE_CLIENT, r -> SERVERPAGE_CLIENT);
        commands.put(PAGE_MASTER, r -> SERVERPAGE_MASTER);
        
        
    }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        System.out.println("servlet path: " + path);
//        Command command = commands.getOrDefault(path.replaceAll(".*/BeautySalon/", ""), r -> SERVERPAGE_INDEX);
        Command command = commands.getOrDefault(path, r -> SERVERPAGE_INDEX);
        String page = command.execute(request);
        if (page.contains(PAGE_REDIRECT)) {
            response.sendRedirect(page.replace(PAGE_REDIRECT, ""));
        } else {
            request.getRequestDispatcher(page).forward(request,response);
        }
    }
}
