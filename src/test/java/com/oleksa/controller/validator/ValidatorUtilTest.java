package com.oleksa.controller.validator;

import static org.junit.Assert.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import com.oleksa.controller.exception.UnparsableDateParameter;
import com.oleksa.controller.exception.UnparsableIdException;
import com.oleksa.controller.exception.UnparsableTimeParameter;

public class ValidatorUtilTest {

	@Test
	public void testParseValidName() {
		Stream.of(null, "", "name", "four4", "name_", "name.name", "0names")
		.forEach(p -> assertFalse(ValidatorUtil.isValidName(p)));
	}
	
	@Test
	public void testParseValidFullname() {
		Stream.of(null, "", "name", "four4", "name_", "name.name", "0names")
		.forEach(p -> assertFalse(ValidatorUtil.validFullname(p)));
	}
	
	@Test
	public void testParseValidFullnameUkr() {
		Stream.of(null, "" , "Єґер" , "Полюй", "Дудак Вася", "Лариса Л.", "Треть’як Богдан Г.", "Лариса Kiss")
		.forEach(p -> assertFalse(ValidatorUtil.validFullname(p)));
		
		Stream.of("Іванов І.І.", "Петро Геннадій Дмитрович")
		.forEach(p -> assertTrue(ValidatorUtil.validFullname(p)));
	}
	
	@Test
	public void testParsePageParam() {
		Stream.of(null, "", "shit", "page", "-256", "-1", "0")
		.forEach(p -> assertEquals(0, ValidatorUtil.parsePageParameter(p)));
		assertEquals(0, ValidatorUtil.parsePageParameter("1"));
		assertEquals(1, ValidatorUtil.parsePageParameter("2"));
		assertEquals(9, ValidatorUtil.parsePageParameter("10"));
	}
	
	@Test
	public void testParseStarsParam() {
		Stream.of(null, "", "star", "-256", "-1", "6", "10", "100")
		.forEach(p -> assertEquals(0, ValidatorUtil.parseStarsParameter(p)));
		
		IntStream.range(0, 6).forEach(i -> assertEquals(i, ValidatorUtil.parseStarsParameter(String.valueOf(i))));
	}
	
	@Test
	public void testParseIdParam() {
		Stream.of(null, "", "star", "-256", "-1", "0")
		.forEach(p -> {
			try {
				assertEquals(0, ValidatorUtil.parseIdParameter(p));
				fail();
			} catch (UnparsableIdException e) {
				assertNotNull(e);
			}
		});
		
		IntStream.range(1, 1000).forEach(i -> {
			try {
				assertEquals(i, ValidatorUtil.parseIdParameter(String.valueOf(i)));
			} catch (UnparsableIdException e) {
				fail();
			}
		});
	}
	
	@Test
	public void testParseValidTime() {
		Stream.of(null, "", "name", ":", "000:0", "000:00", "00:000", "00:0", "000:000", "0:0", "00:0", "0:00", ":00", "00:", "24:00", "34:00", "00:61", "23:70")
		.forEach(p -> assertFalse(ValidatorUtil.validTime(p)));
		Stream.of("00:00", "20:00", "12:00", "10:00", "19:00", "09:00", "14:00", "01:00", "23:00")
		.forEach(p -> assertTrue(ValidatorUtil.validTime(p)));
	}
	
	@Test
	public void testParseTimeParamUnparsable() {
		Stream.of(null, "", "name", ":", "000:0", "000:00", "00:000", "00:0", "000:000", "0:0", "00:0", "0:00", ":00", "00:", "24:00", "34:00", "00:61", "23:70")
		.forEach(p -> {
			try {
				ValidatorUtil.parseTimeParameter(p);
				fail();
			} catch (UnparsableTimeParameter e) {
				assertNotNull(e);
			}
		});
		
	}
	
	@Test
	public void testParseTimeParamParsable() {
		Stream.of("00:00", "20:00", "12:00", "10:00", "19:00", "09:00", "14:00", "01:00", "23:00")
		.forEach(p -> { String[] split = p.split(":"); 
						int hour = Integer.parseInt(split[0]);
						int minute = Integer.parseInt(split[1]);
		try {
			assertEquals(LocalTime.of(hour, minute), ValidatorUtil.parseTimeParameter(p));
		} catch (UnparsableTimeParameter e) {
			fail();
		} });
	}
	
	@Test
	public void testParseValidDate() {
		Stream.of(null, "", "name", "/", "//", "///", "0/0/0", "0/01/2018", "01/0/2018", "00/01/2018", "01/00/2018", "01/01/200", "1/1/2018", "32/01/2018", "31/13/2018", "01/01/2017", "01/01/2020")
		.forEach(p -> { /*System.out.println(p); */assertFalse(ValidatorUtil.validDate(p)); });
		Stream.of("01/01/2018", "12/01/2018", "31/01/2018", "01/12/2019", "31/12/2019", "29/09/2019", "31/02/2019")
		.forEach(p -> assertTrue(ValidatorUtil.validDate(p)));
	}
	
	@Test
	public void testParseDateParamParsable() {
		Stream.of("2018-01-01", "2018-01-31", "2018-12-31", "2019-01-31", "2019-12-31")
		.forEach(p -> {
			String[] split = p.split("-");
			int year = Integer.parseInt(split[0]);
			int month = Integer.parseInt(split[1]);
			int day = Integer.parseInt(split[2]);
			try {
				assertEquals(LocalDate.of(year, month, day), ValidatorUtil.parseDateParameter(p));
			} catch (UnparsableDateParameter e) {
				fail();
			}
		});
	}
	
	@Test
	public void testParseDateParamUnparsable() {
		Stream.of(null, "", "name", "/", "//", "///", "0/0/0", "0/01/2018", "01/0/2018",
				"00/01/2018", "01/00/2018", "01/01/200", "1/1/2018", "32/01/2018", "31/13/2018",
				"01/01/2017", "01/01/2020", "2019-02-31")
		.forEach(p -> {
			try {
				ValidatorUtil.parseDateParameter(p);
				fail();
			} catch (UnparsableDateParameter e) {
				assertNotNull(e);
			}
		});
	}
	
	@Test(expected = UnparsableDateParameter.class)
	public void testBadDate() throws UnparsableDateParameter {
		LocalDate parameter = ValidatorUtil.parseDateParameter("2019-02-31");
		fail();
	}
}
