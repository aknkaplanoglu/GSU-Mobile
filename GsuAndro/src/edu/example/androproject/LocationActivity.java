package edu.example.androproject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;
import com.example.login.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Akin on 03-Dec-14.
 */
public class LocationActivity extends Activity {

    GpsTracker gpsTracker;
    TextView editLong;
    TextView editLat;
    double latitude;
    double longitude;

    private static final int scheduledTime = 5 * 1000; // one minute

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.location);

        editLat = (TextView) findViewById(R.id.latitude);
        editLong = (TextView) findViewById(R.id.longitude);

        gpsTracker = new GpsTracker(LocationActivity.this);

        if (gpsTracker.canGetLocation) {

            System.out.println(latitude);



            Toast.makeText(getApplicationContext(), "Your location is - \nLat:" + latitude + "\nLong:" + longitude, Toast.LENGTH_LONG).show();

            final Handler handler = new Handler();
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        public void run() {
                            gpsTracker = new GpsTracker(LocationActivity.this);
                            latitude = gpsTracker.getLatitude();
                            longitude = gpsTracker.getLongitude();
                            editLong.setText(String.valueOf(longitude));
                            editLat.setText(String.valueOf(latitude));
                            Toast.makeText(getApplicationContext(), "Your location1111 is - \nLat:" + latitude + "\nLong:" + longitude, Toast.LENGTH_LONG).show();
                            //   new LocationTask(longitude, latitude) {
                            //      @Override
                            //     public void onFailure() {
                            //       Log.d("DEBUG", "onFailure");
                            // }
                            //}.execute();

                        }
                    });
                }
            };
            timer.schedule(task, 0, scheduledTime);

        } else {
            gpsTracker.showSettingsAlert();
        }


    }

}