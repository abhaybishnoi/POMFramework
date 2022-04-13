package com.qa.opencart.pages;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsPage {

    private WebDriver driver;
    private ElementUtil eleUtil;

    private By searchHeader = By.cssSelector("div#content h1");
    private By products = By.cssSelector("div.caption a");

    public SearchResultsPage(WebDriver driver){
        this.driver=driver;
        eleUtil = new ElementUtil(driver);
    }

    public String getResultPageHeaderValue(){
        if (eleUtil.doIsDisplayed(searchHeader)){
            return eleUtil.doElementGetText(searchHeader);
        }
        return null;
    }
    public int getProductSearchCount(){
        return eleUtil.waitForElementsToBeVisible(products, Constants.DEFAULT_TIME_OUT).size();
    }

    public List<String> getProductResultsList(){
        List<WebElement> productList = eleUtil.waitForElementsToBeVisible(products, Constants.DEFAULT_TIME_OUT);
        List<String> productValList = new ArrayList<String>();
        for (WebElement e: productList) {
            String text = e.getText();
            productValList.add(text);
        }
        return productValList;
    }

    public ProductInfoPage selectProduct(String mainProductName){
        System.out.println("Product name is : " +mainProductName);
        List<WebElement> productList = eleUtil.waitForElementsToBeVisible(products,Constants.DEFAULT_TIME_OUT);
        for (WebElement e : productList) {
            String producttext = e.getText();
            if (producttext.equalsIgnoreCase(mainProductName)) {
                e.click();
                break;
            }
        }
        return new ProductInfoPage(driver);
    }
}
