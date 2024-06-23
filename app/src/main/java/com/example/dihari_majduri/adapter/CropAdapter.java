package com.example.dihari_majduri.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dihari_majduri.R;
import com.example.dihari_majduri.pojo.Crop;
import com.example.dihari_majduri.pojo.Employee;

import java.util.List;

public class CropAdapter extends RecyclerView.Adapter<CropAdapter.CropViewHolder> {

    private List<Crop> cropList;

    public CropAdapter(List<Crop> cropList) {
        this.cropList=cropList;
    }

    @NonNull
    @Override
    public CropViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_crops, parent, false);
        return new CropViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CropViewHolder holder, int position) {
        Crop crop = cropList.get(position);
        holder.cropName.setText(crop.getName());
    }

    @Override
    public int getItemCount() {
        return cropList.size();
    }

    public static class CropViewHolder extends RecyclerView.ViewHolder {

        TextView cropName;


        public CropViewHolder(@NonNull View itemView) {
            super(itemView);
            cropName = itemView.findViewById(R.id.cropName);
        }
    }

}

