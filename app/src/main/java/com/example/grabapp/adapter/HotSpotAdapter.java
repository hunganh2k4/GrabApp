package com.example.grabapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grabapp.R;
import com.example.grabapp.model.HotSpot;

import java.util.List;

public class HotSpotAdapter extends RecyclerView.Adapter<HotSpotAdapter.ViewHolder> {
    private Context context;
    private List<HotSpot> hotSpotList;

    public HotSpotAdapter(Context context, List<HotSpot> hotSpotList) {
        this.context = context;
        this.hotSpotList = hotSpotList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hot_spot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HotSpot hotSpot = hotSpotList.get(position);
        holder.txtHotSpotName.setText(hotSpot.getName());
        holder.txtDiscount.setText("Giảm " + hotSpot.getDiscount() + "%");
        holder.imgHotSpot.setImageResource(hotSpot.getImageResId());



    }

    @Override
    public int getItemCount() {
        return hotSpotList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHotSpot;
        TextView txtHotSpotName, txtDiscount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHotSpot = itemView.findViewById(R.id.imgRestaurant);
            txtHotSpotName = itemView.findViewById(R.id.txtHotSpotName);
            txtDiscount = itemView.findViewById(R.id.txtDiscount);
        }
    }
}