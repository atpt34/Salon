package com.oleksa.model.dao;

import com.oleksa.model.dao.ScheduleDao;

/**
 * 
 * @author atpt34
 *
 */
public abstract class DaoFactory {

    DaoFactory() { }

    private static class FactoryHolder {
        static final DaoFactory FACTORY = new JdbcDaoFactory();
    }

    public static DaoFactory getFactory() {
        return FactoryHolder.FACTORY;
    }

    public abstract UserDao getUserDao();

    public abstract ScheduleDao getScheduleDao();

    public abstract RecordDao getRecordDao();

}
