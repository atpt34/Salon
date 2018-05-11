package com.oleksa.model.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import com.oleksa.model.dao.ScheduleDao;
import com.oleksa.model.entity.Record;
import com.oleksa.model.entity.Schedule;
import com.oleksa.model.entity.User;
import com.oleksa.model.entity.UserRole;

import static com.oleksa.model.dao.impl.JdbcConstants.*;

public class ScheduleDaoImpl extends JdbcTemplate<Schedule> implements ScheduleDao {
	
	public ScheduleDaoImpl(DataSource dataSource) {
		super(dataSource);
	}

	private static void prepareInsert(Schedule t, PreparedStatement statement) {
		try {
			statement.setInt(1, t.getMaster().getId());
			statement.setDate(2, Date.valueOf(t.getDay()));
			statement.setTime(3, Time.valueOf(t.getBeginHour()));
			statement.setTime(4, Time.valueOf(t.getEndHour()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Schedule create(Schedule t) throws Exception {
		int id = super.create(t, SC_INSERT, ScheduleDaoImpl::prepareInsert);
		t.setId(id);
		return t;
	}

	@Override
	public void deleteById(Integer i) {
		// TODO Auto-generated method stub

	}

	@Override
	public Schedule update(Schedule t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Schedule> findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Schedule> findAll() {
		return super.findAll(SC_SELECT_ALL, ScheduleDaoImpl::mapToScheduleWithUser);
	}

	@Override
	public List<Schedule> findAllByMasterId(int masterId) {
		return super.findAllByForeignKey(SC_SELECT_BY_MASTER, masterId, ScheduleDaoImpl::mapToSchedule);
	}

	private static Schedule mapToScheduleWithUser(ResultSet resultSet) {
        try {
            UserRole role = UserRole.valueOf(resultSet.getString(US_ROLE).toUpperCase());
            String password = resultSet.getString(US_PASSWORD);
            String name = resultSet.getString(US_NAME);
            String email = resultSet.getString(US_EMAIL);
            int masterId = resultSet.getInt(US_ID);
            String fullname = resultSet.getString(US_FULL_NAME);
            int id = resultSet.getInt(SC_ID);
            LocalDate day = resultSet.getDate(SC_DAY).toLocalDate();
			LocalTime startHour = resultSet.getTime(SC_START_TIME).toLocalTime();
			LocalTime endHour = resultSet.getTime(SC_END_TIME).toLocalTime();
			User master = new User(masterId, name, email, password, role, fullname); // TODO: duplicate users
			return new Schedule(id, master, day, startHour, endHour, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	
	private static Schedule mapToSchedule(ResultSet resultSet) {
		try {
            int id = resultSet.getInt(SC_ID);
            LocalDate day = resultSet.getDate(SC_DAY).toLocalDate();
			LocalTime startHour = resultSet.getTime(SC_START_TIME).toLocalTime();
			LocalTime endHour = resultSet.getTime(SC_END_TIME).toLocalTime();
			User master = null;
			return new Schedule(id, master, day, startHour, endHour, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
}
