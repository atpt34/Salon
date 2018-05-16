package com.oleksa.model.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface Loggable {

	default Logger getLogger() {
		return LogManager.getLogger(getClass());
	}
	
}
