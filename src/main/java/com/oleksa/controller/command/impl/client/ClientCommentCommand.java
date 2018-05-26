package com.oleksa.controller.command.impl.client;

import static com.oleksa.controller.constants.MessagesConstants.*;

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

/**
 * Update record with given comment by user.
 * @author atpt34
 *
 */
public class ClientCommentCommand implements Command, Loggable {

    private RecordService recordService;

    public ClientCommentCommand(RecordService recordService) {
        this.recordService = recordService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String execute(HttpServletRequest request) {
        try {
            String text = request.getParameter(PARAM_TEXT);
            String stars = request.getParameter(PARAM_STARS);
            String idParam = request.getParameter(PARAM_ID);
            int id = ValidatorUtil.parseIdParameter(idParam);
            List<Record> found = (List<Record>) request.getSession().getAttribute(ATTRIBUTE_RECORDS);
            if (Objects.isNull(found) || found.isEmpty()) {
                return handleNoSearch(request);
            }
            Comment comment = new Comment(null, 
                    ValidatorUtil.parseTextParameter(text), ValidatorUtil.parseStarsParameter(stars));
            Optional<Record> first = found.stream().filter(k -> k.getId().equals(id)).findFirst();
            if (!first.isPresent()) {
                return handleNoSearch(request);
            }
            Record record = first.get();
            Optional<User> client = (Optional<User>) request.getSession().getAttribute(PARAM_USER);
            record.setClient(client.get());
            record.setComment(comment);
            recordService.update(record);
            request.getSession().removeAttribute(ATTRIBUTE_RECORDS);
            return PAGE_REDIRECT + PAGE_CLIENT;
        } catch (UnparsableIdException e) {
            return handleNoSearch(request);
        }
    }

    private String handleNoSearch(HttpServletRequest request) {
        getLogger().error(MSG_NO_CHOSEN_RECORDS);
        request.setAttribute(PARAM_ERROR, MSG_NO_CHOSEN_RECORDS);
        return PARENT_DIR + SERVERPAGE_CLIENT;
    }

}
