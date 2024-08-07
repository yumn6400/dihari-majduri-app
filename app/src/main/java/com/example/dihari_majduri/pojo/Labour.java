package com.example.dihari_majduri.pojo;

public class Labour {
    private String name;
    private String mobileNumber;
    private int id;

    private int farmerId;
    public Labour(){}

    public Labour( String name, String mobileNumber) {

        this.name = name;
        this.mobileNumber = mobileNumber;
    }
    public Labour(int id , String name, String mobileNumber,int farmerId) {
        this.id = id;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.farmerId=farmerId;
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
    public void setFarmerId(int farmerId){
        this.farmerId=farmerId;
    }
    public int getFarmerId(){
        return this.farmerId;
    }
}
