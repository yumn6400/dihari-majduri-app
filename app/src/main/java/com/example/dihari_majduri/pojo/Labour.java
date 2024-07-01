package com.example.dihari_majduri.pojo;

public class Labour {
    private String name;
    private String mobileNumber;

    private int id;

    public Labour(){}
    public Labour(String name, String mobileNumber) {
        this.name = name;
        this.mobileNumber = mobileNumber;
    }

    public void setId(int id)
    {
        this.id=id;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber=mobileNumber;
    }
    public String getName() {
        return name;
    }

    public int getId()
    {
        return this.id;
    }
    public String getMobileNumber() {
        return mobileNumber;
    }
}
