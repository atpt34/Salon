package com.oleksa.model.util;

import java.util.Objects;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceUtil {

    private static DataSource dataSource;
    
    static {
        dataSource = Objects.requireNonNull(initDataSource());
    }
    
    private static DataSource initDataSource() {
        try {
            InitialContext initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/salon");
            System.out.println(dataSource);
            return dataSource;
        } catch (NamingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public static DataSource getDataSource() {
        return dataSource;
    }

}
