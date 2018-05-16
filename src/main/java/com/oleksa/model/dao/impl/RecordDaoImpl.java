package com.oleksa.model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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
import com.oleksa.model.entity.UserRole;
import com.oleksa.model.exception.RecordOccupiedException;

import static com.oleksa.model.dao.impl.JdbcConstants.*;

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
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			int recordId = 0;
			try(PreparedStatement statement = connection.prepareStatement(RC_INSERT, Statement.RETURN_GENERATED_KEYS);) {
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
			try(PreparedStatement statement = connection.prepareStatement(SR_RC_INSERT);) {
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
	public void deleteById(Integer i) {
		
	}

	@Override
	public Record update(Record record) throws Exception {
		try(Connection connection = dataSource.getConnection()) {
			connection.setAutoCommit(false);
			try(PreparedStatement statement = connection.prepareStatement(CM_INSERT, Statement.RETURN_GENERATED_KEYS);) {
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
			try(PreparedStatement statement = connection.prepareStatement(RC_UPDATE_COMMENT, Statement.RETURN_GENERATED_KEYS);) {
				Comment comment = record.getComment();
				statement.setInt(1, comment.getId());
				statement.setInt(2, record.getId());
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
		return super.findById(RC_COMMENT_ID, RecordDaoImpl::mapToRecord, id);
	}

	@Override
	public List<Record> findAll() {
		return null;
	}

	@Override
	public List<Record> findAllByClientId(int clientId) {
		return super.findAllByForeignKey(RC_SELECT_BY_CLIENT, clientId, RecordDaoImpl::mapToRecord);
	}
	
	@Override
	public List<Record> findAllWithComments() {
		return super.findAll(RC_SELECT_ALL_COMMENTS, RecordDaoImpl::mapToRecordWithCommentAndUser);
	}

	private static Record mapToRecord(ResultSet resultSet) {
		try {
            int id = resultSet.getInt(RC_ID);
			User client = null;
			LocalTime hour = resultSet.getTime(RC_HOUR).toLocalTime();
			LocalDate day = resultSet.getDate(RC_DAY).toLocalDate();
			Comment comment = null;
			Set<Schedule> schedules = null;
			return new Record(id, client, hour, day, comment, schedules);
        } catch (SQLException e) {
        	LOGGER.error(e);
            throw new RuntimeException(e);
        }
	}

	private static Record mapToRecordWithCommentAndUser(ResultSet resultSet) {
		try {
			UserRole role = UserRole.valueOf(resultSet.getString(US_ROLE).toUpperCase());
            String password = resultSet.getString(US_PASSWORD);
            String name = resultSet.getString(US_NAME);
            String email = resultSet.getString(US_EMAIL);
            int clientId = resultSet.getInt(US_ID);
            String fullname = resultSet.getString(US_FULL_NAME);
            User client = new User(clientId, name, email, password, role, fullname);
            int commentId = resultSet.getInt(CM_ID);
            String text = resultSet.getString(CM_WORDS);
			int stars = resultSet.getInt(CM_STARS);
			Comment comment = new Comment(commentId, text, stars);
            int id = resultSet.getInt(RC_ID);
			LocalTime hour = resultSet.getTime(RC_HOUR).toLocalTime();
			LocalDate day = resultSet.getDate(RC_DAY).toLocalDate();
			Set<Schedule> schedules = null;
			return new Record(id, client, hour, day, comment, schedules);
        } catch (SQLException e) {
        	LOGGER.error(e);
            throw new RuntimeException(e);
        }
	}
}
