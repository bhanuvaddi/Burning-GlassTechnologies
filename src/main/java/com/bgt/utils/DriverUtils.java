package com.bgt.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class DriverUtils {
    private static final Logger log = LogManager.getLogger(DriverUtils.class);
    public static EventFiringWebDriver webdriver;
    public static ConfigFileReader configFileReader = new ConfigFileReader();

    private static String userPath=System.getProperty ("user.dir");
    private static String home=System.getProperty ("user.home");
    private static final String DRIVER_BASE_PATH = userPath + "/src/test/resources/drivers/";
    private static final String LINUX_CHROMEDRIVER = "/src/test/resources/drivers/Linux";
    private static final String WIN_CHROMEDRIVER = "chromedriver.exe";
    private static String driverType = configFileReader.getBrowser().toLowerCase();
    private static int implicitWaitTime = 0;
    private static String os = System.getProperty("os.name").toLowerCase();
    private static String browserBinaryPath ="";
    private static String driverConfigPath = "";
    private static Boolean isHeadless = false;

    static int getImplicitWaitTime() {
        return implicitWaitTime;
    }

    static void setImplicitWaitTime(int implicitWaitTime) {
        DriverUtils.implicitWaitTime = implicitWaitTime;
        webdriver.manage ().timeouts ().implicitlyWait (implicitWaitTime, TimeUnit.SECONDS);
    }

    public static EventFiringWebDriver getWebDriver() {
        if (webdriver==null || webdriver.toString().contains ("null")) {
            webdriver=DriverUtils.getDriverInstance ();
            log.info ("Creating new driver instance");
        }
        return webdriver;
    }

    private static EventFiringWebDriver getDriverInstance() {
        log.debug("OS detected: "+ os);
        log.info("Browser: "+ driverType);
        File BROWSER_DRIVER;
        RemoteWebDriver driver = null;
        String driverPath ="";

        switch (driverType) {
            case "ie": {
                BROWSER_DRIVER = new File(DRIVER_BASE_PATH + "/IEDriverServer.exe");
                if (BROWSER_DRIVER.exists()){
                    System.setProperty ("webdriver.ie.driver", String.valueOf(BROWSER_DRIVER));
                }else {
                    log.warn("IE Driver not available @ : " + DRIVER_BASE_PATH);
                    log.warn("Initializing IE Driver through 'WebDriverManager'");
                    WebDriverManager.config().setTargetPath(DRIVER_BASE_PATH);
                    WebDriverManager.iedriver().setup();
                    driverPath = WebDriverManager.iedriver().getBinaryPath();
                }
                driver = new InternetExplorerDriver();
                Capabilities cp = driver.getCapabilities();
                log.info ("Browser Name    :" + " " + cp.getBrowserName ());
                log.info ("Browser Version    :" + " " + cp.getVersion ());
                log.info ("Browser Driver Path    :" + " " + driverPath);
                break;
            }
            case "chrome": {
                HashMap<String, Object> chromePrefs= new HashMap<>();
                chromePrefs.put ("profile.default_content_settings.popups", 0);
                ChromeOptions options=new ChromeOptions ();

                if (driverConfigPath.isEmpty()) {
                    if (os.contains("win")){
                        driverPath = DRIVER_BASE_PATH + WIN_CHROMEDRIVER;
                    }
                    else if (os.contains("linux")){
                        driverPath = LINUX_CHROMEDRIVER;
                    }
                }
                BROWSER_DRIVER = new File(driverPath);
                if(BROWSER_DRIVER.exists()){
                    System.setProperty ("webdriver.chrome.driver", String.valueOf(BROWSER_DRIVER));
                }else{
                    log.warn("Chrome Driver not available @ : " + driverPath);
                    log.warn("Initializing Chrome Driver through 'WebDriverManager'");
                    WebDriverManager.config().setTargetPath(DRIVER_BASE_PATH);
                    WebDriverManager.chromedriver().setup();
                    driverPath = WebDriverManager.chromedriver().getBinaryPath();
                }

                if (isHeadless) {
                    chromePrefs.put("behavior", "allow");
                    chromePrefs.put("downloadPath",  home + "/Downloads/");
                    chromePrefs.put ("download.prompt_for_download", false);
                    //chromePrefs.put("download.default_directory", home + "/Downloads/");
                    options.addArguments("--headless");
                    options.addArguments ("--disable-gpu");
                    options.addArguments("window-size=1920x1080");
                    options.addArguments("--proxy-server='direct://'"); //remove if going slow
                    options.addArguments("--proxy-bypass-list=*"); //remove if going slow
                    log.debug("Running in headless mode");
                }

                options.addArguments ("no-sandbox");
                options.addArguments ("--start-maximized");
                options.setExperimentalOption ("prefs", chromePrefs);
                options.setExperimentalOption("useAutomationExtension", false);
                options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                options.setCapability(ChromeOptions.CAPABILITY, options);
                System.setProperty ("webdriver.chrome.silentOutput", "true");
                driver=new ChromeDriver (options);
                Capabilities cap2= driver.getCapabilities ();
                log.info ("Chrome Browser Name    :" + " " + cap2.getBrowserName ());
                log.info ("Chrome Browser Version    :" + " " + cap2.getVersion ());
                log.info ("Browser Driver Path    :" + " " + driverPath);
                break;
            }

            case "firefox": {
                //Firefox V55 requires Gheckodriver v20.1 or below
                BROWSER_DRIVER = new File(DRIVER_BASE_PATH + "/geckodriver.exe");
                if(BROWSER_DRIVER.exists()){
                    System.setProperty ("webdriver.gecko.driver", String.valueOf(BROWSER_DRIVER));
                }else {
                    log.warn("Firefox Driver not available @ : " + DRIVER_BASE_PATH);
                    log.warn("Initializing Firefox Driver through 'WebDriverManager'");
                    WebDriverManager.config().setTargetPath(DRIVER_BASE_PATH);
                    WebDriverManager.firefoxdriver().setup();
                    driverPath = WebDriverManager.firefoxdriver().getBinaryPath();
                }
                FirefoxProfile profile=new FirefoxProfile ();
                profile.setPreference ("browser.download.folderList", 2);
                profile.setPreference ("browser.download.manager.showWhenStarting", false);
                profile.setPreference ("browser.helperApps.neverAsk.saveToDisk", "application/x-iso9660-image, application/json");
                profile.setPreference ("pdfjs.disabled", true);  // disable the built-in viewer
                FirefoxOptions cap =new FirefoxOptions();
                cap.setCapability (FirefoxDriver.PROFILE, profile);
                driver=new FirefoxDriver (cap);
                Capabilities cap2= driver.getCapabilities ();
                log.info ("Browser Name    :" + " " + cap2.getBrowserName ());
                log.info ("Browser Version    :" + " " + cap2.getVersion ());
                log.info ("Browser Driver Path    :" + " " + driverPath);
                break;
            }
        }
        webdriver = new EventFiringWebDriver(driver);
        webdriver.manage ().window ().maximize ();
        setImplicitWaitTime((int) configFileReader.getImplicitlyWait());
        return webdriver;
    }
}
