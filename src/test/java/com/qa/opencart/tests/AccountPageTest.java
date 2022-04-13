package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;

@Epic("Epic - 101  - Design Account Page for OpenCart application")
@Story("US 102 - Design Account Page")
public class AccountPageTest extends BaseTest {

    @BeforeClass
    @Description("Account page test....")
    @Severity(SeverityLevel.CRITICAL)
    public void accPageSetup(){
        accPage = loginpage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
    }

    @Test
    @Description("Account page title test....")
    @Severity(SeverityLevel.NORMAL)
    public void accountsPageTitleTest(){
        String accAccountPageTitle = accPage.getAccountsPageTitle();
        System.out.println("Acc page title is : " +accAccountPageTitle);
        Assert.assertEquals(accAccountPageTitle, Constants.ACCOUNT_PAGE_TITLE);
    }

    @Test
    @Description("Account page header test....")
    @Severity(SeverityLevel.NORMAL)
    public void accPageheaderTest(){
        Assert.assertTrue(accPage.isAccountPageHeaderExist());
    }

    @Test
    @Description("Account page search exist test....")
    @Severity(SeverityLevel.NORMAL)
    public void searchExistTest(){
        Assert.assertTrue(accPage.isSearchExist());
    }

    @Test
    @Description("Account section test....")
    @Severity(SeverityLevel.NORMAL)
    public void accSectionsTest(){
        List<String> accSecList = accPage.getAccountPageSectionList();
        System.out.println("Account section list : " +accSecList);
        Assert.assertEquals(accSecList,Constants.ACCOUNT_SECTION_LIST);
    }

    @Test
    @Description("Search test....")
    @Severity(SeverityLevel.MINOR)
    public void searchTest(){
        searchResultsPage = accPage.doSearch("Macbook");
        String actSearchHeader = searchResultsPage.getResultPageHeaderValue();
        Assert.assertTrue(actSearchHeader.contains("Macbook"));
    }

    @Test
    @Description("Search count test....")
    @Severity(SeverityLevel.MINOR)
    public void searchProductCountTest(){
        searchResultsPage = accPage.doSearch("Macbook");
        int actProductCount = searchResultsPage.getProductSearchCount();
        Assert.assertEquals(actProductCount,Constants.MACBOOK_PRODUCT_COUNT);
    }

    @Test
    @Description("Search product list test....")
    @Severity(SeverityLevel.MINOR)
    public void getSearchProductListTest(){
        searchResultsPage = accPage.doSearch("Macbook");
        List<String> actProductList = searchResultsPage.getProductResultsList();
        System.out.println(actProductList);
    }
}

