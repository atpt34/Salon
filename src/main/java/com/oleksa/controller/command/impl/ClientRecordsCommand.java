package com.oleksa.controller.command.impl;

import static com.oleksa.controller.constants.MessagesConstants.*;

import javax.servlet.http.HttpServletRequest;

import com.oleksa.controller.command.Command;

public class ClientRecordsCommand implements Command {

//	private RecordService service;
	
	@Override
	public String execute(HttpServletRequest request) {
//		request.setAttribute(PARAM_RECORDS, service);
		return SERVERPAGE_CLIENT_RECORDS;
	}

}
