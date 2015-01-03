package edu.example.androproject;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.*;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import edu.example.androproject.dto.SatalliteInfos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akin on 09-Dec-14.
 */
public class GpsTracker extends Service implements LocationListener {

    private final Context context;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;

    Location location = null;
    Float snr;
    Float elevation;
    Integer ttff;
    Float speed;
    Float accuracy;
    double longitude;
    double latitude;
    List<SatalliteInfos> satalliteInfosList = new ArrayList<SatalliteInfos>();

    public List<SatalliteInfos> getSatalliteInfosList() {
        return satalliteInfosList;
    }

    public void setSatalliteInfosList(List<SatalliteInfos> satalliteInfosList) {
        this.satalliteInfosList = satalliteInfosList;
    }

    public Float getSnr() {
        return snr;
    }

    public void setSnr(Float snr) {
        this.snr = snr;
    }

    public Float getElevation() {
        return elevation;
    }

    public void setElevation(Float elevation) {
        this.elevation = elevation;
    }

    public Integer getTtff() {
        return ttff;
    }

    public void setTtff(Integer ttff) {
        this.ttff = ttff;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public Float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Float accuracy) {
        this.accuracy = accuracy;
    }

    private static final long MIN_DISTANCE_CHANGES_FOR_UPDATES = 1;//1 METERS

    private static final long MIN_TIME_BETWEEN_UPDATES = 1000 * 3; //1 MINUTE

    protected LocationManager locationManager;

    public GpsTracker(Context context) {
        this.context = context;
        getLocation();
    }

    public Location getLocation() {

        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isNetworkEnabled && !isGPSEnabled) {
                System.out.println("GPS VE NETWORK YOK");
            } else {

                this.canGetLocation = true;

                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BETWEEN_UPDATES, MIN_DISTANCE_CHANGES_FOR_UPDATES, this);

                    Log.d("GPS ENABLED", "GPS ENABLED");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        //Satallite için gerekli bilgiler
                        locationManager.addGpsStatusListener(new GpsStatus.Listener() {
                            @Override
                            public void onGpsStatusChanged(int i) {
                                GpsStatus status = locationManager.getGpsStatus(null);
                                    Iterable<GpsSatellite> gpsSatellites = status.getSatellites();
                                    for (GpsSatellite gpsSatellite : gpsSatellites) {
                                        SatalliteInfos satalliteInfos = new SatalliteInfos();
                                        //For signal to noise ratio
                                        satalliteInfos.setSnr(gpsSatellite.getSnr());
                                        satalliteInfos.setElevation(gpsSatellite.getElevation());
                                        satalliteInfos.setPrn(gpsSatellite.getPrn());
                                        satalliteInfosList.add(satalliteInfos);
                                    }
                                    setSatalliteInfosList(satalliteInfosList);

                                    setTtff(status.getTimeToFirstFix());
                            }
                        });
                        if (location != null) {
                            if (location.hasSpeed()) {
                                setSpeed(location.getSpeed());
                            }
                            setAccuracy(location.getAccuracy()); //Lower the integer better the accuracy.
                            longitude = location.getLongitude();
                            latitude = location.getLatitude();
                        }
                    }

                }

                if (isNetworkEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BETWEEN_UPDATES, MIN_DISTANCE_CHANGES_FOR_UPDATES, this);
                        Log.d("NETWORK ENABLED", "NETWORK ENABLED");
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                if (location.hasSpeed()) {
                                    setSpeed(location.getSpeed());
                                }
                                setAccuracy(location.getAccuracy());
                                longitude = location.getLongitude();
                                latitude = location.getLatitude();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    public void stopUsingGps() {
        if (locationManager != null) {
            locationManager.removeUpdates(GpsTracker.this);
        }
    }

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void showSettingsAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("GPS");
        alertDialog.setMessage("GPS is not enabled.Do you want to go to settings menu?");

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
