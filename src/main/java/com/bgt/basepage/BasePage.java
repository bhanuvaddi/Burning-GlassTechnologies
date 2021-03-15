package com.bgt.basepage;

import com.bgt.utils.ConfigFileReader;
import com.bgt.utils.DriverUtils;
import com.bgt.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.TestException;

import java.util.List;

public class BasePage {

    protected EventFiringWebDriver webDriver;
    private SeleniumUtils seleniumUtils;
    protected ConfigFileReader configFileReader;

    public BasePage() {
        configFileReader = new ConfigFileReader();
        this.webDriver= DriverUtils.getWebDriver();
        seleniumUtils=new SeleniumUtils ();
    }

    //Navigation
    public void switchToWindow(String windowHandle) {
        seleniumUtils.switchToWindow (windowHandle);
    }

    public void popUpOK() {
        seleniumUtils.popUpOK ();
    }

    public void popUpCancel() {
        seleniumUtils.popUpCancel ();
    }

    public void scrollToElementOfPage(WebElement element) {
        seleniumUtils.scrollToElementOfPage (element);
    }

    public String getLinkUrl(WebElement element) {
        return seleniumUtils.getLinkUrl (element);
    }

    //Wait functions
    public void implicitWait(long timeout) {
        seleniumUtils.implicitWait (timeout);
    }

    public void explicitWait(final long timeout) {
        seleniumUtils.explicitWait (timeout);
    }


    public void waitForClickable(WebElement element, long timeout) {
        seleniumUtils.waitForClickable (element, timeout);
    }

    public void waitForVisibilityOfElement(WebElement element, long timeout) {
        seleniumUtils.waitForVisibilityOfElement (element, timeout);
    }

    public void waitForInvisibilityOfElementById(String elementId) {
        List<WebElement> elems = webDriver.findElements(By.id(elementId));
        if (elems.size()>0) {
            WebElement wb = webDriver.findElement(By.id(elementId));
            waitForInvisibilityOfElement(wb);
        }
    }

    // need to fix this method
    public void waitForPageToLoad() {
        seleniumUtils.waitForPageToLoad ();
    }

    public void explicitWait(Long waittime){
        seleniumUtils.explicitWait(waittime);
    }
    //Elements status
    public void click(WebElement element) {
        seleniumUtils.click (element);
    }

    public WebElement elementIfVisible(WebElement element) {
        return seleniumUtils.elementIfVisible (element);
    }

    public WebElement elementIfClickable(WebElement element) {
        return seleniumUtils.elementIfClickable (element);
    }

    //Enabled
    public boolean isElementEnabled(WebElement element) {
        return seleniumUtils.isElementEnabled (element);
    }

    //Disabled
    public boolean isElementDisabled(WebElement element) {
        return seleniumUtils.isElementDisabled (element);
    }

    //Check boxes
    public void tickCheckbox(WebElement element) {
        seleniumUtils.tickCheckbox (element);
    }

    public void unTickChkbox(WebElement element) {
        seleniumUtils.unTickChkbox (element);
    }

    public boolean verifyChkBoxDisabled(WebElement element) {

        return seleniumUtils.verifyChkBoxDisabled (element);
    }

    public boolean getCheckBoxSelectedState(WebElement element) {
        return seleniumUtils.getCheckBoxSelectedState (element);
    }

    public void clickElementWithRetry(WebElement element) {
        seleniumUtils.clickElementWithRetry (element);
    }

    public void retryingFindClick(WebElement element) {
        seleniumUtils.retryingFindClick (element);
    }

    //Attach File
    public void attachAFile(WebElement element, String filePath) {
        seleniumUtils.attachAFile (element, filePath);
    }

    //Get text
    public String getText(WebElement element) {
        return seleniumUtils.getText (element);
    }

    //Enter text
    public void tabOut(WebElement element) {
        seleniumUtils.tabOut (element);
    }

    public void enterIntoInputField(WebElement element, String text) {
        seleniumUtils.enterIntoInputField (element, text);
    }

    public void typeTextInFieldUsingJavaScriptExecutor(WebElement element, String text) {
        seleniumUtils.typeTextInFieldUsingJavaScriptExecutor (element, text);
    }

    //Present and enabled
    public boolean isElementPresentAndEnabled(WebElement element) {

        return seleniumUtils.isElementPresentAndEnabled (element);
    }

    //Present+displayed
    public boolean isElementPresentAndDisplayed(WebElement element) {
        return seleniumUtils.isElementPresentAndDisplayed (element);
    }

