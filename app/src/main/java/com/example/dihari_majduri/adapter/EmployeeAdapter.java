package com.example.dihari_majduri.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dihari_majduri.EditEmployeeActivity;
import com.example.dihari_majduri.R;
import com.example.dihari_majduri.pojo.Employee;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private List<Employee> employeeList;
    private Context context;

    public EmployeeAdapter(Context context,List<Employee> employeeList) {
        this.context=context;
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_employees, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        holder.employeeName.setText(employee.getName());
        holder.employeeMobile.setText(employee.getMobile());
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
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
                Employee employee= employeeList.get(position);
                Intent intent = new Intent(context, EditEmployeeActivity.class);
              //  intent.putExtra("cropName", cropWorkDetails.getCropName());
              //  intent.putExtra("cropWorkType", cropWorkDetails.getCropWorkType());
               // intent.putExtra("dateOfWork", cropWorkDetails.getDate());
              //  intent.putExtra("employeeCount", cropWorkDetails.getEmployeeCount());
                context.startActivity(intent);
            }
        }
    }


}

