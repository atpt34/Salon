package com.oleksa.model.util;

import java.util.Objects;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataSourceUtil {

	private static final Logger LOGGER = LogManager.getLogger(DataSourceUtil.class);
	
    private static DataSource dataSource;
    
    static {
        dataSource = Objects.requireNonNull(initDataSource());
    }
    
    private static DataSource initDataSource() {
        try {
            InitialContext initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/salon");
            LOGGER.info(dataSource);
            return dataSource;
        } catch (NamingException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }
    
    public static DataSource getDataSource() {
        return dataSource;
    }

}
