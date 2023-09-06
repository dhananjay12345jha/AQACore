package com.automation.core.allure;


import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

/**
 * Few methods to add to Allure report
 */
@Slf4j
public class Report {

    /**
     * Method to add screenshot as Image to the html report
     *
     * @param imageName  as String
     * @param screenShot as byteArray
     */
    public static void addScreenshotAsImage(final String imageName, final byte[] screenShot) {
        try {
            final InputStream bios = new ByteArrayInputStream(screenShot);
            Allure.addAttachment(imageName, bios);
            bios.close();
        } catch (final IOException e) {
            log.error(e.getMessage(), e);
        }
    }
    /**
     * Method to add screenshot to the report
     *
     * @param screenShot inform of bytes
     * @return screenshot in bytes
     */
    @Attachment(value = "screenshot", type = "image/png")
    public static byte[] addScreenshot(final byte[] screenShot) {
        return screenShot;
    }

    /**
     * Method to add parameter to the testcase
     *
     * @param key   - Parameter Key
     * @param value - Parameter Value
     */
    public static void addParameter(final String key, final String value) {
        Allure.parameter(key, value);
    }


    /**
     * Method to add text to the report
     *
     * @return text
     */
    public static void addText(final String name, final String value) {
        Allure.addAttachment(name, value);
    }


    public static void copyCategoriesFile() throws IOException {
        final Path source = Paths.get(System.getProperty("user.dir") + "src/test/resources/categories.json");
        final Path target =  allureFilePath("categories.json");
        Files.copy(source, target);
    }

    public static void environment(final Map<String, String> properties) throws IOException {
        final Properties existingProps = new Properties();
        if (Files.exists(allureFilePath())) {
            try(final InputStream inputStream = Files.newInputStream(allureFilePath())) {
                existingProps.load(inputStream);
            }
        }

        try (final OutputStream outputStream = Files.newOutputStream(allureFilePath())) {
            final Properties props = new Properties();
            props.putAll(existingProps);
            props.putAll(properties);
            props.store(outputStream, null);
        }
    }

    private static Path allureFilePath(final String file) throws IOException {
        final String path = System.getProperty("user.dir") + "/reports/allure-results";
        final Path envPropFile = Paths.get(path + "/" + file);
        if (!Files.exists(Paths.get(path))) {
            Files.createDirectories(Paths.get(path));
        }
        return envPropFile;
    }

    private static Path allureFilePath() throws IOException {
        return allureFilePath("environment.properties");
    }
}
