package com.oleksa.controller.command.impl;

import static com.oleksa.controller.constants.MessagesConstants.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.oleksa.controller.command.Command;
import com.oleksa.controller.validator.ValidatorUtil;
import com.oleksa.model.entity.Schedule;
import com.oleksa.model.entity.User;
import com.oleksa.model.service.ScheduleService;

public class MasterCreateScheduleCommand implements Command {

	private ScheduleService scheduleService;

	public MasterCreateScheduleCommand(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	@Override
	public String execute(HttpServletRequest request) {
		String dateParam = request.getParameter(PARAM_DATE);
		LocalDate day = ValidatorUtil.parseDateParameter(dateParam);
		String startTimeParam = request.getParameter(PARAM_START_TIME);
		LocalTime startHour = ValidatorUtil.parseTimeParameter(startTimeParam);
		String finishTimeParam = request.getParameter(PARAM_FINISH_TIME);
		LocalTime endHour = ValidatorUtil.parseTimeParameter(finishTimeParam);
		Optional<User> master = (Optional<User>) request.getSession().getAttribute(PARAM_USER);
		Schedule schedule = new Schedule(null, master.get(), day, startHour, endHour, null);
		scheduleService.create(schedule);
		return SERVERPAGE_MASTER_SCHEDULES;
	}

}
