package com.IRIS.testcases;

import static com.IRIS.constants.AssertionConstants.ADDED_ITEM_IS_NOT_VISIBLE;
import static com.IRIS.constants.AssertionConstants.ADD_TO_CART_BUTTON_IS_NOT_VISIBLE;
import static com.IRIS.constants.AssertionConstants.BUTTON_BACK_HOME_IS_NOT_VISIBLE_AND_ENABLED;
import static com.IRIS.constants.AssertionConstants.BUTTON_CANCEL_IS_NOT_VISIBLE_AND_ENABLED;
import static com.IRIS.constants.AssertionConstants.BUTTON_CANCEL_ON_CHECKOUT_IS_NOT_VISIBLE_AND_ENABLED;
import static com.IRIS.constants.AssertionConstants.BUTTON_CHECKOUT_IS_NOT_VISIBLE_AND_ENABLED;
import static com.IRIS.constants.AssertionConstants.BUTTON_CONTINUE_IS_NOT_VISIBLE_AND_ENABLED;
import static com.IRIS.constants.AssertionConstants.BUTTON_CONTINUE_SHOPPING_IS_NOT_VISIBLE_AND_ENABLED;
import static com.IRIS.constants.AssertionConstants.BUTTON_FINISH_IS_NOT_VISIBLE_AND_ENABLED;
import static com.IRIS.constants.AssertionConstants.BUTTON_REMOVE_IS_NOT_VISIBLE_AND_ENABLED;
import static com.IRIS.constants.AssertionConstants.CHECKOUT_COMPLETE_TITLE_DOES_NOT_MATCH;
import static com.IRIS.constants.AssertionConstants.CHECKOUT_TITLE_DOES_NOT_MATCH;
import static com.IRIS.constants.AssertionConstants.DESCRIPTION_DOES_NOT_MATCH;
import static com.IRIS.constants.AssertionConstants.FINAL_CHECKOUT_TEXT_DOES_NOT_MATCH;
import static com.IRIS.constants.AssertionConstants.NAME_DOES_NOT_MATCH;
import static com.IRIS.constants.AssertionConstants.NOT_NAVIGATE_TO_HOME_PAGE_AFTER_CLICKING_ON_BACK_HOME_BUTTON;
import static com.IRIS.constants.AssertionConstants.PRICE_DOES_NOT_MATCH;
import static com.IRIS.constants.AssertionConstants.QUANTITY_DOES_NOT_MATCH;
import static com.IRIS.constants.AssertionConstants.SHOPPING_CART_LINK_IS_NOT_VISIBLE;
import static com.IRIS.constants.AssertionConstants.SUMMARY_INFORMATION_LIST_DOES_NOT_MATCH;
import static com.IRIS.constants.AssertionConstants.SUMMARY_INFORMATION_VALUE_LIST_DOES_NOT_MATCH;
import static com.IRIS.constants.AssertionConstants.THANK_YOU_TEXT_DOES_NOT_MATCH;
import static com.IRIS.constants.CartConstants.BUTTON_CHECKOUT;
import static com.IRIS.constants.CartConstants.BUTTON_CONTINUE_SHOPPING;
import static com.IRIS.constants.CartConstants.BUTTON_REMOVE;
import static com.IRIS.constants.CartConstants.CART;
import static com.IRIS.constants.CartConstants.TEXT_CART_ITEM_PRICE;
import static com.IRIS.constants.CartConstants.TEXT_CART_QUANTITY;
import static com.IRIS.constants.CartConstants.TEXT_ITEM_DESCRIPTION;
import static com.IRIS.constants.CartConstants.TEXT_YOUR_CART;
import static com.IRIS.constants.CartConstants.YOUR_CART;
import static com.IRIS.constants.CheckoutConstants.BUTTON_BACK_HOME;
import static com.IRIS.constants.CheckoutConstants.BUTTON_CANCEL;
import static com.IRIS.constants.CheckoutConstants.BUTTON_CONTINUE;
import static com.IRIS.constants.CheckoutConstants.BUTTON_FINISH;
import static com.IRIS.constants.CheckoutConstants.CHECKOUT;
import static com.IRIS.constants.CheckoutConstants.CHECKOUT_COMPLETE;
import static com.IRIS.constants.CheckoutConstants.CHECKOUT_YOUR_INFORMATION;
import static com.IRIS.constants.CheckoutConstants.FIRST_NAME;
import static com.IRIS.constants.CheckoutConstants.HOME_PAGE_URL;
import static com.IRIS.constants.CheckoutConstants.INPUT_FIRST_NAME;
import static com.IRIS.constants.CheckoutConstants.INPUT_LAST_NAME;
import static com.IRIS.constants.CheckoutConstants.INPUT_POSTAL_CODE;
import static com.IRIS.constants.CheckoutConstants.LAST_NAME;
import static com.IRIS.constants.CheckoutConstants.ORDER_CONFIRMATION_MESSAGE;
import static com.IRIS.constants.CheckoutConstants.POSTAL_CODE;
import static com.IRIS.constants.CheckoutConstants.SUMMARY_INFORMATION_LIST;
import static com.IRIS.constants.CheckoutConstants.SUMMARY_INFORMATION_VALUE_LIST;
import static com.IRIS.constants.CheckoutConstants.TEXT_CHECKOUT_FINAL_MESSAGE;
import static com.IRIS.constants.CheckoutConstants.TEXT_CHECKOUT_TITLE;
import static com.IRIS.constants.CheckoutConstants.TEXT_SUMMARY_LABEL;
import static com.IRIS.constants.CheckoutConstants.TEXT_SUMMARY_VALUE;
import static com.IRIS.constants.CheckoutConstants.TEXT_THANK_YOU;
import static com.IRIS.constants.CheckoutConstants.THANK_YOU_MESSAGE;
import static com.IRIS.constants.HomePageConstants.BUTTON_ADD_TO_CART_BACKPACK;
import static com.IRIS.constants.HomePageConstants.BUTTON_LOGIN;
import static com.IRIS.constants.HomePageConstants.HOMEPAGE;
import static com.IRIS.constants.HomePageConstants.INPUT_PASSWORD;
import static com.IRIS.constants.HomePageConstants.INPUT_USERNAME;
import static com.IRIS.constants.HomePageConstants.*;
import static com.IRIS.constants.HomePageConstants.STANDARD_PASSWORD;
import static com.IRIS.constants.HomePageConstants.STANDARD_USERNAME;
import static com.IRIS.constants.HomePageConstants.TEXT_ITEM_DESC;
import static com.IRIS.constants.HomePageConstants.TEXT_ITEM_NAME;
import static com.IRIS.constants.HomePageConstants.TEXT_ITEM_PRICE;
import static com.IRIS.constants.HomePageConstants.TEXT_SHOPPING_CART_ITEMVALUE;
import static com.IRIS.constants.constants.APP_URL;
import static com.IRIS.constants.constants.CHROME;
import static com.IRIS.constants.constants.HARISH;
import static com.IRIS.constants.constants.SMOKE;
import static com.IRIS.constants.DataBaseConstants.*;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.IRIS.actions.SeleniumActions;
import com.IRIS.utils.GetTestData;
import com.IRIS.utils.RetryAnalyzer;
import com.IRIS.utils.SQLJDBCUtilityToTestUI;
import com.IRIS.validations.TestngValidation;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;


