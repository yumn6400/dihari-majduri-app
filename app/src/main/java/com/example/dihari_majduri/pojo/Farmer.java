package com.example.dihari_majduri.pojo;

public class Farmer {
    private int id;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String pin;

    // Constructor
    public Farmer(String firstName, String lastName, String mobileNumber, String pin) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.pin = pin;
    }

    public Farmer(int id , String firstName, String lastName, String mobileNumber) {
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
    }

    // Getter and Setter for firstName
    public void setId(int id){
        this.id=id;
    }
    public int getId()
    {
        return this.id;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter and Setter for lastName
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Getter and Setter for mobileNumber
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    // Getter and Setter for pin
    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    // Override toString() method for easier printing
    @Override
    public String toString() {
        return "Employer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", pin='" + pin + '\'' +
                '}';
    }
}

