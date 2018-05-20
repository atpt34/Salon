package com.oleksa.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import javax.sql.DataSource;

import com.oleksa.model.logger.Loggable;

public abstract class JdbcTemplate<T> implements Loggable {

    DataSource dataSource;
    
    private JdbcTemplate() {}
    
    JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    DataSource getDataSource() {
        return dataSource;
    }

    void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    List<T> findAll(String sql, Function<ResultSet, T> mapToType) {
        List<T> result = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                ) {
            while(resultSet.next()) {
                T t = mapToType.apply(resultSet);
                result.add(t);
            }
        } catch (SQLException e) {
            getLogger().error(e);
        }
        return result;
    }
    
    public List<T> findAllByForeignKey(String sql, int foreignId, Function<ResultSet, T> mapToType) {
    	List<T> result = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
        		PreparedStatement statement = connection.prepareStatement(sql);
                ) {
        		statement.setInt(1, foreignId);
        		try(ResultSet resultSet = statement.executeQuery();) {
        			while(resultSet.next()) {
                        T t = mapToType.apply(resultSet);
                        result.add(t);
                    }
                }
        } catch (SQLException e) {
        	getLogger().error(e);
        }
        return result;
	}
    
    Optional<T> findById(String sql, Function<ResultSet, T> mapToType, int id) {
        try(Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ) {
                    statement.setInt(1, id);
                    try(ResultSet resultSet = statement.executeQuery();) {
                        if(resultSet.next()) {
                            return Optional.of(mapToType.apply(resultSet));
                        }
                    }
            } catch (SQLException e) {
            	getLogger().error(e);
            }
            return Optional.empty();
    }
    
    Optional<T> findByName(String sql, Function<ResultSet, T> mapToType, String name) {
        try(Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ) {
                    statement.setString(1, name);
                    try(ResultSet resultSet = statement.executeQuery();) {
                        if(resultSet.next()) {
                            return Optional.of(mapToType.apply(resultSet));
                        }
                    }
            } catch (SQLException e) {
            	getLogger().error(e);
            }
            return Optional.empty();
    }
    
    void deleteById(String sql, int id) {
        try(Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ) {
                    statement.setInt(1, id);
                    statement.executeUpdate();
            } catch (SQLException e) {
            	getLogger().error(e);
                e.printStackTrace();
           }
    }
    
    int create(T t, String sql, BiConsumer<T, PreparedStatement> preparator) throws SQLException {
        try(Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ) {
                    preparator.accept(t, statement);
                    statement.executeUpdate();
                    try (ResultSet keys = statement.getGeneratedKeys()) {
                        if(keys.next()) {
                            return keys.getInt(1);
                        }
                    }
                    getLogger().error("no keys");
                    throw new RuntimeException("no keys");
         }
    }
    
    void update(T t, String sql, BiConsumer<T, PreparedStatement> preparator) throws SQLException {
        try(Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ) {
                    preparator.accept(t, statement);
                    statement.executeUpdate();
            }
    }
	
}
