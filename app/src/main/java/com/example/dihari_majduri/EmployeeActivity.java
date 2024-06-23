package com.example.dihari_majduri;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dihari_majduri.adapter.EmployeeAdapter;
import com.example.dihari_majduri.pojo.Employee;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class EmployeeActivity extends AppCompatActivity  {


    private ExtendedFloatingActionButton addEmployee;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee);
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
        recyclerView=findViewById(R.id.recyclerView);
        activityName.setText("Employees");

        addEmployee = findViewById(R.id.addEmployee);
        addEmployee.setOnClickListener(view -> {
            // Network call to check mobile number already exists or not
            Intent intent1 = new Intent(EmployeeActivity.this, AddEmployeeActivity.class);
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
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee("John Doe", "1234567890"));
        employeeList.add(new Employee("Jane Smith", "0987654321"));

        // Set up adapter
        EmployeeAdapter employeeAdapter = new EmployeeAdapter(employeeList);

        recyclerView.setLayoutManager(new LinearLayoutManager(EmployeeActivity.this));

        recyclerView.setAdapter(employeeAdapter);

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