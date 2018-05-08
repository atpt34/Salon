package com.oleksa.model.dao;

import com.oleksa.model.dao.impl.UserDaoImpl;

public class JdbcDaoFactory extends DaoFactory {

    private static class UserDaoHolder {
        static final UserDao userDao = new UserDaoImpl(DataSourceUtil.getDataSource());
    }
    
    @Override
    public UserDao getUserDao() {
        return UserDaoHolder.userDao;
    }

}
