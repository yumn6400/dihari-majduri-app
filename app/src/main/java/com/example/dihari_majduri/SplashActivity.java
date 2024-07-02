package com.example.dihari_majduri;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dihari_majduri.common.ApplicationSettings;

public class SplashActivity extends AppCompatActivity {

    private Button profileButton;
    private Button pinButton;

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
        profileButton=findViewById(R.id.profileButton);
        pinButton=findViewById(R.id.pinButton);
        profileButton.setOnClickListener(view -> {
            Intent intent1 = new Intent(SplashActivity.this, ProfileActivity.class);
            startActivity(intent1);
            finish();
        });
        pinButton.setOnClickListener(view->{
            Intent intent1 = new Intent(SplashActivity.this, LoginActivity.class);
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
        String mobileNumber=null;
        mobileNumber= ApplicationSettings.getValueFromSharedPreferences(this,"mobileNumber");
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