package accountancy.repository.rest;

import accountancy.model.Json;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Requester {

    private String baseURL;

    public Requester(String baseURL) {

        this.baseURL = baseURL;
    }

    public String executeGet(String targetURL) {

        targetURL = baseURL + targetURL;

        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(false);

            //Get Response
            InputStream    is       = connection.getInputStream();
            BufferedReader rd       = new BufferedReader(new InputStreamReader(is));
            StringBuilder  response = new StringBuilder();
            String         line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public String executePost(String targetURL, Object urlParameters) {

        return executeMethod("POST", targetURL, urlParameters);
    }

    private String executeMethod(String method, String targetURL, Object urlParameters) {

        targetURL = baseURL + targetURL;
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty(
                "Content-Type",
                "application/json"
            );

            String json = Json.gson().toJson(urlParameters);
            connection.setRequestProperty(
                "Content-Length",
                Integer.toString(json.getBytes().length)
            );
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                connection.getOutputStream());
            wr.writeBytes(json);
            wr.close();

            //Get Response
            InputStream    is       = connection.getInputStream();
            BufferedReader rd       = new BufferedReader(new InputStreamReader(is));
            StringBuilder  response = new StringBuilder();
            String         line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public String executePut(String targetURL, Object urlParameters) {

        return executeMethod("PUT", targetURL, urlParameters);
    }

    public String executeDelete(String targetURL, Object urlParameters) {

        return executeMethod("DELETE", targetURL, urlParameters);
    }
}
