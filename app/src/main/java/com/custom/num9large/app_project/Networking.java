package com.custom.num9large.app_project;

import android.os.AsyncTask;

import java.io.IOException;
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
    protected void setupHTTP() {
        try {
            // Create URL
            URL githubEndpoint = new URL("https://api.github.com/");

            // Create connection
            HttpsURLConnection myConnection =
                    (HttpsURLConnection) githubEndpoint.openConnection();

            // Setup user agent header so that the app can be uniquely recognised by REST API websites
            myConnection.setRequestProperty("User-Agent", "my-rest-app-v0.1");
        } catch(IOException e) {
            System.out.println(e);
        }
    }

}
