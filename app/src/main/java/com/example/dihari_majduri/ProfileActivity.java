package com.example.dihari_majduri;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {

    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView mobileNumberTextView;

    private String firstName;
    private String lastName;
    private String mobileNumber;
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
      firstNameTextView= findViewById(R.id.firstName);
      lastNameTextView=findViewById(R.id.lastName);
      mobileNumberTextView=findViewById(R.id.mobileNumber);

     Button saveButton= findViewById(R.id.saveButton);
     errorMessage=findViewById(R.id.errorMessage);
     errorMessage.setVisibility(View.INVISIBLE);
     saveButton.setOnClickListener(view -> {

         firstName=firstNameTextView.getText().toString().trim();
         lastName=lastNameTextView.getText().toString().trim();
         mobileNumber=mobileNumberTextView.getText().toString().trim();
         System.out.println("***************First name : "+firstName);
         System.out.println("*************Last Name : "+lastName);
         System.out.println("******************Mobile number :"+mobileNumber);

         // Network call to check mobile number already exists or not
         Intent intent1 = new Intent(ProfileActivity.this, PinActivity.class);
         intent1.putExtra("firstName",firstName);
         intent1.putExtra("lastName",lastName);
         intent1.putExtra("mobileNumber",mobileNumber);
         startActivity(intent1);
         finish();
     });
    }
}