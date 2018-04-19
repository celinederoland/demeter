package accountancy.repository.rest;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Requester {

    private static String base = "http://accountancy.localhost";

    public static String executeGet(String targetURL) {

        targetURL = base + targetURL;

        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(false);

            System.out.println(connection.getRequestMethod());


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

    public static String executePost(String targetURL, Object urlParameters) {

        targetURL = base + targetURL;
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty(
                "Content-Type",
                "application/json"
            );

            String json = (new Gson()).toJson(urlParameters);
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
}
