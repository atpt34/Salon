package com.oleksa.model.dao;

import java.util.List;

import com.oleksa.model.entity.Record;
import com.oleksa.model.entity.Schedule;
import com.oleksa.model.exception.RecordOccupiedException;

/**
 * 
 * @author atpt34
 *
 */
public interface RecordDao extends CrudDao<Record, Integer> {

    List<Record> findAllByClientId(int clientId);

    List<Record> findAllByClientIdWithMaster(int clientId);

    @Override
    Record create(Record t) throws RecordOccupiedException;

    List<Record> findAllWithComments();

}
