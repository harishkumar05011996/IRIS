package com.IRIS.constants;

import static com.IRIS.testcases.BeforeAfterSuite.dbQueryProperties;


public class DataBaseConstants {
	
	//TODO Database Query sections
	public static final String DATABASE =  "dataBase";
	
	//TODO locators
	public static final String FUNCTION_INPUT_SEARCH =  "function_input_search";
	public static final String BTN_SERACH_FUNCTION =  "btn_serach_function";
	public static final String BTN_DOWNLOAD =  "btn_download";
	
	
    public static final String FUNCTION_ID_QUERY =  dbQueryProperties.getProperty("Function_id_Query");
    public static final String PPR01_MODULE_QUERY =  dbQueryProperties.getProperty("PPR01_module_query");
    
    
    //TODO column name Section
    public static final String USER_ID = "user_id";
    public static final String FUNCTION_ID = "function_id";
    
    //TODO Test data for database Testing
    public static final String USERID_SUNIL_KUMAR = "Sunil Kumar";
    public static final String USERID_HRISHI = "HRishi";
    public static final String FUNCTIONID_904 = "904";
    
    //TODO Test cases Description
    public static final String DATABASE_TESTING_DESCRIPTION = "This is for verify the database Testing";
    
    //TODO Assertions constants
    public static final String FIRST_USER_ID_IS_MATCHED = "first User ID is matched";
    public static final String SECOND_USER_ID_IS_MATCHED = "Second User ID is not matched";
    public static final String FIRST_FUNCTION_ID_IS_MATCHED = "First Function ID is matched";
    public static final String SECOND_FUNCTION_ID_IS_MATCHED = "Second Function ID is matched";

}
