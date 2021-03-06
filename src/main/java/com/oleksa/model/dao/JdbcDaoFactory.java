package com.oleksa.model.dao;

import com.oleksa.model.dao.impl.RecordDaoImpl;
import com.oleksa.model.dao.impl.ScheduleDaoImpl;
import com.oleksa.model.dao.impl.UserDaoImpl;
import com.oleksa.model.util.DataSourceUtil;

/**
 * Default factory for DaoFactory.
 * 
 * @author atpt34
 *
 */
public class JdbcDaoFactory extends DaoFactory {

    private static class UserDaoHolder {
        static final UserDao IMPL = new UserDaoImpl(DataSourceUtil.getDataSource());
    }

    @Override
    public UserDao getUserDao() {
        return UserDaoHolder.IMPL;
    }

    private static class ScheduleDaoHolder {
        static final ScheduleDao IMPL = new ScheduleDaoImpl(DataSourceUtil.getDataSource());
    }

    @Override
    public ScheduleDao getScheduleDao() {
        return ScheduleDaoHolder.IMPL;
    }

    private static class RecordDaoHolder {
        static final RecordDao IMPL = new RecordDaoImpl(DataSourceUtil.getDataSource());
    }

    @Override
    public RecordDao getRecordDao() {
        return RecordDaoHolder.IMPL;
    }

}
