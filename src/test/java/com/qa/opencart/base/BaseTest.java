package com.qa.opencart.base;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.*;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.JavaScriptUtil;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import java.util.Properties;

public class BaseTest {

    public DriverFactory df;
    public Properties prop;
    public WebDriver driver;
    public LoginPage loginpage;
    public AccountPage accPage;
    public ElementUtil eleUtil;
    public SearchResultsPage searchResultsPage;
    public ProductInfoPage productInfoPage;
    public RegistrationPage registrationPage;
    public SoftAssert softAssert;
    public JavaScriptUtil jsUtil;

    @Parameters({ "browser","browserversion" })
    @BeforeTest
    public void setUp(String browser,String browserversion){
        df = new DriverFactory();
        prop = df.init_prop();

        if(browser!= null){
            prop.setProperty("browser",browser);
            prop.setProperty("browserversion",browserversion);
        }
        driver = df.init_driver(prop);
        loginpage = new LoginPage(driver);
        softAssert = new SoftAssert();
    }

    @AfterTest
    public void tearDown(){
        driver.quit();
    }

}
