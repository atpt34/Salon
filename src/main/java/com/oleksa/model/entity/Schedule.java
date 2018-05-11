package com.oleksa.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Schedule extends AbstractEntity<Integer> {

	private User master;
	private LocalDate day;
	private LocalTime startHour;
	private LocalTime endHour;
	private List<Record> records; // no or Set

	public Schedule(Integer id, User master, LocalDate day, LocalTime startHour, LocalTime endHour,
			List<Record> records) {
		super(id);
		this.master = master;
		this.day = day;
		this.startHour = startHour;
		this.endHour = endHour;
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
		this.day = day;
	}

	public LocalTime getBeginHour() {
		return startHour;
	}

	public void setBeginHour(LocalTime startHour) {
		this.startHour = startHour;
	}

	public LocalTime getEndHour() {
		return endHour;
	}

	public void setEndHour(LocalTime endHour) {
		this.endHour = endHour;
	}

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((startHour == null) ? 0 : startHour.hashCode());
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result + ((endHour == null) ? 0 : endHour.hashCode());
		result = prime * result + ((master == null) ? 0 : master.hashCode());
		result = prime * result + ((records == null) ? 0 : records.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schedule other = (Schedule) obj;
		if (startHour == null) {
			if (other.startHour != null)
				return false;
		} else if (!startHour.equals(other.startHour))
			return false;
		if (day == null) {
			if (other.day != null)
				return false;
		} else if (!day.equals(other.day))
			return false;
		if (endHour == null) {
			if (other.endHour != null)
				return false;
		} else if (!endHour.equals(other.endHour))
			return false;
		if (master == null) {
			if (other.master != null)
				return false;
		} else if (!master.equals(other.master))
			return false;
		if (records == null) {
			if (other.records != null)
				return false;
		} else if (!records.equals(other.records))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Schedule [master=" + master + ", day=" + day + ", startHour=" + startHour + ", endHour=" + endHour
				/*+ ", records=" + records*/ + "]";
	}
	
}
