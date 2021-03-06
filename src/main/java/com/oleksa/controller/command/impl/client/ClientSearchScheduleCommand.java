package com.oleksa.controller.command.impl.client;

import static com.oleksa.controller.constants.MessagesConstants.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.oleksa.controller.command.Command;
import com.oleksa.controller.exception.UnparsableDateParameter;
import com.oleksa.controller.exception.UnparsableTimeParameter;
import com.oleksa.controller.validator.ValidatorUtil;
import com.oleksa.model.entity.Schedule;
import com.oleksa.model.logger.Loggable;
import com.oleksa.model.service.ScheduleService;

/**
 * Performs search for schedules given client's date and time.
 * @author atpt34
 *
 */
public class ClientSearchScheduleCommand implements Command, Loggable {

    private ScheduleService scheduleService;

    public ClientSearchScheduleCommand(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        LocalTime time = null;
        LocalDate date = null;
        try {
            String timeParam = request.getParameter(PARAM_TIME);
            String dateParam = request.getParameter(PARAM_DATE);
            time = ValidatorUtil.parseTimeParameter(timeParam);
            date = ValidatorUtil.parseDateParameter(dateParam);
            List<Schedule> find = scheduleService.findFreeOnDayAndTime(date, time);
            Map<Integer, Schedule> collect = find.stream().collect(Collectors.toMap(Schedule::getId, Function.identity()));
            getLogger().info(collect);
            request.getSession().setAttribute(ATTRIBUTE_SCHEDULES, collect);
            request.setAttribute(PARAM_SCHEDULES, find);
            request.setAttribute(PARAM_DATE, date);
            request.setAttribute(PARAM_TIME, time);
            return SERVERPAGE_CLIENT_SEARCH_SCHEDULE;
        } catch (UnparsableTimeParameter | UnparsableDateParameter e) {
            getLogger().error(e);
            request.setAttribute(PARAM_ERROR, MSG_RETRY_SEARCH);
            request.setAttribute(PARAM_DATE, LocalDate.now());
            request.setAttribute(PARAM_TIME, LocalTime.NOON);
            return SERVERPAGE_CLIENT_SEARCH_SCHEDULE;
        } 
    }

}
