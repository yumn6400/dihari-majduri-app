package com.example.dihari_majduri.pojo;

public class CropWorkDetails {
    private String date;
    private int employeeCount;
    private String cropName;
    private String cropWorkType;

    // Constructor
    public CropWorkDetails(String date, int employeeCount, String cropName, String cropWorkType) {
        this.date = date;
        this.employeeCount = employeeCount;
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

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
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
                ", employeeCount=" + employeeCount +
                ", cropName='" + cropName + '\'' +
                ", cropWorkType='" + cropWorkType + '\'' +
                '}';
    }
}

