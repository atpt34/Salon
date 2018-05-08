package com.oleksa.model.dao;

public abstract class DaoFactory {

    DaoFactory() { }
    
    private static class FactoryHolder {
        static final DaoFactory FACTORY = new JdbcDaoFactory();
    }
    
    public static DaoFactory getFactory() {
        return FactoryHolder.FACTORY;
    }
    
    public abstract UserDao getUserDao();
}
