package com.oleksa.model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oleksa.model.dao.RecordDao;
import com.oleksa.model.entity.Comment;
import com.oleksa.model.entity.Record;
import com.oleksa.model.entity.Schedule;
import com.oleksa.model.entity.User;
import com.oleksa.model.exception.RecordOccupiedException;

import static com.oleksa.model.dao.impl.DatabaseProperties.*;

public class RecordDaoImpl extends JdbcTemplate<Record> implements RecordDao {

    private static final Logger LOGGER = LogManager.getLogger(RecordDaoImpl.class);

    public RecordDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    private static void prepareInsert(Record r, PreparedStatement statement) {
        try {
            statement.setInt(1, r.getClient().getId());
            statement.setTime(2, Time.valueOf(r.getHour()));
            statement.setDate(3, Date.valueOf(r.getDay()));
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public Record create(Record t) throws RecordOccupiedException {
        try(Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            int recordId = 0;
            try(PreparedStatement statement = connection.prepareStatement(RC_INSERT.getValue(), PreparedStatement.RETURN_GENERATED_KEYS);) {
                prepareInsert(t, statement);
                statement.executeUpdate();
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if(keys.next()) {
                        recordId = keys.getInt(1);
                        t.setId(recordId);
                    } else {
                        getLogger().error("no keys");
                        throw new RuntimeException("no keys");                    	
                    }
                }
            }
            try(PreparedStatement statement = connection.prepareStatement(SR_RC_INSERT.getValue());) {
                Set<Schedule> schedules = t.getSchedules();
                for (Schedule schedule : schedules) {
                    int scheduleId = schedule.getId();
                    statement.setInt(1, scheduleId);
                    statement.setInt(2, recordId);
                    statement.addBatch();
                }
                statement.executeBatch();
            }
            connection.commit();
        } catch(SQLException e) {  
            getLogger().error(e);
            throw new RecordOccupiedException();
        }
        return t;
    }

    @Override
    public void deleteById(Integer id) {
        super.deleteById(RC_DELETE_BY_ID.getValue(), id);
    }

    @Override
    public Record update(Record record) throws Exception {
        try(Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try(PreparedStatement statement = connection.prepareStatement(CM_INSERT.getValue(), PreparedStatement.RETURN_GENERATED_KEYS);) {
                Comment comment = record.getComment();
                statement.setString(1, comment.getText());
                statement.setInt(2, comment.getStars());
                statement.executeUpdate();
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if(keys.next()) {
                        comment.setId(keys.getInt(1));
                    } else {
                        getLogger().error("no keys");
                        throw new RuntimeException("no keys");                    	
                    }
                }
            }
            try(PreparedStatement statement = connection.prepareStatement(RC_UPDATE.getValue(), PreparedStatement.RETURN_GENERATED_KEYS);) {
                Comment comment = record.getComment();
                statement.setInt(1, record.getClient().getId());
                statement.setTime(2, Time.valueOf(record.getHour()));
                statement.setDate(3, Date.valueOf(record.getDay()));
                statement.setInt(4, comment.getId());
                statement.setInt(5, record.getId());
                statement.executeUpdate();
            }
            connection.commit();
            return record;
        } catch(SQLException e) {
            getLogger().error(e);
            throw new RecordOccupiedException();
        }
    }

    @Override
    public Optional<Record> findById(Integer id) {
        return super.findById(RC_COMMENT_ID.getValue(), JdbcMapperImpl::mapToRecord, id);
    }

    @Override
    public List<Record> findAll() {
        return super.findAll(RC_SELECT_ALL.getValue(), JdbcMapperImpl::mapToRecord);
    }

    @Override
    public List<Record> findAllByClientId(int clientId) {
        return super.findAllByForeignKey(RC_SELECT_BY_CLIENT.getValue(), clientId, JdbcMapperImpl::mapToRecord);
    }

    @Override
    public List<Record> findAllByClientIdWithMaster(int clientId) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(RC_SELECT_ALL_WITH_MASTER_COMMENT.getValue());
                ) {
            Map<Record, Set<Schedule>> result = new LinkedHashMap<>();
            statement.setInt(1, clientId);
            try(ResultSet resultSet = statement.executeQuery();) {
                Map<Integer, Schedule> schedules = new HashMap<>();
                Map<Integer, User> masters = new HashMap<>();
                while(resultSet.next()) {
                    User master = JdbcMapperImpl.mapToUser(resultSet);
                    int masterId = master.getId();
                    masters.putIfAbsent(masterId, master);
                    Schedule schedule = JdbcMapperImpl.mapToSchedule(resultSet);
                    schedule.setMaster(masters.get(masterId));
                    int scheduleId = schedule.getId();
                    schedules.putIfAbsent(scheduleId, schedule);
                    Record record = JdbcMapperImpl.mapToRecord(resultSet);
                    if (resultSet.getInt(CM_ID.getValue()) != 0) {
                        Comment comment = JdbcMapperImpl.mapToComment(resultSet);
                        record.setComment(comment);
                    }
                    result.putIfAbsent(record, new HashSet<>());
                    result.get(record).add(schedules.get(scheduleId));
                }
            }
            getLogger().debug(result);
            result.forEach((k, v) -> k.setSchedules(v));
            return new ArrayList<>(result.keySet());
        } catch (SQLException e) {
            getLogger().error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Record> findAllWithComments() {
        return super.findAll(RC_SELECT_ALL_COMMENTS.getValue(), JdbcMapperImpl::mapToRecordWithCommentAndUser);
    }

}
