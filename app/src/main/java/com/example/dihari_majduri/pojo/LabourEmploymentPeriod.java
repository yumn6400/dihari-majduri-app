package com.example.dihari_majduri.pojo;

import java.sql.Date;
import java.util.List;

public class LabourEmploymentPeriod {

    private int id;
    private String date;
    private String cropName;
    private String cropWorkTypeName;

    private int labourCount;
    private List<Labour> labours;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public List<Labour> getLabours() {
        return labours;
    }

    public void setLabours(List<Labour> labours) {
        this.labours = labours;
    }

    public void setLabourCount(int labourCount)
    {
        this.labourCount=labourCount;
    }
    public int getLabourCount()
    {
        return this.labourCount;
    }

}
