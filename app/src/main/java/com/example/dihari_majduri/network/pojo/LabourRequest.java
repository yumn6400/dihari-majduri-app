package com.example.dihari_majduri.network.pojo;

import com.example.dihari_majduri.pojo.Labour;

public class LabourRequest {
    private Labour labour;

    private int farmerId;

    public LabourRequest(){}
    public LabourRequest(Labour labour,int farmerId) {
        this.labour = labour;
        this.farmerId=farmerId;
    }

    public void setLabour(Labour labour){
        this.labour=labour;
    }

    public void setFarmerId(int farmerId)
    {
        this.farmerId=farmerId;
    }
    public int getFarmerId()
    {
        return this.farmerId;
    }
    public Labour getLabour() {
        return labour;
    }

}
