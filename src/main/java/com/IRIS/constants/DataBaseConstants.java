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
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_FULL_NAME = "full_name";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_USER_ID = "user_id";
    
    //TODO Test data for database Testing
    public static final String USERID_SUNIL_KUMAR = "Sunil Kumar";
    public static final String USERID_HRISHI = "HRishi";
    public static final String FUNCTIONID_904 = "904";
    
    //TODO Test cases Description
    public static final String DATABASE_TESTING_DESCRIPTION = "This is for verify the database Testing";
    
    //TODO Assertions constants
    public static final String USER_IDS_DOES_NOT_MATCH = "User Ids does not match";
    public static final String USERNAMES_DOES_NOT_MATCH = "Usernames does not match";
    public static final String FULL_NAME_DOES_NOT_MATCH = "Full name does not match";
    public static final String EMAILS_DOES_NOT_MATCH = "Emails does not match";
    public static final String PHONE_NUMBERS_DOES_NOT_MATCH = "Phone numbers does not match";

}
