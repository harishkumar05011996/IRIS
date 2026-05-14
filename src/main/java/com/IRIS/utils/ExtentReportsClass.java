package com.IRIS.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportsClass {
    public WebDriver driver;
    public ExtentSparkReporter spark;
    public ExtentReports extent;
    public ExtentTest logger;

    @BeforeTest
    public void startReport() {
        // Create an object of Extent Reports
        extent = new ExtentReports();
        spark = new ExtentSparkReporter(System.getProperty("user.dir") + "\\src\\main\\resources\\extentedReports\\YMSLIExtentedReport " + LocalDate.now() + ".html");
        extent.attachReporter(spark);
        extent.setSystemInfo("Host Name", "YMSLI");
        extent.setSystemInfo("Environment", "Test");
        extent.setSystemInfo("User Name", "Harish Kumar");
        spark.config().setDocumentTitle("Automation Report");
        // Name of the report
        spark.config().setReportName("YMSLI Automation Report");
        // Dark Theme
        spark.config().setTheme(Theme.STANDARD);
    }

//This method is to capture the screenshot and return the path of the screenshot.
    public static String getScreenShot(WebDriver driver, String screenshotName) throws IOException {
        String FilePath = System.getProperty("user.dir") + "\\src\\main\\resources\\ScreenShots\\" + screenshotName + " "+ LocalDate.now() + "Report"+ ".png";
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
// after execution, you could see a folder "FailedTestsScreenshots" under src folder
        String destination = FilePath;
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }

    @BeforeMethod
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ve00ym493\\git\\harishdemo\\src\\main\\resources\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.google.com/");
    }

    @Test
    public void verifyTitle() {
        logger = extent.createTest("To verify Google Title");
        Assert.assertEquals(driver.getTitle(), "Google");
    }

    @Test
    public void verifyLogo() {
        logger = extent.createTest("To verify Google Logo");
        boolean img = driver.findElement(By.xpath("//img&#91;@id='hplogo']")).isDisplayed();
        logger.createNode("Image is Present");
        Assert.assertTrue(img);
        logger.createNode("Image is not Present");
        Assert.assertFalse(img);
    }
    
    @AfterMethod
    public void getResult(ITestResult result) throws Exception {
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.log(Status.FAIL,
                    MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
            logger.log(Status.FAIL,
                    MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
            String screenshotPath = getScreenShot(driver, result.getMethod().getMethodName());
            logger.fail("Test Case Failed Snapshot is below " + logger.addScreenCaptureFromPath(screenshotPath));
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.log(Status.SKIP,
                    MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.log(Status.PASS,
                    MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
            String screenshotPath = getScreenShot(driver, result.getMethod().getMethodName());
            logger.pass("Test Case Pass Snapshot is below " + logger.addScreenCaptureFromPath(screenshotPath));
        }
    }

    @AfterTest
    public void endReport() {
        extent.flush();
    }
}