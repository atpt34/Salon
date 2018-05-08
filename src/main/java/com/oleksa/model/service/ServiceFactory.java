package com.oleksa.model.service;

import com.oleksa.model.dao.DaoFactory;
import com.oleksa.model.service.impl.UserServiceImpl;

public class ServiceFactory {

    private ServiceFactory() { }
    
    private static class FactoryHolder {
        static final ServiceFactory FACTORY = new ServiceFactory();
    }
    
    public static ServiceFactory getFactory() {
        return FactoryHolder.FACTORY;
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

}
