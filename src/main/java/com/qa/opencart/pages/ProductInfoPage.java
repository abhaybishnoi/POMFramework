package com.qa.opencart.pages;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductInfoPage {

    private WebDriver driver;
    private ElementUtil eleUtil;
    private Map<String, String> productInfoMap;

    private By productHeader = By.cssSelector("div#content h1");
    private By productImages = By.cssSelector("div #content img");
    private By productmetaData = By.cssSelector("div#content ul.list-unstyled:nth-of-type(1) li");
    private By pricemetaData = By.cssSelector("div#content ul.list-unstyled:nth-of-type(2) li");
    private By qty = By.cssSelector("#input-quantity");
    private By addToCartBtn = By.id("button-cart");
    private By successText = By.cssSelector("div .alert.alert-success.alert-dismissible");


    public ProductInfoPage(WebDriver driver) {
        this.driver=driver;
        eleUtil = new ElementUtil(driver);
    }

    public String getProductHeaderText(){
        return eleUtil.doElementGetText(productHeader).trim();
    }

    public int getProductImagesCount(){
        return eleUtil.waitForElementsToBeVisible(productImages, Constants.DEFAULT_TIME_OUT).size();
    }

    public Map<String,String> getProductInfo(){
        productInfoMap = new HashMap<String, String>();
        productInfoMap.put("productname",getProductHeaderText());
        productInfoMap.put("imagescount",String.valueOf(getProductImagesCount()));
        getProductMetaData();
        getPriceMetaData();
        return productInfoMap;
    }

    private void getProductMetaData() {
        List<WebElement> metaDataList = eleUtil.waitForElementsToBeVisible(productmetaData, Constants.DEFAULT_TIME_OUT);
        for (WebElement e : metaDataList) {
            String dataText = e.getText().trim();
            String[] data = dataText.split(":");
            String metaKey = data[0].trim();
            String metaValue = data[1].trim();
            productInfoMap.put(metaKey, metaValue);
        }

    }
    private void getPriceMetaData() {
        List<WebElement> metaPriceList = eleUtil.waitForElementsToBeVisible(pricemetaData, Constants.DEFAULT_TIME_OUT);
        String price = metaPriceList.get(0).getText().trim();
        String exPrice = metaPriceList.get(1).getText().trim();
        productInfoMap.put("price",price);
        productInfoMap.put("exPrice",exPrice);
    }

    }
