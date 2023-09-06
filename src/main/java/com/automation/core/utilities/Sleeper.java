package com.automation.core.utilities;

import org.apache.commons.lang3.time.StopWatch;

/**
 * Sleeper class for the code to sleep
 */
public class Sleeper {
    public static void sleep(final long millis) {
        final StopWatch stopwatch = new StopWatch();
        stopwatch.start();
        do {

        } while (stopwatch.getTime() < millis);
        stopwatch.stop();
        stopwatch.reset();
    }
}
