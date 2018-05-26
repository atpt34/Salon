package com.oleksa.model.service;

import java.util.List;

import com.oleksa.model.entity.User;
import com.oleksa.model.exception.InvalidCredentialsException;
import com.oleksa.model.exception.NotUniqueEmailException;
import com.oleksa.model.exception.NotUniqueNameException;

/**
 * @author atpt34
 *
 */
public interface UserService {

    User create(User user) throws NotUniqueNameException, NotUniqueEmailException;

    User findUserByCredentials(String name, String password) throws InvalidCredentialsException;

    List<User> findAll();

    User update(User user) throws NotUniqueNameException, NotUniqueEmailException;

    void delete(User user);

}
