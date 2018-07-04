package org.ucl.newton.service.data.model;

import java.util.List;

public class Weather {
    private String date;
    private List<Astronomy> astronomy;
    private String maxtempC;
    private String mintempC;
    private String totalSnow_cm;
    private String sunHour;
    private String uvIndex;
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Astronomy> getAstronomy() {
        return astronomy;
    }

    public void setAstronomy(List<Astronomy> astronomy) {
        this.astronomy = astronomy;
    }

    public String getMaxtempC() {
        return maxtempC;
    }

    public void setMaxtempC(String maxtempC) {
        this.maxtempC = maxtempC;
    }

    public String getMintempC() {
        return mintempC;
    }

    public void setMintempC(String mintempC) {
        this.mintempC = mintempC;
    }

    public String getTotalSnow_cm() {
        return totalSnow_cm;
    }

    public void setTotalSnow_cm(String totalSnow_cm) {
        this.totalSnow_cm = totalSnow_cm;
    }

    public String getSunHour() {
        return sunHour;
    }

    public void setSunHour(String sunHour) {
        this.sunHour = sunHour;
    }

    public String getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(String uvIndex) {
        this.uvIndex = uvIndex;
    }
}
