package com.oleksa.controller.command;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author atpt34
 *
 */
@FunctionalInterface
public interface Command {

    String execute(HttpServletRequest request);
    
}
