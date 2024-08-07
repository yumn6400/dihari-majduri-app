package com.example.dihari_majduri.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dihari_majduri.R;
import com.example.dihari_majduri.services.DashboardService;
import com.example.dihari_majduri.common.NetworkConnectivityManager;
import com.example.dihari_majduri.common.ProgressLayoutManager;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;



public class DashboardActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ExtendedFloatingActionButton addNewDihari;
    private TextView homeButton ;
    private TextView labourButton;
    private TextView moreButton;

    private NetworkConnectivityManager networkConnectivityManager;
    private ProgressLayoutManager progressLayoutManager;
    private DashboardService dashboardService;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private int navProfileId;
    private int navChangePinId;
    private int navChangeLanguageId;
    private int navAboutUsId;
    private int navContactUsId;
    private int navPrivacyPolicyId;

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
        labourButton= findViewById(R.id.labourButton);
        moreButton= findViewById(R.id.moreButton);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        networkConnectivityManager=new NetworkConnectivityManager(this,this);
        progressLayoutManager=new ProgressLayoutManager(this,this);
        dashboardService=new DashboardService(this,progressLayoutManager,recyclerView);

        navProfileId = R.id.nav_profile;
        navChangePinId=R.id.nav_change_pin;
        navChangeLanguageId = R.id.nav_change_language;
        navAboutUsId = R.id.nav_about_us;
        navContactUsId = R.id.nav_contact_us;
        navPrivacyPolicyId = R.id.nav_privacy_policy;
    }

    private void setListener()
    {
        addNewDihari.setOnClickListener(view -> {
            openBottomSheet();
        });
        labourButton.setOnClickListener(view -> {
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
                    drawerLayout.openDrawer(navigationView);
                });
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == navProfileId) {
                Toast.makeText(DashboardActivity.this, "Profile Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DashboardActivity.this, EditProfileActivity.class);
                startActivity(intent);
                // finish();
            }else if(itemId==navChangePinId){
                Toast.makeText(DashboardActivity.this, "Change Pin Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DashboardActivity.this, ChangePinActivity.class);
                startActivity(intent);
            } else if (itemId == navChangeLanguageId) {
                Intent intent = new Intent(DashboardActivity.this, SelectLanguageActivity.class);
                startActivity(intent);
                Toast.makeText(DashboardActivity.this, "Change Language Clicked", Toast.LENGTH_SHORT).show();
            } else if (itemId == navAboutUsId) {
                Toast.makeText(DashboardActivity.this, "About Us Clicked", Toast.LENGTH_SHORT).show();
            } else if (itemId == navContactUsId) {
                Toast.makeText(DashboardActivity.this, "Contact Us Clicked", Toast.LENGTH_SHORT).show();
            } else if (itemId == navPrivacyPolicyId) {
                Toast.makeText(DashboardActivity.this, "Privacy Policy Clicked", Toast.LENGTH_SHORT).show();
            }
            drawerLayout.closeDrawer(navigationView);
            return true;
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