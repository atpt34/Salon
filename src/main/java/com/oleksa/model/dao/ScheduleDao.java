package com.oleksa.model.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.oleksa.model.entity.Schedule;
import com.oleksa.model.exception.InvalidIntervalException;
import com.oleksa.model.pagination.PaginationResult;

/**
 * 
 * @author atpt34
 *
 */
public interface ScheduleDao extends CrudDao<Schedule, Integer> {

    @Override
    Schedule create(Schedule t) throws InvalidIntervalException;

    List<Schedule> findAllByMasterId(int masterId);

    List<Schedule> findAllByMasterIdWithRecords(int masterId);

    PaginationResult<Schedule> findPage(int page, int defaultItemsOnPage);

    List<Schedule> findFreeOnDayAndTime(LocalDate day, LocalTime time);
}
