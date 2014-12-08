package edu.example.androproject;


import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class AsyncLoginTask extends AsyncTask {

    protected JSONObject resp = null;

    protected final String baseUrl;

    protected AsyncLoginTask() {
        //Server address and port
        baseUrl = "http://192.168.1.8:8080/ServiceProvider/services/rest/v1";
    }

    public abstract void onFailure();

    protected static HttpClient getHttpClient() {
        return new DefaultHttpClient();
    }

    protected static String parseResponseToString(HttpResponse response) throws IOException {
        return getBufferedReaderFromResponse(response).readLine();
    }

    private static BufferedReader getBufferedReaderFromResponse(HttpResponse response) throws IOException {
        return new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
    }

    private static HttpGet getHttpGet(String uri) {

        HttpGet httpGet = new HttpGet(uri);
        httpGet.addHeader("accept","application/json");
        return httpGet;
    }

    protected static HttpResponse executeHttpGet(String uri) throws IOException {
        return getHttpClient().execute(getHttpGet(uri));
    }

    protected static JSONObject getJsonObjectFromString(String stringResponse) throws JSONException {
        return new JSONObject(new JSONTokener(stringResponse));
    }

    protected static JSONArray getJsonArrayFromString(String stringResponse) throws JSONException {
        return new JSONArray(stringResponse);
    }

}
