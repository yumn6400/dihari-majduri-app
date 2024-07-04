package com.example.dihari_majduri.common;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.dihari_majduri.R;



public class ProgressLayoutManager {
    private final Context context;
    private final Activity activity;
    private boolean isProgressShowing = false;
    private ProgressBar progressBar;
    private final boolean isDialog;
    private ViewGroup progressView;

    public ProgressLayoutManager(Context context, Activity activity) {
        this(context, activity, false);
    }
    public ProgressLayoutManager(Context context, Activity activity, boolean isDialog) {
        this.context = context;
        this.activity = activity;
        this.isDialog = isDialog;
        initializeProgressBar();
    }

    private void initializeProgressBar() {
        if (this.activity != null) {
            progressBar = this.activity.findViewById(R.id.progressBar);
            if (progressBar != null) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void showProgressingView() {
        if (isDialog) {
            showDialogProgress();
        } else {
            showInlineProgress();
        }

        disableUserInteraction();
    }

    private void showDialogProgress() {
        if (!isProgressShowing && activity != null) {
            isProgressShowing = true;
            ViewGroup rootView = (ViewGroup) activity.findViewById(android.R.id.content).getRootView();
            LayoutInflater inflater = LayoutInflater.from(activity);
            progressView = (ViewGroup) inflater.inflate(R.layout.progress_bar_layout, rootView, false);
            rootView.addView(progressView);
        }
    }

    private void showInlineProgress() {
        if (!isProgressShowing && progressBar != null) {
            isProgressShowing = true;
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void disableUserInteraction() {
        if (this.activity != null) {
            this.activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    public void hideProgressingView() {
        enableUserInteraction();

        if (isDialog && activity != null) {
            hideDialogProgress();
        } else {
            hideInlineProgress();
        }
    }

    private void enableUserInteraction() {
        if (this.activity != null) {
            this.activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    private void hideDialogProgress() {
        ViewGroup rootView = (ViewGroup) activity.findViewById(android.R.id.content).getRootView();
        rootView.removeView(progressView);
        isProgressShowing = false;
    }

    private void hideInlineProgress() {
        if (progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
            isProgressShowing = false;
        }
    }
}
