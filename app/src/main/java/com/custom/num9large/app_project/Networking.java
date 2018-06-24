package com.custom.num9large.app_project;

import android.os.AsyncTask;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Networking extends AsyncTask<String, Void, String> {

    // We want the networking logic to run in the background, so it will be executed in a background thread-
    // -A backgorund thread can be used with "AsyncTask"
    @Override
    protected String doInBackground(String... params) {
        /*for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
        }*/
        setupHTTP();
        return "Executed";
    }

    // Setup connection to REST endpoint
    private void setupHTTP() {
        String returnedJsonValue = null;
        try {
            // Create URL
            URL githubEndpoint = new URL("https://api.github.com/");

            // Create connection
            HttpsURLConnection myConnection =
                    (HttpsURLConnection) githubEndpoint.openConnection();

            // Setup user agent header so that the app can be uniquely recognised by REST API websites
            myConnection.setRequestProperty("User-Agent", "my-rest-app-v0.1");

            //checking the request headers http response code
            if (myConnection.getResponseCode() == 200) {
                System.out.println("the http response is good");
                InputStream responseBody = myConnection.getInputStream();

                InputStreamReader responseBodyReader =
                        new InputStreamReader(responseBody, "UTF-8");

                //this is the call to the method that runs the json reader and returns the value as a string
                returnedJsonValue = getJsonReader(responseBodyReader);


            } else {
                System.out.println("the http response is not good" + myConnection.getResponseCode());
            }

            myConnection.disconnect();
            System.out.println(returnedJsonValue);

        } catch (IOException e) {
            System.out.println(e);
            System.out.println("check your connection to the internet");
        }
    }

    //this is the json reader method that gets pased the response and returns the necessary string
    private String getJsonReader(InputStreamReader responseBodyReader) {
        JsonReader jsonReader = new JsonReader(responseBodyReader);

        String value = null;

        try {
            jsonReader.beginObject(); // Start processing the JSON object
            while (jsonReader.hasNext()) { // Loop through all keys
                String key = jsonReader.nextName(); // Fetch the next key
                if (key.equals("organization_url")) { // Check for the desired key we need to search!!!!!!
                    // Fetch the value as a String
                    value = jsonReader.nextString();

                    // Do something with the value
                    // not sure what we want to do with the value yet just printing for now
                    // best to return it as a string

                    System.out.println(value);

                    break; // Break out of the loop
                } else {
                    jsonReader.skipValue(); // Skip values of other keys
                }
            }

            jsonReader.close();

        } catch (IOException e) {
            System.out.println(e);
            System.out.println("there was nothing to read");
            return value;
        }

        return (value);
    }

}
