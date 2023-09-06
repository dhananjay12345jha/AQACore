package com.automation.core.driver;

import com.automation.core.caps.CloudCapabilities;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.SessionId;
import org.springframework.core.env.Environment;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * CoreIOSDriver class is to instantiate the WebDriver extending default {@link IOSDriver}
 */
public class CoreIOSDriver extends CoreDriver {

    private final IOSDriver wrappedDriver;

    /**
     * Constructor
     * @param url - RemoteAddress URL
     * @param env - Environment Variables
     * @throws MalformedURLException
     */
    public CoreIOSDriver(final String url, final Environment env) throws MalformedURLException {
        super(new IOSDriver<>(new URL(url), getCaps(env)));
        wrappedDriver = (IOSDriver) getWrappedDriver();
    }

    /**
     * Method to build desired capabilities
     * @param env Environment Variables
     * @return DesiredCapabilities
     */
    private static Capabilities getCaps(final Environment env) {
        return CloudCapabilities.getCaps(env);
    }

    public Capabilities getCapabilities()
    {
        return wrappedDriver.getCapabilities();
    }

    public SessionId getSessionId()
    {
        return wrappedDriver.getSessionId();
    }
}
