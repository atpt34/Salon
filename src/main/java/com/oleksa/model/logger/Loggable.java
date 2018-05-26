package com.oleksa.model.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class that implements this interface can write logs.
 * 
 * @author atpt34
 *
 */
public interface Loggable {

    default Logger getLogger() {
        return LogManager.getLogger(getClass());
    }

}
