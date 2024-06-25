package com.example.dihari_majduri.pojo;

public class Labour {
    private String name;
    private String mobile;

    public Labour(){}
    public Labour(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public void setName(String name){
        this.name=name;
    }
    public void setMobile(String mobile)
    {
        this.mobile=mobile;
    }
    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }
}
