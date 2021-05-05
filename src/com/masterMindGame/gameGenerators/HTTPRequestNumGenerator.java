package com.masterMindGame.gameGenerators;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.masterMindGame.exceptionHandlers.InvalidMethodCallException;
import com.masterMindGame.exceptionHandlers.InvalidResponseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPRequestNumGenerator {
    private String apiKey;

    private JsonArray data;
    private JsonElement result;
    private int totalNum = 0;

    public HTTPRequestNumGenerator(String apiKey) {
        this.apiKey = "\"" + apiKey + "\"";
    }

    public void generate(int n, int min, int max, boolean replacement, String id) throws InvalidResponseException {
        // Initialize loading info
        String info = "{\"jsonrpc\": \"2.0\", \"method\": \"generateIntegers\", \"params\": {\"apiKey\":" + apiKey + ", \"n\":" + n + ",\"min\":" + min + ",\"max\": " + max + " ,\"replacement\": " + replacement + " },\"id\": \"" + id + "\"}";

        // To append each line and build our response content
        StringBuilder responseContent = new StringBuilder();
        URL url;
        totalNum = n;

        HttpURLConnection connection = null;
        // Initialize a bufferReader to read texts from a character-input stream
        BufferedReader reader = null;
        // To read every line from the endpoint
        String line;

        // Establish connection
        try {
            url = new URL("https://api.random.org/json-rpc/4/invoke");
            // Open our connection to this API endpoint
            connection = (HttpURLConnection) url.openConnection();
            // Set our intend to use the URL connection for input and output to true
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // Set up request to get data from the endpoint
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            //Write to OP stream
            OutputStream os = connection.getOutputStream();
            os.write(info.getBytes());
            os.flush();
            os.close();

            // Get response from the server
            int status = connection.getResponseCode();

            // If the connection is not successful, ask our reader to read the error message
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                // While we still have things to read
                while ((line = reader.readLine()) != null) {
                    // Build response content from our input string (HttpURLConnection's response)
                    responseContent.append(line);
                }
                // After reading is completed, close the reader
                reader.close();
            } else {  // If the connection is successful
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
            }
            // Parse the response into JSON format
            JsonParser parser = new JsonParser();
            JsonElement root = parser.parse(responseContent.toString());

            // Extract result from root
            result = root.getAsJsonObject().get("result");
            if (result == null) {
                JsonElement error = root.getAsJsonObject().get("error");
                JsonElement message = error.getAsJsonObject().get("message");
                throw new InvalidResponseException(message.toString());
            }

            // Otherwise, extract the data we need
            JsonElement random = result.getAsJsonObject().get("random");
            JsonObject randomNums = (JsonObject) random;
            data = randomNums.get("data").getAsJsonArray();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the connection
            connection.disconnect();
        }

    }

    public int[] getElementAsArray() throws InvalidMethodCallException {
        int[] randomsArray = new int[totalNum];
        if (totalNum == 0) {
            throw new InvalidMethodCallException("Index does not exist!", null);
        }
        String eachDataElement;
        for (int i = 0; i < totalNum; i++) {
            try {
                eachDataElement = (data.get(i)).toString();
            } catch (NullPointerException nullPointerException) {
                throw new InvalidMethodCallException("Index does not exist!", nullPointerException);
            }
            randomsArray[i] = Integer.parseInt(eachDataElement);
        }
        return randomsArray;
    }
}