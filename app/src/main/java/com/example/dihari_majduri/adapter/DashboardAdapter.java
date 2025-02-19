package com.example.dihari_majduri.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dihari_majduri.Activities.DashboardLabourActivity;
import com.example.dihari_majduri.R;
import com.example.dihari_majduri.pojo.LabourEmploymentPeriod;
import com.google.gson.Gson;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder> {

    private List<LabourEmploymentPeriod> labourEmploymentPeriodList;
    private Context context;

    public DashboardAdapter(Context context, List<LabourEmploymentPeriod>  labourEmploymentPeriodList) {
        this.context=context;
        this. labourEmploymentPeriodList =  labourEmploymentPeriodList;
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
         LabourEmploymentPeriod labourEmploymentPeriod=labourEmploymentPeriodList.get(position);
        holder.cropName.setText(labourEmploymentPeriod.getCropName());
        holder.cropWorkType.setText(labourEmploymentPeriod.getCropWorkTypeName());
        holder.dateOfWork.setText(labourEmploymentPeriod.getDate().toString());
        holder.labourCount.setText("Labours: "+labourEmploymentPeriod.getLabourCount());
    }

    @Override
    public int getItemCount() {
        return labourEmploymentPeriodList.size();
    }

    public class DashboardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cropName;
        TextView cropWorkType;
        TextView labourCount;
        TextView dateOfWork;

        public DashboardViewHolder(@NonNull View itemView) {
            super(itemView);
            cropName = itemView.findViewById(R.id.cropName);
            cropWorkType = itemView.findViewById(R.id.cropWorkType);
            dateOfWork = itemView.findViewById(R.id.dateOfWork);
            labourCount = itemView.findViewById(R.id.labourCount);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                LabourEmploymentPeriod labourEmploymentPeriod = labourEmploymentPeriodList.get(position);
                Intent intent = new Intent(context, DashboardLabourActivity.class);

                // Convert the list of Labour objects to a JSON string
                Gson gson = new Gson();
                String labourListJson = gson.toJson(labourEmploymentPeriod.getLabours());

                // Pass the JSON string as an extra
                intent.putExtra("labourList", labourListJson);
                context.startActivity(intent);
            }
        }

    }

}

