package com.example.dihari_majduri.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dihari_majduri.EditLabourActivity;
import com.example.dihari_majduri.R;
import com.example.dihari_majduri.pojo.Labour;
import java.util.List;

public class LabourAdapter extends RecyclerView.Adapter<LabourAdapter.EmployeeViewHolder> {

    private List<Labour> labourList;
    private Context context;

    public LabourAdapter(Context context, List<Labour> labourList) {
        this.context=context;
        this.labourList = labourList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_labours, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Labour labour = labourList.get(position);
        holder.employeeName.setText(labour.getName());
        holder.employeeMobile.setText(labour.getMobileNumber());
    }

    @Override
    public int getItemCount() {
        return labourList.size();
    }

    public  class EmployeeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView employeeName;
        TextView employeeMobile;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeName = itemView.findViewById(R.id.employeeName);
            employeeMobile = itemView.findViewById(R.id.employeeMobile);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            System.out.println("***********Employee Adapter*******");
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

