package com.oleksa.model.service;

import com.oleksa.model.dao.DaoFactory;
import com.oleksa.model.service.impl.ScheduleServiceImpl;
import com.oleksa.model.service.impl.UserServiceImpl;

public class ServiceFactory {
	
	public ServiceFactory() {
		
	}
    
    private static class UserServiceImplHolder {
        static final UserServiceImpl IMPL;
        static {
            IMPL = new UserServiceImpl(DaoFactory.getFactory().getUserDao()); 
        }
    }
    
    public UserService getUserService() {
        return UserServiceImplHolder.IMPL;
    }
    
    private static class ScheduleServiceImplHolder {
        static final ScheduleServiceImpl IMPL;
        static {
            IMPL = new ScheduleServiceImpl(DaoFactory.getFactory().getScheduleDao()); 
        }
    }

	public ScheduleService getScheduleService() {
		return ScheduleServiceImplHolder.IMPL;
	}

}
