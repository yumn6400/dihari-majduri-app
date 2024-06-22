package com.example.dihari_majduri.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dihari_majduri.R;
import com.example.dihari_majduri.pojo.Employee;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private List<Employee> employeeList;

    public EmployeeAdapter(List<Employee> employeeList) {
        System.out.println("Employee adaptor parameterized constructor got called");
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("On create view holder method got called");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_employees, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        System.out.println("On bind view holder method got called");
        Employee employee = employeeList.get(position);
        holder.employeeName.setText(employee.getName());
        holder.employeeMobile.setText(employee.getMobile());
    }

    @Override
    public int getItemCount() {
        System.out.println("Size :"+ employeeList.size());
        return employeeList.size();
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {

        TextView employeeName;
        TextView employeeMobile;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            System.out.println("employee view holder method got called");
            employeeName = itemView.findViewById(R.id.employeeName);
            employeeMobile = itemView.findViewById(R.id.employeeMobile);
        }
    }

}

