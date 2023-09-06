package com.automation.core.caps;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.core.env.Environment;

@Slf4j
public class CloudCapabilities {
    public static DesiredCapabilities getCaps(final Environment env) {
        final DesiredCapabilities caps = new DesiredCapabilities();
        System.getProperties().forEach((key, value) -> {
            if (key.toString().startsWith("core_ENV#")) {
                caps.setCapability(key.toString().split("core_ENV#")[1], value);
            }
        });
        return caps;
    }
}
