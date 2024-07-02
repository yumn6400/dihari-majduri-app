package com.example.dihari_majduri.network.pojo;

import java.util.Arrays;
import java.util.List;

public class DihariRequest {
    private String cropName;
    private String cropWorkTypeName;
    private String date;
    private String laboursName;

    public DihariRequest(String cropName, String cropWorkTypeName, String date, String laboursName) {
        this.cropName = cropName;
        this.cropWorkTypeName = cropWorkTypeName;
        this.date = date;
        this.laboursName = laboursName;
    }


    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getCropWorkTypeName() {
        return cropWorkTypeName;
    }

    public void setCropWorkTypeName(String cropWorkTypeName) {
        this.cropWorkTypeName = cropWorkTypeName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLaboursName() {
        return laboursName;
    }

    public void setLaboursName(String laboursName) {
        this.laboursName = laboursName;
    }

    public List<String> getLaboursList() {
        return Arrays.asList(laboursName.split("\\s*,\\s*"));
    }
}
