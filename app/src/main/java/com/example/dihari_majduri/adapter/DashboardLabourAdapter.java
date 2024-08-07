package com.example.dihari_majduri.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dihari_majduri.Activities.EditLabourActivity;
import com.example.dihari_majduri.R;
import com.example.dihari_majduri.pojo.Labour;
import java.util.List;

public class DashboardLabourAdapter extends RecyclerView.Adapter<DashboardLabourAdapter.LabourViewHolder> {

    private List<Labour> labourList;
    private Context context;

    public DashboardLabourAdapter(Context context, List<Labour> labourList) {
        this.context=context;
        this.labourList = labourList;
    }

    @NonNull
    @Override
    public LabourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dashboard_labours, parent, false);
        return new LabourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LabourViewHolder holder, int position) {
        Labour labour = labourList.get(position);
        holder.labourName.setText(labour.getName());
        holder.labourMobile.setText(labour.getMobileNumber());
    }

    @Override
    public int getItemCount() {
        return labourList.size();
    }

    public  class LabourViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView labourName;
        TextView labourMobile;

        public LabourViewHolder(@NonNull View itemView) {
            super(itemView);
            labourName = itemView.findViewById(R.id.labourName);
            labourMobile = itemView.findViewById(R.id.labourMobile);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            System.out.println("***********Labour Adapter*******");
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Labour labour = labourList.get(position);
                Intent intent = new Intent(context, EditLabourActivity.class);
                intent.putExtra("id", labour.getId());
                intent.putExtra("name", labour.getName());
                intent.putExtra("mobileNumber", labour.getMobileNumber());
                context.startActivity(intent);
            }
        }
    }


}

