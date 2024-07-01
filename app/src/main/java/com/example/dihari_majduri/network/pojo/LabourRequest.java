package com.example.dihari_majduri.network.pojo;

import com.example.dihari_majduri.pojo.Labour;

public class LabourRequest {
    private Labour labour;

    private int ownerId;

    public LabourRequest(){}
    public LabourRequest(Labour labour,int ownerId) {
        this.labour = labour;
        this.ownerId=ownerId;
    }

    public void setLabour(Labour labour){
        this.labour=labour;
    }

    public void setOwnerId(int ownerId)
    {
        this.ownerId=ownerId;
    }
    public int getOwnerId()
    {
        return this.ownerId;
    }
    public Labour getLabour() {
        return labour;
    }

}
