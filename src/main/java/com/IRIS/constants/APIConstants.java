package com.IRIS.constants;

import static com.IRIS.testcases.BeforeAfterSuite.configurationMap;

public class APIConstants {
	
    public static final String PYMIDOL_API_LOGIN_URL = configurationMap.getProperty("pymidolAPILoginURL");

	
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String AUTHORIZATION = "Authorization";
	public static final String APPLICATIO_JSON = "Applicatio/json";
	public static final String BEARER = "Bearer ";
    public static final String TOKEN = "token";
    public static final String DEALER_USERNAME = "811.00";
    public static final String DEALER_PASSWORD = "password";
    
    //TODO API fields names
    public static final String RESPONSEMESSAGE = "responseMessage";
    public static final String DATA = "data";
    public static final String RESULTLIST = "resultList";
    public static final String ZONE = "zone";
    public static final String DEALERCD = "dealerCd";
    public static final String DEALERNM = "dealerNm";
    public static final String DEALERCLASS = "dealerClass";
    public static final String DEALER_NAME_SRI_MOTORS = "SRI MOTORS";
    public static final String INVALID_DEALER_CATEGORY = "AB";
    public static final String VALID_DEALER_CATEGORY = "A";
    
    
    // API Test Data
    public static final String SUCCESS = "Success";
    public static final String SOUTH = "South";
    public static final String JIRA_URL = "https://harishtesting.atlassian.net/rest/api/2/issue/";
    
    //TODO
    public static final String AMR_01_RETRIVE_DESCRIPTION = "This is to verify Retrive API of AMR01";
    
    //TODO URL constants
    public static final String AMR_01_RETRIVE_REQUESTBODY = "{\"user\":{\"userId\":\"811.00\",\"userName\":\"811.00\",\"companyCode\":\"IYM\",\"loginUserType\":\"111\",\"overseasDealerFlag\":false,\"pymidolDealerCd\":\"811.00\",\"propacDealerCd\":\"81100\",\"levelId\":0,\"dealerNm\":\"SRI MOTORS\",\"departmentId\":5,\"role\":\"0\",\"dataSecurityModel\":{\"dealerList\":null}},\"orderNoInt\":null,\"zone\":\" \",\"stateCd\":null,\"dealerCd\":null,\"pagesize\":50,\"pagenum\":0}";
    public static final String AMR_01_RETRIVE_URL = "http://10.167.10.101:8989/spare-parts/spare-parts/amr0101/retrieve";
    
    
    //Assertions Messages
    public static final String STATUS_CODE_IS_MATCHED = "Status code is matched";
    public static final String RESPONSE_MESSAGE_IS_MATCHED = "response Message is matched";
    public static final String ZONE_IS_MATCHED = "Zone is matched";
    public static final String DEALERCODE_IS_MATCHED = "dealerCode is matched";
    public static final String DEALER_NAME_IS_MATCHED = "Dealer Name is matched";
    public static final String DEALER_CLASS_IS_MATCHED = "Dealer class is matched";



}
