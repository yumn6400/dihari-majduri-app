package com.example.dihari_majduri.Activities;

import android.os.Bundle;
import android.view.View;
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
import com.example.dihari_majduri.R;
import com.example.dihari_majduri.common.ApplicationSettings;
import com.example.dihari_majduri.common.NetworkConnectivityManager;
import com.example.dihari_majduri.common.NetworkSettings;
import com.example.dihari_majduri.pojo.Farmer;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    private TextView firstNameTextView;
    private TextView lastNameTextView;


    private String firstName;
    private String lastName;
    private String mobileNumber;

    private Button saveButton;
    private NetworkConnectivityManager networkConnectivityManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
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
        firstNameTextView= findViewById(R.id.firstName);
        lastNameTextView=findViewById(R.id.lastName);
        firstNameTextView.setText(ApplicationSettings.farmerFirstName);
        lastNameTextView.setText(ApplicationSettings.farmerLastName);
        saveButton= findViewById(R.id.saveButton);
        TextView activityName = findViewById(R.id.tvActivityName);
        activityName.setText("Edit Profile");
        ImageView backArrow = findViewById(R.id.ivToolbarBack);
        backArrow.setOnClickListener(view -> finish());
    }

    private void setListener()
    {
        saveButton.setOnClickListener(view -> {
            firstName=firstNameTextView.getText().toString().trim();
            lastName=lastNameTextView.getText().toString().trim();
            System.out.println("***************First name : "+firstName);
            System.out.println("*************Last Name : "+lastName);
            System.out.println("******************Mobile number :"+mobileNumber);
            networkConnectivityManager=new NetworkConnectivityManager(this,this);
            if(networkConnectivityManager.isConnected())
            {
                editFarmerProfile();
            }else {
                networkConnectivityManager.showNetworkConnectivityDialog();
            }
        });
    }


    public void editFarmerProfile() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Farmer farmer = new Farmer(ApplicationSettings.farmerId,this.firstName, this.lastName, ApplicationSettings.farmerMobileNumber);
        Gson gson = new Gson();
        String entityJSONString = gson.toJson(farmer);
        System.out.println("*******JSON STRING :" + entityJSONString);
        JSONObject entityJSON = null;
        try {
            entityJSON = new JSONObject(entityJSONString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = NetworkSettings.FARMER_SERVER;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                entityJSON,
                response -> {
                    try {
                        System.out.println("**********Response :" + response.toString());
                        if (response.getBoolean("success")) {

                            ApplicationSettings.saveToSharedPreferences(this, "firstName", this.firstName);
                            ApplicationSettings.saveToSharedPreferences(this, "lastName", this.lastName);
                            ApplicationSettings.farmerFirstName=this.firstName;
                            ApplicationSettings.farmerLastName=this.lastName;

                            nextActivity();
                        }
                        else {
                            //some code if success is false
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    System.out.println("**********Response Error:" + error.toString());
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


    public void nextActivity()
    {
        finish();
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