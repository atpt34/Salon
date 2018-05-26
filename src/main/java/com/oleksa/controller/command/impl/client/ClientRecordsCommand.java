package com.oleksa.controller.command.impl.client;

import static com.oleksa.controller.constants.MessagesConstants.*;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.oleksa.controller.command.Command;
import com.oleksa.model.entity.Record;
import com.oleksa.model.entity.User;
import com.oleksa.model.service.RecordService;

/**
 * Shows records of given client.
 * @author atpt34
 *
 */
public class ClientRecordsCommand implements Command {

    private RecordService recordService;

    public ClientRecordsCommand(RecordService service) {
        this.recordService = service;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String execute(HttpServletRequest request) {
        Optional<User> client = (Optional<User>) request.getSession().getAttribute(PARAM_USER);
        List<Record> find = recordService.findAllByClient(client.get());
        request.setAttribute(PARAM_RECORDS, find);
        request.getSession().setAttribute(ATTRIBUTE_RECORDS, find);
        return SERVERPAGE_CLIENT_RECORDS;
    }

}
