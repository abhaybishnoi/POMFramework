package com.qa.opencart.utils;

import com.qa.opencart.factory.DriverFactory;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ElementUtil {

    private WebDriver driver;
    private JavaScriptUtil jsUtil;
    public static final Logger log = Logger.getLogger(ElementUtil.class);

    public ElementUtil(WebDriver driver) {
        this.driver=driver;
        jsUtil= new JavaScriptUtil(driver);
    }

    public By getBy(String locatorType, String locatorValue) {
        By locator = null;
        switch (locatorType.toLowerCase()) {
            case "id":
                locator = By.id(locatorValue);
                break;

            case "name":
                locator = By.name(locatorValue);
                break;

            case "classname":
                locator = By.className(locatorValue);
                break;

            case "xpath":
                locator = By.xpath(locatorValue);
                break;

            case "cssselector":
                locator = By.cssSelector(locatorValue);
                break;

            case "linktext":
                locator = By.linkText(locatorValue);
                break;

            case "partiallinktext":
                locator = By.partialLinkText(locatorValue);
                break;

            case "tagname":
                locator = By.tagName(locatorValue);
                break;

            default:
                break;
        }
        return locator;

    }

    public WebElement getElement(By locator){
        WebElement element = driver.findElement(locator);
        if(Boolean.parseBoolean(DriverFactory.highlight)) {
            jsUtil.flash(element);
        }
        return element;
    }

    public List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }

    public void doSendKeys(By locator,String value){
        log.info("locator is : " + locator + " value "+ value);
        WebElement ele = getElement(locator);
        ele.clear();
        ele.sendKeys(value);
    }

    public void doClick(By locator) {
        getElement(locator).click();
    }

    public String doElementGetText(By locator) {
        return getElement(locator).getText();
    }

    public boolean isElementPresent(By locator){
        if(getElements(locator).size()>0) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean doIsDisplayed(By locator) {
        return getElement(locator).isDisplayed();
    }

    public boolean doIsEnabled(By locator) {
        return getElement(locator).isEnabled();
    }

    public List<String> getLinksText(By locator){
        List<WebElement> elementlist = getElements(locator);
        List<String> elementText = new ArrayList<String>();
        for (WebElement e : elementlist) {
            String text = e.getText();
            if (!text.isEmpty()) {
                System.out.println(text);
                elementText.add(text);
            }
        }
        return elementText;
    }

    public List<String> getElementAttributeList(By locator , String attrName)
    {
        List<WebElement> elemList = getElements(locator);
        List<String> arrlist = new ArrayList<String>();
        for (WebElement e : elemList)
        {
            String attrvalue = e.getAttribute(attrName);
            System.out.println(attrvalue);
            arrlist.add(attrvalue);
        }
        return arrlist;
    }

    /************************Drop Down Utils********************/

    public void doSelectByVisibleText(By locator, String text) {
        Select select = new Select(getElement(locator));
        select.selectByVisibleText(text);
    }

    public void doSelectByIndex(By locator, int index) {
        Select select = new Select(getElement(locator));
        select.selectByIndex(index);
    }

    public void doSelectByValue(By locator, String value) {
        Select select = new Select(getElement(locator));
        select.selectByValue(value);
    }

    public List<String> doGetDropDownOptions(By locator) {
        Select select = new Select(getElement(locator));
        List<WebElement> optionsList = select.getOptions();
        List<String> optionsValList = new ArrayList<String>();
        System.out.println(optionsList.size());

        for (WebElement e : optionsList) {
            String text = e.getText();
            optionsValList.add(text);
        }
        return optionsValList;
    }

    public void doSelectDropDownValue(By locator, String value) {
        Select select = new Select(getElement(locator));
        List<WebElement> optionsList = select.getOptions();
        for(WebElement e : optionsList) {
            String text = e.getText();
            if(text.equals(value)) {
                e.click();
                break;
            }
        }
    }

    // *******************Actions Utils ************************//
    public void selectSubMenu(By parentMenu, By childMenu) throws InterruptedException {
        Actions act = new Actions(driver);
        act.moveToElement(getElement(parentMenu)).perform();
        Thread.sleep(2000);
        getElement(childMenu).click();
    }

    public void selectSubMenu(By parentMenu, By childMenu, By subChildMenu) throws InterruptedException {
        Actions act = new Actions(driver);
        act.moveToElement(getElement(parentMenu)).perform();
        Thread.sleep(2000);
        act.moveToElement(getElement(childMenu)).perform();
        Thread.sleep(2000);
        getElement(subChildMenu).click();
    }

    public void selectSubMenu(By parentMenu, By childMenu1, By subChildMenu2, By subChildMenu3)
            throws InterruptedException {
        Actions act = new Actions(driver);
        act.moveToElement(getElement(parentMenu)).perform();
        Thread.sleep(2000);
        act.moveToElement(getElement(childMenu1)).perform();
        Thread.sleep(2000);
        act.moveToElement(getElement(subChildMenu2)).perform();
        Thread.sleep(2000);
        getElement(subChildMenu3).click();
    }


    public void doContextClick(By locator) {
        Actions act = new Actions(driver);
        act.contextClick(getElement(locator)).perform();
    }

    public int getRightClickOptionsCount(By rightClick, By rightClickOptions) {
        return getRightClickOptionsList(rightClick, rightClickOptions).size();
    }

    public List<String> getRightClickOptionsList(By rightClick, By rightClickOptions) {
        List<String> rightClickItems = new ArrayList<String>();
        doContextClick(rightClick);
        List<WebElement> itemsList = getElements(rightClickOptions);
        System.out.println(itemsList.size());

        for (WebElement e : itemsList) {
            String text = e.getText();
            //System.out.println(text);
            rightClickItems.add(text);
        }
        return rightClickItems;
    }

    public void selectRightClickMenu(By rightClick, By rightClickOptions, String value) {
        doContextClick(rightClick);
        List<WebElement> itemsList = getElements(rightClickOptions);
        System.out.println(itemsList.size());
        for (WebElement e : itemsList) {
            String text = e.getText();
            //System.out.println(text);
            if (text.equals(value)) {
                e.click();
                break;
            }
        }

    }

    /**
     * Clicks in the middle of the given element. Equivalent to: Actions.moveToElement(onElement).click()
     * @param locator
     */
    public void doActionsClick(By locator) {
        Actions act = new Actions(driver);
        act.click(getElement(locator)).perform();
    }

    /**
     * Equivalent to calling: Actions.click(element).sendKeys(keysToSend).
     * @param locator
     * @param value
     */
    public void doActionsSendKeys(By locator, String value) {
        Actions act = new Actions(driver);
        act.sendKeys(getElement(locator), value).perform();
    }


    //***********************Wait utils**********************

    /**
     *An expectation for checking that an element is present on DOM of a page
     * This does not necessarily mean that element is visible
     * @param locator
     * @param timeout
     * @return
     */

    public WebElement waitForElementPresent(By locator , int timeout){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * An expectation of checking that an element is present on DOM of a page and visible.
     * Visibility means that the element is only displayed but also has height and width
     * that is greater than 0.
     * @param locator
     * @param timeout
     * @return
     */
    public WebElement waitForElementToBeVisible(By locator , int timeout){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     *
     * @param locator
     * @param timeout
     * @param pollingtime
     * @return
     */
    public WebElement waitForElementPresent(By locator , int timeout, int pollingtime){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout),Duration.ofMillis(pollingtime));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     *
     * @param locator
     * @param timeout
     * @param pollingtime
     * @return
     */
    public WebElement waitForElementToBeVisible(By locator , int timeout, int pollingtime){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout),Duration.ofMillis(pollingtime));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public Alert waitForAlert(int timeout){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    public void alertAccept(int timeout){
        waitForAlert(timeout).accept();

    }
    public void dismissAccept(int timeout){
        waitForAlert(timeout).dismiss();
    }
    public void getAlertText(int timeout){
        waitForAlert(timeout).getText();
    }

    public String waitForUrl(int timeOut, String urlFraction) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
            return driver.getCurrentUrl();
        }
        return null;
    }

    public String waitForUrlToBe(int timeOut, String urlVal) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        if (wait.until(ExpectedConditions.urlToBe(urlVal))) {
            return driver.getCurrentUrl();
        }
        return null;
    }

    public String waitForTitleContains(int timeOut, String titleFraction) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
            return driver.getTitle();
        } else {
            System.out.println("title is not correct.....");
            return null;
        }
    }

    public String waitForTitleIs(int timeOut, String titleVal) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        if (wait.until(ExpectedConditions.titleIs(titleVal))) {
            return driver.getTitle();
        } else {
            System.out.println("title is not correct.....");
            return null;
        }
    }

    public WebDriver waitForFrameByLocator(int timeOut, By frameLocator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
    }

    public WebDriver waitForFrameByIndex(int timeOut, int frameIndex) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
    }

    public WebDriver waitForFrameByString(int timeOut, String frameLocator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
    }

    public WebDriver waitForFrameByElement(int timeOut, WebElement frameElement) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
    }

    /**
     * An expectation for checking an element is visible and enabled such that you
     * can click it.
     *
     * @param locator
     * @param timeOut
     */
    public void clickWhenReady(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    /**
     * An expectation for checking an element is visible and enabled such that you
     * can click it.
     *
     * @param locator
     * @param timeOut
     */
    public void clickElementWhenReady(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.elementToBeClickable(getElement(locator))).click();
    }

    public void printAllElementsText(By locator, int timeOut) {
        List<WebElement> eleList = waitForElementsToBeVisible(locator, timeOut);
        for (WebElement e : eleList) {
            System.out.println(e.getText());
        }
    }

    public List<String> getElementsTextListWithWait(By locator, int timeOut) {
        List<WebElement> eleList = waitForElementsToBeVisible(locator, timeOut);
        List<String> eleTextList = new ArrayList<String>();
        for (WebElement e : eleList) {
            String text = e.getText();
            eleTextList.add(text);
        }
        return eleTextList;
    }

    public List<WebElement> waitForElementsToBeVisible(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }


}