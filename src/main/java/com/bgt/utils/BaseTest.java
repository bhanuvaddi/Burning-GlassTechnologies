package com.bgt.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {

    private static WebDriver driver;

    private static WebDriver startDriver(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        return driver;
    }

    public static WebDriver getDriver(){
        if(driver!=null){
            return driver;
        }

        WebDriver driver = startDriver();
        return driver;
    }

    private static void closeDriver(){
        driver.close();
    }
}
