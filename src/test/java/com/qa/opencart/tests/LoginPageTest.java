package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Epic - 100  - Design Login Page for OpenCart application")
@Story("US 101 - Design Login Page")
public class LoginPageTest extends BaseTest {

    @Test
    @Description("Login page title test....")
    @Severity(SeverityLevel.NORMAL)
    public void loginPageTitleTest() {
        String actTitle = loginpage.getLoginPageTitle();
        System.out.println("page title : " + actTitle);
        Assert.assertEquals(actTitle, Constants.LOGIN_PAGE_TITLE);
    }

    @Test
    @Description("Login page URL test....")
    @Severity(SeverityLevel.NORMAL)
    public void loginPageUrlTest() {
        String url = loginpage.getLoginPageUrl();
        System.out.println("login page url : " + url);
        Assert.assertTrue(url.contains(Constants.LOGIN_PAGE_FRACTION_URL));
    }

    @Test
    @Description("Check for forgot pwd link....")
    @Severity(SeverityLevel.CRITICAL)
    public void forgotPwdLinkTest() {
        Assert.assertTrue(loginpage.isForgotPwdLinkExist());
    }

    @Test
    @Description("Login page with correct un and correct pwd test....")
    @Severity(SeverityLevel.BLOCKER)
    public void loginTest() {
        accPage = loginpage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
        Assert.assertTrue(accPage.isAccountPageHeaderExist());
    }

    @Test
    @Description("Register link existence test....")
    @Severity(SeverityLevel.CRITICAL)
    public void isRegisterLinkExist(){
        Assert.assertTrue(loginpage.registerLinkExist());
    }
}

