package com.oleksa.model.service;

import java.util.List;

import com.oleksa.model.entity.Schedule;
import com.oleksa.model.entity.User;

public interface ScheduleService {
	
	Schedule create(Schedule schedule);
	
	List<Schedule> findAll();
	
	List<Schedule> findAllByMaster(User master);

}
