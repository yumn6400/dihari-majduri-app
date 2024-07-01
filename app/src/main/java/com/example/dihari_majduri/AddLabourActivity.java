package com.example.dihari_majduri;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dihari_majduri.common.ApplicationSettings;
import com.example.dihari_majduri.common.NetworkSettings;
import com.example.dihari_majduri.network.pojo.LabourRequest;
import com.example.dihari_majduri.pojo.Labour;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddLabourActivity extends AppCompatActivity {
private Button saveButton;
private TextView nameTextView;
private TextView mobileNumberTextView;
private String name;
private String mobileNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_labour);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initComponent();
    }

    private void initComponent()
    {
        nameTextView=findViewById(R.id.name);
        mobileNumberTextView=findViewById(R.id.mobileNumber);
        ImageView backArrow = findViewById(R.id.ivToolbarBack);
        backArrow.setOnClickListener(view -> finish());
        TextView activityName = findViewById(R.id.tvActivityName);
        activityName.setText("Add Employee");
        saveButton=findViewById(R.id.saveButton);
        saveButton.setOnClickListener(view -> {
            name=nameTextView.getText().toString().trim();
            mobileNumber=mobileNumberTextView.getText().toString().trim();
            System.out.println("Name :"+name+",Mobile number :"+mobileNumber);
            // Save Labour Information
            addNewLabour();



            Intent intent1 = new Intent(AddLabourActivity.this, LabourActivity.class);
            startActivity(intent1);
            finish();
        });
    }

    public void addNewLabour()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // Create an Employer object
        Labour labour=new Labour(this.name,  this.mobileNumber);
        LabourRequest labourRequest=new LabourRequest(labour,ApplicationSettings.owner_id);
        // Serialize the Employer object to JSON
        Gson gson = new Gson();
        String entityJSONString = gson.toJson(labourRequest);
        System.out.println("*******JSON STRING :"+entityJSONString);
        // Create a JSONObject from the JSON string
        JSONObject entityJSON = null;
        try {
            entityJSON = new JSONObject(entityJSONString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Define the URL to send the request to
        String url = NetworkSettings.LABOUR_SERVER;
        // Create a JsonObjectRequest
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, entityJSON,
                response->{
                    System.out.println("**********Response :"+response);
                    // Handle the server's response here
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
        jsonObjectRequest.setRetryPolicy(NetworkSettings.requestPolicy);
        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }

    private void generateServerError(VolleyError error) {
        // Handle the error response here
        error.printStackTrace();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
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