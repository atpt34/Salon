package com.oleksa.model.dao.impl;

public enum DatabaseProperties {
    
    US_FULL_NAME("us_full_name"),
    US_ID("us_id"),
    US_EMAIL("us_email"),
    US_NAME("us_name"),
    US_PASSWORD("us_password"),
    US_ROLE("us_role"),
    US_NAME_UNIQUE("us_name_un"),
    US_EMAIL_UNIQUE("us_email_un"),
    US_SELECT_ALL("SELECT * FROM user_t"),
    US_SELECT_BY_ID("SELECT * FROM user_t WHERE us_id = ?"),
    US_SELECT_BY_EMAIL("SELECT * FROM user_t WHERE us_email = ?"),
    US_SELECT_BY_NAME("SELECT * FROM user_t WHERE us_name = ?"),
    US_UPDATE("UPDATE user_t SET us_name = ?, us_password = ?, us_role = ?, "
            + "us_email = ?, us_full_name = ? WHERE us_id = ?"),
    US_INSERT("INSERT INTO user_t(us_name, us_password, us_role, us_email, us_full_name) " + 
            " VALUES( ?, ?, ?, ?, ?);"),
    US_DELETE_BY_ID("DELETE FROM user_t WHERE us_id = ?"),

    SC_ID("sc_id"),
    SC_MASTER_ID("sc_master_id"),
    SC_DAY("sc_day"),
    SC_START_TIME("sc_start_time"),
    SC_END_TIME("sc_end_time"),
    SC_SELECT_BY_ID("SELECT * FROM schedule_t WHERE sc_id = ?"),
	SC_SELECT_ALL("SELECT * FROM schedule_t INNER JOIN user_t ON us_id = sc_master_id"),
	SC_SELECT_COUNT("SELECT COUNT(*) FROM schedule_t INNER JOIN user_t ON us_id = sc_master_id"),
	SC_SELECT_LIMIT("SELECT * FROM schedule_t INNER JOIN user_t ON us_id = sc_master_id "
			+ "ORDER BY sc_master_id, sc_day LIMIT ? OFFSET ?"),
	SC_SELECT_BY_DATE_LIMIT("SELECT * FROM schedule_t INNER JOIN user_t ON us_id = sc_master_id "
			+ "ORDER BY sc_day LIMIT ? OFFSET ?"),
	SC_SELECT_OCCUPIED_HOURS_BY_ID("SELECT so_time FROM schedule_occupied_time_t WHERE so_sc_id = ?"),
	SC_SELECT_BY_MASTER("SELECT * FROM schedule_t WHERE sc_master_id = ?"),
	SC_SELECT_BY_MASTER_WITH_RECORDS("SELECT * FROM schedule_t LEFT JOIN schedule_record_m2m ON sc_id = sr_sc_id "
			+ "LEFT JOIN record_t ON sr_rc_id = rc_id WHERE sc_master_id = ?"),
	SC_SELECT_FREE_BY_DAY_AND_TIME("SELECT * FROM schedule_t INNER JOIN user_t ON us_id = sc_master_id "
			+ "WHERE sc_id NOT IN ( "
			+ "SELECT so_sc_id FROM schedule_occupied_time_t WHERE so_time = ? ) "
			+ "AND sc_day = ? AND ? BETWEEN sc_start_time AND sc_end_time;"),
	SC_INSERT("INSERT INTO schedule_t(sc_master_id, sc_day, sc_start_time, sc_end_time) VALUES( ?, ?, ?, ?);"),
	SC_UPDATE("UPDATE schedule_t SET sm_master_id = ?, sc_day = ?, sc_start_time = ?, sc_end_time = ? WHERE sc_id = ?"),
	SC_DELETE_BY_ID("DELETE FROM schedule_t WHERE sc_id = ?"),
	
	RC_ID("rc_id"),
	RC_CLIENT_ID("rc_client_id"),
	RC_HOUR("rc_start_time"),
	RC_DAY("rc_day"),
	RC_COMMENT_ID("rc_comment_id"),
	RC_SELECT_ALL("SELECT * FROM record_t"),
	RC_SELECT_ALL_WITH_MASTER_COMMENT("SELECT * FROM record_t LEFT JOIN comment_t ON rc_comment_id = cm_id "
			+ "INNER JOIN schedule_record_m2m ON rc_id = sr_rc_id INNER JOIN schedule_t ON sr_sc_id = sc_id "
			+ "INNER JOIN user_t ON sc_master_id = us_id WHERE rc_client_id = ?"),
	RC_SELECT_BY_ID("SELECT * FROM record_t WHERE rc_id = ?"),
	RC_SELECT_BY_CLIENT("SELECT * FROM record_t WHERE rc_client_id = ?"),
	RC_SELECT_ALL_COMMENTS("SELECT * FROM record_t INNER JOIN user_t ON rc_client_id = us_id "
			+ "INNER JOIN comment_t ON rc_comment_id = cm_id WHERE rc_comment_id IS NOT NULL"),
	RC_INSERT("INSERT INTO record_t(rc_client_id, rc_start_time, rc_day) VALUES(?, ?, ?)"),
	RC_UPDATE("UPDATE record_t SET rc_client_id = ?, rc_start_time = ?, rc_day = ?, rc_comment_id = ? WHERE rc_id = ?"),
	RC_DELETE_BY_ID("DELETE FROM record_t WHERE rc_id = ?;"),
	
	CM_ID("cm_id"),
	CM_WORDS("cm_words"),
	CM_STARS("cm_stars"),
	CM_INSERT("INSERT INTO comment_t (cm_words, cm_stars) VALUES (?, ?)"),
	CM_DELETE_BY_ID("DELETE FROM comment_t WHERE cm_id = ?"),
	
	SR_SC_ID("sr_sc_id"),
	SR_RC_ID("sr_rc_id"),
	SR_RC_INSERT("INSERT INTO schedule_record_m2m(sr_sc_id, sr_rc_id) VALUES (?, ?)");
	
	private final String value;
	
	private DatabaseProperties(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
