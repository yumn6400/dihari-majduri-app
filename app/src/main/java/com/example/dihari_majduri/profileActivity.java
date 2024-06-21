package com.example.dihari_majduri;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class profileActivity extends AppCompatActivity {


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
    private void initComponent()
    {
     String firstName= String.valueOf(findViewById(R.id.firstName));
     String lastName=String.valueOf(findViewById(R.id.lastName));
     String mobileNumber=String.valueOf(findViewById(R.id.mobileNumber));
     Button saveButton= findViewById(R.id.saveButton);
     saveButton.setOnClickListener(view -> {
         Intent intent1 = new Intent(profileActivity.this, pinActivity.class);
         intent1.putExtra("firstName",firstName);
         intent1.putExtra("lastName",lastName);
         intent1.putExtra("mobileNumber",mobileNumber);
         startActivity(intent1);
         finish();
     });
    }
}