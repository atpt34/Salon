package com.oleksa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import static com.oleksa.controller.constants.MessagesConstants.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.oleksa.controller.command.Command;
import com.oleksa.controller.exception.UnparsableIdException;
import com.oleksa.controller.validator.ValidatorUtil;
import com.oleksa.model.entity.Comment;
import com.oleksa.model.entity.Record;
import com.oleksa.model.entity.Schedule;
import com.oleksa.model.entity.User;
import com.oleksa.model.exception.RecordOccupiedException;
import com.oleksa.model.logger.Loggable;
import com.oleksa.model.service.RecordService;

public class ClientRecordCommand implements Command, Loggable {

	private RecordService recordService;

	public ClientRecordCommand(RecordService recordService) {
		this.recordService = recordService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String execute(HttpServletRequest request) {
		Optional<User> client = (Optional<User>) request.getSession().getAttribute(PARAM_USER);
		LocalTime time = LocalTime.parse(request.getParameter(PARAM_TIME));
		LocalDate date = LocalDate.parse(request.getParameter(PARAM_DATE));
		String[] ids = request.getParameterValues(PARAM_ID);
		if(Objects.isNull(ids)) {
			getLogger().error(MSG_NO_PREV_SEARCH);
			request.setAttribute(PARAM_ERROR, MSG_RETRY_SEARCH);
			return SERVERPAGE_CLIENT_SEARCH_SCHEDULE;
		}
		getLogger().debug(Arrays.toString(ids));
		Set<Integer> idSet = new HashSet<>();
		for (String idParam : ids) {
			try {
				idSet.add(ValidatorUtil.parseIdParameter(idParam));
			} catch (UnparsableIdException e) {
				getLogger().error(e);
				return PARENT_DIR + SERVERPAGE_CLIENT;
			}
		}
		getLogger().debug(idSet);
		List<Schedule> found = (List<Schedule>) request.getSession().getAttribute(ATTRIBUTE_SCHEDULES);
		if (Objects.isNull(found)) {
			getLogger().error(MSG_NO_PREV_SEARCH);
			request.setAttribute(PARAM_ERROR, MSG_RETRY_SEARCH);
			return SERVERPAGE_CLIENT_SEARCH_SCHEDULE;
		}
		getLogger().debug(found);
		Set<Schedule> schedules = new HashSet<>();
		try {
			found.stream().filter(k -> idSet.contains(k.getId())).forEach(k -> schedules.add(k));
			getLogger().debug(schedules);
			Record record = new Record(null, client.get(), time, date, null, schedules);
			recordService.create(record);
			request.getSession().removeAttribute(ATTRIBUTE_SCHEDULES);
		} catch (RecordOccupiedException e) {
			getLogger().error(e);
			request.setAttribute(PARAM_ERROR, MSG_RETRY_SEARCH);
			return SERVERPAGE_CLIENT_SEARCH_SCHEDULE;
		}
		return PARENT_DIR + SERVERPAGE_CLIENT;
	}

}
