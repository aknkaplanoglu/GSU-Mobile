package edu.example.androproject.dto;

/**
 * Created by Akin on 03-Jan-15.
 */
public class SatalliteInfos {

    private Float snr;
    private Float elevation;
    private Integer prn; //random generated number for satallite


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

    public Integer getPrn() {
        return prn;
    }

    public void setPrn(Integer prn) {
        this.prn = prn;
    }
}
