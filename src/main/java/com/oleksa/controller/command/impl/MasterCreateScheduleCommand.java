package com.oleksa.controller.command.impl;

import static com.oleksa.controller.constants.MessagesConstants.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.oleksa.controller.command.Command;
import com.oleksa.controller.exception.UnparsableDateParameter;
import com.oleksa.controller.exception.UnparsableTimeParameter;
import com.oleksa.controller.validator.ValidatorUtil;
import com.oleksa.model.entity.Schedule;
import com.oleksa.model.entity.User;
import com.oleksa.model.exception.InvalidIntervalException;
import com.oleksa.model.logger.Loggable;
import com.oleksa.model.service.ScheduleService;

public class MasterCreateScheduleCommand implements Command, Loggable {

	private ScheduleService scheduleService;

	public MasterCreateScheduleCommand(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String execute(HttpServletRequest request) {
		LocalDate day = null;
		try {
			String dateParam = request.getParameter(PARAM_DATE);
			day = ValidatorUtil.parseDateParameter(dateParam);
		} catch (UnparsableDateParameter e) {
			getLogger().error(e);
			request.setAttribute(PARAM_ERROR, MSG_INVALID_INPUT);
			return PARENT_DIR + SERVERPAGE_MASTER;
		}
		LocalTime startHour = null;
		try {
			String startTimeParam = request.getParameter(PARAM_START_TIME);
			startHour = ValidatorUtil.parseTimeParameter(startTimeParam);
		} catch (UnparsableTimeParameter e) {
			getLogger().error(e);
			request.setAttribute(PARAM_ERROR, MSG_INVALID_INPUT);
			return PARENT_DIR + SERVERPAGE_MASTER;
		}
		LocalTime endHour = null;
		try {
			String finishTimeParam = request.getParameter(PARAM_FINISH_TIME);
			endHour = ValidatorUtil.parseTimeParameter(finishTimeParam);
		} catch (UnparsableTimeParameter e) {
			getLogger().error(e);
			request.setAttribute(PARAM_ERROR, MSG_INVALID_INPUT);
			return PARENT_DIR + SERVERPAGE_MASTER;
		}
		Optional<User> master = (Optional<User>) request.getSession().getAttribute(PARAM_USER);
		Schedule schedule = new Schedule(null, master.get(), day, startHour, endHour, null);
		try {
			scheduleService.create(schedule);
		} catch (InvalidIntervalException e) {
			getLogger().error(e);
			request.setAttribute(PARAM_ERROR, MSG_INVALID_INPUT);
			return PARENT_DIR + SERVERPAGE_MASTER;
		}
		return PAGE_REDIRECT + PAGE_MASTER;
	}

}
