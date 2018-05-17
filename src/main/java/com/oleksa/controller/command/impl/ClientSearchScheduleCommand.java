package com.oleksa.controller.command.impl;

import static com.oleksa.controller.constants.MessagesConstants.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.oleksa.controller.command.Command;
import com.oleksa.controller.validator.ValidatorUtil;
import com.oleksa.model.entity.Schedule;
import com.oleksa.model.service.ScheduleService;

public class ClientSearchScheduleCommand implements Command {

	private ScheduleService scheduleService;

	public ClientSearchScheduleCommand(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	@Override
	public String execute(HttpServletRequest request) {
		String timeParam = request.getParameter(PARAM_TIME);
		String dateParam = request.getParameter(PARAM_DATE);
		LocalTime time = ValidatorUtil.parseTimeParameter(timeParam);
		LocalDate date = ValidatorUtil.parseDateParameter(dateParam);
		List<Schedule> find = scheduleService.findFreeOnDayAndTime(date, time);
		request.getSession().setAttribute(ATTRIBUTE_SCHEDULES, find);
		request.setAttribute(PARAM_SCHEDULES, find);
		request.setAttribute(PARAM_DATE, date);
		request.setAttribute(PARAM_TIME, time);
		return SERVERPAGE_CLIENT_SEARCH_SCHEDULE;
	}

}
