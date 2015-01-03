package edu.example.androproject.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by Akin on 03-Jan-15.
 */
public class GpsSatallite {

    private Float accuracy;
    private Float speed;
    private Integer timeToFirstFix;
    private List<SatalliteInfos> satalliteInfoses;
    private List<Double> location;
    private String userName;
    private Date locationTime;


    public Date getLocationTime() {
        return locationTime;
    }

    public void setLocationTime(Date locationTime) {
        this.locationTime = locationTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public Integer getTimeToFirstFix() {
        return timeToFirstFix;
    }

    public void setTimeToFirstFix(Integer timeToFirstFix) {
        this.timeToFirstFix = timeToFirstFix;
    }

    public List<SatalliteInfos> getSatalliteInfoses() {
        return satalliteInfoses;
    }

    public void setSatalliteInfoses(List<SatalliteInfos> satalliteInfoses) {
        this.satalliteInfoses = satalliteInfoses;
    }

    public Float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Float accuracy) {
        this.accuracy = accuracy;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

}
