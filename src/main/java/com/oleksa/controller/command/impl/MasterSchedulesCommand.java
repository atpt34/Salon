package com.oleksa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import com.oleksa.controller.command.Command;
import com.oleksa.model.entity.User;
import com.oleksa.model.service.ScheduleService;

import static com.oleksa.controller.constants.MessagesConstants.*;

import java.util.Optional;

public class MasterSchedulesCommand implements Command {

	private ScheduleService scheduleService;

	public MasterSchedulesCommand(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	@Override
	public String execute(HttpServletRequest request) {
		System.out.println("master schedules cmd");
		Optional<User> master = (Optional<User>) request.getSession().getAttribute(PARAM_USER);
		request.setAttribute(PARAM_SCHEDULES, scheduleService.findAllByMaster(master.get()));
		return SERVERPAGE_MASTER_SCHEDULES;
	}

}
