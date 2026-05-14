package com.IRIS.actions;

import static com.IRIS.constants.APIConstants.*;

import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.json.JSONObject;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class RestClientFacade {

    private RestClientFacade() {
    }

    public static Client getClient() {
        return ClientBuilder.newClient().register(LoggingFilter.class);
    }

    public static String encode(String str1, String str2) {
        return new String(Base64.getEncoder().encode((str1 + ":" + str2).getBytes()));
    }

    public static Client getProxyClient(String proxyIpPort) {
        ClientConfig config = new ClientConfig();
        config.property(ClientProperties.PROXY_URI, proxyIpPort);
        config.connectorProvider(new ApacheConnectorProvider());
        return ClientBuilder.newClient(config).register(LoggingFilter.class);
    }

    public static Client getBasicAuthenticatedClient(String username, String password) {
        return ClientBuilder.newClient().register(LoggingFilter.class)
                .register(new BasicAuthenticator(username, password));
    }

    public static JSONObject post(Client client, String url, JSONObject requestObject) {
        return post(client, url, requestObject.toString());
    }

    public static JSONObject post(Client client, String url, String requestObject, Map<String, String> headers) {
        MultivaluedMap<String, Object> headersMap = new MultivaluedHashMap<>(headers);
        Response res = client.target(url).request(MediaType.APPLICATION_JSON).headers(headersMap)
                .post(Entity.json(requestObject));
        JSONObject responseEntity = setResponse("POST", url, res);
        return responseEntity;
    }

    public static JSONObject post(Client client, String url, String requestObject, Map<String, String> headers,
            long time) {
        MultivaluedMap<String, Object> headersMap = new MultivaluedHashMap<>(headers);
        Response res = client.target(url).request(MediaType.APPLICATION_JSON).headers(headersMap)
                .post(Entity.xml(requestObject));
        JSONObject responseEntity = setResponse("POST", url, res);
        return responseEntity;
    }

    public static JSONObject post(Client client, String url, Map<String, String> headers, String grantType,
            String clientCredentials) {
        MultivaluedMap<String, Object> headersMap = new MultivaluedHashMap<>(headers);
        Form form = new Form(grantType, clientCredentials);
        Response res = client.target(url).request(MediaType.APPLICATION_FORM_URLENCODED).headers(headersMap)
                .post(Entity.form(form));
        JSONObject responseEntity = setResponse("POST", url, res);
        return responseEntity;
    };

    public static JSONObject post(Client client, String url, String requestObject, Map<String, String> headers,
            String logPath, String logFilePath) {
        LoggingFilter.logRequestToFile(logPath, requestObject, logFilePath);
        MultivaluedMap<String, Object> headersMap = new MultivaluedHashMap<>(headers);
        Response res = client.target(url).request(MediaType.APPLICATION_JSON).headers(headersMap)
                .post(Entity.xml(requestObject));
        JSONObject responseEntity = setResponse("POST", url, res, logPath, logFilePath);
        return responseEntity;
    }

    public static JSONObject post(Client client, String url, Map<String, String> cookies, String requestObject) {
        Invocation.Builder invocationBuilder = getClient().target(url).request(MediaType.APPLICATION_JSON);
        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            invocationBuilder.cookie(entry.getKey(), entry.getValue());
        }

        Response res = invocationBuilder.post(Entity.json(requestObject));
        JSONObject responseEntity = setResponse("POST", url, res);

        return responseEntity;
    }

    public static JSONObject post(Client client, String url, String requestObject) {
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        Response res = client.target(url).request(MediaType.APPLICATION_JSON).post(Entity.json(requestObject));
        JSONObject responseEntity = setResponse("POST", url, res);
        return responseEntity;
    }

    public static Response put(Client client, String url, JSONObject requestObject) {
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        Response res = client.target(url).request(MediaType.APPLICATION_JSON)
                .put(Entity.json(requestObject.toString()));
        return res;
    }

    public static Response put(Client client, String url, String requestObject, Map<String, String> headers) {
        MultivaluedMap<String, Object> headersMap = new MultivaluedHashMap<>(headers);
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        Response res = client.target(url).request(MediaType.APPLICATION_JSON).headers(headersMap)
                .put(Entity.json(requestObject.toString()));
        return res;
    }

    public static JSONObject get(Client client, String url) {
        Response res = client.target(url).request(MediaType.APPLICATION_JSON).get();
        JSONObject responseEntity = setResponse("GET", url, res);
        return responseEntity;
    }

    public static JSONObject get(Client client, String url, Map<String, String> headers) {
        MultivaluedMap<String, Object> headersMap = new MultivaluedHashMap<>(headers);
        Response res = client.target(url).request(MediaType.APPLICATION_JSON).headers(headersMap).get();
        JSONObject responseEntity = setResponse("GET", url, res);
        return responseEntity;
    }

    public static JSONObject get(Client client, String url, Map<String, String> headers, String logPath,
            String logFilePath) {
        MultivaluedMap<String, Object> headersMap = new MultivaluedHashMap<>(headers);
        Response res = client.target(url).request(MediaType.APPLICATION_JSON).headers(headersMap).get();
        JSONObject responseEntity = setResponse("GET", url, res, logPath, logFilePath);
        return responseEntity;
    }

    public static JSONObject delete(Client client, String url) {
        Response res = client.target(url).request(MediaType.APPLICATION_JSON).delete();
        JSONObject responseEntity = setResponse("DELETE", url, res);
        return responseEntity;
    }

    public static Response delete(Client client, String url, Map<String, String> headers) {
        MultivaluedMap<String, Object> headersMap = new MultivaluedHashMap<>(headers);
        Response res = client.target(url).request(MediaType.APPLICATION_JSON).headers(headersMap).delete();
        return res;
    }

    public static JSONObject setResponse(String method, String url, Response res) {
        System.out.println(res.getStatus());
        String response = res.readEntity(String.class);
        LoggingFilter.log(method, url, res.getStatus(), response);
        JSONObject responseEntity = new JSONObject(response);
        return responseEntity;
    }

    public static JSONObject setResponse(String method, String url, Response res, String logPath, String logFilePath) {
        String response = res.readEntity(String.class);
        LoggingFilter.logResponseToFile(logPath, response, logFilePath);
        JSONObject responseEntity = new JSONObject(response);
        return responseEntity;
    }

    public static Response postResponse(Client client, String url, String requestObject, Map<String, String> headers) {
        MultivaluedMap<String, Object> headersMap = new MultivaluedHashMap<>(headers);
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        Response res = client.target(url).request(MediaType.APPLICATION_JSON).headers(headersMap)
                .post(Entity.json(requestObject));
        return res;
    }

    public static Response postResponse(Client client, String url, String requestObject, Map<String, String> headers,
            String logPath, String logFilePath) {
        LoggingFilter.logRequestToFile(logPath, requestObject, logFilePath);
        MultivaluedMap<String, Object> headersMap = new MultivaluedHashMap<>(headers);
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        Response res = client.target(url).request(MediaType.APPLICATION_JSON).headers(headersMap)
                .post(Entity.json(requestObject));
        String response = res.readEntity(String.class);
        LoggingFilter.logResponseToFile(logPath, response, logFilePath);
        return res;
    }

    public static Response postResponseWithLogs(Client client, String url, String requestObject,
            Map<String, String> headers, String logPath, String logFilePath) {
        LoggingFilter.logRequestToFile(logPath, requestObject, logFilePath);
        MultivaluedMap<String, Object> headersMap = new MultivaluedHashMap<>(headers);
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        Response res = client.target(url).request(MediaType.APPLICATION_JSON).headers(headersMap)
                .post(Entity.json(requestObject));
        return res;
    }

    public static Response postResponseWithLogs(Client client, String url, Map<String, String> headers,
            String grantType, String clientCredentials) {
        MultivaluedMap<String, Object> headersMap = new MultivaluedHashMap<>(headers);
        Form form = new Form(grantType, clientCredentials);
        Response res = client.target(url).request(MediaType.APPLICATION_FORM_URLENCODED).headers(headersMap)
                .post(Entity.form(form));
        return res;
    }

    public static Response putResponseWithLogs(Client client, String url, String requestObject,
            Map<String, String> headers, String logPath, String logFilePath) {
        LoggingFilter.logRequestToFile(logPath, requestObject, logFilePath);
        MultivaluedMap<String, Object> headersMap = new MultivaluedHashMap<>(headers);
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        Response res = client.target(url).request(MediaType.APPLICATION_JSON).headers(headersMap)
                .put(Entity.json(requestObject.toString()));
        return res;
    }

    public static Response putResponse(Client client, String url, String requestObject, Map<String, String> headers,
            String logPath, String logFilePath) {
        MultivaluedMap<String, Object> headersMap = new MultivaluedHashMap<>(headers);
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        Response res = client.target(url).request(MediaType.APPLICATION_JSON).headers(headersMap)
                .put(Entity.json(requestObject.toString()));
        return res;
    }

    public static Response getResponseStatus(Client client, String url, Map<String, String> headers) {
        MultivaluedMap<String, Object> headersMap = new MultivaluedHashMap<>(headers);
        Response res = client.target(url).request(MediaType.APPLICATION_JSON).headers(headersMap).get();
        return res;
    }

    public static Response getResponseStatus(Client client, String url, Map<String, String> headers, String logPath,
            String logFilePath) {
        MultivaluedMap<String, Object> headersMap = new MultivaluedHashMap<>(headers);
        Response res = client.target(url).request(MediaType.APPLICATION_JSON).headers(headersMap).get();
        String response = res.readEntity(String.class);
        LoggingFilter.logResponseToFile(logPath, response, logFilePath);
        return res;
    }

    public static Response patch(String url, String requestObject, Map<String, String> headers, String logPath,
            String logFilePath) throws NoSuchAlgorithmException, KeyManagementException {
        LoggingFilter.logRequestToFile(logPath, requestObject, logFilePath);
        MultivaluedMap<String, Object> headersMap = new MultivaluedHashMap<>(headers);
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        SSLContext sc = SSLContext.getInstance("TLSv1");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        Client client = ClientBuilder.newBuilder().hostnameVerifier(new NullHostnameVerifier()).sslContext(sc).build();
        Response res = client.target(url).request("application/json").headers(headersMap)
                .build("PATCH", Entity.entity(requestObject, MediaType.APPLICATION_JSON))
                .property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true).invoke();
        return res;
    }

    public static JSONObject post(String url, String requestObject, Map<String, String> headers) {
        JSONObject responseEntity = null;
        try {
            MultivaluedMap<String, Object> headersMap = new MultivaluedHashMap<>(headers);
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
            SSLContext sc = SSLContext.getInstance("TLSv1");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            Client client = ClientBuilder.newBuilder().hostnameVerifier(new NullHostnameVerifier()).sslContext(sc)
                    .build();
            Response res = client.target(url).request(MediaType.APPLICATION_JSON).headers(headersMap)
                    .post(Entity.json(requestObject));
            responseEntity = setResponse("POST", url, res);
        } catch (Exception e) {
        }
        return responseEntity;
    }

    static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            // No need to implement.
        }

        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            // No need to implement.
        }
    } };

    private static class NullHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public static void printAPIResponse(ExtentTest logger, String data) {
        logger.info("API response body \r\n");
        logger.info(MarkupHelper.createCodeBlock(data, CodeLanguage.JSON));
    }

    public static void printDefectResponse(ExtentTest logger, String data) {
        logger.info("Defect has been created here are the details: \r\n");
        logger.info(MarkupHelper.createCodeBlock(data, CodeLanguage.JSON));
    }

    public static String generateBasicAuthToken() {
        String credentials = "h4harish.6@gmail.com" + ":"
                + "ATATT3xFfGF0qyji0nq4HBSH2TMarGMaFj8sc2e0SFvJrE6KBJY9Olj0AqnJhxoWiscjqxrnce7hEie1oXuERAv-BpZ-VVp4LTj-0JtIrbkDcJq38r1fZv1yjFzHIpZmPkZwfr0O-OHk1GK2C3ebRRj6FVZAjOkJLklO3FhPt-P-d54A0FfOtwA=3C78060D";
        byte[] encodedBytes = Base64.getEncoder().encode(credentials.getBytes(StandardCharsets.UTF_8));
        return "Basic " + new String(encodedBytes, StandardCharsets.UTF_8);
    }

    public static void CreateJiraDefect(String Summary, String description, ExtentTest logger) {
        Map<String, String> headers = new HashMap<>();
        try {
            headers.put(CONTENT_TYPE, APPLICATIO_JSON);
            headers.put(AUTHORIZATION, generateBasicAuthToken());
            Response response = postResponse(getClient(), JIRA_URL, createTherequestBody(Summary, description),
                    headers);
            if (response.getStatus() == 201) {
                String responseInString = response.readEntity(String.class);
                RestClientFacade.printDefectResponse(logger, responseInString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String createTherequestBody(String Summary, String description) {
        JsonObject json = new JsonObject();
        JsonObject project = new JsonObject();
        project.addProperty("key", "LD");
        JsonObject issuetype = new JsonObject();
        issuetype.addProperty("name", "Bug");
        json.add("fields", new JsonObject());
        json.getAsJsonObject("fields").add("project", project);
        json.getAsJsonObject("fields").addProperty("summary", Summary);
        json.getAsJsonObject("fields").addProperty("description", description);
        json.getAsJsonObject("fields").add("issuetype", issuetype);
        json.getAsJsonObject("fields").add("customfield_10041", new JsonObject());
        json.getAsJsonObject("fields").getAsJsonObject("customfield_10041").addProperty("value", "Transactions");
        json.getAsJsonObject("fields").addProperty("customfield_10037", "Test");
        String requestBody = new Gson().toJson(json);

        return requestBody;
    }

    public static void updateJiraIssuesWithIncrementingKeys(String baseKey, int startIndex, int endIndex) {
        Map<String, String> headers = new HashMap<>();
        try {
//                headers.put("Accept", APPLICATIO_JSON);
            headers.put(AUTHORIZATION, generateBasicAuthToken());
            
            for (int i = startIndex; i <= endIndex; i++) {
                String issueKey = baseKey + "-" + i;
                System.out.println("issueKey " + issueKey);
                String apiUrl = JIRA_URL + issueKey;
                System.out.println("apiUrl " + apiUrl);

                // Retrieve the existing issue data
                Response getResponse = getResponseStatus(getClient(), apiUrl, headers);
                System.out.println("getResponse " + getResponse);

                if (getResponse.getStatus() == 200) {
                    String existingIssueData = getResponse.readEntity(String.class);
                    JSONObject issueJson = new JSONObject(existingIssueData);

                    // Modify the description field in the JSON
                    JSONObject fields = issueJson.getJSONObject("fields");
                    String currentDescription = fields.getString("description");
                    System.out.println("currentDescription " + currentDescription);
                    // Check if the description contains the "Navigate to" step
                    if (currentDescription.contains("Nagivate to")) {
                        // Replace the existing step with the new one
                        String newDescription = currentDescription.replaceAll(
                                "Nagivate to [http://10.167.10.105/pymidol4/auth/login.|http://10.167.10.105/pymidol4/auth/login.]",
                                "Navigate to url - https://************.com");
                        fields.put("description", newDescription);

                        // Update the issue with the modified JSON data
                        Response updateResponse = put(getClient(), apiUrl, issueJson.toString(), headers);
                        System.out.println("updateResponse " + updateResponse);

                        if (updateResponse.getStatus() == 204) {
                            System.out.println("Description updated successfully for issue " + issueKey);
                        } else {
                            System.out.println("Failed to update description for issue " + issueKey);
                        }
                    } else {
                        System.out.println("Navigate step not found in issue " + issueKey + ". Skipping...");
                    }
                } else {
                    System.out.println("Issue " + issueKey + " not found. Skipping...");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        updateJiraIssuesWithIncrementingKeys("PYM", 705, 705);
    }
}
