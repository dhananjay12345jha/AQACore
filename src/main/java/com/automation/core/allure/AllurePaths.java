package com.automation.core.allure;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class AllurePaths {
    public static final String REPORT_PATH = getCurrentDir() + "/target/site/allure-maven-plugin";
    public static final String SOURCE_JS = ">Allure<";
    public static final String TARGET_JS = ">core<";
    public static final String SOURCE_NAME = "Allure Report";
    public static final String TARGET_NAME = "core Report";
    public static final String BACKUP_REPORT_PATH = getCurrentDir() + "/core-reports/";

    public static final int DEFAULT_WAIT_TIME = 15;
    public static final int DEFAULT_POLLING_TIME = 2;
    public static final boolean LOG = true;

    //Report Encoding
    public static final String ENCODING = "UTF-8";

    private static String getCurrentDir() {
        return System.getProperty("user.dir");
    }

    public static String getReportPath() {
        final String directory =  "./target/";
        final Path path = Paths.get(directory);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (final IOException e) {
            log.error(e.getMessage(), e);
        }
        return directory;
    }
}
