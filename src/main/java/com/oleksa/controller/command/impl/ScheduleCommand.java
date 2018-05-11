package com.oleksa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;

import com.oleksa.controller.command.Command;
import com.oleksa.model.service.ScheduleService;

import static com.oleksa.controller.constants.MessagesConstants.*;

public class ScheduleCommand implements Command {
	
	ScheduleService scheduleService;
	
	public ScheduleCommand(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	@Override
	public String execute(HttpServletRequest request) {
		request.setAttribute(PARAM_SCHEDULES, scheduleService.findAll());
		return SERVERPAGE_SCHEDULES;
	}

}
