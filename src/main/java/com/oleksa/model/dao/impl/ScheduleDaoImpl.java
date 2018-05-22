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
import com.oleksa.model.exception.InvalidIntervalException;
import com.oleksa.model.pagination.PaginationResult;

import static com.oleksa.model.dao.impl.DatabaseProperties.*;

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
	public Schedule create(Schedule t) throws InvalidIntervalException {
		int id;
		try {
			id = super.create(t, SC_INSERT.getValue(), ScheduleDaoImpl::prepareInsert);
			t.setId(id);
			return t;
		} catch (SQLException e) {
			getLogger().error(e);
			throw new InvalidIntervalException();
		}
	}

	@Override
	public void deleteById(Integer id) {
		super.deleteById(SC_DELETE_BY_ID.getValue(), id);
	}

	private static void prepareUpdate(Schedule t, PreparedStatement statement) {
		try {
			statement.setInt(1, t.getMaster().getId());
			statement.setDate(2, Date.valueOf(t.getDay()));
			statement.setTime(3, Time.valueOf(t.getStartHour()));
			statement.setTime(4, Time.valueOf(t.getEndHour()));
			statement.setInt(5, t.getId());
		} catch (SQLException e) {
			LOGGER.error(e);
		}
	}
	
	@Override
	public Schedule update(Schedule t) throws Exception {
		super.update(t, SC_UPDATE.getValue(), ScheduleDaoImpl::prepareUpdate);
		return t;
	}

	@Override
	public Optional<Schedule> findById(Integer id) {
		return super.findById(SC_SELECT_BY_ID.getValue(), JdbcMapperImpl::mapToSchedule, id);
	}

	@Override
	public List<Schedule> findAll() {
		return super.findAll(SC_SELECT_ALL.getValue(), JdbcMapperImpl::mapToScheduleWithUser);
	}

	@Override
	public List<Schedule> findAllByMasterId(int masterId) {
		return super.findAllByForeignKey(SC_SELECT_BY_MASTER.getValue(), masterId, JdbcMapperImpl::mapToSchedule);
	}

	@Override
	public List<Schedule> findAllByMasterIdWithRecords(int masterId) {
		try (Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SC_SELECT_BY_MASTER_WITH_RECORDS.getValue());
            ) {
			Map<Schedule, Set<Record>> result = new HashMap<>();
			statement.setInt(1, masterId);
    		try(ResultSet resultSet = statement.executeQuery();) {
    			Map<Integer, Record> records = new HashMap<>();
    			while(resultSet.next()) {
    				Schedule schedule = JdbcMapperImpl.mapToSchedule(resultSet);
    				result.putIfAbsent(schedule, new HashSet<>());
    				if (resultSet.getInt(RC_ID.getValue()) != 0) {
    					Record record = JdbcMapperImpl.mapToRecord(resultSet);
    					int recordId = record.getId();
    					records.putIfAbsent(recordId, record);
    					result.get(schedule).add(records.get(recordId));
    				}
                }
            }
    		getLogger().debug(result);
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
	            ResultSet resultSet = statement.executeQuery(SC_SELECT_COUNT.getValue());) {
					if (resultSet.next()) {
						total = resultSet.getInt(1);
					} else {
                    	getLogger().error("no count");
                        throw new RuntimeException("no count");
                    }
				}
			try (PreparedStatement statement = connection.prepareStatement(SC_SELECT_LIMIT.getValue());
                ) {
        			statement.setInt(1, itemsOnPage);
    				statement.setInt(2, offset);
            		try(ResultSet resultSet = statement.executeQuery();) {
            			while(resultSet.next()) {
                            items.add(JdbcMapperImpl.mapToScheduleWithUser(resultSet));
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
			PreparedStatement statement = connection.prepareStatement(SC_SELECT_FREE_BY_DAY_AND_TIME.getValue());
             ) {
			statement.setTime(1, Time.valueOf(time));
			statement.setDate(2, Date.valueOf(day));
			statement.setTime(3, Time.valueOf(time));
    		try(ResultSet resultSet = statement.executeQuery();) {
    			while(resultSet.next()) {
                    result.add(JdbcMapperImpl.mapToScheduleWithUser(resultSet));
                }
            }
		} catch (SQLException e) {
			getLogger().error(e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return result;
	}

}
