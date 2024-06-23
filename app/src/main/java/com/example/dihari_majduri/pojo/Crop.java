package com.example.dihari_majduri.pojo;

import java.math.BigDecimal;
import java.util.List;

public class Crop {
    private String name;

    private List<CropWorkType> cropWorkTypeList;

    public Crop(String name, List<CropWorkType> cropWorkTypeList) {
        this.name = name;
        this.cropWorkTypeList=cropWorkTypeList;
    }

    public String getName() {
        return name;
    }

    public List<CropWorkType> getCropWorkTypeList() {
        return cropWorkTypeList;
    }
}
