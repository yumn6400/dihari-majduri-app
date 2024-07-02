package com.example.dihari_majduri;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.example.dihari_majduri.adapter.CropAdapter;
import com.example.dihari_majduri.adapter.LabourAdapter;
import com.example.dihari_majduri.common.NetworkSettings;
import com.example.dihari_majduri.pojo.Crop;
import com.example.dihari_majduri.pojo.Labour;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CropActivity extends AppCompatActivity {
    private ExtendedFloatingActionButton addCrop;
    private RecyclerView recyclerView;
    private List<Crop> cropList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crop);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initComponent();
    }
    private void initComponent()
    {
        cropList=new ArrayList<>();
        ImageView backArrow = findViewById(R.id.ivToolbarBack);
        backArrow.setOnClickListener(view -> finish());
        TextView activityName = findViewById(R.id.tvActivityName);
        recyclerView=findViewById(R.id.recyclerView);
        activityName.setText("Employees");

        addCrop = findViewById(R.id.addCrop);
        addCrop.setOnClickListener(view -> {
            // Network call to check mobile number already exists or not
            Intent intent1 = new Intent(CropActivity.this, AddCropActivity.class);
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
       // cropList.add(new Crop("soyabean",null));
        getAllCrops();
        // Set up adapter
        CropAdapter cropAdapter = new CropAdapter(cropList);

        recyclerView.setLayoutManager(new LinearLayoutManager(CropActivity.this));

        recyclerView.setAdapter(cropAdapter);

    }

    public void setCropsData(List<Crop> list)
    {
        CropAdapter cropAdapter=new CropAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(CropActivity.this));
        recyclerView.setAdapter(cropAdapter);
    }
    public void getAllCrops()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Define the URL to send the request to
        String url = NetworkSettings.CROP_SERVER;
        // Create a JsonObjectRequest
        StringRequest stringRequest=new StringRequest(
                Request.Method.GET, url,
                response->{
                    try {
                        System.out.println("**********Employee Response :" + response);
                        JSONObject jsonObject = new JSONObject(response);
                        if ((boolean) jsonObject.get("success")) {
                            JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.get("result")));
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
                            setCropsData(cropList);
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