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

public class UserDaoImpl extends JdbcTemplate<User> implements UserDao {
    
    private static final String US_FULL_NAME = "us_full_name";
    private static final String US_ID = "us_id";
    private static final String US_EMAIL = "us_email";
    private static final String US_NAME = "us_name";
    private static final String US_PASSWORD = "us_password";
    private static final String US_ROLE = "us_role";
    
    private static final String US_NAME_UNIQUE = "us_name_un";
    private static final String US_EMAIL_UNIQUE = "us_email_un";
    
    private static final String SELECT_ALL = "SELECT * FROM user_t";
    private static final String SELECT_BY_ID = "SELECT * FROM user_t WHERE us_id = ?";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM user_t WHERE us_email = ?";
    private static final String SELECT_BY_NAME = "SELECT * FROM user_t WHERE us_name = ?";
    private static final String UPDATE = "UPDATE user_t SET us_name = ?, us_password = ?, us_role = ?, "
            + "us_email = ?, us_full_name = ? WHERE us_id = ?";
    private static final String INSERT = "INSERT INTO user_t(us_name, us_password, us_role, us_email, us_full_name) " + 
            " VALUES( ?, ?, ?, ?, ?);";
    private static final String DELETE_BY_ID = "DELETE FROM user_t WHERE us_id = ?";
     

    public UserDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return super.findById(SELECT_BY_ID, UserDaoImpl::mapToUser, id);
    }

    @Override
    public List<User> findAll() {
        return super.findAll(SELECT_ALL, UserDaoImpl::mapToUser);
    }
    
    @Override
    public Optional<User> findByName(String name) {
        return super.findByName(SELECT_BY_NAME, UserDaoImpl::mapToUser, name);
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        return super.findByName(SELECT_BY_EMAIL, UserDaoImpl::mapToUser, email);
    }
    
    @Override
    public void deleteById(Integer id) {
        super.deleteById(DELETE_BY_ID, id);
    }
    
    private static void prepareInsert(User t, PreparedStatement statement) {
        try {
            statement.setString(1, t.getName());
            statement.setString(2, t.getPassword());
            statement.setString(3, t.getRole().name());
            statement.setString(4, t.getEmail());
            statement.setString(5, t.getFullname());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User create(User u) throws NotUniqueNameException, NotUniqueEmailException {
        int id = u.getId();
        try {
            id = super.create(u, INSERT, UserDaoImpl::prepareInsert);
            u.setId(id);
            return u;
        } catch (SQLException e) {
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
            super.update(u, UPDATE, UserDaoImpl::prepareUpdate);
            return u;
        } catch (SQLException e) {
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
