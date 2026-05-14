package com.IRIS.utils;

import java.util.Base64;

public class EncrptionAndDecryption {

    public static String passwordEncription(String Password) {
        byte[] encodedBytes = Base64.getEncoder().encode(Password.getBytes());
        
        return new String(encodedBytes);

    }

    public static String passworddecription(String encriptedPassword) {
        byte[] decodeBytes = Base64.getDecoder().decode(encriptedPassword.getBytes());
        
        return new String(decodeBytes);


    }
}