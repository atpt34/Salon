package com.oleksa.model.service.impl;

import java.util.List;

import com.oleksa.model.dao.RecordDao;
import com.oleksa.model.entity.Record;
import com.oleksa.model.entity.User;
import com.oleksa.model.exception.RecordOccupiedException;
import com.oleksa.model.service.RecordService;

/**
 * 
 * @author atpt34
 *
 */
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
        return recordDao.findAllByClientIdWithMaster(client.getId());
    }

    @Override
    public void update(Record record) {
        try {
            recordDao.update(record);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Record record) {
        recordDao.deleteById(record.getId());
    }

    @Override
    public List<Record> findAllWithComments() {
        return recordDao.findAllWithComments();
    }

    @Override
    public void sendEmail(User user) {
        Thread thread = new Thread(new MailRunner(user));
        thread.setDaemon(true);
        thread.start();
    }

}
