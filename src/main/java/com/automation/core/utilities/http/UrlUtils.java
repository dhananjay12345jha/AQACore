package com.automation.core.utilities.http;

import com.automation.core.utilities.grid.PropUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class UrlUtils {

    private static final String MALFORMED_URL_ERROR = "[ERROR] MalformedURLException";

    public static URL updatePath(final URL url, final String path) {
        final URL tempUrl;
        try {
            tempUrl = new URL(url.getProtocol(), url.getHost(), url.getPort(), path, null);
        } catch (final MalformedURLException ex) {
            throw new IllegalArgumentException(MALFORMED_URL_ERROR, ex);
        }
        return tempUrl;
    }

    public  static URL envUrl(final Type type, final Integer port) {
        final String error = "[ERROR] env is a required property, please pass in at runtime or set it as your system environment variable." +
                             "\nThis property must be set in order to specify the ";
        final String env = PropUtils.get("env").orElseThrow(() -> new IllegalStateException(error));
        URL result;
        final String url;
        final boolean isExternal = type.toString().contains("test") || type.toString().contains("rtl");

        switch (env.toLowerCase().trim()) {
            case "local" :
               url = "http://localhost";
                break;
            case "dev" :
                if (!PropUtils.has("internal")) {
                    throw new IllegalStateException("[ERROR] You have specified an env of 'dev' and with an external argument which does not exist");
                }
                url = String.format(type.toString(), "dev", 1);
                break;

            case "qa":
            case "pre-prod":
                url = "";
                break;

            default:
                throw new IllegalArgumentException(
                        String.format("Unsupported argument: %s", env)
                );
        }
        try {
            result = new URL(url);
            result = (port != null) ?  new URL(result.getProtocol(), result.getHost(), port, "") : result;
        } catch (final MalformedURLException ex) {
            throw new IllegalArgumentException(MALFORMED_URL_ERROR, ex);
        }
        return result;
    }

    public  static URL envUrl(final Integer port) {
        return envUrl(Type.WEB_APP, port);
    }

    public  static URL envUrl(final Type type) {
        final String port = PropUtils.get("port").isPresent() ? PropUtils.get("port").get() : null;
        if(port != null){
            return envUrl(type, Integer.parseInt(port));
        } else {
            return envUrl(type, null);
        }
    }

    public  static URL envUrl() {
        return envUrl(Type.WEB_APP);
    }

    public static URL envUrl(final String path) {
        return updatePath(envUrl(), path);
    }

    public  static URL appendToPath(final URL url, final String pathSegment) {
        return updatePath(url, url.getFile() + pathSegment);
    }

    public enum ENV {
        LOCAL("local"),
        DEV("dev"),
        QA1("qa"),
        PREPROD("preprod", "pre-prod");

        final List<String> envNames;

        ENV(final String... envNames) {
            this.envNames = Arrays.asList(envNames);
        }

        public static ENV toEnum(final String name) {
            return Arrays.stream(ENV.values())
                    .filter(envVals -> envVals.envNames.contains(name.toLowerCase()))
                    .findFirst().orElseThrow(() -> new IllegalStateException("Unable to convert ENV name " + name + " to ENV enum"));
        }

    }

    public enum Type {
        WEB_APP("https://<>%s>.com/", "");

        private final String baseHostTemplate;

        Type(final String baseHostTemplate) {
            this.baseHostTemplate = baseHostTemplate;
        }

        Type(final String internal, final String external) {
            if (PropUtils.has("external")) {
                baseHostTemplate = external;
                PropUtils.set("external", "");
            } else {
                baseHostTemplate = internal;
                PropUtils.set("internal", "");
            }
        }

        @Override
        public String toString() {
            return baseHostTemplate;
        }
    }

}
