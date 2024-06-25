package com.example.dihari_majduri;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dihari_majduri.adapter.EmployeeAdapter;
import com.example.dihari_majduri.network.pojo.NetworkSettings;
import com.example.dihari_majduri.pojo.Employee;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class EmployeeActivity extends AppCompatActivity  {
    private ExtendedFloatingActionButton addEmployee;
    private RecyclerView recyclerView;
    private TextView homeButton ;
    private TextView employeeButton;
    private TextView moreButton;

    private List<Employee> employees;

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
        employees=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView);
        homeButton= findViewById(R.id.homeButton);
        employeeButton= findViewById(R.id.employeeButton);
        moreButton= findViewById(R.id.moreButton);
        employeeButton.setOnClickListener(view -> {
            Toast.makeText(EmployeeActivity.this, "Employee Clicked", Toast.LENGTH_SHORT).show();
        });

        homeButton.setOnClickListener(view-> {
            Intent intent1 = new Intent(EmployeeActivity.this, DashboardActivity.class);
            startActivity(intent1);
            finish();
        });

        moreButton.setOnClickListener(View->
        {
            Toast.makeText(EmployeeActivity.this, "More Clicked", Toast.LENGTH_SHORT).show();
        });
        addEmployee = findViewById(R.id.addEmployee);
        addEmployee.setOnClickListener(view -> {
            // Network call to check mobile number already exists or not
            Intent intent1 = new Intent(EmployeeActivity.this, AddEmployeeActivity.class);
            startActivity(intent1);
            finish();
        });
        getAllEmployees();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  List<Employee> employeeList = new ArrayList<>();
     //   employeeList.add(new Employee("John Doe", "1234567890"));
     //   employeeList.add(new Employee("Jane Smith", "0987654321"));
        // Set up adapter
        getAllEmployees();
      //  EmployeeAdapter employeeAdapter = new EmployeeAdapter(this,employees);
      //  recyclerView.setLayoutManager(new LinearLayoutManager(EmployeeActivity.this));
      //  recyclerView.setAdapter(employeeAdapter);
    }

    public void setEmployeesData(List<Employee> list)
    {
        EmployeeAdapter employeeAdapter = new EmployeeAdapter(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(EmployeeActivity.this));
        recyclerView.setAdapter(employeeAdapter);
    }

    public void getAllEmployees()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);


        // Define the URL to send the request to
        String url = NetworkSettings.EMPLOYEE_SERVER;

        // Create a JsonObjectRequest
        StringRequest stringRequest=new StringRequest(
                Request.Method.GET, url,
                response->{
                    try {

                        System.out.println("**********Employee Response :" + response);
                        JSONObject jsonObject = new JSONObject(response);
                        if ((boolean) jsonObject.get("success")) {
                            JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.get("result")));
                            JSONObject job;
                            Employee employee;
                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                job = (JSONObject) jsonArray.get(i);
                                employee=new Employee();
                                employee.setName(job.getString("name"));
                                employee.setMobile(job.getString("mobileNumber"));
                                employees.add(employee);
                            }
                            setEmployeesData(employees);
                        }
                    }catch(Exception e)
                    {
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

        // Set retry policy
        stringRequest.setRetryPolicy(NetworkSettings.requestPolicy);

        // Add the request to the RequestQueue
        requestQueue.add(stringRequest);
    }

    // Method to handle server errors
    private void generateServerError(VolleyError error) {
        // Handle the error response here
        error.printStackTrace();
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