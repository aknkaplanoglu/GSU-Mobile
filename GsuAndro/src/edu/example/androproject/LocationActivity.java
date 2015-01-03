package edu.example.androproject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.example.login.R;
import edu.example.androproject.dto.GpsSatallite;
import edu.example.androproject.dto.SatalliteInfos;

import java.util.ArrayList;
import java.util.List;
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
    Float snr;
    Float elevation;
    Integer ttff;
    Float speed;
    Float accuracy;
    List<SatalliteInfos> satalliteInfosList;

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
                            GpsSatallite satallite = new GpsSatallite();
                            gpsTracker = new GpsTracker(LocationActivity.this);
                            latitude = gpsTracker.getLatitude();
                            longitude = gpsTracker.getLongitude();
                            elevation = gpsTracker.getElevation();
                            snr = gpsTracker.getSnr();
                            speed = gpsTracker.getSpeed();
                            accuracy = gpsTracker.getAccuracy();
                            satalliteInfosList = gpsTracker.getSatalliteInfosList();
                            ttff = gpsTracker.getTtff();
                            satallite.setAccuracy(accuracy);
                            satallite.setSpeed(speed);
                            satallite.setTimeToFirstFix(ttff);
                            satallite.setSatalliteInfoses(satalliteInfosList);
                            List<Double> location = new ArrayList<Double>();
                            location.add(0, latitude);
                            location.add(1, longitude);
                            satallite.setLocation(location);
                            location.clear();
                            editLong.setText(String.valueOf(longitude));
                            editLat.setText(String.valueOf(latitude));
                            Toast.makeText(getApplicationContext(), "Your location1111 is - \nLat:" + latitude + "\nLong:" + longitude, Toast.LENGTH_LONG).show();
                            new LocationTask(satallite) {
                                @Override
                                public void onFailure() {
                                    Log.d("DEBUG", "onFailure");
                                }
                            }.execute();

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