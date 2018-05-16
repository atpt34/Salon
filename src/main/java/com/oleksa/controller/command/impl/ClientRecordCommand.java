package com.oleksa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import static com.oleksa.controller.constants.MessagesConstants.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.oleksa.controller.command.Command;
import com.oleksa.model.entity.Comment;
import com.oleksa.model.entity.Record;
import com.oleksa.model.entity.Schedule;
import com.oleksa.model.entity.User;
import com.oleksa.model.exception.RecordOccupiedException;
import com.oleksa.model.service.RecordService;

public class ClientRecordCommand implements Command {

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
		System.out.println(Arrays.toString(ids));
		Set<Schedule> schedules = new HashSet<>();
		for (String id : ids) { // no id check, and if empty
			schedules.add(new Schedule(Integer.parseInt(id), null, null, null, null, null));
		}
		Comment comment = null;
		Record record = new Record(null, client.get(), time, date, comment, schedules);
		try {
			recordService.create(record);
		} catch (RecordOccupiedException e) {
			// handle exception
		}
		return PARENT_DIR + SERVERPAGE_CLIENT;
	}

}
