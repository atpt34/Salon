package com.oleksa.model.dao.impl;

public interface JdbcConstants {

    
    String US_FULL_NAME = "us_full_name";
    String US_ID = "us_id";
    String US_EMAIL = "us_email";
    String US_NAME = "us_name";
    String US_PASSWORD = "us_password";
    String US_ROLE = "us_role";
    
    String US_NAME_UNIQUE = "us_name_un";
    String US_EMAIL_UNIQUE = "us_email_un";

    String US_SELECT_ALL = "SELECT * FROM user_t";
    String US_SELECT_BY_ID = "SELECT * FROM user_t WHERE us_id = ?";
    String US_SELECT_BY_EMAIL = "SELECT * FROM user_t WHERE us_email = ?";
    String US_SELECT_BY_NAME = "SELECT * FROM user_t WHERE us_name = ?";
    String US_UPDATE = "UPDATE user_t SET us_name = ?, us_password = ?, us_role = ?, "
            + "us_email = ?, us_full_name = ? WHERE us_id = ?";
    String US_INSERT = "INSERT INTO user_t(us_name, us_password, us_role, us_email, us_full_name) " + 
            " VALUES( ?, ?, ?, ?, ?);";
    String US_DELETE_BY_ID = "DELETE FROM user_t WHERE us_id = ?";

    String SC_ID = "sc_id";
    String SC_MASTER_ID = "sc_master_id";
    String SC_DAY = "sc_day";
    String SC_START_TIME = "sc_start_time";
    String SC_END_TIME = "sc_end_time";
	String SC_SELECT_ALL = "SELECT * FROM schedule_t INNER JOIN user_t ON us_id = sc_master_id";
	String SC_SELECT_COUNT = "SELECT COUNT(*) FROM schedule_t INNER JOIN user_t ON us_id = sc_master_id";
	String SC_SELECT_LIMIT = "SELECT * FROM schedule_t INNER JOIN user_t ON us_id = sc_master_id ORDER BY sc_master_id LIMIT ? OFFSET ?";
	String SC_SELECT_BY_MASTER = "SELECT * FROM schedule_t WHERE sc_master_id = ?";
	String SC_SELECT_BY_MASTER_WITH_RECORDS = "SELECT * FROM schedule_t INNER JOIN schedule_record_m2m ON sc_id = sr_sc_id "
			+ "INNER JOIN record_t ON sr_rc_id = rc_id WHERE sc_master_id = ?";
	String SC_SELECT_FREE_BY_DAY_AND_TIME = "SELECT * FROM schedule_t INNER JOIN user_t ON us_id = sc_master_id "
			+ "WHERE sc_id NOT IN ( "
			+ "SELECT so_sc_id FROM schedule_occupied_time_t WHERE so_time = ? ) "
			+ "AND sc_day = ? AND ? BETWEEN sc_start_time AND sc_end_time;";
	String SC_INSERT = "INSERT INTO schedule_t(sc_master_id, sc_day, sc_start_time, sc_end_time) VALUES( ?, ?, ?, ?);";
	
	String RC_ID = "rc_id";
	String RC_CLIENT_ID = "rc_client_id";
	String RC_HOUR = "rc_start_time";
	String RC_DAY = "rc_day";
	String RC_COMMENT_ID = "rc_comment_id";
	String RC_SELECT_ALL = "SELECT * FROM record_t INNER JOIN user_t ON us_id = rc_client_id INNER JOIN comment_t ON rc_comment_id = cm_id";
	String RC_SELECT_BY_ID = "SELECT * FROM record_t WHERE rc_id = ?";
	String RC_SELECT_BY_CLIENT = "SELECT * FROM record_t WHERE rc_client_id = ?";
//	String RC_SELECT_BY_SCHEDULE = "SELECT * FROM schedule_t INNER JOIN schedule_record_m2m ON sc_id = sr_sc_id INNER JOIN record_t ON rc_id = sr_rc_id WHERE sc_id = ?";
	String RC_INSERT = "INSERT INTO record_t(rc_client_id, rc_start_time, rc_day) VALUES(?, ?, ?)";
	String RC_UPDATE = "";
	String RC_UPDATE_COMMENT = "UPDATE record_t SET rc_comment_id = ? WHERE rc_id = ?;";
	String RC_SELECT_ALL_COMMENTS = "SELECT * FROM record_t INNER JOIN user_t ON rc_client_id = us_id INNER JOIN comment_t ON rc_comment_id = cm_id WHERE rc_comment_id IS NOT NULL";
	
	String CM_ID = "cm_id";
	String CM_WORDS = "cm_words";
	String CM_STARS = "cm_stars";
	String CM_INSERT = "INSERT INTO comment_t (cm_words, cm_stars) VALUES (?, ?)";
	
	String SR_SC_ID = "sr_sc_id";
	String SR_RC_ID = "sr_rc_id";
	String SR_RC_INSERT = "INSERT INTO schedule_record_m2m(sr_sc_id, sr_rc_id) VALUES (?, ?)";
}
