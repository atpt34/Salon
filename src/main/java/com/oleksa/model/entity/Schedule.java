package com.oleksa.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Schedule by master on which clients make records.
 * 
 * @author atpt34
 *
 */
public final class Schedule extends AbstractEntity<Integer> {

    private User master;
    private LocalDate day;
    private LocalTime startHour;
    private LocalTime endHour;
    private Set<Record> records;
    private Set<LocalTime> freeHours;

    public Schedule(Integer id, User master, LocalDate day, LocalTime startHour, LocalTime endHour,
            Set<Record> records) {
        super(id);
        this.master = master;
        setDay(day);
        setStartHour(startHour);
        setEndHour(endHour);
        this.records = records;
        this.freeHours = null;
    }

    public void initFreeHours() {
        freeHours = new LinkedHashSet<>();
        for(LocalTime lt = startHour; lt.isBefore(endHour); lt = lt.plusHours(1)) {
            freeHours.add(lt);
        }
        freeHours.add(endHour);
    }

    public User getMaster() {
        return master;
    }

    public void setMaster(User master) {
        this.master = master;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = Objects.requireNonNull(day);
    }

    public LocalTime getStartHour() {
        return startHour;
    }

    public void setStartHour(LocalTime startHour) {
        this.startHour = Objects.requireNonNull(startHour);
    }

    public LocalTime getEndHour() {
        return endHour;
    }

    public void setEndHour(LocalTime endHour) {
        this.endHour = Objects.requireNonNull(endHour);
    }

    public Set<Record> getRecords() {
        return records;
    }

    public void setRecords(Set<Record> records) {
        this.records = records;
    }

    public Set<LocalTime> getFreeHours() {
        return freeHours;
    }

    public void setFreeHours(Set<LocalTime> freeHours) {
        this.freeHours = freeHours;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, master, day, startHour, endHour, records, freeHours);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Schedule that = (Schedule) obj;
        return Objects.equals(id, that.id)
                && Objects.equals(master, that.master)
                && Objects.equals(day, that.day)
                && Objects.equals(startHour, that.startHour)
                && Objects.equals(endHour, that.endHour)
                && Objects.equals(records, that.records)
                && Objects.equals(freeHours, that.freeHours);
    }

    @Override
    public String toString() {
        return "Schedule [ id=" + id + ", master=" + master + ", day=" + day + ", startHour=" + startHour + ", endHour=" + endHour
                + ", freeHours=" + freeHours + "]";
    }

}
