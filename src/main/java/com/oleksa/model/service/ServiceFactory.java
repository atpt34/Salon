package com.oleksa.model.service;

/**
 * 
 * @author atpt34
 *
 */
public interface ServiceFactory {

    UserService getUserService();

    ScheduleService getScheduleService();

    RecordService getRecordService();

}
