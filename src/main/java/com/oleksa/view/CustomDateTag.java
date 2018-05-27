package com.oleksa.view;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import static com.oleksa.controller.constants.MessagesConstants.*;

public class CustomDateTag extends TagSupport {

    private LocalDate date;
    private String locale;
    
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            if (locale.equals(PARAM_LANG_UA)) {
                pageContext.getOut()
                        .write(date.format(DateTimeFormatter.ofPattern(DATE_UA_FORMAT)));
            } else {
                pageContext.getOut()
                        .write(date.format(DateTimeFormatter.ofPattern(DATE_EN_FORMAT)));
            }
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
    
}
