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
    us_password varchar(64) NOT NULL,
    us_role varchar(15) NOT NULL,
    us_email varchar(320) NOT NULL,
    us_full_name varchar(255) NOT NULL
);
ALTER TABLE user_t ADD CONSTRAINT user_pk PRIMARY KEY(us_id);
CREATE UNIQUE INDEX us_email_un ON user_t (us_email);
ALTER TABLE "user_t" ADD "us_name" character varying(15) NOT NULL;
CREATE UNIQUE INDEX us_name_un ON user_t (us_name);
CREATE TABLE record_t (
    rc_id serial NOT NULL,
    rc_client_id int4 NOT NULL,
    rc_description text,
    rc_start_time time NOT NULL,
    rc_comment_id int4 NOT NULL
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
ALTER TABLE record_t ADD CONSTRAINT client_record_fk FOREIGN KEY (rc_client_id) REFERENCES user_t(us_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE schedule_t ADD CONSTRAINT master_schdule_fk FOREIGN KEY (sc_master_id) REFERENCES user_t(us_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE schedule_record_m2m ADD CONSTRAINT record_on_schedules_fk FOREIGN KEY (sr_rc_id) REFERENCES record_t(rc_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE schedule_record_m2m ADD CONSTRAINT schedule_records_fk FOREIGN KEY (sr_sc_id) REFERENCES schedule_t(sc_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE record_t ADD CONSTRAINT record_comment_fk FOREIGN KEY (rc_comment_id) REFERENCES comment_t(cm_id) ON DELETE CASCADE ON UPDATE CASCADE;

INSERT INTO "user_t" ("us_password", "us_role", "us_email", "us_full_name", "us_name")
VALUES ('123456', 'admin', 'atpt34@gmail.com', 'Oleksii Tretinick', 'atpt34');
