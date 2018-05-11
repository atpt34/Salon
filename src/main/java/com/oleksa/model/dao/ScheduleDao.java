package com.oleksa.model.dao;

import java.util.List;

import com.oleksa.model.entity.Schedule;

public interface ScheduleDao extends CrudDao<Schedule, Integer> {

	List<Schedule> findAllByMasterId(int masterId);
	
	
}
