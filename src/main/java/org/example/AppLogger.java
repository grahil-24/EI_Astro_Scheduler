package org.example;

import java.util.logging.Logger;

public class AppLogger implements TaskObserver {
    private static final Logger logger = Logger.getLogger(AppLogger.class.getName());

    @Override
    public void update(String message) {
        logger.info("Log: " + message);
        System.out.flush();
    }

}
