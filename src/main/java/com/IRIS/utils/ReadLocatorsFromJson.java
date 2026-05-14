package com.IRIS.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

import com.IRIS.locatorUpdates.OREntity.Attribute;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ReadLocatorsFromJson {

    private static WebDriver driver;

    public static void main(String[] args) throws InterruptedException {
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
            driver.navigate().to("https://www.google.com/");
            driver.manage().window().maximize();
            Thread.sleep(5 * 1000);
            String searchBox = getElement(driver, "login_button").getAttribute("value");
            System.out.println("searchBox " + searchBox);
        } finally {
            if (driver != null) {
                driver.close();
                driver.quit();
            }
        }
    }

    public static WebElement getElement(WebDriver driver, String LocatorName) {
        return driver.findElement(getlocatorsvalues(LocatorName));
    }

    public static By getlocatorsvalues(String locatorName) {
        Gson gson = new Gson();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\ve00ym493\\Downloads\\Login.json"))) {
            JsonObject locatorsJson = gson.fromJson(br, JsonObject.class);
            String locatorValue = locatorsJson.get(locatorName).getAsJsonObject().get("locatorValue").getAsString();
            System.out.println("locatorValue " + locatorValue);
            String locatortype = locatorsJson.get(locatorName).getAsJsonObject().get("locatorType").getAsString();
            System.out.println("locatortype " + locatortype);
            return getLocator(locatortype, locatorValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This Method will return the type of the locator.
     * 
     * @param propertyType
     * @param propertyValue
     * @return
     */
    public static By getLocator(String propertyType, String propertyValue) {
        if (propertyType.toUpperCase().equals(Attribute.CSS.name())) {
            return By.cssSelector(propertyValue);
        } else if (propertyType.toUpperCase().equals(Attribute.ID.name())) {
            return By.id(propertyValue);
        } else if (propertyType.toUpperCase().equals(Attribute.NAME.name())) {
            return By.name(propertyValue);
        } else if (propertyType.toUpperCase().equals(Attribute.XPATH.name())) {
            return By.xpath(propertyValue);
        } else if (propertyType.toUpperCase().equals(Attribute.LINKTEXT.name())) {
            return By.linkText(propertyValue);
        } else if (propertyType.toUpperCase().equals(Attribute.CLASSNAME.name())) {
            return By.className(propertyValue);
        }
        return null;
    }

}