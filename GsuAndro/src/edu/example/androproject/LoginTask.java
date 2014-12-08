package edu.example.androproject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;

public abstract class LoginTask extends AsyncLoginTask {

    private String name;
    private String pass;


    public LoginTask(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
        InputStream in = entity.getContent();


        StringBuffer out = new StringBuffer();
        int n = 1;
        while (n>0) {
            byte[] b = new byte[4096];
            n =  in.read(b);


            if (n>0) out.append(new String(b, 0, n));
        }


        return out.toString();
    }


    @Override
    protected Object doInBackground(Object[] params) {

        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpGet httpGet=new HttpGet(baseUrl + "/islogin/" + name + "/" + pass);
        String text;
        try {
            String telImei = LoginActivity.getInstance().getTelImei();

            HttpResponse response = httpClient.execute(httpGet, localContext);

            //Mod the request to fit your server.
           // System.out.println(baseUrl + "/islogin/" + name + "/" + pass);
            //String stringResponse = parseResponseToString(executeHttpGet(baseUrl + "/islogin/" + name + "/" + pass));

           String stringResponse=getASCIIContentFromEntity(response.getEntity());



            Log.d("Stringresp",stringResponse);
            if (isLoginSuccessfull(stringResponse)) {
                resp = getJsonObjectFromString(stringResponse);
            }

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
