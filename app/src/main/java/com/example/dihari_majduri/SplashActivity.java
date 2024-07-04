package com.example.dihari_majduri;

import android.content.Intent;

import android.os.Bundle;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dihari_majduri.common.ApplicationSettings;
import com.example.dihari_majduri.common.NetworkSettings;
import com.example.dihari_majduri.common.ProgressLayoutManager;
import com.example.dihari_majduri.common.NetworkConnectivityManager;
import com.example.dihari_majduri.pojo.Labour;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    private ProgressLayoutManager progressLayoutManager;
    NetworkConnectivityManager networkConnectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initComponent();
    }

    private void initComponent()
    {
        progressLayoutManager=new ProgressLayoutManager(this,this);
    }
    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
        String mobileNumber = ApplicationSettings.getValueFromSharedPreferences(this,"mobileNumber");
        if(mobileNumber==null || mobileNumber.length()==0)
        {

            Intent intent1 = new Intent(SplashActivity.this, ProfileActivity.class);
            startActivity(intent1);
            finish();
        }
        else {
            ApplicationSettings.ownerFirstName=ApplicationSettings.getValueFromSharedPreferences(this,"firstName");
            ApplicationSettings.ownerLastName=ApplicationSettings.getValueFromSharedPreferences(this,"lastName");
            ApplicationSettings.ownerMobileNumber=ApplicationSettings.getValueFromSharedPreferences(this,"mobileNumber");
            ApplicationSettings.ownerId= Integer.parseInt(ApplicationSettings.getValueFromSharedPreferences(this,"id"));
            Intent intent1 = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent1);
            finish();
        }
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