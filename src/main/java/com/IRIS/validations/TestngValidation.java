package com.IRIS.validations;

import java.math.BigDecimal;
import java.util.List;

import org.testng.asserts.SoftAssert;

import com.IRIS.testcases.BeforeAfterSuite;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class TestngValidation {
    public ExtentTest logger;

    public void assertEquals(SoftAssert assertion, String actual, String expected, String ValidationMessage) {
            assertion.assertEquals(actual, expected, ValidationMessage);
            loggerInformationAssertEquals(actual, expected, ValidationMessage);
    }
    public void assertEquals(SoftAssert assertion, int actual, int expected, String ValidationMessage) {
        assertion.assertEquals(actual, expected, ValidationMessage);
        loggerInformationAssertEquals(actual, expected, ValidationMessage);
}
    public void assertEquals(SoftAssert assertion, BigDecimal actual, BigDecimal expected, String ValidationMessage) {
        assertion.assertEquals(actual, expected, ValidationMessage);
        loggerInformationAssertEquals(actual, expected, ValidationMessage);
}

    public void assertEquals(SoftAssert assertion, List<String> actual, List<String> expected, String ValidationMessage) {
            assertion.assertEquals(actual, expected, ValidationMessage);
            loggerInformationAssertEquals(actual, expected, ValidationMessage);
    }
    
    public void assertEquals(SoftAssert assertion, boolean actual, boolean expected, String ValidationMessage) {
        assertion.assertEquals(actual, expected, ValidationMessage);
//        loggerInformationAssertEquals(actual, expected, ValidationMessage);
}
    
    
    public void validationsStart() {
        logger = BeforeAfterSuite.startReport();
        logger.log(Status.INFO, MarkupHelper.createLabel("VALIDATION POINTS DETAILS FOR CURRENT TEST CASE", ExtentColor.PURPLE));
    }
    
    public void dataBaseValidationsStart() {
        logger = BeforeAfterSuite.startReport();
        logger.log(Status.INFO, MarkupHelper.createLabel("DATABASE VALIDATION POINTS DETAILS FOR CURRENT TEST CASE", ExtentColor.GREEN));
    }

    public void loggerInformationAssertEquals(String actual, String expected, String validationMessage) {
        logger = BeforeAfterSuite.startReport();
        logger.log(Status.INFO, MarkupHelper.createLabel("=>> " + validationMessage + " Actual [" + actual + "]" + " "
                + " Expected [" + expected + "]", ExtentColor.ORANGE));
    }
    
    public void loggerInformationAssertEquals(BigDecimal actual, BigDecimal expected, String validationMessage) {
        logger = BeforeAfterSuite.startReport();
        logger.log(Status.INFO, MarkupHelper.createLabel("=>> " + validationMessage + " Actual [" + actual + "]" + " "
                + " Expected [" + expected + "]", ExtentColor.ORANGE));
    }
    
    public void loggerInformationAssertEquals(int actual, int expected, String validationMessage) {
        logger = BeforeAfterSuite.startReport();
        logger.log(Status.INFO, MarkupHelper.createLabel("=>> " + validationMessage + " Actual [" + actual + "]" + " "
                + " Expected [" + expected + "]", ExtentColor.ORANGE));
    }

    public void loggerInformationAssertEquals(List<String> actual, List<String> expected, String validationMessage) {
        logger = BeforeAfterSuite.startReport();
        logger.log(Status.INFO, MarkupHelper.createLabel("=>> " + validationMessage.replace("not", "") + " Actual " + actual + "" + " "
                + " Expected " + expected, ExtentColor.ORANGE));
    }

    public void loggerInformationAssertEqualsFalse(List<String> actual, List<String> expected,
            String validationMessage) {
        logger = BeforeAfterSuite.startReport();
        logger.log(Status.INFO, MarkupHelper.createLabel("=>> " + validationMessage + " Actual [" + actual + "]" + " " + " Expected [" + expected + "]", ExtentColor.ORANGE));
    }

    public void loggerInformationAssertEqualsFalse(String actual, String expected, String validationMessage) {
        logger = BeforeAfterSuite.startReport();
        logger.log(Status.INFO, MarkupHelper.createLabel("=>> " + validationMessage + " Actual [" + actual + "]" + " " + " Expected [" + expected + "]", ExtentColor.ORANGE));
    }

    public void loggerInformationAssertTrue(boolean condition, String validationMessage) {
        logger = BeforeAfterSuite.startReport();
        logger.log(Status.INFO, MarkupHelper.createLabel("=>> " + validationMessage.replace("not", "") + " Actual [" + condition + "]" + " "
                + " Expected [" + true + "]", ExtentColor.ORANGE));
    }

    public void assertTrue(SoftAssert assertion, boolean condition, String ValidationMessage) {
        assertion.assertTrue(condition, ValidationMessage);
        loggerInformationAssertTrue(condition, ValidationMessage);
    }

}
