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
import com.example.grabapp.model.Restaurant;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    private Context context;
    private List<Restaurant> restaurantList;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener; // Sử dụng đúng interface tự định nghĩa

    public interface OnItemClickListener {
        void onItemClick(Restaurant restaurant);
    }

    // Định nghĩa interface cho sự kiện long click
    public interface OnItemLongClickListener {
        void onItemLongClick(Restaurant restaurant, int position);
    }

    // Constructor không thay đổi
    public RestaurantAdapter(Context context, List<Restaurant> restaurantList, OnItemClickListener listener) {
        this.context = context;
        this.restaurantList = restaurantList;
        this.onItemClickListener = listener;
    }

    // Phương thức setter để thiết lập long click listener
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.nameTextView.setText(restaurant.getName());
        holder.imageView.setImageResource(restaurant.getImageResId());

        // Xử lý sự kiện click thông thường
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(restaurant));

        // Xử lý khi nhấn giữ (long click) vào nhà hàng
        holder.itemView.setOnLongClickListener(v -> {
            if (onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClick(restaurant, position);
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.restaurantName);
            imageView = itemView.findViewById(R.id.restaurantImage);
        }
    }
}
