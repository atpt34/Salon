package com.oleksa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;

import com.oleksa.controller.command.Command;
import com.oleksa.controller.exception.UnparsableIdException;
import com.oleksa.controller.validator.ValidatorUtil;
import com.oleksa.model.entity.Record;
import com.oleksa.model.logger.Loggable;
import com.oleksa.model.service.RecordService;

import static com.oleksa.controller.constants.MessagesConstants.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ClientDeleteCommand implements Command, Loggable {

	private RecordService recordService;

	public ClientDeleteCommand(RecordService recordService) {
		this.recordService = recordService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String execute(HttpServletRequest request) {
		String parameter = request.getParameter(PARAM_ID);
		try {
			int id = ValidatorUtil.parseIdParameter(parameter);
			List<Record> found = (List<Record>) request.getSession().getAttribute(ATTRIBUTE_RECORDS);
			getLogger().info(found);
			if (Objects.isNull(found) || found.isEmpty()) {
				return handleNoPreviousSearch(request);
			}
			Optional<Record> record = found.stream().filter(k -> k.getId().equals(id) ).findFirst();
			getLogger().info(record);
			if (!record.isPresent()) {
				return handleNoPreviousSearch(request);
			}
			recordService.delete(record.get());
			request.getSession().removeAttribute(ATTRIBUTE_RECORDS);
			return PAGE_REDIRECT + PAGE_CLIENT;
		} catch (UnparsableIdException e) {
			return handleNoPreviousSearch(request);
		}
	}

	private String handleNoPreviousSearch(HttpServletRequest request) {
		getLogger().error(MSG_NO_PREV_SEARCH);
		request.setAttribute(PARAM_ERROR, MSG_NO_PREV_SEARCH);
		return PARENT_DIR + SERVERPAGE_CLIENT;
	}

}