    public boolean isElementPresentAndDisplayed(WebElement element, boolean wait) {
        return seleniumUtils.isElementPresentAndDisplayed (element, wait);
    }

    //Present only
    public boolean isElementPresent(WebElement element) {
        return seleniumUtils.isElementPresent (element);
    }

    //Present and selected
    public boolean isElementPresentAndSelected(WebElement element) {
        return seleniumUtils.isElementPresentAndSelected (element);
    }

    //Present+clickable
    public boolean isElementPresentAndClickable(WebElement element) {
        return seleniumUtils.isElementPresentAndClickable (element);
    }

    public boolean isElementPresentAndValueOfAttributeIsEqualTo(WebElement element, String attributeName,
                                                                String attributeValue) {
        return seleniumUtils.isElementPresentAndValueOfAttributeIsEqualTo (element, attributeName, attributeValue);
    }

    //	Attribute value
    public String getAttributeValue(WebElement element, String attributeName) {
        return seleniumUtils.getAttributeValue (element, attributeName);
    }

    //Dropdown functions
    public void selectFromDropDown(WebElement element, String optionToSelect) {
        seleniumUtils.selectFromDropDown (element, optionToSelect);
    }

    public void selectDropDownByVisibleText(WebElement element, String dropDownVisibleText) {
        seleniumUtils.selectDropDownByVisibleText (element, dropDownVisibleText);
    }

    public void selectDropDownByIndex(WebElement element, int index) {
        seleniumUtils.selectDropDownByIndex (element, index);
    }

    public void selectDropDownByValue(WebElement element, int value) {
        seleniumUtils.selectDropDownByValue (element, value);
    }

    //Alert
    public boolean isAlertPresent(WebDriver driver) throws TestException {
        return seleniumUtils.isAlertPresent (driver);
    }

    /***
     * Method : Waits for an alert to be present
     */
    public void waitForAlert(WebDriver driver) throws InterruptedException {
        seleniumUtils.waitForAlert (driver);
    }

    public void escAlert() throws TestException {
        seleniumUtils.escAlert ();
    }

    public void dismissAlert(WebDriver driver) throws TestException {
        seleniumUtils.dismissAlert (driver);
    }

    public void acceptAlert(WebDriver driver) throws TestException {
        seleniumUtils.acceptAlert (driver);
    }

    //Radio button
    public void selectRadioButton(WebElement element) {
        seleniumUtils.selectRadioButton (element);
    }

    public void waitForAttributeToBe(WebElement element, final long timeout, String attribute, String attributeValue) throws TestException {
        seleniumUtils.waitForAttributeToBe(element,timeout,attribute,attributeValue);
    }

    public void waitForAttributeToContain(WebElement element, final long timeout, String attribute, String attributeValue) throws TestException {
        seleniumUtils.waitForAttributeToContain(element,timeout,attribute,attributeValue);
    }

    public void waitForAttributeToNotContain(WebElement element, final long timeout, String attribute, String attributeValue) throws TestException {
        seleniumUtils.waitForAttributeToNotContain(element,timeout,attribute,attributeValue);
    }

    public void waitForElementToBePresent(By by, final long timeout) throws TestException {
        seleniumUtils.waitForElementToBePresent(by,timeout);
    }

    public void waitForSpinner(WebElement spinnerElement, long timeout) {
        seleniumUtils.waitForSpinner(spinnerElement,timeout);
    }

    public boolean isElementPresent(By by) {
        return seleniumUtils.isElementPresent(by);
    }

    public void waitForURLtoContain(String url) {
        seleniumUtils.waitForURLtoContain(url);
    }

    public void waitForURLtoContain(String url,Integer durationSeconds) {
        seleniumUtils.waitForURLtoContain(url, durationSeconds);
    }

    public String getCurrentUrl(){
        return seleniumUtils.getCurrentUrl();
    }

    public void waitForURLtoNotContain(String url, Long durationSeconds) {
        seleniumUtils.waitForURLtoNotContain(url,durationSeconds);
    }

    public void waitForInvisibilityOfElement(WebElement element) {
        seleniumUtils.waitForInvisibilityOfElement (element,30L);
    }

    public void waitForInvisibilityOfElement(WebElement element,Long timeoutSeconds) {
        seleniumUtils.waitForInvisibilityOfElement(element,timeoutSeconds);
    }

    public void waitForIFrameAndSwitchToIt(WebElement element, Long timeoutSeconds) {
        seleniumUtils.waitForIFrameAndSwitchToIt(element,timeoutSeconds);
    }

}
