CREATE DATABASE salon;
GRANT ALL PRIVILEGES ON DATABASE salon TO lexa;

CREATE TABLE comment_t (
	cm_id serial NOT NULL,
	cm_words text,
	cm_stars int4
);
ALTER TABLE comment_t ADD CONSTRAINT comment_pk PRIMARY KEY(cm_id);
CREATE TABLE user_t (
	us_id serial NOT NULL,
	us_name varchar(64) NOT NULL,
	us_password varchar(64) NOT NULL,
	us_role varchar(64) NOT NULL,
	us_email varchar(64) NOT NULL,
	us_full_name varchar(64)
);
ALTER TABLE user_t ADD CONSTRAINT user_pk PRIMARY KEY(us_id);
CREATE UNIQUE INDEX us_email_un ON user_t (us_email);
CREATE TABLE record_t (
	rc_id serial NOT NULL,
	rc_client_id int4 NOT NULL,
	rc_comment_id int4 NOT NULL,
	rc_day date NOT NULL,
	rc_time time NOT NULL
);
ALTER TABLE record_t ADD CONSTRAINT record_pk PRIMARY KEY(rc_id);
CREATE UNIQUE INDEX rc_comm_un ON record_t (rc_comment_id);
CREATE TABLE schedule_t (
	sc_id serial NOT NULL,
	sc_master_id int4 NOT NULL,
	sc_start_time time NOT NULL,
	sc_end_time time NOT NULL,
	sc_day date NOT NULL
);
ALTER TABLE schedule_t ADD CONSTRAINT schedule_pk PRIMARY KEY(sc_id);
CREATE TABLE schedule_record_m2m (
	sr_sc_id int4 NOT NULL,
	sr_rc_id int4 NOT NULL
);
ALTER TABLE schedule_record_m2m ADD CONSTRAINT sc_rc_pk PRIMARY KEY(sr_sc_id,sr_rc_id);
CREATE TABLE schedule_occupied_time_t (
	so_sc_id int4 NOT NULL,
	so_time time NOT NULL
);
ALTER TABLE schedule_occupied_time_t ADD CONSTRAINT so_pk PRIMARY KEY(so_sc_id,so_time);
ALTER TABLE record_t ADD CONSTRAINT client_record_fk FOREIGN KEY (rc_client_id) REFERENCES user_t(us_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE schedule_t ADD CONSTRAINT master_schdule_fk FOREIGN KEY (sc_master_id) REFERENCES user_t(us_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE schedule_record_m2m ADD CONSTRAINT record_on_schedules_fk FOREIGN KEY (sr_rc_id) REFERENCES record_t(rc_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE schedule_record_m2m ADD CONSTRAINT schedule_records_fk FOREIGN KEY (sr_sc_id) REFERENCES schedule_t(sc_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE record_t ADD CONSTRAINT record_comment_fk FOREIGN KEY (rc_comment_id) REFERENCES comment_t(cm_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE schedule_occupied_time_t ADD CONSTRAINT schedule_timing_fk FOREIGN KEY (so_sc_id) REFERENCES schedule_t(sc_id) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE OR REPLACE FUNCTION insert_schedule()
RETURNS TRIGGER AS
$$ 
BEGIN
  IF NEW.sc_start_time > NEW.sc_end_time THEN
     RAISE EXCEPTION 'schedule_wrong_interval';
  END IF;
  IF NEW.sc_start_time < '8:00' OR NEW.sc_end_time  > '20:00' THEN
     RAISE EXCEPTION 'schedule_wrong_working_hours';
  END IF;
  RETURN new;
END;
$$ LANGUAGE PLPGSQL;

CREATE TRIGGER insert_update_schedule_trigger BEFORE INSERT OR UPDATE ON schedule_t
FOR EACH ROW
EXECUTE PROCEDURE insert_schedule();

CREATE OR REPLACE FUNCTION insert_record_on_schedule()
RETURNS TRIGGER AS
$$ 
DECLARE
  schedule_start TIME;
  schedule_finish TIME;
  schedule_day DATE;
  record_day DATE;
  record_time TIME;
BEGIN
  SELECT sc_day, sc_start_time, sc_end_time 
  INTO schedule_day, schedule_start, schedule_finish FROM schedule_t 
  WHERE sc_id = NEW.sr_sc_id;
  SELECT rc_start_time, rc_day INTO record_time, record_day FROM record_t
  WHERE rc_id = NEW.sr_rc_id;
  IF record_day != schedule_day THEN
    RAISE EXCEPTION 'record_wrong_day';
  END IF;
  IF record_time > schedule_finish OR record_time < schedule_start THEN
    RAISE EXCEPTION 'record_wrong_time';
  END IF;
  IF EXISTS (SELECT * FROM schedule_occupied_time_t WHERE so_sc_id = NEW.sr_sc_id AND so_time = record_time) THEN
     RAISE EXCEPTION 'record_occupied';
  END IF;
  INSERT INTO schedule_occupied_time_t(so_sc_id, so_time) VALUES (NEW.sr_sc_id, record_time);
  RETURN NEW;
END;
$$ LANGUAGE PLPGSQL;

CREATE TRIGGER insert_record_on_schedule_trigger BEFORE INSERT ON schedule_record_m2m
FOR EACH ROW
EXECUTE PROCEDURE insert_record_on_schedule();

CREATE OR REPLACE FUNCTION delete_record()
RETURNS TRIGGER AS
$$ 
BEGIN
  DELETE FROM schedule_occupied_time_t 
  WHERE so_sc_id IN (SELECT sr_sc_id FROM schedule_record_m2m WHERE sr_rc_id = OLD.rc_id) AND so_time = OLD.rc_start_time;
  DELETE FROM schedule_record_m2m WHERE sr_rc_id = OLD.rc_id;
  RETURN OLD;
END;
$$ LANGUAGE PLPGSQL;

CREATE TRIGGER delete_record_trigger BEFORE DELETE ON record_t
FOR EACH ROW
EXECUTE PROCEDURE delete_record();

CREATE OR REPLACE FUNCTION delete_schedule()
RETURNS TRIGGER AS
$$ 
BEGIN
  DELETE FROM record_t WHERE rc_id IN (SELECT sr_rc_id FROM schedule_record_m2m WHERE sr_sc_id = OLD.sc_id);
  RETURN OLD;
END;
$$ LANGUAGE PLPGSQL;

CREATE TRIGGER delete_schedule_trigger BEFORE DELETE ON schedule_t
FOR EACH ROW
EXECUTE PROCEDURE delete_schedule();


