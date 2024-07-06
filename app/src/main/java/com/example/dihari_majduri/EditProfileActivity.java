package com.example.dihari_majduri;

import android.content.Intent;
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
import com.example.dihari_majduri.common.ApplicationSettings;
import com.example.dihari_majduri.common.NetworkConnectivityManager;
import com.example.dihari_majduri.common.NetworkSettings;
import com.example.dihari_majduri.pojo.Labour;
import com.example.dihari_majduri.pojo.Owner;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView mobileNumberTextView;

    private String firstName;
    private String lastName;
    private String mobileNumber;
    private TextView errorMessage;
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
        mobileNumberTextView=findViewById(R.id.mobileNumber);
        firstNameTextView.setText(ApplicationSettings.ownerFirstName);
        lastNameTextView.setText(ApplicationSettings.ownerLastName);
        mobileNumberTextView.setText(ApplicationSettings.ownerMobileNumber);
        saveButton= findViewById(R.id.saveButton);
        errorMessage=findViewById(R.id.errorMessage);
        errorMessage.setVisibility(View.INVISIBLE);
        TextView activityName = findViewById(R.id.tvActivityName);
        activityName.setText("Edit Profile");
        ImageView backArrow = findViewById(R.id.ivToolbarBack);
        backArrow.setOnClickListener(view -> finish());
    }

    private void setListener()
    {
        saveButton.setOnClickListener(view -> {
            errorMessage.setVisibility(View.INVISIBLE);
            firstName=firstNameTextView.getText().toString().trim();
            lastName=lastNameTextView.getText().toString().trim();
            mobileNumber=mobileNumberTextView.getText().toString().trim();
            System.out.println("***************First name : "+firstName);
            System.out.println("*************Last Name : "+lastName);
            System.out.println("******************Mobile number :"+mobileNumber);
            networkConnectivityManager=new NetworkConnectivityManager(this,this);
            if(networkConnectivityManager.isConnected())
            {
                checkMobileNumberExists(mobileNumber);
            }else {
                networkConnectivityManager.showNetworkConnectivityDialog();
            }
        });
    }

    public void checkMobileNumberExists(String mobileNumber)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = NetworkSettings.OWNER_SERVER+"/existsByMobileNumber/"+mobileNumber;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                Request.Method.GET, url,null,
                response->{
                    try {
                        System.out.println(response);
                        if(response.getBoolean("success")==true) {
                            boolean exists = response.getBoolean("data");
                            if (exists) {
                                errorMessage.setVisibility(View.VISIBLE);
                            } else {
                                editOwnerProfile();
                            }
                        }
                        else {
                            // Some task if request is not successful
                        }
                    } catch (Exception e) {
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
        jsonObjectRequest.setRetryPolicy(NetworkSettings.requestPolicy);
        requestQueue.add(jsonObjectRequest);
    }
    private void generateServerError(VolleyError error) {
        // Handle the error response here
        error.printStackTrace();
    }

    public void editOwnerProfile() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Owner owner = new Owner(ApplicationSettings.ownerId,this.firstName, this.lastName, this.mobileNumber);
        Gson gson = new Gson();
        String entityJSONString = gson.toJson(owner);
        System.out.println("*******JSON STRING :" + entityJSONString);
        JSONObject entityJSON = null;
        try {
            entityJSON = new JSONObject(entityJSONString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = NetworkSettings.OWNER_SERVER;
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
                            ApplicationSettings.saveToSharedPreferences(this, "mobileNumber", this.mobileNumber);

                            ApplicationSettings.ownerFirstName=this.firstName;
                            ApplicationSettings.ownerLastName=this.lastName;
                            ApplicationSettings.ownerMobileNumber=this.mobileNumber;

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