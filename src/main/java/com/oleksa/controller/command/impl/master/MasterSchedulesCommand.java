package com.oleksa.controller.command.impl.master;

import javax.servlet.http.HttpServletRequest;
import com.oleksa.controller.command.Command;
import com.oleksa.model.entity.Schedule;
import com.oleksa.model.entity.User;
import com.oleksa.model.service.ScheduleService;

import static com.oleksa.controller.constants.MessagesConstants.*;

import java.util.List;
import java.util.Optional;

/**
 * Shows schedules of given master.
 * @author atpt34
 *
 */
public class MasterSchedulesCommand implements Command {

    private ScheduleService scheduleService;

    public MasterSchedulesCommand(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String execute(HttpServletRequest request) {
        Optional<User> master = (Optional<User>) request.getSession().getAttribute(PARAM_USER);
        List<Schedule> find = scheduleService.findAllByMaster(master.get());
        request.setAttribute(PARAM_SCHEDULES, find);
        request.getSession().setAttribute(ATTRIBUTE_SCHEDULES, find);
        return SERVERPAGE_MASTER_SCHEDULES;
    }

}
