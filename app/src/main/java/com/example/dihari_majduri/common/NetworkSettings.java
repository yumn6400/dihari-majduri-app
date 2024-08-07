package com.example.dihari_majduri.common;

import com.android.volley.DefaultRetryPolicy;

public class NetworkSettings {
    public static final String FARMER_SERVER="http://192.168.22.69:8080/farmers";
    public static final String LABOUR_SERVER="http://192.168.22.69:8080/labours";
    public static final String CROP_SERVER="http://192.168.22.69:8080/crops";
    public static final String CROP_WORK_TYPE_SERVER="http://192.168.22.69:8080/crop-work-types";

    public static final String LABOUR_EMPLOYMENT_PERIODS_SERVER="http://192.168.22.69:8080/labour-employment-periods";
    public static final DefaultRetryPolicy requestPolicy=new DefaultRetryPolicy(30000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
}
