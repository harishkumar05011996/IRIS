package com.IRIS.testcases;

import static com.IRIS.constants.APIConstants.AMR_01_RETRIVE_DESCRIPTION;
import static com.IRIS.constants.APIConstants.AMR_01_RETRIVE_REQUESTBODY;
import static com.IRIS.constants.APIConstants.AMR_01_RETRIVE_URL;
import static com.IRIS.constants.APIConstants.APPLICATIO_JSON;
import static com.IRIS.constants.APIConstants.AUTHORIZATION;
import static com.IRIS.constants.APIConstants.BEARER;
import static com.IRIS.constants.APIConstants.CONTENT_TYPE;
import static com.IRIS.constants.APIConstants.DATA;
import static com.IRIS.constants.APIConstants.DEALERCD;
import static com.IRIS.constants.APIConstants.DEALERCLASS;
import static com.IRIS.constants.APIConstants.DEALERCODE_IS_MATCHED;
import static com.IRIS.constants.APIConstants.DEALERNM;
import static com.IRIS.constants.APIConstants.DEALER_CLASS_IS_MATCHED;
import static com.IRIS.constants.APIConstants.DEALER_NAME_IS_MATCHED;
import static com.IRIS.constants.APIConstants.DEALER_NAME_SRI_MOTORS;
import static com.IRIS.constants.APIConstants.DEALER_PASSWORD;
import static com.IRIS.constants.APIConstants.DEALER_USERNAME;
import static com.IRIS.constants.APIConstants.INVALID_DEALER_CATEGORY;
import static com.IRIS.constants.APIConstants.PYMIDOL_API_LOGIN_URL;
import static com.IRIS.constants.APIConstants.RESPONSEMESSAGE;
import static com.IRIS.constants.APIConstants.RESPONSE_MESSAGE_IS_MATCHED;
import static com.IRIS.constants.APIConstants.RESULTLIST;
import static com.IRIS.constants.APIConstants.SOUTH;
import static com.IRIS.constants.APIConstants.STATUS_CODE_IS_MATCHED;
import static com.IRIS.constants.APIConstants.SUCCESS;
import static com.IRIS.constants.APIConstants.TOKEN;
import static com.IRIS.constants.APIConstants.VALID_DEALER_CATEGORY;
import static com.IRIS.constants.APIConstants.ZONE;
import static com.IRIS.constants.APIConstants.ZONE_IS_MATCHED;
import static com.IRIS.constants.constants.CHROME;
import static com.IRIS.constants.constants.HARISH;
import static com.IRIS.constants.constants.SMOKE;
import static com.IRIS.constants.constants.ZERO;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.IRIS.actions.CreateJiraDefact;
import com.IRIS.actions.RestClientFacade;
import com.IRIS.utils.RetryAnalyzer;
import com.IRIS.validations.TestngValidation;
import com.aventstack.extentreports.ExtentTest;

public class APITesting {
    private WebDriver driver;
    private static Client client;
    public ExtentTest logger;
    private BeforeAfterSuite beforeAfterSuite;
    private TestngValidation testngValidation;
    String responseInString = null;


