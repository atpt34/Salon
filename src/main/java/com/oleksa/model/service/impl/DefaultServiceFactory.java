package com.oleksa.model.service.impl;

import com.oleksa.model.dao.DaoFactory;
import com.oleksa.model.service.RecordService;
import com.oleksa.model.service.ScheduleService;
import com.oleksa.model.service.ServiceFactory;
import com.oleksa.model.service.UserService;
import com.oleksa.model.service.impl.RecordServiceImpl;
import com.oleksa.model.service.impl.ScheduleServiceImpl;
import com.oleksa.model.service.impl.UserServiceImpl;

/**
 * 
 * @author atpt34
 *
 */
public class DefaultServiceFactory implements ServiceFactory {

    private static class UserServiceImplHolder {
        static final UserService IMPL;
        static {
            IMPL = new UserServiceImpl(DaoFactory.getFactory().getUserDao()); 
        }
    }

    @Override
    public UserService getUserService() {
        return UserServiceImplHolder.IMPL;
    }

    private static class ScheduleServiceImplHolder {
        static final ScheduleService IMPL;
        static {
            IMPL = new ScheduleServiceImpl(DaoFactory.getFactory().getScheduleDao()); 
        }
    }

    @Override
    public ScheduleService getScheduleService() {
        return ScheduleServiceImplHolder.IMPL;
    }

    private static class RecordServiceImplHolder {
        static final RecordService IMPL;
        static {
            IMPL = new RecordServiceImpl(DaoFactory.getFactory().getRecordDao()); 
        }
    }

    @Override
    public RecordService getRecordService() {
        return RecordServiceImplHolder.IMPL;
    }

}
