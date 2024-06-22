package com.example.dihari_majduri;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class profileActivity extends AppCompatActivity {

private TextView errorMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initComponent();
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

    private void initComponent()
    {
     String firstName= String.valueOf(findViewById(R.id.firstName));
     String lastName=String.valueOf(findViewById(R.id.lastName));
     String mobileNumber=String.valueOf(findViewById(R.id.mobileNumber));
     Button saveButton= findViewById(R.id.saveButton);
     errorMessage=findViewById(R.id.errorMessage);
     errorMessage.setVisibility(View.INVISIBLE);
     saveButton.setOnClickListener(view -> {

         // Network call to check mobile number already exists or not
         Intent intent1 = new Intent(profileActivity.this, pinActivity.class);
         intent1.putExtra("firstName",firstName);
         intent1.putExtra("lastName",lastName);
         intent1.putExtra("mobileNumber",mobileNumber);
         startActivity(intent1);
         finish();
     });
    }
}