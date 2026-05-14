package com.IRIS.testcases;
import static com.IRIS.constants.DataBaseConstants.*;
import static com.IRIS.constants.LoginConstants.BUTTON_SIGNIN;
import static com.IRIS.constants.LoginConstants.DEALER_USERNAME;
import static com.IRIS.constants.LoginConstants.INPUT_LOGIN_NAME;
import static com.IRIS.constants.LoginConstants.INPUT_LOGIN_PASSWORD;
import static com.IRIS.constants.LoginConstants.LOGIN;
import static com.IRIS.constants.LoginConstants.LOGIN_PASSWORD;
import static com.IRIS.constants.LoginConstants.MASTER_USERNAME;
import static com.IRIS.constants.constants.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
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
import com.IRIS.utils.CompareTwoExcelFiles;
import com.IRIS.utils.PostgreSQLJDBCUtility;
import com.IRIS.utils.PostgreSQLJDBCUtilityToTestUI;
import com.IRIS.utils.RetryAnalyzer;
import com.IRIS.validations.TestngValidation;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class DataBaseTesting {
    
    private WebDriver driver;
    private SeleniumActions seleniumActions;
    private BeforeAfterSuite beforeAfterSuite;
    public ExtentTest logger;
    public static ExtentReports extent;
    private TestngValidation testngValidation;
    List<Map<String, Object>> completeTableData = null;

    @BeforeClass
	@Parameters("browser")
    public void beforeClass(String browser) throws MalformedURLException {
        beforeAfterSuite = new BeforeAfterSuite();
        driver = beforeAfterSuite.getDriver(browser);
        seleniumActions = new SeleniumActions(driver);
        testngValidation = new TestngValidation();
        BeforeAfterSuite.deleteAllTmpFiles();

    }
    
    @Test(description = "dataBase_testing", retryAnalyzer = RetryAnalyzer.class)
    public void dataBase_testing() {
        String testcasename = new Object() {}.getClass().getEnclosingMethod().getName();
        logger = BeforeAfterSuite.startReport(testcasename, DATABASE_TESTING_DESCRIPTION, HARISH, SMOKE, CHROME);
        SoftAssert assertion = new SoftAssert();
        seleniumActions.navigateToURL(driver, APP_URL);
        seleniumActions.waitElemntVisibility(driver, LOGIN, INPUT_LOGIN_NAME, 30);
        seleniumActions.sendKeys(driver, LOGIN, INPUT_LOGIN_NAME, MASTER_USERNAME);
        seleniumActions.sendKeys(driver, LOGIN, INPUT_LOGIN_PASSWORD, LOGIN_PASSWORD);
        seleniumActions.click(driver, LOGIN, BUTTON_SIGNIN);
        seleniumActions.waitInSec(5);
        testngValidation.dataBaseValidationsStart();
        completeTableData = PostgreSQLJDBCUtilityToTestUI.getAllTableData(FUNCTION_ID_QUERY, true);
        String text = "1 " + seleniumActions.getText(driver, LOGIN, INPUT_LOGIN_NAME) + " 2".trim();
        testngValidation.assertEquals(assertion, seleniumActions.getValue(driver, LOGIN, INPUT_LOGIN_NAME), text, "input Login Name Value does not match");
//        testngValidation.assertEquals(assertion, getColumnDetails(completeTableData, ZERO, USER_ID), USERID_SUNIL_KUMAR, FIRST_USER_ID_IS_MATCHED);
//        testngValidation.assertEquals(assertion, getColumnDetails(completeTableData, ONE, USER_ID), USERID_HRISHI, SECOND_USER_ID_IS_MATCHED);
//        testngValidation.assertEquals(assertion, getColumnDetails(completeTableData, ZERO, FUNCTION_ID), FUNCTIONID_904, FIRST_FUNCTION_ID_IS_MATCHED);
//        testngValidation.assertEquals(assertion, getColumnDetails(completeTableData, ONE, FUNCTION_ID), FUNCTIONID_904, SECOND_FUNCTION_ID_IS_MATCHED);
        assertion.assertAll();
    }
    
    
    @Test(description = "dataBase_testing2", retryAnalyzer = RetryAnalyzer.class)
    public void dataBase_testing2() throws Exception {
        String testcasename = new Object() {}.getClass().getEnclosingMethod().getName();
        logger = BeforeAfterSuite.startReport(testcasename, DATABASE_TESTING_DESCRIPTION, HARISH, SMOKE, CHROME);
        SoftAssert assertion = new SoftAssert();
        seleniumActions.navigateToURL(driver, APP_URL);
        seleniumActions.waitElemntVisibility(driver, LOGIN, INPUT_LOGIN_NAME, 30);
        seleniumActions.sendKeys(driver, LOGIN, INPUT_LOGIN_NAME, DEALER_USERNAME);
        seleniumActions.sendKeys(driver, LOGIN, INPUT_LOGIN_PASSWORD, LOGIN_PASSWORD);
        seleniumActions.click(driver, LOGIN, BUTTON_SIGNIN);
        seleniumActions.waitInSec(5);
        seleniumActions.sendKeys(driver, DATABASE, FUNCTION_INPUT_SEARCH, "AMR01");
        seleniumActions.click(driver, DATABASE, BTN_SERACH_FUNCTION);
        clickOnDownloadButton(driver, testcasename);
        PostgreSQLJDBCUtility.writeDatatoExcel(PPR01_MODULE_QUERY, testcasename + "DB");
        CompareTwoExcelFiles.CompareTwoExcelSheets(assertion, testcasename, testcasename + "DB");
        assertion.assertAll();

    }

    private void clickOnDownloadButton(WebDriver driver, String testcasename) throws AWTException {
        seleniumActions.waitInSec(THREE);
        seleniumActions.click(driver, DATABASE, BTN_DOWNLOAD);
        seleniumActions.waitInSec(THREE);
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        BeforeAfterSuite.updateFileExtension(testcasename);
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
