package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

public class ProductInfoPageTest extends BaseTest {

    @BeforeClass
    public void productInfoSetup(){
        accPage = loginpage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
    }

    @DataProvider
    public Object[][] getProductData(){
        return new Object[][]{
                {"MacBook","MacBook Pro"},
                {"MacBook","MacBook Air"}
        };
    }

    @Test(dataProvider = "getProductData")
    public void productInfoHeaderTest(String productName , String mainProductName){
        searchResultsPage = accPage.doSearch(productName);
        productInfoPage = searchResultsPage.selectProduct(mainProductName);
        String headerText = productInfoPage.getProductHeaderText().trim();
        Assert.assertEquals(headerText ,mainProductName);
    }

    @Test
    public void productImageTest(){
        searchResultsPage = accPage.doSearch("MacBook");
        productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
        int imageCount = productInfoPage.getProductImagesCount();
        Assert.assertTrue(imageCount == Constants.MACBOOK_IMAGE_COUNT);
    }

    @Test
    public void productInfoTest(){
        searchResultsPage = accPage.doSearch("MacBook");
        productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
        Map<String,String> actProductInfoMap = productInfoPage.getProductInfo();
        actProductInfoMap.forEach((k,v) ->System.out.println(k + ":" + v));

        softAssert.assertEquals(actProductInfoMap.get("productname") , "MacBook Pro");
        softAssert.assertEquals(actProductInfoMap.get("Brand") , "Apple");
        softAssert.assertEquals(actProductInfoMap.get("Reward Points") , "800");
        softAssert.assertEquals(actProductInfoMap.get("price") , "$2,000.00");
        softAssert.assertEquals(actProductInfoMap.get("Product Code") , "Product 18");
        softAssert.assertAll();
    }

}
