package com.oleksa.controller.exception;

/**
 * Indicates that time parameter is not parsable.
 * 
 * @author atpt34
 *
 */
public class UnparsableTimeParameter extends Exception {

    public UnparsableTimeParameter() { }

    public UnparsableTimeParameter(Exception e) {
        super(e);
    }

}
