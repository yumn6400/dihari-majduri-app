package com.example.dihari_majduri.network.pojo;

import com.android.volley.DefaultRetryPolicy;

public class NetworkSettings {
    public static final String EMPLOYER_SERVER="http://192.168.194.69:8080/employers";
    public static final String EMPLOYEE_SERVER="http://192.168.194.69:8080/employees";
    public static final DefaultRetryPolicy requestPolicy=new DefaultRetryPolicy(30000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
}
