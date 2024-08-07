package com.example.dihari_majduri.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dihari_majduri.R;
import com.example.dihari_majduri.common.LocaleHelper;


public class SelectLanguageActivity extends AppCompatActivity {

    private LinearLayout btnHindi, btnEnglish, btnHinglish;
    private RadioButton radioHindi, radioEnglish, radioHinglish;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleHelper.loadLocale(this); // Load saved locale
        setContentView(R.layout.activity_select_language);

        initComponent();
        setListener();

    }


 public void initComponent()
 {
     btnHindi = findViewById(R.id.btn_hindi);
     btnEnglish = findViewById(R.id.btn_english);
     btnHinglish = findViewById(R.id.btn_hinglish);

     radioHindi = findViewById(R.id.radio_hindi);
     radioEnglish = findViewById(R.id.radio_english);
     radioHinglish = findViewById(R.id.radio_hinglish);

     btnSave = findViewById(R.id.btn_save);

     TextView activityName = findViewById(R.id.tvActivityName);
     activityName.setText("Language");
     ImageView backArrow = findViewById(R.id.ivToolbarBack);
     backArrow.setOnClickListener(view -> finish());
 }

 public void setListener()
 {
     btnHindi.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             radioHindi.setChecked(true);
             radioEnglish.setChecked(false);
             radioHinglish.setChecked(false);
         }
     });

     btnEnglish.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             radioHindi.setChecked(false);
             radioEnglish.setChecked(true);
             radioHinglish.setChecked(false);
         }
     });

     btnHinglish.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             radioHindi.setChecked(false);
             radioEnglish.setChecked(false);
             radioHinglish.setChecked(true);
         }
     });

     btnSave.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if (radioHindi.isChecked()) {
                 System.out.println("Radio hindi is clicked");
                 LocaleHelper.setLocale(SelectLanguageActivity.this, "hi");
             } else if (radioEnglish.isChecked()) {
                 System.out.println("Radio english is clicked");
                 LocaleHelper.setLocale(SelectLanguageActivity.this, "en");
             } else if (radioHinglish.isChecked()) {
                 System.out.println("Radio hindiglish is clicked");
                 LocaleHelper.setLocale(SelectLanguageActivity.this, "hin");
             }
             finish();
         }
     });
 }


}
