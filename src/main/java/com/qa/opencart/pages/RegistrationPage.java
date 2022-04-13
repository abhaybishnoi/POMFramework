package com.qa.opencart.pages;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage {

    private WebDriver driver;
    private ElementUtil eleUtil;

    private By firstName = By.id("input-firstname");
    private By lastName = By.id("input-lastname");
    private By email = By.id("input-email");
    private By telephone = By.id("input-telephone");
    private By password = By.id("input-password");
    private By confirmPassword = By.id("input-confirm");
    private By subscribeYes = By.xpath("//label[@class='radio-inline']//input[@type='radio' and @value = '1']");
    private By subscribeNo = By.xpath("//label[@class='radio-inline']//input[@type='radio' and @value = '0']");
    private By agreeCheckBox = By.name("agree");
    private By continueBtn = By.xpath("//input[@value='Continue']");
    private By successMessage = By.xpath("//*[@id=\"content\"]/h1");
    private By logOut = By.linkText("Logout");
    private By registerLink = By.linkText("Register");


    public RegistrationPage(WebDriver driver){
        this.driver = driver;
        eleUtil = new ElementUtil(driver);
    }

    public boolean accountRegistration(String firstName , String lastName ,String email,
                                    String telephone , String password ,String subscribe){
        eleUtil.waitForElementsToBeVisible(this.firstName, Constants.DEFAULT_TIME_OUT);
        eleUtil.doSendKeys(this.firstName,firstName);
        eleUtil.doSendKeys(this.lastName,lastName);
        eleUtil.doSendKeys(this.email,email);
        eleUtil.doSendKeys(this.telephone,telephone);
        eleUtil.doSendKeys(this.password,password);
        eleUtil.doSendKeys(this.confirmPassword,password);
        if (subscribe.equalsIgnoreCase("yes")){
            eleUtil.doClick(this.subscribeYes);
        }else {
            eleUtil.doClick(this.subscribeNo);
        }
        eleUtil.doClick(agreeCheckBox);
        eleUtil.doClick(continueBtn);

        if (getAccountRegisterSuccessMessage().contains(Constants.REGISTER_SUCCESS_MESSAGE)){
            goToRegisterPage();
            return true;
        }else{
            return false;
        }
    }

    private String getAccountRegisterSuccessMessage(){
        return eleUtil.waitForElementToBeVisible(successMessage,Constants.DEFAULT_TIME_OUT).getText();
    }

    private void goToRegisterPage() {
        eleUtil.doClick(logOut);
        eleUtil.doClick(registerLink);
    }
}
