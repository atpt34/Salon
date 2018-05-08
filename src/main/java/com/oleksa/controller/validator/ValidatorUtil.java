package com.oleksa.controller.validator;

import static com.oleksa.controller.constants.RegexConstants.*;

import java.util.Objects;

public class ValidatorUtil {

    public static boolean validName(String name) {
        return Objects.nonNull(name) && name.matches(RE_NAME);
    }
    
    public static boolean validPassword(String pass) {
        return pass != null && pass.matches(RE_PASSWORD);
    }
    
    public static boolean validEmail(String email) {
        return email != null && email.matches(RE_EMAIL);
    }
    
    public static boolean validFullname(String full) {
        return full != null && full.matches(RE_FULLNAME);
    }
}
