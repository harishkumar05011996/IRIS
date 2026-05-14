package com.IRIS.testcases;

import static com.IRIS.constants.constants.APP_URL;
import static com.IRIS.constants.constants.BROWSERMODE;
import static com.IRIS.constants.constants.CHROME;
import static com.IRIS.constants.constants.EDGE;
import static com.IRIS.constants.constants.EXTENTED_REPORT_LOCATION;
import static com.IRIS.constants.constants.FIREFOX;
import static com.IRIS.constants.constants.HEADLESS_EXECUTION;
import static com.IRIS.constants.constants.HUB_URL;
import static com.IRIS.constants.constants.OR_FOLDER_LOCATION;
import static com.IRIS.constants.constants.REMOTE;
import static com.IRIS.constants.constants.RUN_MODE;
import static com.IRIS.constants.constants.SCREENSHOT_LOCATION;
import static java.util.Objects.nonNull;

import java.awt.Desktop;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.IRIS.locatorUpdates.LocatorsReader;
import com.IRIS.locatorUpdates.ORException;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import com.google.common.collect.ImmutableMap;
import com.opencsv.exceptions.CsvValidationException;

import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class BeforeAfterSuite {

    private static Logger LOGGER = LoggerFactory.getLogger(BeforeAfterSuite.class);
    private static ThreadLocal<WebDriver> webDriverCache = new ThreadLocal<WebDriver>();
    public static String excelFilePath = System.getProperty("user.dir") + "\\src\\main\\resources\\ExcelSheets\\";


    public static WebDriver driver;
    private int retryCount = 1;
    public static Properties configurationMap = new Properties();
    public static Properties dbQueryProperties = new Properties();
    public static FileInputStream filePath;
    public static FileInputStream dbQueryFilePath;
    public static final String BASEPATH = System.getProperty("user.dir") + "\\src\\main";
    public static String PROPERTIES_PATH = String.format("%s%s%s%s%s%s%s%s%s%s", System.getProperty("user.dir"), File.separatorChar,"src",File.separatorChar,"main",File.separatorChar,"resources",File.separatorChar,"properties",File.separatorChar);
    public ExtentSparkReporter spark;
    public static ExtentTest logger;
    public static ExtentReports extent;
    
    
    /** THis method is to setup before suite configurations
     * @throws CsvValidationException
     * @throws ORException
     * @throws IOException
     */
    @BeforeSuite(alwaysRun = true)
    public void BeforeSuiteConfuration() throws CsvValidationException, ORException, IOException {
        setup();
        LocatorsReader.buildCacheFromOrDir(OR_FOLDER_LOCATION);
        RepotConfiguration();
    }
    
    
    /** This method is to read all the properties files
     * 
     */
    public static void setup() {
        try {
            filePath = new FileInputStream(PROPERTIES_PATH + "ConfigurationMap.properties");
            dbQueryFilePath = new FileInputStream(PROPERTIES_PATH + "DbQuery.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            configurationMap.load(filePath);
            dbQueryProperties.load(dbQueryFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void RepotConfiguration() {
        extent = new ExtentReports();
        spark = new ExtentSparkReporter(EXTENTED_REPORT_LOCATION + ".html");
        extent.attachReporter(spark);
        extent.setSystemInfo("Author Name", "Harish Tomar");
        extent.setSystemInfo("Application", "IRISAssingment");
        extent.setSystemInfo("APP URL", APP_URL);
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Browser Mode", configurationMap.getProperty(BROWSERMODE));
        spark.config().setDocumentTitle("Automation Report");
        spark.config().setReportName("IRIS Automation Report");
        spark.config().setOfflineMode(false);
        spark.config().setTimelineEnabled(true);
        spark.config().setTheme(Theme.DARK);
        spark.viewConfigurer()
                    .viewOrder()
                    .as(new ViewName[] { 
                       ViewName.DASHBOARD, 
                       ViewName.TEST, 
                       ViewName.AUTHOR, 
                       ViewName.DEVICE, 
                       ViewName.CATEGORY, 
                    })
                  .apply();
    }
    
    public static ExtentTest startReport(String testcasename, String description, String AuthorName, String Category, String device) {
        logger = extent.createTest(testcasename, description).assignAuthor(AuthorName).assignCategory(Category).assignDevice(device);
        return logger;
    }
    
    public static ExtentTest startReport() {
        return logger;
    }
    
    @AfterSuite(alwaysRun = true)
    public void generateReport() throws IOException {
        extent.flush();
//        Desktop.getDesktop().browse(new File(EXTENTED_REPORT_LOCATION + ".html").toURI());
    }

    /**
     * Logs the result of a test case in an Extent report.
     *
     * @param result The result of the test case.
     * @param logger The logger used to write the Extent report.
     * @throws Exception If there is an error taking a screenshot of the test case.
     */
    /**
     * Logs the result of a test case in an Extent report.
     *
     * @param result The result of the test case.
     * @param logger The logger used to write the Extent report.
     * @throws Exception If there is an error taking a screenshot of the test case.
     */
    public void getReportResult(ITestResult result, ExtentTest logger) throws Exception {
        String methodName = result.getMethod().getMethodName();
        String screenshotPath = BeforeAfterSuite.getScreenShot(methodName);
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.log(Status.FAIL, MarkupHelper.createLabel(methodName + " - The test case has FAILED, and the reason for the failure is described below. Please check for more information.", ExtentColor.RED));
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
            logger.fail("Test Case Failed " + logger.addScreenCaptureFromBase64String(screenshotPath, "Click \u261D \u2191 \u261D to open the image"));
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.info("Retrying test " + methodName + " with status " + getExtentReportStatusName(result.getStatus()) + " for the " + (retryCount++) + " time(s).");
            logger.log(Status.SKIP, MarkupHelper.createLabel(methodName + " The test case has SKIPPED, and the reason for the SKIPPED is described below. Please check for more information.", ExtentColor.LIME));
            logger.pass("Test Case retried " + logger.addScreenCaptureFromBase64String(screenshotPath, "Click \u261D \u2191 \u261D to open the image"));
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.log(Status.PASS, MarkupHelper.createLabel(methodName + " The test case has PASSED", ExtentColor.GREEN));
            logger.pass("Test Case Pass " + logger.addScreenCaptureFromBase64String(screenshotPath, "Click \u261D \u2191 \u261D to open the image"));
        } else {
            logger.log(Status.FAIL, MarkupHelper.createLabel(methodName + " - The test case has FAILED, and the reason for the failure is described below. Please check for more information.", ExtentColor.RED));
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
            logger.fail("Test Case Failed " + logger.addScreenCaptureFromBase64String(screenshotPath, "Click \u261D \u2191 \u261D to open the image"));
        }
        }
        
        /**
         * Returns the corresponding Extent report status name based on the numeric status code.
         *
         * @param statusCode the numeric status code
         * @return the corresponding Extent report status name as a string
         */
        public static String getExtentReportStatusName(int statusCode) {
            switch (statusCode) {
                case 1:
                    return Status.PASS.name();
                case 2:
                    return Status.FAIL.name();
                case 3:
                    return Status.SKIP.name();
                default:
                    throw new IllegalArgumentException("Invalid status code: " + statusCode);
            }
        }
    
    public String getResultStatusName(int status) {
        Status resultName = null;
        if (status == 1)
            resultName = Status.PASS;
        if (status == 2)
            resultName = Status.FAIL;
        if (status == 3)
            resultName = Status.SKIP;
        
        return resultName.toString();
    }
    
    /**
     * This method will return the driver
     * 
     * @return
     * @throws MalformedURLException 
     * @throws IOException 
     */
    public WebDriver getDriver(String browser) throws MalformedURLException {
        if (browser.equalsIgnoreCase(CHROME)) {
            driver = createChromeDriver();
        } else if (browser.equalsIgnoreCase(FIREFOX)) {
            driver = createFirefoxDriver();
        } else if (browser.equalsIgnoreCase(EDGE)) {
            driver = createEdgeDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        driver.manage().window().maximize();
        webDriverCache.set(driver);

        return driver;
    }

    private WebDriver createChromeDriver() throws MalformedURLException {
        if (RUN_MODE.equalsIgnoreCase(REMOTE)) {
            return new RemoteWebDriver(new URL(HUB_URL), getChromeOptions());
        } else {
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver(getChromeOptions());
        }
    }

    private WebDriver createFirefoxDriver() throws MalformedURLException {
        if (RUN_MODE.equalsIgnoreCase(REMOTE)) {
            return new RemoteWebDriver(new URL(HUB_URL), getFirefoxOptions());
        } else {
            WebDriverManager.firefoxdriver().setup();
            return new FirefoxDriver(getFirefoxOptions());
        }
    }

    private WebDriver createEdgeDriver() throws MalformedURLException {
        if (RUN_MODE.equalsIgnoreCase(REMOTE)) {
            return new RemoteWebDriver(new URL(HUB_URL), getEdgeOptions());
        } else {
            WebDriverManager.edgedriver().setup();
            return new EdgeDriver(getEdgeOptions());
        }
    }

    
    
    /** This method is to take screenshots
     * @param driver
     * @param screenshotName
     * @return
     * @throws IOException
     */
    public static String getScreenShot(String screenshotName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        String source = ts.getScreenshotAs(OutputType.BASE64);
        return source;
    }

        /**
         * getChromeOptions is a private static method that returns a ChromeOptions object with various configurations.
         * If the configurationMap contains a property with key `BROWSERMODE` and its value is `HEADLESS_EXECUTION`, the ChromeOptions object is set to headless execution mode with various configurations:
         *  - `options.setAcceptInsecureCerts(true)`: accept insecure SSL certificates.
         *  - `options.addArguments("--headless")`: run the browser in headless mode.
         *  - `options.addArguments("disable-infobars")`: disable the infobars.
         *  - `options.addArguments("--disable-extensions")`: disable extensions.
         *  - `options.addArguments("--disable-gpu")`: disable GPU.
         *  - `options.addArguments("--disable-dev-shm-usage")`: overcome limited resource problems.
         *  - `options.addArguments("--window-size=1366,786")`: set the window size.
         *  - `options.addArguments("--no-sandbox")`: bypass OS security model.
         *  - `options.setExperimentalOption("prefs", ImmutableMap.of("download.default_directory", excelFilePath, "download.prompt_for_download", false, "download.extensions_to_open", "", "download.directory_upgrade", true, "safebrowsing.enabled", true))`: set the default download directory and other download preferences.
         *  - `options.setExperimentalOption("saveAs", true)`: enable "Save As" option.
         * If the property does not exist or its value is not `HEADLESS_EXECUTION`, the ChromeOptions object is set with the following configurations:
         *  - `options.addArguments("--window-size=1366,786")`: set the window size.
         *  - `options.setExperimentalOption("prefs", ImmutableMap.of("download.default_directory", excelFilePath, "download.prompt_for_download", false, "download.extensions_to_open", "", "download.directory_upgrade", true, "safebrowsing.enabled", true))`: set the default download directory and other download preferences.
         *
         * @return a ChromeOptions object with the appropriate configurations set.
         */
        private static ChromeOptions getChromeOptions() {
            ChromeOptions options = new ChromeOptions();
            if (nonNull(configurationMap.getProperty(BROWSERMODE))
                    && configurationMap.getProperty(BROWSERMODE).equalsIgnoreCase(HEADLESS_EXECUTION)) {
                options.setAcceptInsecureCerts(true);
                options.addArguments("--headless");
                options.addArguments("disable-infobars"); // disabling infobars
                options.addArguments("--disable-extensions"); // disabling extensions
                options.addArguments("--disable-gpu"); // applicable to windows os only
                options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
                options.addArguments("--window-size=1366,786");
                options.addArguments("--no-sandbox"); // Bypass OS security model
                options.setExperimentalOption("prefs",
                        ImmutableMap.of("download.default_directory", excelFilePath, "download.prompt_for_download",
                                false, "download.extensions_to_open", "", "download.directory_upgrade", true,
                                "safebrowsing.enabled", true));
                options.setExperimentalOption("saveAs", true);
                options.addArguments("--remote-allow-origins=*");
            } else {
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                options.setExperimentalOption("prefs", prefs);
                options.addArguments("--disable-notifications");
                options.addArguments("--incognito"); // optional
                options.addArguments("--remote-allow-origins=*");
                options.addArguments("--window-size=1366,786");
                options.setExperimentalOption("prefs",
                        ImmutableMap.of("download.default_directory", excelFilePath, "download.prompt_for_download",
                                false, "download.extensions_to_open", "", "download.directory_upgrade", true,
                                "safebrowsing.enabled", true));
            }
            return options;
        }

        /**
         * This method is to set Firefox option in case of headless mode.
         * 
         * @return
         */
        private static FirefoxOptions getFirefoxOptions() {
            FirefoxOptions options = new FirefoxOptions();
            if (nonNull(configurationMap.getProperty(BROWSERMODE))
                    && configurationMap.getProperty(BROWSERMODE).equalsIgnoreCase(HEADLESS_EXECUTION)) {
                options.setHeadless(true);
            }
            return options;
        }
    
    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
         if (nonNull(configurationMap.getProperty(BROWSERMODE))
                 && configurationMap.getProperty(BROWSERMODE).equalsIgnoreCase(HEADLESS_EXECUTION)) {
             options.setCapability("useChromium", true);
             options.setAcceptInsecureCerts(true);
             options.addArguments("--headless");
             options.addArguments("disable-infobars"); // disabling infobars
             options.addArguments("--disable-extensions"); // disabling extensions
             options.addArguments("--disable-gpu"); // applicable to windows os only
             options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
             options.addArguments("--window-size=1366,786");
             options.addArguments("--no-sandbox"); // Bypass OS security model
         } else {
             options.addArguments("--window-size=1366,786");
         }
         
         return options;
    }
    

    /** This method is to close and Quit the browser
     * 
     */
    public static void tearDown() {
        WebDriver driver = webDriverCache.get();
        if (driver != null) {
            driver.close();
            driver.quit();
        } else {
            LOGGER.error("##afterTest:{}: driver is null .. someone has closed it inappropriately!");
        }
    }

    public static WebDriver getDrivers() {
        WebDriver driver = webDriverCache.get();
        if (driver != null) {
            driver.manage().window().maximize();
        }
        return driver;
    }

    /**
     * This function will take the full page screenshot
     * 
     * @param webdriver
     * @param fileWithPath
     * @return 
     * @throws Exception
     */
    public static String takeFullPageSnapShot(String testcaseName) throws Exception {
        String FilePath = SCREENSHOT_LOCATION + testcaseName + " "+ LocalDate.now() + " " + System.currentTimeMillis() + ".png";
        //take screenshot of the entire page
        Screenshot screenshot=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
        try {
            ImageIO.write(screenshot.getImage(),"PNG",new File(FilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FilePath;
    }
    
    /**
     * getFileName is a public static method that retrieves the name of a file with extension '.tmp' from a directory.
     * The directory is specified by the excelFilePath and the file names are filtered using a FileFilter implementation.
     * The method returns the name of the first file with '.tmp' extension it finds.
     *
     * @return a string representing the name of the first file with '.tmp' extension found in the directory specified by excelFilePath.
     */
      public static String getFileName() {
        String ExtractedFileName = "";
        File directory = new File(excelFilePath);
        File[] filesWithExtension = directory.listFiles(new FileFilter() {
          public boolean accept(File pathname) {
            return pathname.getName().endsWith(".tmp");
          }
        });
        for (File file : filesWithExtension) {
            LOGGER.debug("File with extension '.tmp': " + file.getName());
          ExtractedFileName = file.getName();
        }
        return ExtractedFileName;
      }
      
      
        public static void deleteAllTmpFiles() {
            File folder = new File(excelFilePath);
            Arrays.stream(folder.listFiles()).filter(f -> f.getName().endsWith(".tmp")).forEach(File::delete);
            Arrays.stream(folder.listFiles()).filter(f -> f.getName().endsWith(".xlsx")).forEach(File::delete);

        }
    
      /**
       * updateFileExtension is a public static method that updates the extension of a file.
       * The file to be updated is retrieved using the getFileName method and its current extension is '.tmp'.
       * The file is then renamed to have a new extension specified by the excelFileName parameter with '.xlsx' appended to it.
       * The method returns a message indicating whether the file was renamed successfully or not.
       *
       * @param excelFileName the desired name for the file without the '.xlsx' extension.
       */
        public static void updateFileExtension(String excelFileName) {
            File originalFile = new File(excelFilePath + getFileName());
            File newFile = new File(excelFilePath + excelFileName + ".xlsx");
            boolean success = originalFile.renameTo(newFile);
            if (success) {
                LOGGER.debug("File renamed successfully");
            } else {
                LOGGER.debug("File rename failed");
            }
        }
    }
