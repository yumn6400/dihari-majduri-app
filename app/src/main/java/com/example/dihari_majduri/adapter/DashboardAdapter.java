package com.example.dihari_majduri.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dihari_majduri.EmployeeActivity;
import com.example.dihari_majduri.R;
import com.example.dihari_majduri.pojo.CropWorkDetails;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder> {

    private List<CropWorkDetails> cropWorkDetailsList;
    private Context context;

    public DashboardAdapter(Context context, List<CropWorkDetails>  cropWorkDetailsList) {
        this.context=context;
        this. cropWorkDetailsList =  cropWorkDetailsList;
    }

    @NonNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dihari, parent, false);
        return new DashboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardViewHolder holder, int position) {
         CropWorkDetails cropWorkDetails = cropWorkDetailsList.get(position);
        holder.cropName.setText(cropWorkDetails.getCropName());
        holder.cropWorkType.setText(cropWorkDetails.getCropWorkType());
        holder.dateOfWork.setText(cropWorkDetails.getDate());
        holder.employeeCount.setText(String.valueOf(cropWorkDetails.getEmployeeCount()));
    }

    @Override
    public int getItemCount() {
        return cropWorkDetailsList.size();
    }

    public class DashboardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cropName;
        TextView cropWorkType;
        TextView employeeCount;
        TextView dateOfWork;

        public DashboardViewHolder(@NonNull View itemView) {
            super(itemView);
            cropName = itemView.findViewById(R.id.cropName);
            cropWorkType = itemView.findViewById(R.id.cropWorkType);
            dateOfWork = itemView.findViewById(R.id.dateOfWork);
            employeeCount = itemView.findViewById(R.id.employeeCount);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                CropWorkDetails cropWorkDetails = cropWorkDetailsList.get(position);
                Intent intent = new Intent(context, EmployeeActivity.class);
                intent.putExtra("cropName", cropWorkDetails.getCropName());
                intent.putExtra("cropWorkType", cropWorkDetails.getCropWorkType());
                intent.putExtra("dateOfWork", cropWorkDetails.getDate());
                intent.putExtra("employeeCount", cropWorkDetails.getEmployeeCount());
                context.startActivity(intent);
            }
        }
    }

}

