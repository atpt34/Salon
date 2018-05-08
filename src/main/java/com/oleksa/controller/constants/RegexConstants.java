package com.oleksa.controller.constants;

public interface RegexConstants {

    String RE_NAME = "[0-9A-Za-z]{6,15}";
    String RE_PASSWORD = "[0-9A-Za-z_.]{6,15}";
    String RE_EMAIL = "[A-Za-z][0-9A-Za-z]{4,16}@([A-Za-z]{1,16}\\.){1,4}[a-z]{1,15}";
    String RE_FULLNAME = "([A-Z][a-z]{2,32})(-[A-Z][a-z]{2,32})? [A-Z]\\. [A-Z]\\.";
    
//    String RE_SECOND_NAME = "([A-Z][a-z]{2,32})(-[A-Z][a-z]{2,32})?";
//    String RE_THIRD_NAME = "([A-Z][a-z]{2,32})(-[A-Z][a-z]{2,32})?";
//    String RE_SHORT_NAME = "([A-Z][a-z]{2,32})(-[A-Z][a-z]{2,32})? ([A-Z]\\.)";
//    String RE_NICK_NAME = "[A-Za-z][0-9A-Za-z]{6,32}";
//    String RE_COMMENT = ".*";
//    String RE_HOME_PHONE = "[0-9]{2}-[0-9]{2}-[0-9]{2}";
//    String RE_MOBILE_PHONE = "(\\+38)?\\(?((093)|(063))\\)?([0-9]{2}-?[0-9]{2}-?[0-9]{3})";
//    String RE_SKYPE = "[A-Za-z][0-9A-Za-z]{6,32}";
//    String RE_INDEX = "[1-9][0-9]{4,}";
//    String RE_CITY = "[A-Z][a-z]{2,32}";
//    String RE_STREET = "[A-Z][a-z]{2,32}";
//    String RE_BUILDING = "[1-9][0-9]{0,32}(( |\\\\|\\/)[a-z])?";
//    String RE_ROOM = "[1-9][0-9]{0,32}";
//    String RE_GROUP = "[A-Z]*";
//    String RE_DATE = "(1[0-9]|2[0-4]|[1-9]):[1-5]?[0-9] [1-3]?[0-9](-| |\\\\|\\/|\\.)[1]?[0-9](-| |\\\\|\\/|\\.)(19|20)[0-9]{2}";
//    String RE_YES = "yes";
    
}
