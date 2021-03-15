package com.bgt.stepdefs;

import com.bgt.pages.BBCNewHomePage;
import com.bgt.pages.LocalWeatherPage;
import com.bgt.utils.ConfigFileReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import static com.bgt.utils.DriverUtils.getWebDriver;

public class BBCLocalWeather  {
    private static final Logger log = LogManager.getLogger(BBCLocalWeather.class);
    ConfigFileReader configFileReader = new ConfigFileReader();
    BBCNewHomePage bbcNewHomePage = new BBCNewHomePage();
    LocalWeatherPage localWeatherPage = new LocalWeatherPage();

    @Given("^User navigate to BBC home page$")
    public void userNavigateToBBCHomePage() {
        getWebDriver().get(configFileReader.getApplicationUrl());
    }

    @When("^User select local weather$")
    public void userSelectLocalWeather() {
        bbcNewHomePage.selectWeatherTab();
    }

    @And("^User supply the postcode or city name as \"([^\"]*)\"$")
    public void userSupplyThePostcodeOrCityNameAS(String searchItem) {
        localWeatherPage.searchWeather(searchItem);
    }

    @Then("^User can view local weather based on \"([^\"]*)\"$")
    public void userCanViewLocalWeatherBasedOn(String searchItem) {
        Assert.assertEquals(true, localWeatherPage.confirmSearch(searchItem));
    }
}
