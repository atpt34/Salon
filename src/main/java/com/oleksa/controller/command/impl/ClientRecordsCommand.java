package com.oleksa.controller.command.impl;

import static com.oleksa.controller.constants.MessagesConstants.*;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.oleksa.controller.command.Command;
import com.oleksa.model.entity.User;
import com.oleksa.model.service.RecordService;

public class ClientRecordsCommand implements Command {

	private RecordService recordService;
	
	public ClientRecordsCommand(RecordService service) {
		this.recordService = service;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String execute(HttpServletRequest request) {
		Optional<User> client = (Optional<User>) request.getSession().getAttribute(PARAM_USER);
		request.setAttribute(PARAM_RECORDS, recordService.findAllByClient(client.get()));
		return SERVERPAGE_CLIENT_RECORDS;
	}

}
