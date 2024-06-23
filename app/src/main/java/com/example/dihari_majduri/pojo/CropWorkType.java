package com.example.dihari_majduri.pojo;


import java.math.BigDecimal;

public class CropWorkType {
    private String name;
    private BigDecimal cost;

    public CropWorkType(String name, BigDecimal cost) {
        this.name = name;
        this.cost=cost;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCost() {
        return cost;
    }
}
