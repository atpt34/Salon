package com.oleksa.model.service;

import java.util.List;

import com.oleksa.model.entity.Record;
import com.oleksa.model.entity.User;
import com.oleksa.model.exception.RecordOccupiedException;

/**
 * 
 * @author atpt34
 *
 */
public interface RecordService {

    Record create(Record record) throws RecordOccupiedException;

    List<Record> findAll();

    List<Record> findAllByClient(User client);

    List<Record> findAllWithComments();

    void update(Record record);

    void delete(Record record);

    void sendEmail(User user);
}
