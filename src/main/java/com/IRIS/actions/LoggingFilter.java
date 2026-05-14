package com.IRIS.actions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class LoggingFilter implements ClientRequestFilter, ClientResponseFilter {
    static final Logger logger = Logger.getLogger(LoggingFilter.class.getName());
    private static final String REQUEST_FILENAME_SUFFIX = "_Request_Payload.txt";
    private static final String RESPONSE_FILENAME_SUFFIX = "_Response.txt";
    private static final String MESSAGE_FILENAME_SUFFIX = "_Message.txt";

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        String requestEntity ="";
        if(requestContext.getEntity() != null) {
            requestEntity = requestContext.getEntity().toString();
        }
        log(requestContext.getMethod(), requestContext.getUri().toString(), requestEntity);
        
    }

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
    }

    /** Logs request. */
    private static void log(final String method, final String uri, final String requestEntity) {
        Date now = new Date();
        String event = String.format("%tT:%tL %s %s %s", now, now, method, uri, requestEntity);
        logger.log(Level.INFO, event);
    }

    /** Logs response. */
    public static void log(final String method, final String uri, final int status, final String responseEntity) {
        Date now = new Date();
        String event = String.format("%tT:%tL %s %s %d %s", now, now, method, uri, status, responseEntity);
        logger.log(Level.INFO, event);
    }
    
    public static void logRequestToFile(String path, String request, String logFilePath) {
        String requestFile = path + REQUEST_FILENAME_SUFFIX;
        logToFile(requestFile, request, logFilePath);
    }

    public static void logResponseToFile(String path, String response, String logFilePath) {
        String responseFile = path + RESPONSE_FILENAME_SUFFIX;
        logToFile(responseFile, response, logFilePath);
    }
    
    public static void logMessageToFile(String path, String response, String logFilePath) {
        String responseFile = path + MESSAGE_FILENAME_SUFFIX;
        logToFile(responseFile, response, logFilePath);
    }
    
    private static void logToFile(String filePath, String content, String logFilePath) {
        try {
            File screenShotFile = new File(logFilePath);
            screenShotFile.mkdirs();
            logger.log(Level.INFO, "===== Directory is created ==== ");
            System.out.println("===== Directory is created ==== ");
            FileWriter fileWriter = new FileWriter(filePath);
            logger.log(Level.SEVERE, content);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(content);
            logger.log(Level.INFO, "===== Writing to file ==== ");
            System.out.println("===== Writing to file ==== ");
            printWriter.close();
            logger.log(Level.INFO, "===== closing file ==== ");
            System.out.println("===== closing file ==== ");
            /*FileWriter fw = new FileWriter(filePath);
            logger.log(Level.SEVERE, content);
            fw.write(content);
            fw.close();*/
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

}