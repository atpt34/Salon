package com.oleksa.model.dao;

import java.util.List;
import java.util.Optional;

import com.oleksa.model.entity.User;
import com.oleksa.model.exception.NotUniqueEmailException;
import com.oleksa.model.exception.NotUniqueNameException;

public interface CrudDao<T, Id> {

    T create(T t) throws Exception;
    
    void deleteById(Id i);
    
    T update(T t) throws Exception;
    
    Optional<T> findById(Id id);
    
    List<T> findAll();
    
//    long count();
//    List<T> findAll(int num, int offset);
}
