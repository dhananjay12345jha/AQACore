package com.automation.core.utilities.grid;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Slf4j
public class PropUtils {

    public static void set(final String property, String value) {
        value = hidePasswords(property, value);
        log.info(String.format("%n[INFO] PROPERTY SET: %s=%s%n", property, value));
        System.setProperty(property, value);
    }

    public static Optional<String> get(final String property) {
        Optional<String> propValue = Optional.empty();

        if (System.getProperty(property) != null && System.getProperties().containsKey(property)) {
            final String prop = System.getProperty(property);
            propValue = Optional.of(StringUtils.isBlank(prop) ? "true" : prop);
        } else if (System.getenv(property) != null) {
            final String prop = System.getenv(property);
            propValue = Optional.of(StringUtils.isBlank(prop) ? "true": prop);
        }

        if (propValue.isPresent()){
            final String sanitised = hidePasswords(property, propValue.get());
           log.info(String.format("%n[INFO] PROPERTY RETRIEVED: %s=%s%n", property, sanitised));
        }

        return propValue;
    }

    public static boolean has(final String property) {
        return PropUtils.get(property).isPresent();
    }

    private static String hidePasswords(final String property, final String value) {
        return StringUtils.containsIgnoreCase(property, "password") && !value.isEmpty() ? value.replaceAll("\\w", "*") : value ;
    }

}
