package com.oleksa.controller.command.impl;

import static com.oleksa.controller.constants.MessagesConstants.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.oleksa.controller.command.Command;
import com.oleksa.controller.exception.UnparsableIdException;
import com.oleksa.controller.validator.ValidatorUtil;
import com.oleksa.model.entity.Comment;
import com.oleksa.model.entity.Record;
import com.oleksa.model.entity.User;
import com.oleksa.model.logger.Loggable;
import com.oleksa.model.service.RecordService;

public class ClientCommentCommand implements Command, Loggable {

	private RecordService recordService;

	public ClientCommentCommand(RecordService recordService) {
		this.recordService = recordService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String execute(HttpServletRequest request) {
		Optional<User> client = (Optional<User>) request.getSession().getAttribute(PARAM_USER);
		String text = request.getParameter(PARAM_TEXT);
		String stars = request.getParameter(PARAM_STARS);
		String idParam = request.getParameter(PARAM_ID);
		int id;
		try {
			id = ValidatorUtil.parseIdParameter(idParam);
		} catch (UnparsableIdException e) {
			getLogger().error(MSG_NO_CHOSEN_RECORDS);
			request.setAttribute(PARAM_ERROR, MSG_NO_CHOSEN_RECORDS);
			return SERVERPAGE_CLIENT;
		}
		List<Record> found = (List<Record>) request.getSession().getAttribute(ATTRIBUTE_RECORDS);
		if (Objects.isNull(found) || found.isEmpty()) {
			getLogger().error(MSG_NO_CHOSEN_RECORDS);
			request.setAttribute(PARAM_ERROR, MSG_NO_CHOSEN_RECORDS);
			return SERVERPAGE_CLIENT;
		}
		Comment comment = new Comment(null, 
				ValidatorUtil.parseTextParameter(text), ValidatorUtil.parseStarsParameter(stars));
		Optional<Record> first = found.stream().filter(k -> k.getId().equals(id)).findFirst();
		if (!first.isPresent()) {
			getLogger().error(MSG_NO_CHOSEN_RECORDS);
			request.setAttribute(PARAM_ERROR, MSG_NO_CHOSEN_RECORDS);
			return SERVERPAGE_CLIENT;
		}
		Record record = first.get();
		record.setComment(comment);
		recordService.update(record);
		return SERVERPAGE_CLIENT_RECORDS;
	}

}
