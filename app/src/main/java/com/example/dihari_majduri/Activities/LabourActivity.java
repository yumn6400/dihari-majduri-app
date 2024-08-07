package com.example.dihari_majduri.Activities;

import android.content.Intent;
import android.os.Bundle;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dihari_majduri.R;
import com.example.dihari_majduri.adapter.LabourAdapter;
import com.example.dihari_majduri.common.ApplicationSettings;
import com.example.dihari_majduri.common.NetworkConnectivityManager;
import com.example.dihari_majduri.common.NetworkSettings;
import com.example.dihari_majduri.common.ProgressLayoutManager;
import com.example.dihari_majduri.pojo.Labour;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LabourActivity extends AppCompatActivity  {
    private ExtendedFloatingActionButton addLabour;
    private RecyclerView recyclerView;
    private TextView homeButton ;
    private TextView labourButton;
    private TextView moreButton;
    private ProgressLayoutManager progressLayoutManager;
    private List<Labour> labours;
    private NetworkConnectivityManager networkConnectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_labour);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initComponent();
        setListener();
    }

    private void initComponent()
    {
        labours =new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView);
        homeButton= findViewById(R.id.homeButton);
        labourButton= findViewById(R.id.labourButton);
        moreButton= findViewById(R.id.moreButton);
        networkConnectivityManager=new NetworkConnectivityManager(this,this);
        progressLayoutManager=new ProgressLayoutManager(this,this);
    }

    private void setListener()
    {
        labourButton.setOnClickListener(view -> {
            Toast.makeText(LabourActivity.this, "Labour Clicked", Toast.LENGTH_SHORT).show();
        });
        homeButton.setOnClickListener(view-> {
            Intent intent1 = new Intent(LabourActivity.this, DashboardActivity.class);
            startActivity(intent1);
            finish();
        });
        moreButton.setOnClickListener(View->
        {
            Toast.makeText(LabourActivity.this, "More Clicked", Toast.LENGTH_SHORT).show();
        });
        addLabour = findViewById(R.id.addLabour);
        addLabour.setOnClickListener(view -> {
            // Network call to check mobile number already exists or not
            Intent intent1 = new Intent(LabourActivity.this, AddLabourActivity.class);
            startActivity(intent1);
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(networkConnectivityManager.isConnected())
        {
            getAllLabours();
        }else {
            networkConnectivityManager.showNetworkConnectivityDialog();
        }

    }
    public void setLaboursData(List<Labour> list)
    {
        LabourAdapter labourAdapter = new LabourAdapter(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(LabourActivity.this));
        recyclerView.setAdapter(labourAdapter);
    }
    public void getAllLabours() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        progressLayoutManager.showProgressingView();
        String url = NetworkSettings.LABOUR_SERVER + "/farmerId/" + ApplicationSettings.farmerId;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url,
                response -> {
                    progressLayoutManager.hideProgressingView();
                    try {
                        System.out.println("**********Labour Response :" + response);
                        JSONObject jsonObject = new JSONObject(response);
                        this.labours.clear();

                        if (jsonObject.getBoolean("success")) {
                           // JSONObject dataObject = jsonObject.getJSONObject("data");
                            Object data = jsonObject.get("data");
                             JSONArray jsonArray = (JSONArray) data;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject job = jsonArray.getJSONObject(i);
                                Labour labour = new Labour();
                                labour.setId(job.getInt("id"));
                                labour.setName(job.getString("name"));
                                labour.setMobileNumber(job.getString("mobileNumber"));
                                this.labours.add(labour);
                            }
                            setLaboursData(this.labours);
                        }
                    } catch (Exception e) {
                        e.printStackTrace(); // More detailed error logging
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


    // Method to handle server errors
    private void generateServerError(VolleyError error) {
        // Handle the error response here
        error.printStackTrace();
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