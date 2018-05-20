package com.oleksa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;

import com.oleksa.controller.command.Command;
import com.oleksa.controller.validator.ValidatorUtil;
import com.oleksa.model.entity.Schedule;
import com.oleksa.model.entity.User;
import com.oleksa.model.logger.Loggable;
import com.oleksa.model.pagination.PaginationResult;
import com.oleksa.model.service.ScheduleService;

import static com.oleksa.controller.constants.MessagesConstants.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class IndexCommand implements Command, Loggable {

	private ScheduleService scheduleService;

	public IndexCommand(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	@Override
	public String execute(HttpServletRequest request) {
		String pageParam = request.getParameter(PARAM_PAGE);
		int page = ValidatorUtil.parsePageParameter(pageParam);
		PaginationResult<Schedule> pageResult = scheduleService.findPage(page);
		getLogger().debug(pageResult);
		Map<User, List<Schedule>> collect = pageResult.getGroupedItems(Schedule::getMaster);
		getLogger().debug(collect);
		request.setAttribute(PARAM_SCHEDULES, collect);
		request.setAttribute(PARAM_TOTAL_PAGES, pageResult.getTotalPages());
		request.setAttribute(PARAM_PAGE, pageResult.getPage());
		return SERVERPAGE_INDEX;
	}

}
