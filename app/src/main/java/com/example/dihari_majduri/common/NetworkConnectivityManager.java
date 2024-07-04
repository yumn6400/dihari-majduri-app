package com.example.dihari_majduri.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;

import com.example.dihari_majduri.R;


public class NetworkConnectivityManager {
    private final Context context;
    private final Activity activity;
    private boolean isDialogOn = false;
    private AlertDialog alertDialog;

    public NetworkConnectivityManager(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public boolean isConnected() {
        boolean isConnected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            isConnected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            if (isConnected && isDialogOn && alertDialog != null) {
                isDialogOn = false;
                alertDialog.dismiss();
                alertDialog = null;
            }
        } catch (Exception e) {

        }
        return isConnected;
    }

    public void openMobileDataSettings() {
        if (context != null) {
            context.startActivity(new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS));
        }
    }

    public void openMobileWifiSettings() {
        if (context != null) {
            context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
        }
    }

    public void showNetworkConnectivityDialog() {
        if (context == null || activity.isFinishing()) return;

        isDialogOn = true;
        if (alertDialog != null) alertDialog.dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ViewGroup viewGroup = activity.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.no_internet_alert_dialog, viewGroup, false);
        builder.setView(dialogView);

        alertDialog = builder.create();
        TextView mobileDataTextView = dialogView.findViewById(R.id.text_view_my_vehicle);
        TextView wifiTextView = dialogView.findViewById(R.id.textViewEdit);

        mobileDataTextView.setOnClickListener(v -> {
            alertDialog.dismiss();
            openMobileDataSettings();
        });

        wifiTextView.setOnClickListener(v -> {
            alertDialog.dismiss();
            openMobileWifiSettings();
        });

        alertDialog.setCancelable(false);
        try {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        } catch (Exception e) {

        }

        alertDialog.show();
    }
}

