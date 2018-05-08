package com.oleksa.model.dao;

import java.util.Optional;

import com.oleksa.model.entity.User;

public interface UserDao extends CrudDao<User, Integer> {

    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
    
}
