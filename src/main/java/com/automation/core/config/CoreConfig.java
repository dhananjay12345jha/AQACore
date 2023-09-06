package com.automation.core.config;

import com.automation.core.allure.Report;
import com.automation.core.driver.*;
import com.automation.core.exceptions.CoreException;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import static com.automation.core.allure.AllurePaths.DEFAULT_WAIT_TIME;


@Configuration
public class CoreConfig {

    /**
     * Access to {@link Environment} variable
     */
    @Autowired
    private Environment environment;


    @Lazy
    @Bean(destroyMethod = "quit")
    @Scope("cucumber-glue")
    @Conditional(IsIOS.class)
    public CoreIOSDriver coreIOSDriver() {
        try {

            final String mode = System.getProperty("mode", "").toUpperCase();
            final String URL = "https://wtcnewlookauto1:qq17uAyWKauUpVCYPNKW@hub-cloud.browserstack.com/wd/hub";
            final CoreIOSDriver iosDriver = new CoreIOSDriver(URL, environment);

            if ("saucelabs".equalsIgnoreCase(mode)) {
                Report.addText("Testobject job link", iosDriver.getCapabilities().getCapability("testobject_test_report_url").toString());
            } else {
                Report.addText("Cloud Session ID", iosDriver.getSessionId().toString());
            }
            iosDriver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_TIME, TimeUnit.SECONDS);
            Runtime.getRuntime().addShutdownHook(new Thread(iosDriver::quit));
            return iosDriver;
        } catch (final BeanCreationException | MalformedURLException e) {
            throw new CoreException("Failed to create the coreDriver instance");
        }
    }

    @Lazy
    @Bean(destroyMethod = "quit")
    @Scope("cucumber-glue")
    @Conditional(IsAndroid.class)
    public CoreAppiumDriver coreAppiumDriver() {
        try {

            final String mode = System.getProperty("mode", "").toUpperCase();
            String URL = "";
            switch (mode) {
                case "SAUCELABS":
                    URL = "http://eu1.appium.testobject.com/wd/hub";
                    break;
                case "BROWSERSTACK":
                    URL = "http://wtcnewlookauto1:qq17uAyWKauUpVCYPNKW@hub-cloud.browserstack.com/wd/hub";
                    break;
            }

            final CoreAppiumDriver appiumDriver = new CoreAppiumDriver(URL, environment);

//            if (mode.equalsIgnoreCase("browserstack")) {
//                Report.addText("Testobject job link", appiumDriver.getCapabilities().getCapability("testobject_test_report_url").toString());
//            } else {
//                Report.addText("Cloud Session ID", appiumDriver.getSessionId().toString());
//            }
            appiumDriver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_TIME, TimeUnit.SECONDS);
            Runtime.getRuntime().addShutdownHook(new Thread(appiumDriver::quit));
            return appiumDriver;
        } catch (final BeanCreationException | MalformedURLException e) {
            throw new CoreException("Failed to create the coreDriver instance");
        }
    }

    @Lazy
    @Bean(destroyMethod = "quit")
    @Scope("cucumber-glue")
    @Conditional(NotIOS.class)
    public CoreWebDriver coreWebDriver() throws MalformedURLException {
        try {
            return new CoreWebDriver(environment);
        } catch (final BeanCreationException e) {
            throw new CoreException("Failed to create the coreDriver instance");
        }
    }

    public static void takeScreenshot(final WebDriver webDriver) throws IOException {
        BufferedImage buffImage = null;
        if (webDriver instanceof CoreIOSDriver) {
            buffImage = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportRetina(600, 105, 5, 3F))
                    .takeScreenshot(webDriver)
                    .getImage();
        } else if (System.getProperty("core_ENV#platformName") != null
                && "android".equalsIgnoreCase(System.getProperty("core_ENV#platformName"))) {
            buffImage = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportRetina(750, 0, 0, 5F))
                    .takeScreenshot(webDriver)
                    .getImage();
        } else {
            buffImage = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(1000))
                    .takeScreenshot(webDriver)
                    .getImage();
        }
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(buffImage, "png", os);
        Report.addScreenshotAsImage("screenshot", os.toByteArray());
    }
}
