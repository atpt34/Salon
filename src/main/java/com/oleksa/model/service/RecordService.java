package com.oleksa.model.service;

import java.util.List;

import com.oleksa.model.entity.Record;
import com.oleksa.model.entity.User;
import com.oleksa.model.exception.RecordOccupiedException;

public interface RecordService {

	Record create(Record record) throws RecordOccupiedException;
	
	List<Record> findAll();
	
	List<Record> findAllByClient(User client);
	
	List<Record> findAllWithComments();
	
	void setComment(Record record);
}
