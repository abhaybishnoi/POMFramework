package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;

import com.qa.opencart.pages.AccountPage;
import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ExcelUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Random;

public class RegistrationPageTest extends BaseTest {

    @BeforeClass
    public void regPageSetup(){
        registrationPage = loginpage.navigateToRegistrationPage();
    }

    public String getRandomMail(){
        Random random = new Random();
        String email= "Jan2022" + random.nextInt(1000) +"@gmail.com";
        return email;
    }

//    @DataProvider
//    public Object[][] getRegisterData(){
//        return new Object[][]{
//                {"Abhay0" ,"Bishnoi","987132232","abhay@mail.com" ,"yes"},
//                {"Abhay1" ,"Bishnoi","984132232","abhay1@mail.com" ,"no"},
//                {"Abhay2" ,"Bishnoi","934132232","abhay1@mail.com" ,"yes"}
//        };
//    }
        @DataProvider
        public Object[][] getRegisterData(){
            Object regData[][] = ExcelUtil.getTestDataMethod(Constants.REGISTER_SHEET_NAME);
            return regData;
        }


    @Test(dataProvider = "getRegisterData")
    public void userRegistrationTest(String firstName,String lastName,String telephone,String password ,String subscribe){
        Assert.assertTrue(registrationPage.accountRegistration(firstName,lastName,
                getRandomMail(),telephone,password,subscribe));
    }



}
