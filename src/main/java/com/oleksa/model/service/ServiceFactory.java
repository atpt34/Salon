package com.oleksa.model.service;

public interface ServiceFactory {
	
	UserService getUserService();
	
	ScheduleService getScheduleService();
	
	RecordService getRecordService();
    
}
