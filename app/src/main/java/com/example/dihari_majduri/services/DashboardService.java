package com.example.dihari_majduri.services;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dihari_majduri.DihariBottomSheetFragment;
import com.example.dihari_majduri.adapter.DashboardAdapter;
import com.example.dihari_majduri.common.ApplicationSettings;
import com.example.dihari_majduri.common.NetworkSettings;
import com.example.dihari_majduri.common.ProgressLayoutManager;
import com.example.dihari_majduri.network.pojo.DihariRequest;
import com.example.dihari_majduri.pojo.Crop;
import com.example.dihari_majduri.pojo.CropWorkType;
import com.example.dihari_majduri.pojo.Labour;
import com.example.dihari_majduri.pojo.LabourEmploymentPeriod;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardService {
    private List<Crop> cropList=new ArrayList<>();
    private List<CropWorkType> cropWorkTypeList=new ArrayList<>();
    private List<Labour> laboursList=new ArrayList<>();
    private List<LabourEmploymentPeriod> labourEmploymentPeriodList=new ArrayList<>();
    private Context context;
    private RecyclerView dihariRecycleView;

    private  ProgressLayoutManager progressLayoutManager;
    public DashboardService(Context context, ProgressLayoutManager progressLayoutManager,RecyclerView dihariRecycleView)
    {
        this.context=context;
        this.progressLayoutManager=progressLayoutManager;
        this.dihariRecycleView=dihariRecycleView;
    }
    public void getAllCrops()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        progressLayoutManager.showProgressingView();
        String url = NetworkSettings.CROP_SERVER;
        StringRequest stringRequest=new StringRequest(
                Request.Method.GET, url,
                response->{
                    progressLayoutManager.hideProgressingView();
                    try {
                        System.out.println("**********Employee Response :" + response);
                        JSONObject jsonObject = new JSONObject(response);
                        if ((boolean) jsonObject.get("success")) {
                            JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.get("data")));
                            JSONObject job;
                            Crop crop;
                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                job = (JSONObject) jsonArray.get(i);
                                crop =new Crop();
                                crop.setId(job.getInt("id"));
                                crop.setName(job.getString("name"));
                                this.cropList.add(crop);
                            }
                            DihariBottomSheetFragment.setCrops(cropList);
                        }
                        else {
                            //some checks if success is false
                        }
                    }catch(Exception e)
                    {
                        System.out.println(e);
                    }
                },
                error-> {
                    System.out.println("**********Response Error:"+error);
                    generateServerError(error);
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        stringRequest.setRetryPolicy(NetworkSettings.requestPolicy);
        requestQueue.add(stringRequest);
    }

    public void getAllCropWorkTypes()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = NetworkSettings.CROP_WORK_TYPE_SERVER;
        progressLayoutManager.showProgressingView();
        StringRequest stringRequest=new StringRequest(
                Request.Method.GET, url,
                response->{
                    progressLayoutManager.hideProgressingView();
                    try {
                        System.out.println("**********Employee Response :" + response);
                        JSONObject jsonObject = new JSONObject(response);
                        if ((boolean) jsonObject.get("success")) {
                            JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.get("data")));
                            JSONObject job;
                            CropWorkType cropWorkType;
                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                job = (JSONObject) jsonArray.get(i);
                                cropWorkType =new CropWorkType();
                                cropWorkType.setId(job.getInt("id"));
                                cropWorkType.setName(job.getString("name"));
                                this.cropWorkTypeList.add(cropWorkType);
                            }
                            DihariBottomSheetFragment.setCropWorkTypes(cropWorkTypeList);
                        }
                        else {
                            // some task on success is false
                        }
                    }catch(Exception e)
                    {
                        System.out.println(e);
                    }
                },
                error-> {
                    System.out.println("**********Response Error:"+error);
                    generateServerError(error);
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        stringRequest.setRetryPolicy(NetworkSettings.requestPolicy);
        requestQueue.add(stringRequest);
    }
    public void getAllLabourEmployments() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = NetworkSettings.LABOUR_EMPLOYMENT_PERIODS_SERVER + "/" + ApplicationSettings.ownerId;
        progressLayoutManager.showProgressingView();
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url,
                response -> {
                    progressLayoutManager.hideProgressingView();
                    try {
                        System.out.println("**********Employee Response :" + response);
                        JSONObject jsonResponse = new JSONObject(response);
                        if (jsonResponse.getBoolean("success")) {
                            JSONArray jsonArray = jsonResponse.getJSONArray("data");
                            labourEmploymentPeriodList.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject job = jsonArray.getJSONObject(i);
                                LabourEmploymentPeriod labourEmploymentPeriod = new LabourEmploymentPeriod();
                                labourEmploymentPeriod.setDate(job.getString("date"));
                                labourEmploymentPeriod.setCropName(job.getString("cropName"));
                                labourEmploymentPeriod.setCropWorkTypeName(job.getString("cropWorkTypeName"));
                                labourEmploymentPeriod.setLabourCount(job.getInt("labourCount"));
                                JSONArray laboursArray = job.getJSONArray("labourPojo");
                                List<Labour> laboursList = new ArrayList<>();
                                for (int j = 0; j < laboursArray.length(); j++) {
                                    JSONObject labourJson = laboursArray.getJSONObject(j);
                                    Labour labourObj = new Labour();
                                    labourObj.setId(labourJson.getInt("id"));
                                    labourObj.setName(labourJson.getString("name"));
                                    labourObj.setMobileNumber(labourJson.getString("mobileNumber"));
                                    laboursList.add(labourObj);
                                }
                                labourEmploymentPeriod.setLabours(laboursList);
                                labourEmploymentPeriodList.add(labourEmploymentPeriod);
                            }
                            setDashboardEmploymentPeriod(labourEmploymentPeriodList);
                        } else {
                            // some taks on success is false
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                },
                error -> {
                    System.out.println("**********Response Error:" + error);
                    generateServerError(error);
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        stringRequest.setRetryPolicy(NetworkSettings.requestPolicy);
        requestQueue.add(stringRequest);
    }

    public void getAllLabours()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        progressLayoutManager.showProgressingView();
        String url = NetworkSettings.LABOUR_SERVER+"/"+ ApplicationSettings.ownerId;
        StringRequest stringRequest=new StringRequest(
                Request.Method.GET, url,
                response->{
                    progressLayoutManager.hideProgressingView();
                    try {
                        System.out.println("**********Employee Response :" + response);
                        JSONObject jsonObject = new JSONObject(response);
                        if ((boolean) jsonObject.get("success")) {
                            JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.get("data")));
                            JSONObject job;
                            Labour labour;
                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                job = (JSONObject) jsonArray.get(i);
                                labour =new Labour();
                                labour.setId(job.getInt("id"));
                                labour.setName(job.getString("name"));
                                labour.setMobileNumber(job.getString("mobileNumber"));
                                laboursList.add(labour);
                            }
                            DihariBottomSheetFragment.setLabours(laboursList);
                        }
                        else {
                            // some task on success is false
                        }
                    }catch(Exception e)
                    {
                        System.out.println(e);
                    }
                },
                error-> {
                    System.out.println("**********Response Error:"+error);
                    generateServerError(error);
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        stringRequest.setRetryPolicy(NetworkSettings.requestPolicy);
        requestQueue.add(stringRequest);
    }

    public  void saveNewLabourInformation(String crop, String cropWorkType, String date, List<Labour> selectedLabours) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        DihariRequest dihariRequest=new DihariRequest(crop,cropWorkType,date,selectedLabours);
        Gson gson = new Gson();
        String entityJSONString = gson.toJson(dihariRequest);
        System.out.println("*******JSON STRING :"+entityJSONString);
        JSONObject entityJSON = null;
        try {
            entityJSON = new JSONObject(entityJSONString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = NetworkSettings.LABOUR_EMPLOYMENT_PERIODS_SERVER+"/"+ ApplicationSettings.ownerId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, entityJSON,
                response -> {
                    System.out.println("**********Response :" + response);
                    // Handle the server's response here
                    //DashboardActivity.getAllLabourEmployments();

                },
                error -> {
                    System.out.println("**********Response Error:" + error);
                    generateServerError(error);
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        jsonObjectRequest.setRetryPolicy(NetworkSettings.requestPolicy);
        requestQueue.add(jsonObjectRequest);
    }


    public  void setDashboardEmploymentPeriod(List<LabourEmploymentPeriod> list)
    {
        DashboardAdapter dashboardAdapter = new DashboardAdapter(context,list);
        dihariRecycleView.setLayoutManager(new LinearLayoutManager(context));
        dihariRecycleView.setAdapter(dashboardAdapter);
    }

    private void generateServerError(VolleyError error) {
        // Handle the error response here
        error.printStackTrace();
    }
}
