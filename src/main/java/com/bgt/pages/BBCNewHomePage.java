package com.bgt.pages;

import com.bgt.basepage.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.TestException;

public class BBCNewHomePage extends BasePage {


    @FindBy(xpath="//a[contains (@href, 'weather')]")
    private WebElement weatherTab;

    public BBCNewHomePage() {
        try {
            PageFactory.initElements(webDriver, this);
        } catch (Exception e) {
            throw new TestException(e.getMessage(), e);
        }
    }

    public void selectWeatherTab(){
        weatherTab.click();
    }

}
