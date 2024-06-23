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

import com.example.dihari_majduri.adapter.CropAdapter;
import com.example.dihari_majduri.pojo.Crop;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CropActivity extends AppCompatActivity {
    private ExtendedFloatingActionButton addCrop;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crop);
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

        addCrop = findViewById(R.id.addCrop);
        addCrop.setOnClickListener(view -> {
            // Network call to check mobile number already exists or not
            Intent intent1 = new Intent(CropActivity.this, AddCropActivity.class);
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
        List<Crop> cropList = new ArrayList<>();
        cropList.add(new Crop("soyabean",null));

        // Set up adapter
        CropAdapter cropAdapter = new CropAdapter(cropList);

        recyclerView.setLayoutManager(new LinearLayoutManager(CropActivity.this));

        recyclerView.setAdapter(cropAdapter);

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