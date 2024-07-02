package com.example.dihari_majduri.network.pojo;



public class LoginRequest {

    private String mobileNumber;

    private String pin;

    public LoginRequest(){}
    public LoginRequest(String mobileNumber,String pin) {
        this.mobileNumber=mobileNumber;
        this.pin=pin;
    }

    public void setMobileNumber(String mobileNumber){
        this.mobileNumber=mobileNumber;
    }

    public void setPin(String pin)
    {
        this.pin=pin;
    }
    public String getMobileNumber()
    {
        return this.mobileNumber;
    }
    public String getPin() {
        return pin;
    }

}

