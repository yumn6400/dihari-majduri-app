package com.example.dihari_majduri;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dihari_majduri.adapter.DashboardAdapter;
import com.example.dihari_majduri.common.ApplicationSettings;
import com.example.dihari_majduri.common.NetworkConnectivityManager;
import com.example.dihari_majduri.common.NetworkSettings;
import com.example.dihari_majduri.common.ProgressLayoutManager;
import com.example.dihari_majduri.pojo.Crop;
import com.example.dihari_majduri.pojo.CropWorkDetails;
import com.example.dihari_majduri.pojo.CropWorkType;
import com.example.dihari_majduri.pojo.Labour;
import com.example.dihari_majduri.pojo.LabourEmploymentPeriod;
import com.example.dihari_majduri.pojo.Owner;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {



    private RecyclerView recyclerView;
    private ExtendedFloatingActionButton addNewDihari;

    private TextView homeButton ;
    private TextView employeeButton;
    private TextView moreButton;
    private List<Crop> cropList=new ArrayList<>();
    private List<CropWorkType> cropWorkTypeList=new ArrayList<>();
    private List<Labour> laboursList=new ArrayList<>();

    private NetworkConnectivityManager networkConnectivityManager;
    private ProgressLayoutManager progressLayoutManager;
    private List<LabourEmploymentPeriod> labourEmploymentPeriodList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initComponent();
    }

    private void initComponent()
    {

        recyclerView=findViewById(R.id.recyclerView);
        addNewDihari = findViewById(R.id.addNewDihari);
        homeButton= findViewById(R.id.homeButton);
        employeeButton= findViewById(R.id.employeeButton);
        moreButton= findViewById(R.id.moreButton);
        networkConnectivityManager=new NetworkConnectivityManager(this,this);
        progressLayoutManager=new ProgressLayoutManager(this,this);

        addNewDihari.setOnClickListener(view -> {
            // Network call to check mobile number already exists or not
            openBottomSheet();
        });

        employeeButton.setOnClickListener(view -> {
            Intent intent1 = new Intent(DashboardActivity.this, LabourActivity.class);
            startActivity(intent1);
            finish();
        });

        homeButton.setOnClickListener(view-> {
                Toast.makeText(DashboardActivity.this, "Home Clicked", Toast.LENGTH_SHORT).show();
        });


        moreButton.setOnClickListener(View->
            {
                Toast.makeText(DashboardActivity.this, "More Clicked", Toast.LENGTH_SHORT).show();
        });
    }

    private void openBottomSheet() {
        DihariBottomSheetFragment bottomSheetFragment = new DihariBottomSheetFragment();
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(networkConnectivityManager.isConnected())
        {
            getAllLabourEmployments();
            getAllCrops();
            getAllCropWorkTypes();
            getAllLabours();

        }else {
            networkConnectivityManager.showNetworkConnectivityDialog();
        }
    }

    public void setDashboardEmploymentPeriod(List<LabourEmploymentPeriod> list)
    {
        DashboardAdapter dashboardAdapter = new DashboardAdapter(this,list);

        recyclerView.setLayoutManager(new LinearLayoutManager(DashboardActivity.this));

        recyclerView.setAdapter(dashboardAdapter);

    }


    public void getAllCrops()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        progressLayoutManager.showProgressingView();
        // Define the URL to send the request to
        String url = NetworkSettings.CROP_SERVER;
        // Create a JsonObjectRequest
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

        // Set retry policy
        stringRequest.setRetryPolicy(NetworkSettings.requestPolicy);

        // Add the request to the RequestQueue
        requestQueue.add(stringRequest);
    }


    public void getAllCropWorkTypes()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // Define the URL to send the request to
        String url = NetworkSettings.CROP_WORK_TYPE_SERVER;
        // Create a JsonObjectRequest
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

        // Set retry policy
        stringRequest.setRetryPolicy(NetworkSettings.requestPolicy);

        // Add the request to the RequestQueue
        requestQueue.add(stringRequest);
    }


    public void getAllLabourEmployments() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = NetworkSettings.LABOUR_EMPLOYMENT_PERIODS_SERVER + "/" + ApplicationSettings.ownerId;
        progressLayoutManager.showProgressingView();
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url,
                response -> {
                    progressLayoutManager.hideProgressingView();
                    try {
                        System.out.println("**********Employee Response :" + response);
                        // Parse response as a JSONObject
                        JSONObject jsonResponse = new JSONObject(response);

                        // Check if the success field is true
                        if (jsonResponse.getBoolean("success")) {
                            // Extract data array
                            JSONArray jsonArray = jsonResponse.getJSONArray("data");

                            labourEmploymentPeriodList.clear();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject job = jsonArray.getJSONObject(i);
                                System.out.println("****1******");
                                LabourEmploymentPeriod labourEmploymentPeriod = new LabourEmploymentPeriod();
                                labourEmploymentPeriod.setDate(job.getString("date"));
                                System.out.println("****11******");
                                labourEmploymentPeriod.setCropName(job.getString("cropName"));
                                labourEmploymentPeriod.setCropWorkTypeName(job.getString("cropWorkTypeName"));
                                labourEmploymentPeriod.setLabourCount(job.getInt("labourCount"));
                                System.out.println("****2******");
                                // Parse LabourPojo array
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
                                System.out.println("****3******");
                                labourEmploymentPeriodList.add(labourEmploymentPeriod);
                            }

                            setDashboardEmploymentPeriod(labourEmploymentPeriodList);
                        } else {
                            System.out.println("Failed to fetch data: " + jsonResponse.getString("message"));
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // Define the URL to send the request to
        progressLayoutManager.showProgressingView();
        String url = NetworkSettings.LABOUR_SERVER+"/"+ ApplicationSettings.ownerId;
        // Create a JsonObjectRequest
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

        // Set retry policy
        stringRequest.setRetryPolicy(NetworkSettings.requestPolicy);

        // Add the request to the RequestQueue
        requestQueue.add(stringRequest);
    }


    // Method to handle server errors
    private void generateServerError(VolleyError error) {
        // Handle the error response here
        error.printStackTrace();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}