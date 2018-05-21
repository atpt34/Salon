package com.oleksa.model.dao.impl;

import static com.oleksa.model.dao.impl.DatabaseProperties.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oleksa.model.entity.Comment;
import com.oleksa.model.entity.Record;
import com.oleksa.model.entity.Schedule;
import com.oleksa.model.entity.User;
import com.oleksa.model.entity.UserRole;

public final class JdbcMapperImpl {
	
    private static final Logger LOGGER = LogManager.getLogger(JdbcMapperImpl.class);
    
    private JdbcMapperImpl() { }
    
	static User mapToUser(ResultSet resultSet) {
        try {
            UserRole role = UserRole.valueOf(resultSet.getString(US_ROLE.getValue()).toUpperCase());
            String password = resultSet.getString(US_PASSWORD.getValue());
            String name = resultSet.getString(US_NAME.getValue());
            String email = resultSet.getString(US_EMAIL.getValue());
            int id = resultSet.getInt(US_ID.getValue());
            String fullname = resultSet.getString(US_FULL_NAME.getValue());
            return new User(id, name, email, password, role, fullname);
        } catch (SQLException e) {
        	LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }
    
	static Schedule mapToSchedule(ResultSet resultSet) {
		try {
            int id = resultSet.getInt(SC_ID.getValue());
            LocalDate day = resultSet.getDate(SC_DAY.getValue()).toLocalDate();
			LocalTime startHour = resultSet.getTime(SC_START_TIME.getValue()).toLocalTime();
			LocalTime endHour = resultSet.getTime(SC_END_TIME.getValue()).toLocalTime();
			User master = null;
			Set<Record> records = null;
			return new Schedule(id, master, day, startHour, endHour, records);
        } catch (SQLException e) {
        	LOGGER.error(e);
            throw new RuntimeException(e);
        }
	}

	static Record mapToRecord(ResultSet resultSet) {
		try {
            int id = resultSet.getInt(RC_ID.getValue());
			User client = null;
			LocalTime hour = resultSet.getTime(RC_HOUR.getValue()).toLocalTime();
			LocalDate day = resultSet.getDate(RC_DAY.getValue()).toLocalDate();
			Comment comment = null;
			Set<Schedule> schedules = null;
			return new Record(id, client, hour, day, comment, schedules);
        } catch (SQLException e) {
        	LOGGER.error(e);
            throw new RuntimeException(e);
        }
	}
	
	static Comment mapToComment(ResultSet resultSet) {
		try {
            int commentId = resultSet.getInt(CM_ID.getValue());
            String text = resultSet.getString(CM_WORDS.getValue());
			int stars = resultSet.getInt(CM_STARS.getValue());
			return new Comment(commentId, text, stars);
        } catch (SQLException e) {
        	LOGGER.error(e);
            throw new RuntimeException(e);
        }
	}
	
	/* combinations of above: */
	
	static Schedule mapToScheduleWithUser(ResultSet resultSet) {
        User master = JdbcMapperImpl.mapToUser(resultSet);
		Schedule schedule = JdbcMapperImpl.mapToSchedule(resultSet);
		schedule.setMaster(master);
		return schedule;
    }
	
	static Record mapToRecordWithCommentAndUser(ResultSet resultSet) {
		User client = JdbcMapperImpl.mapToUser(resultSet);
		Comment comment = JdbcMapperImpl.mapToComment(resultSet);
		Record record = JdbcMapperImpl.mapToRecord(resultSet);
		record.setClient(client);
		record.setComment(comment);
		return record;
	}
}
