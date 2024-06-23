package com.example.dihari_majduri;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashboardActivity extends AppCompatActivity {

    private Button employeeButton;
    private Button cropButton;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent=getIntent();
        this.firstName=intent.getStringExtra("firstName");
        this.lastName=intent.getStringExtra("lastName");
        this.mobileNumber=intent.getStringExtra("mobileNumber");
        initComponent();
    }

    private void initComponent()
    {
        employeeButton=findViewById(R.id.employeeButton);
        cropButton=findViewById(R.id.cropButton);
        employeeButton.setOnClickListener(view -> {
            Intent intent1 = new Intent(DashboardActivity.this, EmployeeActivity.class);
            intent1.putExtra("firstName",firstName);
            intent1.putExtra("lastName",lastName);
            intent1.putExtra("mobileNumber",mobileNumber);
            startActivity(intent1);
            finish();
        });
        cropButton.setOnClickListener(view->{
            Intent intent1 = new Intent(DashboardActivity.this, CropActivity.class);
            intent1.putExtra("firstName",firstName);
            intent1.putExtra("lastName",lastName);
            intent1.putExtra("mobileNumber",mobileNumber);
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