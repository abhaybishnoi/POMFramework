package com.qa.opencart.pages;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.Errors;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;
    private ElementUtil eleUtil;

    //1.private By Locators
    private By emailId = By.id("input-email");
    private By password = By.id("input-password");
    private By loginBtn = By.xpath("//input[@value='Login']");
    private By forgotPwd = By.linkText("Forgotten Password");
    private By registerLink = By.linkText("Register");
    private By loginErrorMsg = By.cssSelector("div.alert.alert-danger.alert-dismissible");
    
    //1.Initialize the driver

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        eleUtil = new ElementUtil(driver);
    }

    //2.public page actions
    @Step("Getting login page title...")
    public String getLoginPageTitle() {
        return eleUtil.waitForTitleIs(Constants.DEFAULT_TIME_OUT, Constants.LOGIN_PAGE_TITLE);
    }

    @Step("Getting login page url...")
    public String getLoginPageUrl() {
        return eleUtil.waitForUrl(Constants.DEFAULT_TIME_OUT, Constants.LOGIN_PAGE_FRACTION_URL);
    }

    @Step("Checking if forgot pwd link exist or not...")
    public boolean isForgotPwdLinkExist() {
        return eleUtil.doIsDisplayed(forgotPwd);
    }

    @Step("login to application with userName {0} and password {1}")
    public AccountPage doLogin(String userName, String pwd) {
        eleUtil.waitForElementToBeVisible(emailId, Constants.DEFAULT_TIME_OUT).sendKeys(userName);
        eleUtil.waitForElementToBeVisible(password, Constants.DEFAULT_TIME_OUT).sendKeys(pwd);
        eleUtil.doClick(loginBtn);
        return new AccountPage(driver);
    }

    @Step("login to application with Invalid userName {0} and password {1}")
    public boolean doInvalidLogin(String userName, String pwd) {
        WebElement emailiD_ele = eleUtil.waitForElementToBeVisible(emailId, Constants.DEFAULT_TIME_OUT);
        emailiD_ele.clear();
        emailiD_ele.sendKeys(userName);
        eleUtil.waitForElementToBeVisible(password, Constants.DEFAULT_TIME_OUT).sendKeys(pwd);
        eleUtil.doClick(loginBtn);
        String actualErrorMsg= eleUtil.doElementGetText(loginErrorMsg);
        System.out.println(actualErrorMsg);
        if (actualErrorMsg.contains(Errors.LOGIN_PAGE_ERROR_MESSG)){
            return true;
        }
        return false;
    }

    @Step("Checking if register link exist or not...")
    public boolean registerLinkExist() {
        return eleUtil.waitForElementToBeVisible(registerLink, Constants.DEFAULT_TIME_OUT).isDisplayed();
    }

    public RegistrationPage navigateToRegistrationPage() {
        if (registerLinkExist()) {
            eleUtil.doClick(registerLink);
            return new RegistrationPage(driver);
        }
        return null;
    }
}
