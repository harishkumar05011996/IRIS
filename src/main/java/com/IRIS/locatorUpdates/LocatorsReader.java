package com.IRIS.locatorUpdates;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

import com.IRIS.locatorUpdates.OREntity.Attribute;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

//TODO ATBBXhywhsg9NwkM6PjTKHkmMt2GCBA242BC

public class LocatorsReader {

    public static Map<String, String> logicalNameType = new ConcurrentHashMap<String, String>();
    public static Map<String, String> logicalNamevalue = new ConcurrentHashMap<String, String>();
    public static Map<String, OREntity> orMap = new ConcurrentHashMap<String, OREntity>();
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LocatorsReader.class);

    public static List<String> types = Arrays.asList("id", "name", "css", "xpath", "linktext");
    private static final String LOGICAL_NAME = "LogicalName";
    private static final String PROPERTY_TYPE = "PropertyType";
    private static final String PROPERTY_VALUE = "PropertyValue";
    private static final String ID = "id";
    private static final String CSS = "css";
    private static final String NAME = "name";
    private static final String TAGNAME = "tagname";
    private static final String LINKTEXT = "linktext";
    private static final String PARTIALLINKTEXT = "partiallinktext";
    private static final String XPATH = "xpath";
    private static final String CLASSNAME = "classname";

    public static void buildCacheFromOrDir(String dirPath) throws ORException, IOException, CsvValidationException {
        File orFolder = new File(dirPath);
        LOGGER.info("Loading OR cache data files from dir: " + orFolder.getPath());
        if (orFolder.exists()) {
            File[] listOfOrFiles = orFolder.listFiles();
            for (File file : listOfOrFiles) {
                LOGGER.info("Going to load OR cache data from file: " + file.getName());
                buildORCacheFromOrFile(file);
            }
        }
    }

    /**
     * Builds OR cache for a single file
     * 
     * @param orFile - file from which locators are to be read.
     * @throws IOException
     * @throws ORException
     * @throws CsvValidationException
     */
    public static void buildORCacheFromOrFile(File orFile) throws IOException, ORException, CsvValidationException {
        String extension = Files.getFileExtension(orFile.getPath());
        buildORCacheFromCsv(orFile);
        if (extension.equalsIgnoreCase("csv")) {
            buildORCacheFromCsv(orFile);
        }
    }

    /**
     * Map for possible/valid locator type.
     */
    private static final ImmutableMap<String, Attribute> lookup = ImmutableMap.<String, Attribute>builder()
            .put(ID, Attribute.ID).put(CSS, Attribute.CSS).put(NAME, Attribute.NAME).put(TAGNAME, Attribute.TAGNAME)
            .put(LINKTEXT, Attribute.LINKTEXT).put(PARTIALLINKTEXT, Attribute.PARTIALLINKTEXT)
            .put(XPATH, Attribute.XPATH).put(CLASSNAME, Attribute.CLASSNAME).build();

    /**
     * Records the locator from CSV file and store them in a common map.
     * 
     * @param orFile - file from which locators are to be read.
     * @throws IOException
     * @throws ORException
     * @throws CsvValidationException
     */
    private static void buildORCacheFromCsv(File orFile) throws IOException, ORException, CsvValidationException {
        CSVReader reader = new CSVReader(new FileReader(orFile));
        String fileName = orFile.getName().split("\\.")[0];
        String[] record = reader.readNext();
        int rowCount = 1;
        while ((record = reader.readNext()) != null) {
            String logicalName = record[0];
            String propertyType = record[1];
            String propertyValue = record[2];
            LOGGER.debug("pageName = {}, locatorName = {}, LocatorType = {}, LocatorValue = {}", fileName, logicalName, propertyType, propertyValue);
            String key;
            key = getOrKey(fileName, logicalName);
            OREntity entity = new OREntity();
            entity.setFileName(fileName);
            entity.setLogicalName(logicalName);
            entity.setAttributeType(lookup.get(propertyType).name());
            entity.setValue(propertyValue);
            orMap.put(key, entity);
        }
        rowCount++;
        reader.close();
    }

    /**
     * Create a unique key with moduleName and locatorName
     * 
     * @param module - pageName
     * @param name   - locator name
     * @return
     */
    public static String getOrKey(String module, String name) {
        return String.format("%s~%s", module, name);
    }

    /**
     * Method that return the OREntity on the basis of moduleName and name
     * 
     * @param module - pageName
     * @param name   - locator name
     * @return
     */
    public static OREntity getEntity(String module, String name) {
        String key = getOrKey(module, name);
        if (orMap.containsKey(key)) {
            OREntity entity = orMap.get(key);
            return entity;
        } else {
            throw new NoSuchElementException(String
                    .format("Element on sheet: %s with name: %s is not present in OR repository file", module, name));
        }
    }

    /** This method is to get and return the web element from the locators.
     * @param sheetName
     * @param logicalName
     * @param driver
     * @return
     */
    public static WebElement getWebElement(String sheetName, String logicalName, WebDriver driver) {
        LOGGER.debug("[returnWebElement] Returning element on sheet:{}, with name:{} ", sheetName, logicalName);
        OREntity orEntity = getEntity(sheetName, logicalName);
        String propertyType = orEntity.getAttributeType();
        String propertyValue = orEntity.getValue();
        WebElement element = null;
        String exception = null;
        try {
            By locator = getLocator(propertyType, propertyValue);
            element = driver.findElement(locator);
        } catch (WebDriverException e) {
            e.addInfo("sheetName", sheetName);
            e.addInfo("logicalName", logicalName);
            exception = e.toString();
            throw e;
        } finally {
        }
        return element;
    }

    /** This method is to get and return the web elements from the locators.
     * @param sheetName
     * @param logicalName
     * @param driver
     * @return
     */
    public static List<WebElement> getWebElements(String sheetName, String logicalName, WebDriver driver) {
        LOGGER.debug("[returnWebElement] Returning element on sheet:{}, with name:{} ", sheetName, logicalName);
        OREntity orEntity = getEntity(sheetName, logicalName);
        String propertyType = orEntity.getAttributeType();
        String propertyValue = orEntity.getValue();
        List<WebElement> element = null;
        String exception = null;
        try {
            By locator = getLocator(propertyType, propertyValue);
            element = driver.findElements(locator);
        } catch (WebDriverException e) {
            e.addInfo("sheetName", sheetName);
            e.addInfo("logicalName", logicalName);
            exception = e.toString();
            throw e;
        } finally {
        }
        return element;
    }

    /** This Method will return the type of the locator.
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
