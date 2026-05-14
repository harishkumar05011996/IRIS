package com.IRIS.constants;

import static com.IRIS.testcases.BeforeAfterSuite.configurationMap;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class constants {
	
	public static  Map<String, Map<String, String>> rowMap = new HashMap<>();
    
    public static final String APP_URL = configurationMap.getProperty("ApplicationURL");
    public static final String API_BASE_URL = configurationMap.getProperty("APIBaseUrl");
    public static final String RUN_MODE = configurationMap.getProperty("runmode");
    public static final String HUB_URL = configurationMap.getProperty("hubURl");
    public static final String VALUE = "Value";
    public static final String CHROME = "Chrome";
    public static final String FIREFOX = "Firefox";
    public static final String EDGE = "Edge";
    public static final String DRIVER = "driver";
    public static final String CHROME_DRIVER_KEY = "webdriver.chrome.driver";
    public static final String FIREFOX_DRIVER_KEY = "webdriver.gecko.driver";
    public static final String EDGE_DRIVER_KEY = "webdriver.edge.driver";
    public static final String BROWSERMODE = "browserMode";
    public static final String HEADLESS_EXECUTION = "HEADLESS_EXECUTION";
    public static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
    
    
    //Report Details
    public static final String HARISH = "Harish";
    public static final String SMOKE = "Smoke";
    public static final String REGRESSION = "Regression";
    public static final String REMOTE = "remote";
    
    //TODO Regrex details
    public static final String SPECIAL_CHARACTERS  = "[^A-Za-z0-9]";
    public static final String EMPTY_SPACE  = "";
    
    //TODO Number constants
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;
    public static final int ZERO = 0;
    
  //TODO Storage Locations
    public static String EXTENTED_REPORT_LOCATION = String.format("%s%s%s%s%s%s%s%s%s%s%s", System.getProperty("user.dir"), File.separatorChar,"src",File.separatorChar,"main",File.separatorChar,"resources",File.separatorChar,"extentedReports",File.separatorChar,"IRISExtentedReport");
    public static String SCREENSHOT_LOCATION = String.format("%s%s%s%s%s%s%s%s%s%s", System.getProperty("user.dir"), File.separatorChar,"src",File.separatorChar,"main",File.separatorChar,"resources",File.separatorChar,"ScreenShots",File.separatorChar);
    public static String CHROME_DRIVER_LOCATION = String.format("%s%s%s%s%s%s%s%s%s%s%s", System.getProperty("user.dir"), File.separatorChar,"src",File.separatorChar,"main",File.separatorChar,"resources",File.separatorChar,"driver",File.separatorChar, "chromedriver");
    public static String CHROME_DRIVER_LOCATION_LOCAL = String.format("%s%s%s%s%s%s%s%s%s%s%s", System.getProperty("user.dir"), File.separatorChar,"src",File.separatorChar,"main",File.separatorChar,"resources",File.separatorChar,"driver",File.separatorChar, "chromedriver.exe");
    public static String FIREFOX_DRIVER_LOCATION = String.format("%s%s%s%s%s%s%s%s%s%s%s", System.getProperty("user.dir"), File.separatorChar,"src",File.separatorChar,"main",File.separatorChar,"resources",File.separatorChar,"driver",File.separatorChar, "geckodriver.exe");
    public static String EDGE_DRIVER_LOCATION = String.format("%s%s%s%s%s%s%s%s%s%s%s", System.getProperty("user.dir"), File.separatorChar,"src",File.separatorChar,"main",File.separatorChar,"resources",File.separatorChar,"driver",File.separatorChar, "msedgedriver");
    public static String EDGE_DRIVER_LOCATION_LOCAL = String.format("%s%s%s%s%s%s%s%s%s%s%s", System.getProperty("user.dir"), File.separatorChar,"src",File.separatorChar,"main",File.separatorChar,"resources",File.separatorChar,"driver",File.separatorChar, "msedgedriver.exe");
    public static String OR_FOLDER_LOCATION = String.format("%s%s%s%s%s%s%s%s%s%s", System.getProperty("user.dir"), File.separatorChar,"src",File.separatorChar,"main",File.separatorChar,"resources",File.separatorChar,"csvFiles",File.separatorChar);
    public static String TEST_DATA_EXCEL_PATH = String.format("%s%s%s%s%s%s%s%s%s%s%s", System.getProperty("user.dir"), File.separatorChar,"src",File.separatorChar,"main",File.separatorChar,"resources",File.separatorChar,"TestData",File.separatorChar,"TestDataExcel.xlsx");
}
