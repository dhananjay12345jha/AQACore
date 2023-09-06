package com.automation.core.driver;

import com.automation.core.allure.Report;
import com.automation.core.caps.CloudCapabilities;
import com.automation.core.exceptions.CoreDriverException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.core.env.Environment;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Optional;
import java.util.logging.Level;

import static com.automation.core.allure.AllurePaths.DEFAULT_WAIT_TIME;
import static java.lang.System.getProperty;
import static org.openqa.selenium.remote.BrowserType.CHROME;

/**
 * CoreDriver class is to instantiate the WebDrivers extending default {@link EventFiringWebDriver}
 */
public class CoreWebDriver extends CoreDriver{

    /**
     * Constructor
     *
     * @param env
     */
    public CoreWebDriver(Environment env) throws MalformedURLException {
        super(getDriver(env));
    }

    public static ThreadLocal<String> sessionID = new ThreadLocal<>();

    public static String seleniumGridWebdriver = "http://<>:4444/wd/hub";
    public static String BrowserStackMobileDriver = "http://us1.appium.testobject.com/wd/hub";
    public static String BrowserStackDriver = "https://<<username>>:<<accessKey>>@hub-cloud.browserstack.com/wd/hub";


    /**
     * This method will instantiate {@link WebDriver}
     *
     * @param env
     * @return {@link WebDriver} object
     * @see WebDriver
     */
    private static WebDriver getDriver(Environment env) throws MalformedURLException {

        WebDriver driver;
        DriverType driverType;
        String browserMode = getProperty("mode", "").toUpperCase();
        if (browserMode.equalsIgnoreCase(""))
            browserMode = System.getProperty("core_ENV#mode", DriverType.CHROME.toString()).toUpperCase();
        String driverVersion = System.getProperty("driverversion", "");
        String driverPath = System.getProperty("driverpath", "");

        try {
            driverType = DriverType.valueOf(browserMode);
        } catch (IllegalArgumentException ex) {
            throw new CoreDriverException("Browser { " + browserMode + " } not found, please use one of the below\n\t" +
                    "1. chrome\n\t" +
                    "2. firefox\n\t" +
                    "3. selenium grid\n\t");
        }

        switch (driverType) {
            case CHROME:
                if (driverPath.equals("")) {
                    (driverVersion.equals("") ? WebDriverManager.chromedriver() : WebDriverManager.chromedriver().version(driverVersion)).setup();
                } else {
                    System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, driverPath);
                }
                ChromeOptions chromeOptionsDefault = new ChromeOptions();
                chromeOptionsDefault.addArguments("--disable-web-security", "--ignore-certificate-errors", "--start-maximized");
                driver = new ChromeDriver();
                driver.manage().window().setSize(new Dimension(375, 812));
                driver.manage().window().setPosition(new Point(0, 0));
                break;

            case SELENIUMGRID:
                String browserName = env.getProperty("core_ENV#browser").toUpperCase();
                String browserVersion = env.getProperty("core_ENV#browserVersion");
                switch (browserName) {
                    case "CHROME":
                        ChromeOptions chromeOptions = new ChromeOptions();
                        LoggingPreferences logPrefs = new LoggingPreferences();
                        logPrefs.enable(LogType.BROWSER, Level.ALL);
                        chromeOptions.addArguments("--disable-web-security", "--ignore-certificate-errors", "--start-maximized", "--incognito", "--window-size=1920,1440", "--headless");
                        chromeOptions.setBinary("<path>\\chrome.exe");
                        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                        desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, CHROME);
                        desiredCapabilities.setCapability(CapabilityType.BROWSER_VERSION, browserVersion);
                        desiredCapabilities.setCapability("build", "build value");
                        desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
                        desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                        driver = new RemoteWebDriver(new URL(seleniumGridWebdriver), desiredCapabilities);
                        Report.addText("[INFO]", "Execution on the local selenium Grid on " + browserName + " - Version " + browserVersion);
                        break;

                    case "FIREFOX":

                        FirefoxProfile profileFF = new FirefoxProfile();
                        profileFF.setPreference("dom.ipc.plugins.enabled.libflashplayer.so", false);
                        profileFF.setPreference("browser.privatebrowsing.autostart", true);
                        FirefoxOptions firefoxOptionsSG = new FirefoxOptions();
                        firefoxOptionsSG.addArguments("-private");
                        firefoxOptionsSG.addPreference("network.proxy.type", 0);
                        firefoxOptionsSG.setHeadless(true);
                        firefoxOptionsSG.setAcceptInsecureCerts(true);
                        firefoxOptionsSG.setProfile(profileFF);
                        firefoxOptionsSG.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
                        driver = new RemoteWebDriver(new URL(seleniumGridWebdriver), firefoxOptionsSG);
                        Report.addText("[INFO]", "Execution on the local selenium Grid on " + browserName);
                        break;

                    case "IE":
                        InternetExplorerOptions capabilitiesIE = new InternetExplorerOptions();
                        capabilitiesIE.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS, true);
                        capabilitiesIE.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                        capabilitiesIE.setCapability(InternetExplorerDriver.IE_SWITCHES, "-private");
                        driver = new RemoteWebDriver(new URL(seleniumGridWebdriver), capabilitiesIE);
                        Report.addText("[INFO]", "Execution on the local selenium Grid on " + browserName);
                        break;

