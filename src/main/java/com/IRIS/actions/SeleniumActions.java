package com.IRIS.actions;

import static com.IRIS.constants.constants.SCREENSHOT_LOCATION;
import static com.IRIS.constants.constants.VALUE;
import static com.IRIS.constants.constants.rowMap;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.IRIS.locatorUpdates.LocatorsReader;
import com.IRIS.testcases.BeforeAfterSuite;
import com.IRIS.utils.EncrptionAndDecryption;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/**
 * @author Harish Kumar
 *
 */
public class SeleniumActions {
    private WebDriver driver;
    public ExtentTest logger;
    public ExtentSparkReporter spark;
    public ExtentReports extent;
    private static Logger LOGGER = LoggerFactory.getLogger(SeleniumActions.class);
    
    protected WebDriver getDriver() {
        return driver;
    }

    public SeleniumActions(WebDriver driver) {
        this.driver = driver;
    }
    
    /** This method will return a WebElement.
     * @param driver
     * @param PageName
     * @param locatorName
     * @return
     */
    public WebElement getWebElemnt(WebDriver driver,String PageName, String locatorName) {
        WebElement element = LocatorsReader.getWebElement(PageName, locatorName, driver);
        
        return element;
    }
    
    public void mouseHover(WebDriver driver,String PageName, String locatorName) {
        WebElement element = LocatorsReader.getWebElement(PageName, locatorName, driver);
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
    }
    
/**
 * This method is not enter the value in any input box
 * @param element
 * @param value
 */
    public void sendKeys(WebDriver driver,String PageName, String locatorName, String value) {
        WebElement element = LocatorsReader.getWebElement(PageName, locatorName, driver);
        element.clear();
        element.sendKeys(value);
        logger.log(Status.INFO, MarkupHelper.createLabel("=>> Send keys to the  " + "PageName = " + PageName +  "  " + " LocatorName= " + locatorName +  " SendKeysValue " + value, ExtentColor.PINK));
    }
    
    /**
     * This method is not enter the password in any input box
     * @param element
     * @param value
     */
        public void enterPassword(WebDriver driver,String PageName, String locatorName, String value) {
            WebElement element = LocatorsReader.getWebElement(PageName, locatorName, driver);
            element.clear();
            element.sendKeys(EncrptionAndDecryption.passworddecription(value));
            logger.log(Status.INFO, MarkupHelper.createLabel("=>> Send keys to the  " + "PageName = " + PageName +  "  " + " LocatorName= " + locatorName +  " SendKeysValue " + "*******", ExtentColor.PINK));
        }
    
    /** This method is to get the current Page URL
     * @param driver
     * @return
     */
    public String getCurrentURL(WebDriver driver) {
        String actualURL = driver.getCurrentUrl();
        
        return actualURL;
    }
    
    /**
     * This method is to perform Click Actions
     * @param element
     */
    public void click(WebDriver driver,String PageName, String locatorName) {
        WebElement element = LocatorsReader.getWebElement(PageName, locatorName, driver);
        element.click();
        logger.log(Status.INFO, MarkupHelper.createLabel("=>> Clicked on the "+ PageName + " " + locatorName, ExtentColor.PINK));
    }
    
    /**
     * This Method  is to use hard coded wait for some particular seconds
     * @param WaitInSec
     */
    public void waitInSec(long WaitInSec) {
        try {
            Thread.sleep(WaitInSec * 1000);
            logger.log(Status.INFO, MarkupHelper.createLabel("=>> wait for "+ WaitInSec + " Seconds", ExtentColor.PINK));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * This Method is to Wait an element visibility some given time frame
     * @param element
     * @param WaitTime
     */
    public void waitElemntVisibility(WebDriver driver, String PageName, String locatorName, long WaitTime) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WaitTime));
        WebElement element = LocatorsReader.getWebElement(PageName, locatorName, driver);
        wait.until(ExpectedConditions.visibilityOf(element));
//        logger.log(Status.INFO, "# Wait for visibility of "+ PageName + " " + locatorName);
    }
    
    /**
     * This Method is to Wait an element Invisibility for given time frame
     * @param element
     * @param WaitTime
     */
    public void waitElemntInVisibility(WebDriver driver,String PageName, String locatorName, long WaitTime) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WaitTime));
        WebElement element = LocatorsReader.getWebElement(PageName, locatorName, driver);
        wait.until(ExpectedConditions.invisibilityOf((element)));
//        logger.log(Status.INFO, "# Wait for Invisibility of "+ PageName + " " + locatorName);

    }
    
    /**
     * This Method is to Wait an element until its clickable 
     * @param element
     * @param WaitTime
     */
    public void waitElemntClickable(WebDriver driver, String PageName, String locatorName, long WaitTime) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WaitTime));
        WebElement element = LocatorsReader.getWebElement(PageName, locatorName, driver);
        wait.until(ExpectedConditions.elementToBeClickable((element)));
