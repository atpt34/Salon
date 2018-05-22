package com.oleksa.controller.constants;

import java.util.Locale;

public enum RegexConstants {

    RE_NAME ("[A-Za-z][0-9A-Za-z]{5,15}"),
	RE_PASSWORD("[0-9A-Za-z_.]{6,15}"),
	RE_EMAIL ("[A-Za-z][0-9A-Za-z]{4,16}@([A-Za-z]{1,16}\\.){1,4}[a-z]{1,15}"),
	RE_FULLNAME ("(([A-Z][a-z]{2,32})(-[A-Z][a-z]{2,32})? ([A-Z]\\.){2})|(([А-ЯЄІЇҐ\'][а-яєіїґ\']{2,32}) (([А-ЯЄІЇҐ\'][а-яєіїґ\']{2,32} [А-ЯЄІЇҐ\'][а-яєіїґ\']{2,32})|(([А-ЯЄІЇҐ]\\.){2})))"),
	RE_PAGE ("[1-9][0-9]{0,32}"),
	RE_ID ("[1-9][0-9]{0,9}"),
	RE_STARS ("[0-5]"),
	RE_TEXT (".*"),
	RE_TIME ("(([01][0-9])|(2[0-3])):00"), 
	RE_DATE ("(((0[1-9])|([12][0-9])|(3[0-1]))/((0[1-9])|(1[0-2]))/201[89])|(201[89]-((0[1-9])|(1[0-2]))-((0[1-9])|([12][0-9])|(3[0-1])))");
	
	private final String regex;
	
	private RegexConstants(String value) {
		this.regex = value;
	}
	
	public String getRegex() {
		return regex;
	}
	
	public String getRegex(Locale locale) {
		return regex;
	}
}
