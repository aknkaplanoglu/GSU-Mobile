package edu.example.androproject;

import com.google.gson.Gson;
import edu.example.androproject.dto.GpsSatallite;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by Akin on 09-Dec-14.
 */
public abstract class LocationTask extends AsyncLoginTask {


    private GpsSatallite gpsSatallite;

    public LocationTask(GpsSatallite gpsSatallite) {
        this.gpsSatallite = gpsSatallite;
    }


    protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
        InputStream in = entity.getContent();


        StringBuffer out = new StringBuffer();
        int n = 1;
        while (n > 0) {
            byte[] b = new byte[4096];
            n = in.read(b);


            if (n > 0) out.append(new String(b, 0, n));
        }


        return out.toString();
    }


    @Override
    protected Object doInBackground(Object[] objects) {

        LoginActivity loginActivity = LoginActivity.getInstance();
        int timeout = 10;
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpParams params_ = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params_, timeout * 1000);
        HttpConnectionParams.setSoTimeout(params_, timeout * 1000);

        try {
            gpsSatallite.setUserName(loginActivity.getUserName());
            gpsSatallite.setLocationTime(new Date());
            Gson gson = new Gson();
            String json = gson.toJson(gpsSatallite);
            System.err.println(json);
            HttpPost postRequest = new HttpPost(
                    "http://localhost:8080/RESTfulExample/json/product/post");
            StringEntity stringEntity = new StringEntity(json);
            stringEntity.setContentType("application/json");
            postRequest.setEntity(stringEntity);
            HttpResponse httpResponse = httpClient.execute(postRequest);
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + httpResponse.getStatusLine().getStatusCode());
            }
            String response = getASCIIContentFromEntity(httpResponse.getEntity());

            System.out.println(response);

        } catch (Exception e) {
            onFailure();
        }

        return resp;
    }


    private boolean isLoginSuccessfull(String stringResponse) {

        if (stringResponse != null) {
            if (stringResponse != "null") {
                if (stringResponse != "false") {
                    return true;
                }
            }
        }
        return false;
    }
}
