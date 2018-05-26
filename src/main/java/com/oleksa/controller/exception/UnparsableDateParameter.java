package com.oleksa.controller.exception;

/**
 * Indicates that give date parameter is not parsable.
 *  
 * @author atpt34
 *
 */
public class UnparsableDateParameter extends Exception {

    public UnparsableDateParameter(Exception e) {
        super(e);
    }

    public UnparsableDateParameter() {
    }

}
