package com.example.dihari_majduri.pojo;


public class ChangePin {
    private int id;
    private String pin;

    // Constructor
    public ChangePin(int id, String pin) {

        this.id=id;
        this.pin = pin;
    }

    // Getter and Setter for firstName
    public void setId(int id){
        this.id=id;
    }
    public int getId()
    {
        return this.id;
    }

    // Getter and Setter for pin
    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }


}

