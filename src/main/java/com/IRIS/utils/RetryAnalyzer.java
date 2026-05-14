package com.IRIS.utils;

import static com.IRIS.testcases.BeforeAfterSuite.configurationMap;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;


public class RetryAnalyzer implements IRetryAnalyzer {

    private static Logger LOGGER = LoggerFactory.getLogger(RetryAnalyzer.class);
    

    private static final String COMMA = ",";
    private static final String MAX_RETRY_COUNT = "maxRetryCount";
    private static final String REASON_OF_FAILURE = "reasonOfFailure";

    private static final String TIMEOUT_EXCEPTION = "org.openqa.selenium.TimeoutException";
    private static final String NO_SUCH_ELEMENT_EXCEPTION = "org.openqa.selenium.NoSuchElementException";

    private int retryCount = 0;
    private static int maxRetryCount = 0;
    private static Set<String> reasonOfFailure = new HashSet<>(Arrays.asList(TIMEOUT_EXCEPTION,
            NO_SUCH_ELEMENT_EXCEPTION));

    static {
        if (StringUtils.isNotEmpty(configurationMap.getProperty(MAX_RETRY_COUNT))) {
            maxRetryCount = Integer.parseInt(configurationMap.getProperty(MAX_RETRY_COUNT));
        }

        if (StringUtils.isNotEmpty(configurationMap.getProperty(REASON_OF_FAILURE))) {
            reasonOfFailure = new HashSet<>(Arrays.asList(configurationMap.getProperty(REASON_OF_FAILURE).split(COMMA)));
        }
        LOGGER.info(String.format(
                "Initialized maxRetryCount = %s and reasonOfFailure = %s for retrying the failed tests", maxRetryCount,
                reasonOfFailure));
    }

    @Override
    public boolean retry(ITestResult result) {
        try {
            if (reasonOfFailure.contains(result.getThrowable().getClass().getName())) {
                if (retryCount < maxRetryCount) {
                    LOGGER.debug("Retrying test " + result.getName() + " with status "
                            + getResultStatusName(result.getStatus()) + " for the " + (retryCount + 1) + " time(s).");

                    retryCount++;
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            LOGGER.error("Exception while retrying execution for failed testcases: " + e);
            return false;
        }
    }

    public String getResultStatusName(int status) {
        Status resultName = null;
        if (status == 1)
            resultName = Status.SUCCESS;
        if (status == 2)
            resultName = Status.FAILURE;
        if (status == 3)
            resultName = Status.SKIP;
        return resultName.toString();
    }

    public enum Status {
        SUCCESS, FAILURE, SKIP
    }
}
