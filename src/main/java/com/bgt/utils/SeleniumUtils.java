package com.bgt.utils;

import com.google.common.base.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.TestException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class SeleniumUtils {
    private static final Logger log = LogManager.getLogger(com.bgt.utils.SeleniumUtils.class);

    private static EventFiringWebDriver webDriver;
    private static final Integer WAIT_TIMEOUT=30;
    private static final Integer POLLING_TIME=100;
    DateFormat dateFormat=new SimpleDateFormat ("yyyyMMdd");


    public SeleniumUtils() {
        this.webDriver=DriverUtils.getWebDriver ();
    }

    public static void closeDriver() {
        webDriver.quit ();
    }

    //Window Navigation
    public void switchToWindow(final String windowHandle) {
        webDriver.switchTo ().window (windowHandle);
    }

    public void popUpOK() {
        webDriver.switchTo ().alert ().accept ();
    }

    public void popUpCancel() {
        webDriver.switchTo ().alert ().dismiss ();
    }

    public void scrollToElementOfPage(final WebElement element) {
        try {
            ((JavascriptExecutor) webDriver).executeScript (
                    "arguments[0].scrollIntoView();", element);
        } catch (NoSuchElementException e) {
            throw new TestException("Failed to find the element: " + element, e);
        }
    }

    public String getLinkUrl(final WebElement element) {
        try {
            return element.getAttribute ("href");
        } catch (NoSuchElementException e) {
            throw new TestException ("explicitWait error", e);
        }
    }

    //Wait functions
    public void implicitWait(final long timeout) {
        try {
            webDriver.manage ().timeouts ().implicitlyWait (0, TimeUnit.SECONDS);
            webDriver.manage ().timeouts ().implicitlyWait (timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new TestException ("implicitWait error", e);
        }
    }

    public static void explicitWait(long millis) {
        try {
            log.debug("Entered the explicit wait : "+ LocalDateTime.now().toString());
            Thread.sleep (millis);
            log.debug("Exit the explicit wait : "+ LocalDateTime.now().toString());
        } catch (InterruptedException e) {
            throw new TestException ("explicitWait error", e);
        }
    }

    public void waitForClickable(WebElement element, final long timeout) throws TestException {
        try {
            FluentWait<WebDriver> customWait=new FluentWait<WebDriver>(webDriver).withTimeout (Duration.ofSeconds(timeout))
                    .pollingEvery (Duration.ofMillis(POLLING_TIME)).ignoring (ElementNotVisibleException.class, StaleElementReferenceException.class);
            customWait.until (ExpectedConditions.elementToBeClickable (element));

        } catch (NoSuchElementException e) {
            throw new TestException ("Failed to find the element: " + element, e);
        }
    }

    public void waitForAttributeToBe(WebElement element, final long timeout, String attribute, String attributeValue) throws TestException {
        try {
            FluentWait<WebDriver> customWait=new FluentWait<WebDriver>(webDriver).withTimeout (Duration.ofSeconds(timeout))
                    .pollingEvery (Duration.ofMillis(POLLING_TIME)).ignoring (ElementNotVisibleException.class, StaleElementReferenceException.class);
            customWait.until (ExpectedConditions.attributeToBe(element,attribute,attributeValue));

        } catch (NoSuchElementException e) {
            throw new TestException ("Failed to find the element: " + element, e);
        }
    }

    public void waitForAttributeToContain(WebElement element, final long timeout, String attribute, String attributeValue) throws TestException {
        try {
            FluentWait<WebDriver> customWait=new FluentWait<WebDriver>(webDriver).withTimeout (Duration.ofSeconds(timeout))
                    .pollingEvery (Duration.ofMillis(POLLING_TIME)).ignoring (ElementNotVisibleException.class, StaleElementReferenceException.class);
            customWait.until (ExpectedConditions.attributeContains(element,attribute,attributeValue));

        } catch (NoSuchElementException e) {
            throw new TestException ("Failed to find the element: " + element, e);
        }
    }

    public void waitForAttributeToNotContain(WebElement element, final long timeout, String attribute, String attributeValue) throws TestException {
        try {
            FluentWait<WebDriver> customWait=new FluentWait<WebDriver>(webDriver).withTimeout (Duration.ofSeconds(timeout))
                    .pollingEvery (Duration.ofMillis(POLLING_TIME)).ignoring (ElementNotVisibleException.class, StaleElementReferenceException.class);
            customWait.until (ExpectedConditions.not(ExpectedConditions.attributeContains(element,attribute,attributeValue)));

        } catch (NoSuchElementException e) {
            throw new TestException ("Failed to find the element: " + element, e);
        }
    }

    public void waitForVisibilityOfElement(WebElement element, final long timeout) {
        try {
            FluentWait<WebDriver> customWait=new FluentWait<WebDriver>(webDriver).withTimeout (Duration.ofSeconds(timeout))
                    .pollingEvery (Duration.ofMillis(POLLING_TIME)).ignoring (ElementNotVisibleException.class, StaleElementReferenceException.class);
            customWait.until (ExpectedConditions.visibilityOf (element));//until(ExpectedConditions.visibilityOf(element));
        } catch (NoSuchElementException e) {
            throw new TestException ("Failed to find the element: " + element, e);
        }
    }

    // need to fix this method
    public void waitForPageToLoad() {
        new WebDriverWait(webDriver,  WAIT_TIMEOUT).until (new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript ("return document.readyState").equals ("complete");

            }
        });
    }

    public void waitForInvisibilityOfElement(WebElement element, Long timeoutSecs) {
        List<WebElement> elements=new ArrayList<WebElement> ();
        if(element != null){
            elements.add(element);
        }
        if (elements.size()>0) {
            try {
                FluentWait<WebDriver> customWait = new FluentWait<WebDriver>(webDriver).withTimeout(Duration.ofSeconds(timeoutSecs)).pollingEvery(Duration.ofMillis(POLLING_TIME)).ignoring(ElementNotVisibleException.class, StaleElementReferenceException.class);
                customWait.until(ExpectedConditions.invisibilityOfAllElements(elements));
            } catch (NoSuchElementException e) {
                throw new TestException("Failed to find the element by: " + element, e);
            }
        }
    }


    //Elements status
    public void click(final WebElement element) {
        try {
            element.click ();
        } catch (NoSuchElementException e) {
            throw new TestException ("WebElement \"" + element + "\" not found on the page", e);
        }
    }

    public WebElement elementIfVisible(final WebElement element) {
        try {
            return element.isDisplayed () ? element : null;
        } catch (NoSuchElementException e) {
            throw new TestException ("WebElement \"" + element + "\" not found on the page", e);
        }
    }

    // changed- removed try catch and added Throws
    public WebElement elementIfClickable(final WebElement element) throws NoSuchElementException {
        return element.isDisplayed () ? element : null;
    }

    //Enabled
    public boolean isElementEnabled(final WebElement element) {
        try {
            return element.isEnabled ();
        } catch (NoSuchElementException e) {
            throw new TestException ("WebElement \"" + element + "\" not found on the page", e);
        }
    }

    //Disabled
    public boolean isElementDisabled(final WebElement element) {
        try {
            final boolean enabled=element.isEnabled ();
            return !enabled;
        } catch (NoSuchElementException e) {
            throw new TestException ("WebElement \"" + element + "\" not found on the page", e);
        }
    }

    //Check boxes
    public void tickCheckbox(final WebElement element) {
        try {
            boolean checkstatus;
            checkstatus=element.isSelected ();
            if (checkstatus) {
                log.info("Checkbox is already checked");
            } else element.click ();
        } catch (NoSuchElementException e) {
            throw new TestException ("WebElement \"" + element + "\" not found on the page", e);
        }
    }

    public void unTickChkbox(final WebElement element) {
        try {
            boolean checkstatus;
            checkstatus=element.isSelected ();
            if (!checkstatus) {
                return;
            } else {
                element.click ();
                System.out.println ("Checkbox is already checked & uncheck now");
            }
        } catch (NoSuchElementException e) {
            throw new TestException ("WebElement \"" + element + "\" not found on the page", e);
        }
    }

    public boolean verifyChkBoxDisabled(final WebElement element) {
        try {
            return isElementDisabled (element);
        } catch (NoSuchElementException e) {
            throw new TestException ("WebElement \"" + element + "\" not found on the page", e);
        }
    }


    public boolean getCheckBoxSelectedState(final WebElement element) {
        boolean checkBoxSelectedState;
        try {
            checkBoxSelectedState=element.isSelected ();
        } catch (NoSuchElementException e) {
            throw new TestException ("WebElement \"" + element + "\" not found on the page", e);
        }
        return checkBoxSelectedState;
    }

    public void clickElementWithRetry(final WebElement element) {
        FluentWait<WebElement> customWait=new FluentWait<WebElement>(element)
                .withTimeout (Duration.ofSeconds(WAIT_TIMEOUT))
                .pollingEvery (Duration.ofMillis(POLLING_TIME)).ignoring (ElementNotVisibleException.class, StaleElementReferenceException.class);

        customWait.until (new Function<WebElement, Boolean>() {
            public Boolean apply(WebElement element) {
                try {
                    element.click ();
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        });
    }

    public void retryingFindClick(final WebElement element) {
        int attempts=0;
        while (attempts < 3) {
            try {
                element.click ();
                break;
            } catch (final StaleElementReferenceException e) {
            }
            attempts++;
        }

    }

    //Attach File
    public void attachAFile(final WebElement element, final String filePath) {
        try {
            element.sendKeys (filePath);
        } catch (NoSuchElementException e) {
            throw new TestException ("WebElement \"" + element + "\" not found on the page", e);
        }
    }

    //Get text
    public String getText(final WebElement element) {
        try {
            return element.getText ();
        } catch (NoSuchElementException e) {
            throw new TestException ("WebElement \"" + element + "\" not found on the page", e);
        }
    }

    //Enter text
    public void tabOut(final WebElement element) {
        try {
            element.sendKeys (Keys.TAB);
        } catch (NoSuchElementException e) {
            throw new TestException ("WebElement \"" + element + "\" not found on the page", e);
        }
    }

    public void enterIntoInputField(WebElement element, String text) {
        try {
            element.clear ();
            element.sendKeys (text);
        } catch (NoSuchElementException e) {
            throw new TestException ("WebElement \"" + element + "\" not found on the page", e);
        }
    }

    public void typeTextInFieldUsingJavaScriptExecutor(WebElement element, String text) {
        try {
            element.click ();
            element.clear ();
            ((JavascriptExecutor) webDriver).executeScript ("document.getElementsByName('" + element.getAttribute ("name") + "').item(0).value = '" + text + "';");
        } catch (NoSuchElementException e) {
            throw new TestException ("WebElement \"" + element + "\" not found on the page", e);
        }
    }

    //Present only
    public static boolean isElementPresent(final WebElement element) {
        return isElementPresent(element, WAIT_TIMEOUT);
    }

    public static boolean isElementImmediatelyPresent(final WebElement element) {
        return isElementPresent(element, 1);
    }

    private static boolean isElementPresent(final WebElement element, int timeout) {
        try {
            webDriver.manage ().timeouts ().implicitlyWait (timeout, TimeUnit.SECONDS);
            element.isDisplayed ();
            return true;
        } catch (Exception e) {
            System.out.println ("WebElement \"" + element + "\" Timeout Exception");
            return false;
        }
    }

    //Present and enabled
    public boolean isElementPresentAndEnabled(WebElement element) {
        try {
            elementIfVisible (element);
            return element.isEnabled ();
        } catch (NoSuchElementException e) {
            System.out.println ("WebElement \"" + element + "\" not found on the page");
            return false;
        } catch (TimeoutException e) {
            System.out.println ("WebElement \"" + element + "\" Timeout Exception");
            return false;
        }
    }

    //Present+displayed
    public boolean isElementPresentAndDisplayed(WebElement element) throws TimeoutException {
        try {
            elementIfVisible (element);
            return element.isDisplayed ();
        } catch (NoSuchElementException e) {
            System.out.println ("WebElement \"" + element + "\" Timeout Exception");
            return false;
        }
    }

    public boolean isElementPresentAndDisplayed(WebElement element, boolean wait) {
        try {
            if (wait) {
                elementIfVisible (element);
            }
            return element.isDisplayed ();
        } catch (NoSuchElementException e) {
            System.out.println ("WebElement \"" + element + "\" not found on the page");
            return false;
        }
    }

    //Present and selected
    public boolean isElementPresentAndSelected(final WebElement element) {
        try {
            elementIfVisible (element);
            return element.isSelected ();
        } catch (NoSuchElementException e) {
            System.out.println ("WebElement \"" + element + "\" Timeout Exception");
            return false;
        }
    }

    //Present+clickable
    public boolean isElementPresentAndClickable(WebElement element) {
        Boolean displayedCondition=false;
        try {
            elementIfVisible (element);
            FluentWait<WebElement> wait=new FluentWait<WebElement>(element).withTimeout (Duration.ofSeconds(WAIT_TIMEOUT))
                    .pollingEvery (Duration.ofMillis(POLLING_TIME)).ignoring (NoSuchElementException.class)
                    .ignoring (ElementNotVisibleException.class).ignoring (StaleElementReferenceException.class)
                    .ignoring (WebDriverException.class).ignoring (TimeoutException.class);

            displayedCondition=wait.until (new Function<WebElement, Boolean>() {
                public Boolean apply(WebElement clickableElement) {
                    try {
                        clickableElement.click ();
                        return true;
                    } catch (Exception e) {
                        System.out.println ("WebElement \"" + element + "\" Timeout Exception");
                        return false;
                    }
                }
            });

        } catch (NoSuchElementException e) {
            displayedCondition=false;
        } catch (TimeoutException e) {
            System.out.println ("WebElement \"" + element + "\" Timeout Exception");
            displayedCondition=false;
        }
        return displayedCondition;
    }

    public boolean isElementPresentAndValueOfAttributeIsEqualTo(final WebElement element, String attributeName,
                                                                String attributeValue) {
        try {
            String result=element.getAttribute (attributeName);
            return result.equals (attributeValue);
        } catch (NoSuchElementException e) {
            System.out.println ("WebElement \"" + element + "\" not found on the page");
            return false;
        }
    }

    //	Attribute value
    public String getAttributeValue(final WebElement element, String attributeName) throws TestException{
        String value;
        try {
            elementIfVisible (element);
            value=element.getAttribute (attributeName);
        } catch (Exception e) {
            throw new TestException ("WebElement \"" + element + "\" not found on the page", e);
        }
        return value;
    }

    //Dropdown functions
    public void selectFromDropDown(final WebElement element, final String optionToSelect) throws TestException{
        boolean flag=false;
        final List<WebElement> optionList=element.findElements (By.tagName ("option"));
        try {
            for (final WebElement option : optionList)
                if (option.getText ().toLowerCase ().contains (optionToSelect.toLowerCase ())) {
                    option.click ();
                    flag=true;
                    break;
                }
            if (!flag) throw new TestException ("There is no such option in the drop down!");
        }
        catch (Exception e) {
            throw new TestException ("Dropdown item :\"" + optionToSelect + "\" is not found in the Dropdown ", e);
        }
    }

    public static void selectDropDownByVisibleText(final WebElement element, String dropDownVisibleText) throws TestException{
        try {
            Select select=new Select(element);
            select.selectByVisibleText (dropDownVisibleText);
        } catch (Exception e) {
            throw new TestException ("WebElement \"" + element + "\" not found on the page", e);
        }
    }

    public static void selectDropDownByIndex(final WebElement element, int index) throws TestException{
        try {
            Select select=new Select(element);
            select.selectByIndex (index);
        } catch (Exception e) {
            throw new TestException ("WebElement \"" + element + "\" not found on the page", e);
        }
    }

    public static void selectDropDownByValue(final WebElement element, int value) throws TestException{
        try {
            Select select=new Select(element);
            select.selectByIndex (value);
        } catch (Exception e) {
            throw new TestException ("WebElement \"" + element + "\" not found on the page", e);
        }
    }

    //Alert
    public static boolean isAlertPresent(final WebDriver driver) throws TestException {
        try {
            driver.switchTo ().alert ();
            return true;
        } catch (final Exception e) {
            System.out.println ("No Alert present");
            return false;
        }
    }

    /*** Method : Waits for an alert to be present
     * iterates for 25 times waiting for 1 second each time
     * @param driver - WebDriver
     * @throws InterruptedException - NoAlertPresentException
     */
    public void waitForAlert(WebDriver driver) throws InterruptedException {
        int i=0;
        while(i++<25)
        {
            try
            {
                Alert alert = driver.switchTo().alert();
                break;
            }
            catch(NoAlertPresentException e)
            {
                Thread.sleep(1000);
                continue;
            }
        }
    }

    public void escAlert() throws TestException {
        try {
            if (isAlertPresent (webDriver)) {
                final Actions action=new Actions(webDriver);
                action.sendKeys (Keys.ESCAPE).build ().perform ();
            }
        } catch (Exception e) {
            throw new TestException ("No Alert present to escape", e);
        }
    }

    public static void dismissAlert(final WebDriver driver) throws TestException {
        try {
            if (isAlertPresent (driver)) {
                final Alert Alert=driver.switchTo ().alert ();
                Alert.dismiss ();
            }
        } catch (Exception e) {
            throw new TestException ("No Alert present to dismiss", e);
        }
    }

    public static void acceptAlert(final WebDriver driver) throws TestException {
        try {
            if (isAlertPresent (driver)) {
                final Alert Alert=driver.switchTo ().alert ();
                Alert.accept ();
            }
        } catch (Exception e) {
            throw new TestException("No Alert present to accept", e);
        }
    }

    //Radio button
    public void selectRadioButton(final WebElement element) throws TestException {
        try {
            element.click ();
        } catch (Exception e) {
            throw new TestException ("WebElement \"" + element + "\" not found on the page", e);
        }
    }

    public void waitForElementToBePresent(By by, final long timeout) {
        try {
            FluentWait<WebDriver> customWait=new FluentWait<WebDriver>(webDriver).withTimeout (Duration.ofSeconds(timeout))
                    .pollingEvery (Duration.ofMillis(POLLING_TIME)).ignoring (ElementNotVisibleException.class, StaleElementReferenceException.class);
            customWait.until (ExpectedConditions.presenceOfElementLocated (by));//until(ExpectedConditions.visibilityOf(element));
        } catch (NoSuchElementException e) {
            throw new TestException ("Failed to find the locator: " + by.toString(), e);
        }
    }


    public void waitForSpinner(WebElement spinnerElement, long timeout) {

        String spinnerState = null;
        try {
            spinnerState = spinnerElement.getAttribute("style");
        } catch (StaleElementReferenceException e) {
            waitForPageToLoad();
            spinnerState = spinnerElement.getAttribute("style");
            log.debug("caught stale element exception and retrying.");

        }
        log.debug("Is spinner running: " + spinnerState.contains("visible"));
        if (spinnerState.contains("visible")) {
            log.debug("waiting for page spinner to complete");
            waitForAttributeToContain(spinnerElement, timeout, "style", "hidden");
            log.debug("spinner now complete ");
        }
    }

    public boolean isElementPresent(By by) {
        int implicitWaitTime = DriverUtils.getImplicitWaitTime();
        DriverUtils.setImplicitWaitTime(0);
        boolean isElementPresent = webDriver.findElements(by).size() > 0;
        DriverUtils.setImplicitWaitTime(implicitWaitTime);
        return  isElementPresent;
    }

    public String getCurrentUrl(){
        String currentUrl = webDriver.getCurrentUrl();
        return currentUrl;
    }

    public void waitForURLtoContain(String urlFragment) {
        waitForURLtoContain(urlFragment, WAIT_TIMEOUT) ;
    }

    public void waitForURLtoContain(String urlFragment, Integer timeoutSeconds) {
        try {
            FluentWait<WebDriver> customWait=new FluentWait<WebDriver>(webDriver).withTimeout (Duration.ofSeconds(timeoutSeconds))
                    .pollingEvery (Duration.ofMillis(POLLING_TIME)).ignoring (ElementNotVisibleException.class, StaleElementReferenceException.class);
            customWait.until (ExpectedConditions.urlContains(urlFragment));

        } catch (NoSuchElementException e) {
            throw new TestException ("Failed to find the url: " + urlFragment, e);
        }
    }

    public void waitForURLtoNotContain(String urlFragment,Long timeoutSeconds) {
        try {
            FluentWait<WebDriver> customWait=new FluentWait<WebDriver>(webDriver).withTimeout (Duration.ofSeconds(timeoutSeconds))
                    .pollingEvery (Duration.ofMillis(POLLING_TIME)).ignoring (ElementNotVisibleException.class, StaleElementReferenceException.class);
            customWait.until (ExpectedConditions.not(ExpectedConditions.urlContains(urlFragment)));

        } catch (NoSuchElementException e) {
            throw new TestException ("Failed to find the url: " + urlFragment, e);
        }
    }

    public void waitForIFrameAndSwitchToIt(WebElement element, final long timeoutInSecs) throws TestException {
        try {
            FluentWait<WebDriver> customWait=new FluentWait<WebDriver>(webDriver).withTimeout (Duration.ofSeconds(timeoutInSecs))
                    .pollingEvery (Duration.ofMillis(POLLING_TIME))
                    .ignoring (ElementNotVisibleException.class, StaleElementReferenceException.class)
                    .ignoring (NoSuchElementException.class);
            customWait.until (ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));

        } catch (TimeoutException e) {
            throw new TestException ("Failed to find the iframe element: " + element, e);
        }
    }

}
