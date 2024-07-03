package com.example.dihari_majduri;

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
import com.example.dihari_majduri.adapter.LabourAdapter;
import com.example.dihari_majduri.common.ApplicationSettings;
import com.example.dihari_majduri.common.NetworkSettings;
import com.example.dihari_majduri.pojo.Labour;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LabourActivity extends AppCompatActivity  {
    private ExtendedFloatingActionButton addEmployee;
    private RecyclerView recyclerView;
    private TextView homeButton ;
    private TextView employeeButton;
    private TextView moreButton;

    private List<Labour> labour;

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

        // Retrieve the JSON string from the Intent extras
        Intent intent = getIntent();
        String labourListJson = intent.getStringExtra("labourList");
System.out.println("**************"+labourListJson);
        // Convert the JSON string back to a list of Labour objects
        Gson gson = new Gson();
        Type labourListType = new TypeToken<List<Labour>>() {}.getType();
        labour = gson.fromJson(labourListJson, labourListType);


        setEmployeesData(labour);
    }

    private void initComponent()
    {
        labour =new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView);
        homeButton= findViewById(R.id.homeButton);
        employeeButton= findViewById(R.id.employeeButton);
        moreButton= findViewById(R.id.moreButton);
        employeeButton.setOnClickListener(view -> {
            Toast.makeText(LabourActivity.this, "Employee Clicked", Toast.LENGTH_SHORT).show();
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
        addEmployee = findViewById(R.id.addEmployee);
        addEmployee.setOnClickListener(view -> {
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
        //getAllLabours();
        setEmployeesData(labour);
    }

    public void setEmployeesData(List<Labour> list)
    {
        LabourAdapter labourAdapter = new LabourAdapter(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(LabourActivity.this));
        recyclerView.setAdapter(labourAdapter);
    }

    public void getAllLabours()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // Define the URL to send the request to
        String url = NetworkSettings.LABOUR_SERVER+"/"+ ApplicationSettings.owner_id;
        // Create a JsonObjectRequest
        StringRequest stringRequest=new StringRequest(
                Request.Method.GET, url,
                response->{
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
                                LabourActivity.this.labour.add(labour);
                            }
                            setEmployeesData(LabourActivity.this.labour);
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