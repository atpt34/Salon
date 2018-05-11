package com.oleksa.model.dao;

import java.util.List;

import com.oleksa.model.entity.Record;
import com.oleksa.model.entity.Schedule;

public interface RecordDao extends CrudDao<Record, Integer> {

	List<Record> findAllByClientId(int clientId);
	
	
}
