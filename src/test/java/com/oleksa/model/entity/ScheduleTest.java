package com.oleksa.model.entity;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ScheduleTest {

	@Test
	public void testInitFreeHours() {
		LocalTime start = LocalTime.of(10, 0);
		LocalTime finish = LocalTime.of(18, 0);
		Set<LocalTime> expected = new HashSet<>();
		expected.add(start);
		expected.add(LocalTime.of(11, 0));
		expected.add(LocalTime.of(12, 0));
		expected.add(LocalTime.of(13, 0));
		expected.add(LocalTime.of(14, 0));
		expected.add(LocalTime.of(15, 0));
		expected.add(LocalTime.of(16, 0));
		expected.add(LocalTime.of(17, 0));
		expected.add(finish);
		LocalDate day = LocalDate.of(2018, 5, 30);
		User master = new User(null, "oleksa", "email", "pass", UserRole.ADMIN, null);
		Schedule schedule = new Schedule(null, master, day, start, finish, null);
		schedule.initFreeHours();
		Set<LocalTime> actual = schedule.getFreeHours();
//		System.out.println(actual);
		assertEquals(expected, actual);
		LocalTime remove1 = LocalTime.of(14, 00);
		LocalTime remove2 = LocalTime.of(11, 00);
		expected.remove(remove2);
		expected.remove(remove1);
		expected.remove(start);
		actual.remove(start);
		actual.remove(remove1);
		actual.remove(remove2);
//		System.out.println(actual);
		assertEquals(expected, actual);
	}
	
}
