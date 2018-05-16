package com.oleksa.controller.command.impl;

import static com.oleksa.controller.constants.MessagesConstants.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.oleksa.controller.command.Command;
import com.oleksa.controller.validator.ValidatorUtil;
import com.oleksa.model.entity.Comment;
import com.oleksa.model.entity.Record;
import com.oleksa.model.entity.User;
import com.oleksa.model.service.RecordService;

public class ClientCommentCommand implements Command {

	private RecordService recordService;

	public ClientCommentCommand(RecordService recordService) {
		this.recordService = recordService;
	}

	@Override
	public String execute(HttpServletRequest request) {
		Optional<User> client = (Optional<User>) request.getSession().getAttribute(PARAM_USER);
		String text = request.getParameter(PARAM_TEXT);
		String stars = request.getParameter(PARAM_STARS);
		Comment comment = new Comment(null, 
				ValidatorUtil.parseTextParameter(text), ValidatorUtil.parseStarsParameter(stars));
		LocalTime hour = null;
		LocalDate day = null;
		Record record = new Record(null, client.get(), hour, day, comment, null);
		recordService.setComment(record);
		return SERVERPAGE_CLIENT_RECORDS;
	}

}
