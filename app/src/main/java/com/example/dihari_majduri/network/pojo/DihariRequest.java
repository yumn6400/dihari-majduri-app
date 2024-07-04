package com.example.dihari_majduri.network.pojo;

import com.example.dihari_majduri.pojo.Labour;

import java.util.Arrays;
import java.util.List;

public class DihariRequest {

    private String cropName;
    private String cropWorkTypeName;
    private String date;
    private List<Labour> laboursList;

    public DihariRequest(String cropName, String cropWorkTypeName, String date, List<Labour> laboursList) {
        this.cropName = cropName;
        this.cropWorkTypeName = cropWorkTypeName;
        this.date = date;
        this.laboursList=laboursList;
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


    public void setLaboursList(List<Labour> laboursList)
    {
        this.laboursList=laboursList;
    }
    public List<Labour> getLaboursList()
    {
        return this.laboursList;
    }
}