//        logger.log(Status.INFO, "# Wait for clickable of "+ PageName + " " + locatorName);
    }
    
    
    /** This method is to navigate to any URL
     * @param driver
     * @param URL
     */
    public void navigateToURL(WebDriver driver, String URL) {
        driver.navigate().to(URL);
        logger = BeforeAfterSuite.logger;
        logger.log(Status.INFO, MarkupHelper.createLabel("SELENIUM ACTIONS DETAILS FOR CURRENT TEST CASE", ExtentColor.PURPLE));
        logger.log(Status.INFO, MarkupHelper.createLabel("=>> Navigate to "+ URL, ExtentColor.PINK));
    }
    
    /** This method is to switch to a new tab
     * @param driver
     * @param PageName
     * @param locatorName
     */
    public void switchToNewTab(WebDriver driver, String PageName, String locatorName) {
        WebElement element = LocatorsReader.getWebElement(PageName, locatorName, driver);
        String winHandleBefore = driver.getWindowHandle();
        element.click();
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
        logger.log(Status.INFO, MarkupHelper.createLabel("=>> Switched to new tab "+ PageName + " " + locatorName, ExtentColor.PINK));

    }
    
    /**
     * This method is to scroll to an particular Element
     * @param driver
     * @param PageName
     * @param locatorName
     */
    public void scrollToAnElement(WebDriver driver, String PageName, String locatorName) {
        WebElement element = LocatorsReader.getWebElement(PageName, locatorName, driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;            
        js.executeScript("arguments[0].scrollIntoView();", element);
        logger.log(Status.INFO, MarkupHelper.createLabel("=>> Scoll down to the element "+ PageName + " " + locatorName, ExtentColor.PINK));

    }
    
    
    /** THis method is to extract the text from a WebElement
     * @param driver
     * @param PageName
     * @param locatorName
     * @return
     */
    public String getText (WebDriver driver, String PageName, String locatorName) {
        WebElement element = LocatorsReader.getWebElement(PageName, locatorName, driver);
        String actualText = element.getText().trim();
        return  actualText;
    }
    
    /** This method is to extract the value from a webElement
     * @param driver
     * @param PageName
     * @param locatorName
     * @return
     */
    public String getValue (WebDriver driver, String PageName, String locatorName) {
        WebElement element = LocatorsReader.getWebElement(PageName, locatorName, driver);
        String actualValue = element.getAttribute(VALUE).trim();
        return  actualValue;
    }
    
    /** This method is to extract the CSS property from a webElement
     * @param driver
     * @param PageName
     * @param locatorName
     * @param CssPropertyName
     * @return
     */
    public String getCssProperty (WebDriver driver, String PageName, String locatorName, String CssPropertyName) {
        WebElement element = LocatorsReader.getWebElement(PageName, locatorName, driver);
        String actualCssProperty = element.getCssValue(CssPropertyName).trim();
        return  actualCssProperty;
    }
    
    /** This method is to extract the text from list of webElements
     * @param driver
     * @param PageName
     * @param locatorName
     * @return
     */
    public List<String> getListOfString (WebDriver driver, String PageName, String locatorName) {
        List<WebElement> elements = LocatorsReader.getWebElements(PageName, locatorName, driver);
        return elements.stream().map(list -> list.getText().trim()).filter(text -> !text.isEmpty()).map(text -> text.replaceAll("\\r\\n", "")).collect(Collectors.toList());
    }
    
    /**
    * This function will take screenshot
     * @param testcaseName
     * @throws Exception
     */
    public void takeSnapShot(String testcaseName) throws Exception{
    String FilePath =  SCREENSHOT_LOCATION + testcaseName + " " +System.currentTimeMillis()+ ".png";
    TakesScreenshot scrShot =((TakesScreenshot) driver);
    File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
    File DestFile=new File(FilePath);
    FileUtils.copyFile(SrcFile, DestFile);
    }
    
    public void printqueryResults(ExtentTest logger, List<Map<String, Object>> tableData) {
        logger.info(MarkupHelper.createJsonCodeBlock(tableData));
    	
    }
    public void printqueryResults(ExtentTest logger, Map<String, String> tableData) {
        logger.info(MarkupHelper.createJsonCodeBlock(tableData));
    	
    }
    
    public String getTestDataForColumn(String testCaseId, String ColumnName) {
        String columnvalue = rowMap.get(testCaseId).get(ColumnName).trim();
        
        return columnvalue;
    }
    
}
