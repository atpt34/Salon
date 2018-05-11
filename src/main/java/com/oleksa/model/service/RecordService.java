package com.oleksa.model.service;

import java.util.List;

import com.oleksa.model.entity.Record;
import com.oleksa.model.entity.User;

public interface RecordService {

	Record create(Record record);
	
	List<Record> findAll();
	
	List<Record> findAllByClient(User client);
	
}
