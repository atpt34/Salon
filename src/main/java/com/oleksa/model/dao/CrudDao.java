package com.oleksa.model.dao;

import java.util.List;
import java.util.Optional;

import com.oleksa.model.logger.Loggable;

/**
 * 
 * @author atpt34
 *
 * @param <T> type
 * @param <Id> type of identification column
 */
public interface CrudDao<T, Id> extends Loggable {

    T create(T t) throws Exception;

    void deleteById(Id i);

    T update(T t) throws Exception;

    Optional<T> findById(Id id);

    List<T> findAll();

}
