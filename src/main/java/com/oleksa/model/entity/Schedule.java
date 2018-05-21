package com.oleksa.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

public final class Schedule extends AbstractEntity<Integer> {

	private User master;
	private LocalDate day;
	private LocalTime startHour;
	private LocalTime endHour;
	private Set<Record> records;

	public Schedule(Integer id, User master, LocalDate day, LocalTime startHour, LocalTime endHour,
			Set<Record> records) {
		super(id);
		this.master = master;
		setDay(day);
		setStartHour(startHour);
		setEndHour(endHour);
		this.records = records;
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

	@Override
	public int hashCode() {
		return Objects.hash(id, master, day, startHour, endHour, records);
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
				&& Objects.equals(records, that.records);
	}

	@Override
	public String toString() {
		return "Schedule [master=" + master + ", day=" + day + ", startHour=" + startHour + ", endHour=" + endHour
				+ ", id=" + id + "]";
	}

}
