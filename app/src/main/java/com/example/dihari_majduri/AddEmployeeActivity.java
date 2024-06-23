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

public class AddEmployeeActivity extends AppCompatActivity {
private Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_employee);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initComponent();
    }

    private void initComponent()
    {
        ImageView backArrow = findViewById(R.id.ivToolbarBack);
        backArrow.setOnClickListener(view -> finish());
        TextView activityName = findViewById(R.id.tvActivityName);
        activityName.setText("Add Employee");
        saveButton=findViewById(R.id.saveButton);
        saveButton.setOnClickListener(view -> {
            // Network call to check mobile number already exists or not
            Intent intent1 = new Intent(AddEmployeeActivity.this, EmployeeActivity.class);
            startActivity(intent1);
            finish();
        });
    }
}