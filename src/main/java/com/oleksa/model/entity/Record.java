package com.oleksa.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

/**
 * 
 * Record that client makes.
 * 
 * @author atpt34
 *
 */
public final class Record extends AbstractEntity<Integer> {

    private User client;
    private LocalTime hour;
    private LocalDate day;
    private Comment comment;
    private Set<Schedule> schedules;

    public Record(Integer id, User client, LocalTime hour, LocalDate day, Comment comment, Set<Schedule> schedules) {
        super(id);
        this.client = client;
        setHour(hour);
        setDay(day);
        this.comment = comment;
        this.schedules = schedules;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = Objects.requireNonNull(hour);
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = Objects.requireNonNull(day);
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, hour, day, comment, schedules);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Record that = (Record) obj;
        return Objects.equals(id, that.id)
                && Objects.equals(client, that.client)
                && Objects.equals(hour, that.hour)
                && Objects.equals(day, that.day)
                && Objects.equals(comment, that.comment)
                && Objects.equals(schedules, that.schedules);

    }

    @Override
    public String toString() {
        return "Record [id=" + id + ", client=" + client + ", hour=" + hour + ", day=" + day + ", comment=" + comment /* + ", schedules=" + schedules*/
                + "]";
    }

}
