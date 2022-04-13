package com.qa.opencart.pages;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class AccountPage {

    private WebDriver driver;
    private ElementUtil eleUtil;

    private By search = By.name("search");
    private By searchBtn=By.cssSelector(".btn.btn-default.btn-lg");
    private By header = By.cssSelector("div#logo a");
    private By accountsList = By.cssSelector("div#content h2");

    public AccountPage(WebDriver driver){
        this.driver=driver;
        eleUtil = new ElementUtil(driver);
    }

    public String getAccountsPageTitle(){
        return eleUtil.waitForTitleIs(Constants.DEFAULT_TIME_OUT,Constants.ACCOUNT_PAGE_TITLE);
    }

    public boolean isAccountPageHeaderExist(){
        return eleUtil.doIsDisplayed(header);
    }

    public boolean isSearchExist(){
        return  eleUtil.doIsDisplayed(search);
    }

    public List<String> getAccountPageSectionList(){
        List<WebElement> secList = eleUtil.getElements(accountsList);
        List<String> accSecValList = new ArrayList<>();
        for (WebElement e : secList){
            String text = e.getText();
            accSecValList.add(text);
        }
        return accSecValList;
   }

   public SearchResultsPage doSearch(String productName){
        if (isSearchExist()){
         eleUtil.doSendKeys(search,productName);
         eleUtil.doClick(searchBtn);
         return new SearchResultsPage(driver);
        }
       return null;
   }
}
