package com.example.dihari_majduri;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dihari_majduri.adapter.DashboardAdapter;
import com.example.dihari_majduri.pojo.CropWorkDetails;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {


    private Button cropButton;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private RecyclerView recyclerView;
    private ExtendedFloatingActionButton addNewDihari;

    private TextView homeButton ;
    private TextView employeeButton;
    private TextView moreButton;
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
      //  employeeButton=findViewById(R.id.employeeButton);
       // cropButton=findViewById(R.id.cropButton);
        recyclerView=findViewById(R.id.recyclerView);
        addNewDihari = findViewById(R.id.addNewDihari);
        homeButton= findViewById(R.id.homeButton);
        employeeButton= findViewById(R.id.employeeButton);
        moreButton= findViewById(R.id.moreButton);
        addNewDihari.setOnClickListener(view -> {
            // Network call to check mobile number already exists or not
            openBottomSheet();
        });

        employeeButton.setOnClickListener(view -> {
            Intent intent1 = new Intent(DashboardActivity.this, LabourActivity.class);
            intent1.putExtra("firstName",firstName);
            intent1.putExtra("lastName",lastName);
            intent1.putExtra("mobileNumber",mobileNumber);
            startActivity(intent1);
            finish();
        });


        homeButton.setOnClickListener(view-> {
                Toast.makeText(DashboardActivity.this, "Home Clicked", Toast.LENGTH_SHORT).show();
        });



        moreButton.setOnClickListener(View->
            {
                Toast.makeText(DashboardActivity.this, "More Clicked", Toast.LENGTH_SHORT).show();
        });
    }

    private void openBottomSheet() {
        DihariBottomSheetFragment bottomSheetFragment = new DihariBottomSheetFragment();
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<CropWorkDetails> cropWorkDetailsList = new ArrayList<>();
        cropWorkDetailsList.add(new CropWorkDetails("2022-02-01", 12,"Soyabean","katai"));
        cropWorkDetailsList.add(new CropWorkDetails("2022-02-01", 3,"Sarso","katai"));
        cropWorkDetailsList.add(new CropWorkDetails("2022-02-01", 12,"Soyabean","katai"));
        cropWorkDetailsList.add(new CropWorkDetails("2022-02-01", 3,"Sarso","katai"));
        cropWorkDetailsList.add(new CropWorkDetails("2022-02-01", 12,"Soyabean","katai"));
        cropWorkDetailsList.add(new CropWorkDetails("2022-02-01", 3,"Sarso","katai"));
        cropWorkDetailsList.add(new CropWorkDetails("2022-02-01", 12,"Soyabean","katai"));
        cropWorkDetailsList.add(new CropWorkDetails("2022-02-01", 3,"Sarso","katai"));
        cropWorkDetailsList.add(new CropWorkDetails("2022-02-01", 12,"Soyabean","katai"));
        cropWorkDetailsList.add(new CropWorkDetails("2022-02-01", 3,"Sarso","katai"));

        // Set up adapter
        DashboardAdapter dashboardAdapter = new DashboardAdapter(this,cropWorkDetailsList);

        recyclerView.setLayoutManager(new LinearLayoutManager(DashboardActivity.this));

        recyclerView.setAdapter(dashboardAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
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