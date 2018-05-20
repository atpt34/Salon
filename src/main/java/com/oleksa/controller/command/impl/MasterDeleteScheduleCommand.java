package com.oleksa.controller.command.impl;

import static com.oleksa.controller.constants.MessagesConstants.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.oleksa.controller.command.Command;
import com.oleksa.controller.exception.UnparsableIdException;
import com.oleksa.controller.validator.ValidatorUtil;
import com.oleksa.model.entity.Schedule;
import com.oleksa.model.logger.Loggable;
import com.oleksa.model.service.ScheduleService;

public class MasterDeleteScheduleCommand implements Command, Loggable {

	private ScheduleService scheduleService;

	public MasterDeleteScheduleCommand(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String execute(HttpServletRequest request) {
		String parameter = request.getParameter(PARAM_ID);
		try {
			int id = ValidatorUtil.parseIdParameter(parameter);
			List<Schedule> found = (List<Schedule>) request.getSession().getAttribute(ATTRIBUTE_SCHEDULES);
			if (Objects.isNull(found) || found.isEmpty()) {
				getLogger().error(MSG_NO_PREV_SEARCH);
				return SERVERPAGE_MASTER_SCHEDULES;
			}
			Optional<Schedule> schedule = found.stream().filter(k -> k.getId().equals(id) ).findFirst();
			if (!schedule.isPresent()) {
				getLogger().error(MSG_NO_PREV_SEARCH);
				return SERVERPAGE_MASTER_SCHEDULES;
			}
			scheduleService.delete(schedule.get());
			request.getSession().removeAttribute(ATTRIBUTE_SCHEDULES);
		} catch (UnparsableIdException e) {
			getLogger().error(MSG_NO_PREV_SEARCH);
		}
		return PARENT_DIR + SERVERPAGE_MASTER_SCHEDULES;
	}

}