public class HomePage {

    private WebDriver driver;
    private SeleniumActions seleniumActions;
    private BeforeAfterSuite beforeAfterSuite;
    public ExtentTest logger;
    public static ExtentReports extent;
    private TestngValidation testngValidation;
    
	@Parameters("browser")
    @BeforeClass
    public void beforeClass(String browser) throws MalformedURLException {
        beforeAfterSuite = new BeforeAfterSuite();
        driver = beforeAfterSuite.getDriver(browser);
        seleniumActions = new SeleniumActions(driver);
        testngValidation = new TestngValidation();
    }

    @Test(description = "checkout_01", retryAnalyzer = RetryAnalyzer.class)
    public void saucelab_add_to_cart_checkout() {
    	        String testcasename = new Object() {}.getClass().getEnclosingMethod().getName();
               logger = BeforeAfterSuite.startReport(testcasename, "This is for Testing Purpose", HARISH, SMOKE, CHROME);
               SoftAssert assertion = new SoftAssert();
               seleniumActions.navigateToURL(driver, APP_URL);
               seleniumActions.waitElemntVisibility(driver, HOMEPAGE, INPUT_USERNAME, 30);
               seleniumActions.sendKeys(driver, HOMEPAGE, INPUT_USERNAME, GetTestData.getValue("checkout_01", "username"));
               seleniumActions.sendKeys(driver, HOMEPAGE, INPUT_PASSWORD, GetTestData.getValue("checkout_01", "password"));
               seleniumActions.waitElemntVisibility(driver, HOMEPAGE, BUTTON_LOGIN, 20);
               seleniumActions.click(driver, HOMEPAGE, BUTTON_LOGIN);
               seleniumActions.waitElemntVisibility(driver, HOMEPAGE, BUTTON_ADD_TO_CART_BACKPACK, 30);
               String expectedItemName = seleniumActions.getText(driver, HOMEPAGE, TEXT_ITEM_NAME);
               String expectedItemDescription = seleniumActions.getText(driver, HOMEPAGE, TEXT_ITEM_DESC);
               String expectedItemPrice = seleniumActions.getText(driver, HOMEPAGE, TEXT_ITEM_PRICE);
               testngValidation.assertTrue(assertion, seleniumActions.getWebElemnt(driver, HOMEPAGE, BUTTON_ADD_TO_CART_BACKPACK).isDisplayed(), ADD_TO_CART_BUTTON_IS_NOT_VISIBLE);
               testngValidation.assertTrue(assertion, seleniumActions.getWebElemnt(driver, HOMEPAGE, LINK_SHOPPING_CART).isDisplayed(), SHOPPING_CART_LINK_IS_NOT_VISIBLE);
               seleniumActions.click(driver, HOMEPAGE, BUTTON_ADD_TO_CART_BACKPACK);
               String expectedAddToCartItemValue = seleniumActions.getText(driver, HOMEPAGE, TEXT_SHOPPING_CART_ITEMVALUE);
               testngValidation.assertEquals(assertion, expectedAddToCartItemValue, "1", ADDED_ITEM_IS_NOT_VISIBLE);
               seleniumActions.click(driver, HOMEPAGE, LINK_SHOPPING_CART);
               seleniumActions.waitInSec(2);
               seleniumActions.waitElemntVisibility(driver, CART, TEXT_YOUR_CART, 30);
               testngValidation.assertEquals(assertion, seleniumActions.getText(driver, CART, TEXT_YOUR_CART), YOUR_CART, ADDED_ITEM_IS_NOT_VISIBLE);
               String actualItemName = seleniumActions.getText(driver, CART, TEXT_ITEM_NAME);
               String actualItemDescription = seleniumActions.getText(driver, CART, TEXT_ITEM_DESCRIPTION);
               String actualItemPrice = seleniumActions.getText(driver, CART, TEXT_CART_ITEM_PRICE);
               String actualItemQuantity = seleniumActions.getText(driver, CART, TEXT_CART_QUANTITY);
               testngValidation.assertEquals(assertion, actualItemQuantity, expectedAddToCartItemValue, QUANTITY_DOES_NOT_MATCH);
               testngValidation.assertEquals(assertion, actualItemName, expectedItemName, NAME_DOES_NOT_MATCH);
               testngValidation.assertEquals(assertion, actualItemDescription, expectedItemDescription, DESCRIPTION_DOES_NOT_MATCH);
               testngValidation.assertEquals(assertion, actualItemPrice, expectedItemPrice, PRICE_DOES_NOT_MATCH);
               testngValidation.assertTrue(assertion, seleniumActions.getWebElemnt(driver, CART, BUTTON_CONTINUE_SHOPPING).isEnabled(), BUTTON_CONTINUE_SHOPPING_IS_NOT_VISIBLE_AND_ENABLED);
               testngValidation.assertTrue(assertion, seleniumActions.getWebElemnt(driver, CART, BUTTON_CHECKOUT).isEnabled(), BUTTON_CHECKOUT_IS_NOT_VISIBLE_AND_ENABLED);
               testngValidation.assertTrue(assertion, seleniumActions.getWebElemnt(driver, CART, BUTTON_REMOVE).isEnabled(), BUTTON_REMOVE_IS_NOT_VISIBLE_AND_ENABLED);
               seleniumActions.click(driver, CART, BUTTON_CHECKOUT);
               seleniumActions.waitInSec(2);
               testngValidation.assertEquals(assertion, seleniumActions.getText(driver, CHECKOUT, TEXT_CHECKOUT_TITLE), CHECKOUT_YOUR_INFORMATION, CHECKOUT_TITLE_DOES_NOT_MATCH);
               testngValidation.assertTrue(assertion, seleniumActions.getWebElemnt(driver, CHECKOUT, BUTTON_CANCEL).isEnabled(), BUTTON_CANCEL_IS_NOT_VISIBLE_AND_ENABLED);
               testngValidation.assertTrue(assertion, seleniumActions.getWebElemnt(driver, CHECKOUT, BUTTON_CONTINUE).isEnabled(), BUTTON_CONTINUE_IS_NOT_VISIBLE_AND_ENABLED);
               seleniumActions.sendKeys(driver, CHECKOUT, INPUT_FIRST_NAME, GetTestData.getValue("checkout_01", "firstname"));
               seleniumActions.sendKeys(driver, CHECKOUT, INPUT_LAST_NAME, GetTestData.getValue("checkout_01", "lastname"));
               seleniumActions.sendKeys(driver, CHECKOUT, INPUT_POSTAL_CODE, GetTestData.getValue("checkout_01", "portalcode"));
               seleniumActions.click(driver, CHECKOUT, BUTTON_CONTINUE);
               seleniumActions.waitInSec(2);
               String actualItemNameCheckout = seleniumActions.getText(driver, CART, TEXT_ITEM_NAME);
               String actualItemDescriptionCheckOut = seleniumActions.getText(driver, CART, TEXT_ITEM_DESCRIPTION);
               String actualItemPriceCheckout = seleniumActions.getText(driver, CART, TEXT_CART_ITEM_PRICE);
               String actualItemQuantityCheckout = seleniumActions.getText(driver, CART, TEXT_CART_QUANTITY);
               testngValidation.assertEquals(assertion, actualItemQuantityCheckout, expectedAddToCartItemValue, QUANTITY_DOES_NOT_MATCH);
               testngValidation.assertEquals(assertion, actualItemNameCheckout, expectedItemName, NAME_DOES_NOT_MATCH);
               testngValidation.assertEquals(assertion, actualItemDescriptionCheckOut, expectedItemDescription, DESCRIPTION_DOES_NOT_MATCH);
               testngValidation.assertEquals(assertion, actualItemPriceCheckout, expectedItemPrice, PRICE_DOES_NOT_MATCH);
               testngValidation.assertTrue(assertion, seleniumActions.getWebElemnt(driver, CHECKOUT, BUTTON_CANCEL).isEnabled(), BUTTON_CANCEL_ON_CHECKOUT_IS_NOT_VISIBLE_AND_ENABLED);
               testngValidation.assertTrue(assertion, seleniumActions.getWebElemnt(driver, CHECKOUT, BUTTON_FINISH).isEnabled(), BUTTON_FINISH_IS_NOT_VISIBLE_AND_ENABLED);
               List<String> summaryInformation = seleniumActions.getListOfString(driver, CHECKOUT, TEXT_SUMMARY_LABEL);
               List<String> summaryInformationValue = seleniumActions.getListOfString(driver, CHECKOUT, TEXT_SUMMARY_VALUE);
               testngValidation.assertEquals(assertion, summaryInformation, SUMMARY_INFORMATION_LIST, SUMMARY_INFORMATION_LIST_DOES_NOT_MATCH);
               testngValidation.assertEquals(assertion, summaryInformationValue, SUMMARY_INFORMATION_VALUE_LIST, SUMMARY_INFORMATION_VALUE_LIST_DOES_NOT_MATCH);
               seleniumActions.click(driver, CHECKOUT, BUTTON_FINISH);
               seleniumActions.waitInSec(2);
               testngValidation.assertEquals(assertion, seleniumActions.getText(driver, CHECKOUT, TEXT_CHECKOUT_TITLE), CHECKOUT_COMPLETE, CHECKOUT_COMPLETE_TITLE_DOES_NOT_MATCH);
               testngValidation.assertEquals(assertion, seleniumActions.getText(driver, CHECKOUT, TEXT_THANK_YOU), THANK_YOU_MESSAGE, THANK_YOU_TEXT_DOES_NOT_MATCH);
               testngValidation.assertEquals(assertion, seleniumActions.getText(driver, CHECKOUT, TEXT_CHECKOUT_FINAL_MESSAGE), ORDER_CONFIRMATION_MESSAGE, FINAL_CHECKOUT_TEXT_DOES_NOT_MATCH);
               testngValidation.assertTrue(assertion, seleniumActions.getWebElemnt(driver, CHECKOUT, BUTTON_BACK_HOME).isEnabled(), BUTTON_BACK_HOME_IS_NOT_VISIBLE_AND_ENABLED);
               seleniumActions.click(driver, CHECKOUT, BUTTON_BACK_HOME);
               seleniumActions.waitElemntVisibility(driver, HOMEPAGE, BUTTON_ADD_TO_CART_BACKPACK, 30);
               testngValidation.assertEquals(assertion, seleniumActions.getCurrentURL(driver), HOME_PAGE_URL, NOT_NAVIGATE_TO_HOME_PAGE_AFTER_CLICKING_ON_BACK_HOME_BUTTON);
               List<Map<String, Object>> allData = SQLJDBCUtilityToTestUI.getAllTableData("SELECT * FROM iris.username");
               testngValidation.assertEquals(assertion, SQLJDBCUtilityToTestUI.getColumnValuesAsString(allData, COLUMN_USER_ID), USERIDS, USER_IDS_DOES_NOT_MATCH);
               testngValidation.assertEquals(assertion, SQLJDBCUtilityToTestUI.getColumnValuesAsString(allData, COLUMN_USERNAME), USERNAMES, USERNAMES_DOES_NOT_MATCH);
               testngValidation.assertEquals(assertion, SQLJDBCUtilityToTestUI.getColumnValuesAsString(allData, COLUMN_FULL_NAME), FULLNAMES, FULL_NAME_DOES_NOT_MATCH);
               testngValidation.assertEquals(assertion, SQLJDBCUtilityToTestUI.getColumnValuesAsString(allData, COLUMN_EMAIL), EMAILS, EMAILS_DOES_NOT_MATCH);
               testngValidation.assertEquals(assertion, SQLJDBCUtilityToTestUI.getColumnValuesAsString(allData, COLUMN_PHONE), PHONENUMBERS, PHONE_NUMBERS_DOES_NOT_MATCH);
               assertion.assertAll();
           } 
       

    
    @AfterMethod
    public void afterMethod(ITestResult results) throws Exception {
        beforeAfterSuite.getReportResult(results, logger);
    }

    @AfterClass
    public void afterClass() {
        BeforeAfterSuite.tearDown();
    }
}