                    case "EDGE":
                        EdgeOptions edgeOptions = new EdgeOptions();
                        edgeOptions.setCapability("InPrivate", true);
                        driver = new RemoteWebDriver(new URL(seleniumGridWebdriver), edgeOptions);
                        Report.addText("[INFO]", "Execution on the local selenium Grid on " + browserName);
                        break;

                    default:
                        DesiredCapabilities defaultdesiredCapabilities = new DesiredCapabilities();
                        driver = new RemoteWebDriver(new URL(seleniumGridWebdriver), defaultdesiredCapabilities);
                }
                break;
            case CHROMEHEADLESS:
                if (driverPath.equals("")) {
                    (driverVersion.equals("") ? WebDriverManager.chromedriver() : WebDriverManager.chromedriver().version(driverVersion)).setup();
                } else {
                    System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, driverPath);
                }
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                driver = new ChromeDriver(chromeOptions);
                break;
            case FIREFOX:
                if (driverPath.equals("")) {
                    (driverVersion.equals("") ? WebDriverManager.firefoxdriver() : WebDriverManager.firefoxdriver().version(driverVersion)).setup();

                } else {
                    System.setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY, driverPath);
                }
                driver = new FirefoxDriver();
                break;
            case EDGE:
                if (driverPath.equals("")) {
                    (driverVersion.equals("") ? WebDriverManager.edgedriver() : WebDriverManager.edgedriver().version(driverVersion)).setup();
                } else {
                    System.setProperty(EdgeDriverService.EDGE_DRIVER_EXE_PROPERTY, driverPath);
                }
                driver = new EdgeDriver();
                break;
            case IE:
                if (driverPath.equals("")) {
                    (driverVersion.equals("") ? WebDriverManager.iedriver() : WebDriverManager.iedriver().version(driverVersion)).setup();
                } else {
                    System.setProperty(InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY, driverPath);
                }
                driver = new InternetExplorerDriver();
                break;
            case BROWSERSTACK:
                driver = createBrowserStackDriver(env);
                break;
            default:
                throw new CoreDriverException("Browser { " + browserMode + " } not found, please use one of the below\n\t" +
                                              "1. chrome\n\t" +
                                              "2. firefox\n\t" +
                                              "3. safari\n\t" +
                                              "4. edge\n\t" +
                                              "5. ie\n\t" +
                                              "6. chromeheadless\n\t" +
                                              "7. selenium grid");
        }
        if (!browserMode.equalsIgnoreCase("BrowserStack")) {
            driver.manage().window().maximize();
        } else {
            if (System.getProperty("core_ENV#deviceType", "").equalsIgnoreCase("desktop")) {
                driver.manage().window().maximize();
            }
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_WAIT_TIME));
        Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
        return driver;
    }

    private static WebDriver createBrowserStackDriver(Environment env) {
        RemoteWebDriver webDriver;
        DesiredCapabilities capabilities = CloudCapabilities.getCaps(env);
        String deviceType = env.getProperty("core_ENV#deviceType");
        if (env.getProperty("core_ENV#browser", "").equalsIgnoreCase("chrome")) {
            ChromeOptions opts = new ChromeOptions();
            opts.addArguments("--disable-web-security", "--ignore-certificate-errors", "--start-maximized");
            capabilities.setCapability(ChromeOptions.CAPABILITY, opts);
        }
        if (deviceType.equalsIgnoreCase("mobile")) {
            try {
                webDriver = new RemoteWebDriver(new URL(BrowserStackDriver.replace("<<username>>", env.getProperty("BROWSERSTACK_USERNAME"))
                        .replace("<<accessKey>>", env.getProperty("BROWSERSTACK_ACCESS_KEY"))), capabilities);
                sessionID.set(webDriver.getSessionId().toString());
//                Report.addText("TestObject job link", webDriver.getCapabilities().getCapability("testobject_test_report_url").toString());
                if (!sessionCreated(webDriver)) {
                    throw new CoreDriverException("[ERROR] Unable to create browserstack driver session for device");
                }
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            return webDriver;
        } else {
            try {
                webDriver = new RemoteWebDriver(new URL(BrowserStackDriver
                        .replace("<<username>>", env.getProperty("BROWSERSTACK_USERNAME"))
                        .replace("<<accessKey>>", env.getProperty("BROWSERSTACK_ACCESS_KEY"))), capabilities);
                sessionID.set(webDriver.getSessionId().toString());

                //Report.addText("BrowserStack job link", sauceDesktopresults + sessionID);

                if (!sessionCreated(webDriver)) {
                    throw new CoreDriverException("[ERROR] Unable to create browserstack driver session for desktop");
                }
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            return webDriver;
        }
    }

    public static boolean sessionCreated(WebDriver webDriver) {
        return driverSession(webDriver).isPresent();
    }

    public static Optional<String> driverSession(WebDriver webDriver) {
        Optional<String> sessionId = Optional.empty();
        try {
            sessionId = Optional.of(((RemoteWebDriver) webDriver).getSessionId().toString());
        } catch (Exception ex) {
        }
        return sessionId;
    }
}

