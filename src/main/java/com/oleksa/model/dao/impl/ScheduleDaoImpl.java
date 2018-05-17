package com.oleksa.model.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.oleksa.model.dao.ScheduleDao;
import com.oleksa.model.entity.Record;
import com.oleksa.model.entity.Schedule;
import com.oleksa.model.entity.User;
import com.oleksa.model.entity.UserRole;
import com.oleksa.model.pagination.PaginationResult;

import static com.oleksa.model.dao.impl.JdbcConstants.*;

public class ScheduleDaoImpl extends JdbcTemplate<Schedule> implements ScheduleDao {
	
	private static final Logger LOGGER = LogManager.getLogger(ScheduleDaoImpl.class);
			
	public ScheduleDaoImpl(DataSource dataSource) {
		super(dataSource);
	}

	private static void prepareInsert(Schedule t, PreparedStatement statement) {
		try {
			statement.setInt(1, t.getMaster().getId());
			statement.setDate(2, Date.valueOf(t.getDay()));
			statement.setTime(3, Time.valueOf(t.getStartHour()));
			statement.setTime(4, Time.valueOf(t.getEndHour()));
		} catch (SQLException e) {
			LOGGER.error(e);
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

	@Override
	public List<Schedule> findAllByMasterIdWithRecords(int masterId) {
		try (Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SC_SELECT_BY_MASTER_WITH_RECORDS);
            ) {
			Map<Schedule, Set<Record>> result = new HashMap<>();
			statement.setInt(1, masterId);
    		try(ResultSet resultSet = statement.executeQuery();) {
    			Map<Integer, Record> records = new HashMap<>();
    			while(resultSet.next()) {
    	            int id = resultSet.getInt(SC_ID);
    	            LocalDate day = resultSet.getDate(SC_DAY).toLocalDate();
    				LocalTime startHour = resultSet.getTime(SC_START_TIME).toLocalTime();
    				LocalTime endHour = resultSet.getTime(SC_END_TIME).toLocalTime();
    				int recordId = resultSet.getInt(RC_ID);
    				LocalTime hour = resultSet.getTime(RC_HOUR).toLocalTime();
    				LocalDate recordDay = resultSet.getDate(RC_DAY).toLocalDate();
    				Record record = new Record(recordId, null, hour, recordDay, null, null);
    				records.putIfAbsent(recordId, record);
					Schedule schedule = new Schedule(id, null, day, startHour, endHour, null);
					result.putIfAbsent(schedule, new HashSet<>());
					result.get(schedule).add(records.get(recordId));
                }
            }
    		getLogger().info(result);
    		result.forEach((k, v) -> k.setRecords(v));
			return new ArrayList<>(result.keySet());
		} catch (SQLException e) {
            getLogger().error(e);
            throw new RuntimeException(e);
        }
	}
	
	@Override
	public PaginationResult<Schedule> findPage(int page, int itemsOnPage) {
		List<Schedule> items = new ArrayList<>();
		int total = 0;
		int offset = page * itemsOnPage;
		try(Connection connection = dataSource.getConnection()) {
			connection.setAutoCommit(false);
			try (Statement statement = connection.createStatement();
	            ResultSet resultSet = statement.executeQuery(SC_SELECT_COUNT);) {
					if (resultSet.next()) {
						total = resultSet.getInt(1);
					} else {
                    	getLogger().error("no count");
                        throw new RuntimeException("no count");
                    }
				}
			try (PreparedStatement statement = connection.prepareStatement(SC_SELECT_LIMIT);
                ) {
        			statement.setInt(1, itemsOnPage);
    				statement.setInt(2, offset);
            		try(ResultSet resultSet = statement.executeQuery();) {
            			while(resultSet.next()) {
                            items.add(mapToScheduleWithUser(resultSet));
                        }
                    }
        		}
			connection.commit();
		} catch (SQLException e) {
            getLogger().error(e);
            throw new RuntimeException(e);
        }
		return new PaginationResult<>(items, page, itemsOnPage, total);
	}
	
	@Override
	public List<Schedule> findFreeOnDayAndTime(LocalDate day, LocalTime time) {
		List<Schedule> result = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SC_SELECT_FREE_BY_DAY_AND_TIME);
             ) {
			statement.setTime(1, Time.valueOf(time));
			statement.setDate(2, Date.valueOf(day));
			statement.setTime(3, Time.valueOf(time));
    		try(ResultSet resultSet = statement.executeQuery();) {
    			while(resultSet.next()) {
                    result.add(mapToScheduleWithUser(resultSet));
                }
            }
		} catch (SQLException e) {
			getLogger().error(e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return result;
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
        	LOGGER.error(e);
            throw new RuntimeException(e);
        }
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
        	LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

}
