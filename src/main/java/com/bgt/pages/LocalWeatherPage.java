package com.bgt.pages;

import com.bgt.basepage.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.TestException;

public class LocalWeatherPage extends BasePage {
    private static final Logger log = LogManager.getLogger(LocalWeatherPage.class);

    @FindBy(id = "ls-c-search__input-label")
    private WebElement weatherSearchTextField;

    @FindBy(id = "location-list")
    private WebElement searchList;

    @FindBy(xpath="//*[contains(@id, 'location-list')]//li")
    private WebElement searchList1stItem;

    @FindBy(xpath="//input[contains (@title, 'Search for a location')]")
    private WebElement searchButton;

    @FindBy(id = "wr-location-name-id")
    private WebElement searchItemResultsHeader;

    public LocalWeatherPage() {
        try {
            PageFactory.initElements(webDriver, this);
        } catch (Exception e) {
            throw new TestException(e.getMessage(), e);
        }
    }

    public void searchWeather(String searchItem){
        weatherSearchTextField.clear();
        weatherSearchTextField.sendKeys(searchItem);
        try{
            if (searchList1stItem.isDisplayed()){
                searchList1stItem.click();
            }
        }catch (StaleElementReferenceException exception){
            log.debug("search List Item not visible");
            weatherSearchTextField.sendKeys(Keys.ENTER);
        }
    }

    public boolean confirmSearch(String searchItem){
         String resultHeader = searchItemResultsHeader.getText();
        boolean _bool = searchItem.toLowerCase().contains(resultHeader.toLowerCase());
        return _bool;
    }
}
