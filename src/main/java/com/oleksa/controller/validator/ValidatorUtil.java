package com.oleksa.controller.validator;

import static com.oleksa.controller.constants.RegexConstants.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import com.oleksa.controller.exception.UnparsableIdException;

public class ValidatorUtil {
	
	private ValidatorUtil() {}

    public static boolean isValidName(String name) {
        return Objects.nonNull(name) && name.matches(RE_NAME.getRegex());
    }
    
    public static boolean validPassword(String pass) {
        return pass != null && pass.matches(RE_PASSWORD.getRegex());
    }
    
    public static boolean validEmail(String email) {
        return email != null && email.matches(RE_EMAIL.getRegex());
    }
    
    public static boolean validFullname(String full) {
        return full != null && full.matches(RE_FULLNAME.getRegex());
    }

    public static boolean validPage(String page) {
        return page != null && page.matches(RE_PAGE.getRegex());
    }
    
	public static boolean validDate(String date) {
        return date != null && date.matches(RE_DATE.getRegex());
    }
	
	public static boolean validTime(String time) {
        return time != null && time.matches(RE_TIME.getRegex());
    }
    
	public static boolean validId(String id) {
        return id != null && id.matches(RE_ID.getRegex());
    }
	
	public static boolean validText(String text) {
        return text != null && text.matches(RE_TEXT.getRegex());
    }
	
	public static boolean validStars(String stars) {
        return stars != null && stars.matches(RE_STARS.getRegex());
    }
	
	public static int parsePageParameter(String pageParam) {
		int page = 0;  // to zero indexing
		if(validPage(pageParam)) {
			page = Integer.parseInt(pageParam);
			--page;
		}
		return page;
	}
	
	public static String parseTextParameter(String textParam) {
		if(validText(textParam)) {
			return textParam;
		}
		return "";
	}
	
	public static int parseIdParameter(String idParam) throws UnparsableIdException {
		if(validId(idParam)) {
			return Integer.parseInt(idParam);
		}
		throw new UnparsableIdException();
	}
	
	public static int parseStarsParameter(String starsParam) {
		if(validStars(starsParam)) {
			return Integer.parseInt(starsParam);
		}
		return 0;
	}
	
	public static LocalTime parseTimeParameter(String timeParam) {
		if(validTime(timeParam)) {
			return LocalTime.parse(timeParam);
		}
		return LocalTime.NOON;
	}
	
	public static LocalDate parseDateParameter(String dateParam) {
		if(validDate(dateParam)) {
			return LocalDate.parse(dateParam);
		}
		return LocalDate.now();
	}
}