    @BeforeClass
    @Parameters("browser")
    public void setup(String browser) throws MalformedURLException {
        ClientConfig config = new ClientConfig();
        config.property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION, true);
        client = ClientBuilder.newClient(config);
        beforeAfterSuite = new BeforeAfterSuite();
        testngValidation = new TestngValidation();
        driver = beforeAfterSuite.getDriver(browser);
    }

    @Test(description = "get_retrive_details_amr01", retryAnalyzer = RetryAnalyzer.class)
    public void get_retrive_details_amr01() {
        Map<String, String> headers = new HashMap<>();
        try {
            String testcasename = new Object() {}.getClass().getEnclosingMethod().getName();
            logger = BeforeAfterSuite.startReport(testcasename, AMR_01_RETRIVE_DESCRIPTION, HARISH, SMOKE, CHROME);
            String bearerToken = genearteBearerToken(DEALER_USERNAME, DEALER_PASSWORD);
            SoftAssert assertion = new SoftAssert();
            headers.put(CONTENT_TYPE, APPLICATIO_JSON);
            headers.put(AUTHORIZATION, bearerToken);
            generateAPILogs(headers);
            Response response = RestClientFacade.postResponse(client, AMR_01_RETRIVE_URL, AMR_01_RETRIVE_REQUESTBODY , headers);
            responseInString = response.readEntity(String.class);
            System.out.println("responseInString"+responseInString);
            JSONObject test = new JSONObject(responseInString);
            testngValidation.validationsStart();
            testngValidation.assertEquals(assertion, response.getStatus(), 200, STATUS_CODE_IS_MATCHED);
            testngValidation.assertEquals(assertion, test.getString(RESPONSEMESSAGE), SUCCESS, RESPONSE_MESSAGE_IS_MATCHED);
            testngValidation.assertEquals(assertion, test.getJSONObject(DATA).getJSONArray(RESULTLIST).getJSONObject(ZERO).getString(ZONE), SOUTH, ZONE_IS_MATCHED);
            testngValidation.assertEquals(assertion, test.getJSONObject(DATA).getJSONArray(RESULTLIST).getJSONObject(ZERO).getString(DEALERCD), DEALER_USERNAME, DEALERCODE_IS_MATCHED);
            testngValidation.assertEquals(assertion, test.getJSONObject(DATA).getJSONArray(RESULTLIST).getJSONObject(ZERO).getString(DEALERNM), DEALER_NAME_SRI_MOTORS, DEALER_NAME_IS_MATCHED);
            testngValidation.assertEquals(assertion, test.getJSONObject(DATA).getJSONArray(RESULTLIST).getJSONObject(ZERO).getString(DEALERCLASS), INVALID_DEALER_CATEGORY, DEALER_CLASS_IS_MATCHED);
            assertion.assertAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test(description = "get_retrive_details_amr02", retryAnalyzer = RetryAnalyzer.class)
    public void get_retrive_details_amr02() {
        Map<String, String> headers = new HashMap<>();
        try {
            String testcasename = new Object() {}.getClass().getEnclosingMethod().getName();
            logger = BeforeAfterSuite.startReport(testcasename, AMR_01_RETRIVE_DESCRIPTION, HARISH, SMOKE, CHROME);
            String bearerToken = genearteBearerToken(DEALER_USERNAME, DEALER_PASSWORD);
            SoftAssert assertion = new SoftAssert();
            headers.put(CONTENT_TYPE, APPLICATIO_JSON);
            headers.put(AUTHORIZATION, bearerToken);
            generateAPILogs(headers);
            Response response = RestClientFacade.postResponse(client, AMR_01_RETRIVE_URL, AMR_01_RETRIVE_REQUESTBODY , headers);
            responseInString = response.readEntity(String.class);
            JSONObject test = new JSONObject(responseInString);
            testngValidation.validationsStart();
            testngValidation.assertEquals(assertion, response.getStatus(), 200, STATUS_CODE_IS_MATCHED);
            testngValidation.assertEquals(assertion, test.getString(RESPONSEMESSAGE), SUCCESS, RESPONSE_MESSAGE_IS_MATCHED);
            testngValidation.assertEquals(assertion, test.getJSONObject(DATA).getJSONArray(RESULTLIST).getJSONObject(ZERO).getString(ZONE), SOUTH, ZONE_IS_MATCHED);
            testngValidation.assertEquals(assertion, test.getJSONObject(DATA).getJSONArray(RESULTLIST).getJSONObject(ZERO).getString(DEALERCD), DEALER_USERNAME, DEALERCODE_IS_MATCHED);
            testngValidation.assertEquals(assertion, test.getJSONObject(DATA).getJSONArray(RESULTLIST).getJSONObject(ZERO).getString(DEALERNM), DEALER_NAME_SRI_MOTORS, DEALER_NAME_IS_MATCHED);
            testngValidation.assertEquals(assertion, test.getJSONObject(DATA).getJSONArray(RESULTLIST).getJSONObject(ZERO).getString(DEALERCLASS), VALID_DEALER_CATEGORY, DEALER_CLASS_IS_MATCHED);
            assertion.assertAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    private String genearteBearerToken(String userName, String password) {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, APPLICATIO_JSON);
        String RequestBody = "{\"sysOwnerCd\":\"IYM\",\"userId\":\"" + userName + "\",\"password\":\"" + password + "\",\"pymidolFlag\":\"1\"}";
        Response response = RestClientFacade.postResponse(client, PYMIDOL_API_LOGIN_URL,  RequestBody, headers);
        String data = response.readEntity(String.class);
        JSONObject test = new JSONObject(data);
        String bearerToken = BEARER + test.get(TOKEN);
        
        return bearerToken;
        
    }

    private void generateAPILogs(Map<String, String> headers) {
        logger.info("Enable Rest client");
        logger.info("Set headres as Accept & Value " +headers.get(CONTENT_TYPE));
        logger.info("Hit the required API call");
    }

    @AfterMethod
    public void afterMethod(ITestContext context, ITestResult results) throws Exception {
        beforeAfterSuite.getReportResult(results, logger);
        CreateJiraDefact.CreateJiraTickets(context, results, logger);
        RestClientFacade.printAPIResponse(logger, responseInString);
    }
    

    @AfterClass
    public void afterClass() {
        BeforeAfterSuite.tearDown();
    }
    
}
