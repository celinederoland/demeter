package accountancy.repository.rest;

import accountancy.model.Json;
import accountancy.repository.BaseRepository;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Requester {

    private final BaseRepository repository;
    private       String         baseURL;

    public Requester(String baseURL, BaseRepository repository) {

        this.baseURL = baseURL;
        this.repository = repository;
    }

    public String executeGet(String targetURL) {

        targetURL = baseURL + targetURL;

        HttpURLConnection connection = null;

        try {
            //Create connection
            connection = this.initConnection(targetURL, "GET");

            connection.setUseCaches(false);
            connection.setDoOutput(false);

            //Get Response
            return getResponse(connection);
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

    private HttpURLConnection initConnection(String targetURL, String method) throws IOException {

        URL               url        = new URL(targetURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.setRequestProperty("Content-Language", "en-US");
        connection.setRequestProperty("Authorization", "Bearer " + System.getenv("ACCOUNTANCY_TOKEN"));
        return connection;
    }

    private String getResponse(HttpURLConnection connection) throws IOException {

        InputStream is;
        try {
            is = connection.getInputStream();
        } catch (IOException e) {
            is = connection.getErrorStream();
        }
        BufferedReader rd       = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder  response = new StringBuilder();
        String         line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        return response.toString();
    }

    public String executePut(String targetURL, Object urlParameters) {

        return executeMethod("PUT", targetURL, urlParameters);
    }

    private String executeMethod(String method, String targetURL, Object urlParameters) {

        targetURL = baseURL + targetURL;
        HttpURLConnection connection = null;

        try {
            //Create connection
            connection = this.initConnection(targetURL, method);

            String json = Json.gson(repository).toJson(urlParameters);
            connection.setRequestProperty(
                "Content-Length",
                Integer.toString(json.getBytes().length)
            );

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            sendRequest(connection, json);

            //Get Response
            return getResponse(connection);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void sendRequest(HttpURLConnection connection, String json) throws IOException {

        DataOutputStream wr = new DataOutputStream(
            connection.getOutputStream());
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
        writer.write(json);
        writer.close();
    }

    public String executeDelete(String targetURL, Object urlParameters) {

        return executeMethod("DELETE", targetURL, urlParameters);
    }


}
