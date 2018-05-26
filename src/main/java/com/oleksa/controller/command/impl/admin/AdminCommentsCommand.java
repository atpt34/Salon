package com.oleksa.controller.command.impl.admin;

import static com.oleksa.controller.constants.MessagesConstants.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.oleksa.controller.command.Command;
import com.oleksa.model.entity.Record;
import com.oleksa.model.service.RecordService;

/**
 * Shows comments made by clients.
 * 
 * @author atpt34
 *
 */
public class AdminCommentsCommand implements Command {

    private RecordService recordService;

    public AdminCommentsCommand(RecordService recordService) {
        this.recordService = recordService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        List<Record> find = recordService.findAllWithComments();
        request.getSession().setAttribute(ATTRIBUTE_RECORDS, find);
        request.setAttribute(PARAM_RECORDS, find);
        return SERVERPAGE_ADMIN_COMMENTS;
    }

}
