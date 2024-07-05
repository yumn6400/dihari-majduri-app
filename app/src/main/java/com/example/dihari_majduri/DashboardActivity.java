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
import androidx.recyclerview.widget.RecyclerView;
import com.example.dihari_majduri.common.NetworkConnectivityManager;
import com.example.dihari_majduri.common.ProgressLayoutManager;
import com.example.dihari_majduri.services.DashboardService;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;


public class DashboardActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ExtendedFloatingActionButton addNewDihari;
    private TextView homeButton ;
    private TextView employeeButton;
    private TextView moreButton;

    private NetworkConnectivityManager networkConnectivityManager;
    private ProgressLayoutManager progressLayoutManager;
    private DashboardService dashboardService;

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
        initComponent();
        setListener();
    }
    private void initComponent()
    {
        recyclerView=findViewById(R.id.recyclerView);
        addNewDihari = findViewById(R.id.addNewDihari);
        homeButton= findViewById(R.id.homeButton);
        employeeButton= findViewById(R.id.employeeButton);
        moreButton= findViewById(R.id.moreButton);
        networkConnectivityManager=new NetworkConnectivityManager(this,this);
        progressLayoutManager=new ProgressLayoutManager(this,this);
        dashboardService=new DashboardService(this,progressLayoutManager,recyclerView);
    }

    private void setListener()
    {
        addNewDihari.setOnClickListener(view -> {
            openBottomSheet();
        });
        employeeButton.setOnClickListener(view -> {
            Intent intent1 = new Intent(DashboardActivity.this, LabourActivity.class);
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
        DihariBottomSheetFragment bottomSheetFragment = new DihariBottomSheetFragment(this,this,dashboardService);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }



    @Override
    protected void onResume() {
        super.onResume();
        if(networkConnectivityManager.isConnected())
        {
            dashboardService.getAllLabourEmployments();
            dashboardService.getAllCrops();
            dashboardService.getAllCropWorkTypes();
            dashboardService.getAllLabours();
        }else {
            networkConnectivityManager.showNetworkConnectivityDialog();
        }
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