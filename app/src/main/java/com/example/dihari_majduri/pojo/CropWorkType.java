package com.example.dihari_majduri.pojo;


import java.math.BigDecimal;

public class CropWorkType {
    private String name;
   private int id;

   public CropWorkType(){}
    public CropWorkType(int id , String name) {
        this.name = name;
        this.id=id;
    }

    public void setId(int id){
        this.id=id;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getName() {
        return name;
    }

    public int getId(){return id;}
}
