package com.oleksa.model.dao.impl;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import com.oleksa.model.dao.UserDao;
import com.oleksa.model.entity.User;
import com.oleksa.model.entity.UserRole;
import com.oleksa.model.exception.NotUniqueNameException;
import com.oleksa.model.exception.NotUniqueEmailException;
import static com.oleksa.model.dao.impl.JdbcConstants.*;

public class UserDaoImpl extends JdbcTemplate<User> implements UserDao {

    public UserDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return super.findById(US_SELECT_BY_ID, UserDaoImpl::mapToUser, id);
    }

    @Override
    public List<User> findAll() {
        return super.findAll(US_SELECT_ALL, UserDaoImpl::mapToUser);
    }
    
    @Override
    public Optional<User> findByName(String name) {
        return super.findByName(US_SELECT_BY_NAME, UserDaoImpl::mapToUser, name);
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        return super.findByName(US_SELECT_BY_EMAIL, UserDaoImpl::mapToUser, email);
    }
    
    @Override
    public void deleteById(Integer id) {
        super.deleteById(US_DELETE_BY_ID, id);
    }
    
    private static void prepareInsert(User t, PreparedStatement statement) {
        try {
            statement.setString(1, t.getName());
            statement.setString(2, t.getPassword());
            statement.setString(3, t.getRole().name());
            statement.setString(4, t.getEmail());
            statement.setString(5, t.getFullname());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User create(User u) throws NotUniqueNameException, NotUniqueEmailException {
        int id = u.getId();
        try {
            id = super.create(u, US_INSERT, UserDaoImpl::prepareInsert);
            u.setId(id);
            return u;
        } catch (SQLException e) {
        	getLogger().error(e);
            String message = e.getMessage();
            if(message.contains(US_NAME_UNIQUE)) {
                throw new NotUniqueNameException();
            }
            if(message.contains(US_EMAIL_UNIQUE)) {
                throw new NotUniqueEmailException();
            }
            throw new RuntimeException(e);
        }
    }
    
    private static void prepareUpdate(User t, PreparedStatement statement) {
        try {
            statement.setString(1, t.getName());
            statement.setString(2, t.getPassword());
            statement.setString(3, t.getRole().name());
            statement.setString(4, t.getEmail());
            statement.setString(5, t.getFullname());
            statement.setInt(6, t.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public User update(User u) throws NotUniqueNameException, NotUniqueEmailException {
        try {
            super.update(u, US_UPDATE, UserDaoImpl::prepareUpdate);
            return u;
        } catch (SQLException e) {
        	getLogger().error(e);
            String message = e.getMessage();
            if(message.contains(US_NAME_UNIQUE)) {
                throw new NotUniqueNameException();
            }
            if(message.contains(US_EMAIL_UNIQUE)) {
                throw new NotUniqueEmailException();
            }
            throw new RuntimeException(e);
        }
    }

    private static User mapToUser(ResultSet resultSet) {
        try {
            UserRole role = UserRole.valueOf(resultSet.getString(US_ROLE).toUpperCase());
            String password = resultSet.getString(US_PASSWORD);
            String name = resultSet.getString(US_NAME);
            String email = resultSet.getString(US_EMAIL);
            int id = resultSet.getInt(US_ID);
            String fullname = resultSet.getString(US_FULL_NAME);
            return new User(id, name, email, password, role, fullname);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
