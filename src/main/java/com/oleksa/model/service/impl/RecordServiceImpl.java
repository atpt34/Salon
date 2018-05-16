package com.oleksa.model.service.impl;

import java.util.List;

import com.oleksa.model.dao.RecordDao;
import com.oleksa.model.entity.Record;
import com.oleksa.model.entity.User;
import com.oleksa.model.exception.RecordOccupiedException;
import com.oleksa.model.service.RecordService;

public class RecordServiceImpl implements RecordService {

	private RecordDao recordDao;

	public RecordServiceImpl(RecordDao recordDao) {
		this.recordDao = recordDao;
	}

	@Override
	public Record create(Record record) throws RecordOccupiedException {
		return recordDao.create(record);
	}

	@Override
	public List<Record> findAll() {
		return recordDao.findAll();
	}

	@Override
	public List<Record> findAllByClient(User client) {
		return recordDao.findAllByClientId(client.getId());
	}

	@Override
	public void setComment(Record record) {
		try {
			recordDao.update(record);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Record> findAllWithComments() {
		return recordDao.findAllWithComments();
	}

}
