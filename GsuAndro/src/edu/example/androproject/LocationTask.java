package edu.example.androproject;

import android.util.Log;

/**
 * Created by Akin on 09-Dec-14.
 */
public abstract class LocationTask extends AsyncLoginTask {


    private double longitude;
    private double latitude;

    public LocationTask(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }


    @Override
    protected Object doInBackground(Object[] objects) {

        LoginActivity loginActivity = LoginActivity.getInstance();

        try {
            String userName = loginActivity.getUserName();
            String stringResponse = parseResponseToString(executeHttpGet(baseUrl + "/sendLocation/" + userName + "/" + longitude + "/" + latitude));

            Log.d("Stringresp", stringResponse);
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
