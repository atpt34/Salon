package com.oleksa.model.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.oleksa.model.dao.ScheduleDao;
import com.oleksa.model.entity.Schedule;
import com.oleksa.model.entity.User;
import com.oleksa.model.exception.InvalidIntervalException;
import com.oleksa.model.pagination.PaginationResult;
import com.oleksa.model.service.ScheduleService;

/**
 * 
 * @author atpt34
 *
 */
public class ScheduleServiceImpl implements ScheduleService {

    private static final int DEFAULT_ITEMS_ON_PAGE = 5;
    private ScheduleDao scheduleDao;

    public ScheduleServiceImpl(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    @Override
    public Schedule create(Schedule schedule) throws InvalidIntervalException {
        return scheduleDao.create(schedule);
    }

    @Override
    public List<Schedule> findAll() {
        return scheduleDao.findAll();
    }

    @Override
    public List<Schedule> findAllByMaster(User master) {
        return scheduleDao.findAllByMasterIdWithRecords(master.getId());
    }

    @Override
    public PaginationResult<Schedule> findPage(int page) {
        return scheduleDao.findPage(page, DEFAULT_ITEMS_ON_PAGE);
    }

    @Override
    public List<Schedule> findFreeOnDayAndTime(LocalDate day, LocalTime time) {
        return scheduleDao.findFreeOnDayAndTime(day, time);
    }

    @Override
    public void delete(Schedule schedule) {
        scheduleDao.deleteById(schedule.getId());
    }

}
