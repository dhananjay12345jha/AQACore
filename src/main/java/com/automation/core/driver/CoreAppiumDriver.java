package com.automation.core.driver;

import com.automation.core.caps.CloudCapabilities;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Capabilities;
import org.springframework.core.env.Environment;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * CoreAppiumDriver class is to instantiate the WebDriver extending default {@link AppiumDriver}
 */
public class CoreAppiumDriver extends CoreDriver{

    public CoreAppiumDriver(final String remoteAddress, final Environment env) throws MalformedURLException {
        super(new AppiumDriver<>(new URL(remoteAddress), getCaps(env)));
    }

    /**
     * Method to build desired capabilities
     *
     * @param env Environment Variables
     * @return DesiredCapabilities
     */
    private static Capabilities getCaps(final Environment env) {
        return CloudCapabilities.getCaps(env);
    }
}
