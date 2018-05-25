package com.oleksa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import static com.oleksa.controller.constants.MessagesConstants.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.oleksa.controller.command.Command;
import com.oleksa.controller.exception.UnparsableDateParameter;
import com.oleksa.controller.exception.UnparsableIdException;
import com.oleksa.controller.exception.UnparsableTimeParameter;
import com.oleksa.controller.validator.ValidatorUtil;
import com.oleksa.model.entity.Record;
import com.oleksa.model.entity.Schedule;
import com.oleksa.model.entity.User;
import com.oleksa.model.exception.RecordOccupiedException;
import com.oleksa.model.logger.Loggable;
import com.oleksa.model.service.RecordService;

public class ClientCreateRecordCommand implements Command, Loggable {

	private RecordService recordService;

	public ClientCreateRecordCommand(RecordService recordService) {
		this.recordService = recordService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String execute(HttpServletRequest request) {
		LocalTime time = null;
		LocalDate date = null;
		try {
			String timeParam = request.getParameter(PARAM_TIME);
			String dateParam = request.getParameter(PARAM_DATE);
			time = ValidatorUtil.parseTimeParameter(timeParam);
			date = ValidatorUtil.parseDateParameter(dateParam);
			String[] ids = request.getParameterValues(PARAM_ID);
			Map<Integer, Schedule> found = 
					(Map<Integer, Schedule>) request.getSession().getAttribute(ATTRIBUTE_SCHEDULES);
			if(Objects.isNull(ids) || (ids.length == 0) || Objects.isNull(found) || found.isEmpty()) {
				getLogger().error(MSG_NO_CHOSEN_SCHEDULES);
				return handleException(request, LocalTime.NOON, LocalDate.now());
			}
			getLogger().debug(Arrays.toString(ids));
			getLogger().debug(found);
			Set<Schedule> schedules = new HashSet<>();
			for (String idParam : ids) {
				int id = ValidatorUtil.parseIdParameter(idParam);
				if (found.containsKey(id)) {
					schedules.add(found.get(id));
				}
			}
			if (schedules.isEmpty()) {
				getLogger().error(MSG_NO_PREV_SEARCH);
				return handleException(request, LocalTime.NOON, LocalDate.now());
			}
			getLogger().debug(schedules);
			Optional<User> client = (Optional<User>) request.getSession().getAttribute(PARAM_USER);
			Record record = new Record(null, client.get(), time, date, null, schedules);
			recordService.create(record);
			request.getSession().removeAttribute(ATTRIBUTE_SCHEDULES);
			return PAGE_REDIRECT + PAGE_CLIENT;
		} catch (UnparsableTimeParameter | UnparsableDateParameter e) {
			getLogger().error(e);
			return handleException(request, LocalTime.NOON, LocalDate.now());
		} catch (UnparsableIdException | RecordOccupiedException e) {
			getLogger().error(e);
			return handleException(request, time, date);
		}
	}

	private String handleException(HttpServletRequest request, LocalTime time, LocalDate date) {
		request.setAttribute(PARAM_ERROR, MSG_RETRY_SEARCH);
		request.setAttribute(PARAM_DATE, date);
		request.setAttribute(PARAM_TIME, time);
		return SERVERPAGE_CLIENT_SEARCH_SCHEDULE;
	}

}
