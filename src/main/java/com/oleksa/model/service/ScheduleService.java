package com.oleksa.model.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.oleksa.model.entity.Schedule;
import com.oleksa.model.entity.User;
import com.oleksa.model.pagination.PaginationResult;

public interface ScheduleService {
	
	Schedule create(Schedule schedule);
	
	List<Schedule> findAll();
	
	List<Schedule> findAllByMaster(User master);

	PaginationResult<Schedule> findPage(int page);
	
	List<Schedule> findFreeOnDayAndTime(LocalDate day, LocalTime time);

}
