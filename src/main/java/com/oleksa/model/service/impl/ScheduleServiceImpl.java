package com.oleksa.model.service.impl;

import java.util.List;

import com.oleksa.model.dao.ScheduleDao;
import com.oleksa.model.entity.Schedule;
import com.oleksa.model.entity.User;
import com.oleksa.model.service.ScheduleService;

public class ScheduleServiceImpl implements ScheduleService {

	private ScheduleDao scheduleDao;

	public ScheduleServiceImpl(ScheduleDao scheduleDao) {
		this.scheduleDao = scheduleDao;
	}

	@Override
	public Schedule create(Schedule schedule) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Schedule> findAll() {
		return scheduleDao.findAll();
	}

	@Override
	public List<Schedule> findAllByMaster(User master) {
		return scheduleDao.findAllByMasterId(master.getId());
	}

}
