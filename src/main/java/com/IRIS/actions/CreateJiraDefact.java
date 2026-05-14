package com.IRIS.actions;

import static com.IRIS.testcases.BeforeAfterSuite.configurationMap;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import com.IRIS.actions.RestClientFacade;
import com.aventstack.extentreports.ExtentTest;

public class CreateJiraDefact  {

    private static final String COMMA = ",";
    private static final String REASON_OF_FAILURE = "reasonOfFailure";

    private static final String TIMEOUT_EXCEPTION = "org.openqa.selenium.TimeoutException";
    private static final String NO_SUCH_ELEMENT_EXCEPTION = "org.openqa.selenium.NoSuchElementException";

    private static Set<String> reasonOfFailure = new HashSet<>(
            Arrays.asList(TIMEOUT_EXCEPTION, NO_SUCH_ELEMENT_EXCEPTION));

    public static void CreateJiraTickets(ITestContext context, ITestResult result, ExtentTest logger) {
        if (StringUtils.isNotEmpty(configurationMap.getProperty(REASON_OF_FAILURE))) {
            reasonOfFailure = new HashSet<>(
                    Arrays.asList(configurationMap.getProperty(REASON_OF_FAILURE).split(COMMA)));
        }
        String summary = result.getThrowable().getMessage().replaceAll("\\n", " ");
        String description = result.getName() + " " + result.getThrowable().getMessage().replaceAll("\\n", " ");
        if (!reasonOfFailure.contains(result.getThrowable().getClass().getName())) {
            RestClientFacade.CreateJiraDefect(summary.replaceAll(" 	", ""), description.replaceAll(" 	", ""), logger);
        }
    }

}