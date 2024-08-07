package com.example.dihari_majduri.pojo;

public class CropWorkDetails {
    private String date;
    private int labourCount;
    private String cropName;
    private String cropWorkType;

    // Constructor
    public CropWorkDetails(String date, int labourCount, String cropName, String cropWorkType) {
        this.date = date;
        this.labourCount = labourCount;
        this.cropName = cropName;
        this.cropWorkType = cropWorkType;
    }

    // Getters and Setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLabourCount() {
        return labourCount;
    }

    public void setLabourCount(int labourCount) {
        this.labourCount = labourCount;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getCropWorkType() {
        return cropWorkType;
    }

    public void setCropWorkType(String cropWorkType) {
        this.cropWorkType = cropWorkType;
    }

    // toString method
    @Override
    public String toString() {
        return "CropWorkDetails{" +
                "date='" + date + '\'' +
                ", labourCount=" + labourCount +
                ", cropName='" + cropName + '\'' +
                ", cropWorkType='" + cropWorkType + '\'' +
                '}';
    }
}

