package com.example.grabapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grabapp.R;
import com.example.grabapp.model.FavoriteManager;
import com.example.grabapp.model.Product;
import com.example.grabapp.model.Restaurant;

import java.util.List;

public class FavoritePagerAdapter extends RecyclerView.Adapter<FavoritePagerAdapter.ViewHolder> {

    private Context context; // Thêm biến context

    // Constructor nhận context
    public FavoritePagerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_page, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == 0) {
            List<Restaurant> favoriteRestaurants = FavoriteManager.getFavoriteRestaurants();
            RestaurantAdapter restaurantAdapter = new RestaurantAdapter(
                    context, // Dùng context từ adapter
                    favoriteRestaurants,
                    restaurant -> Toast.makeText(context, "Clicked: " + restaurant.getName(), Toast.LENGTH_SHORT).show()
            );

            restaurantAdapter.setOnItemLongClickListener((restaurant, pos) -> {
                showDeleteConfirmationDialog(restaurant, pos, true);
            });

            holder.recyclerView.setAdapter(restaurantAdapter);
        } else {
            List<Product> favoriteProducts = FavoriteManager.getFavoriteProducts();
            ProductAdapter productAdapter = new ProductAdapter(context, favoriteProducts, product -> {
                Toast.makeText(context, "Clicked: " + product.getName(), Toast.LENGTH_SHORT).show();
            });
            holder.recyclerView.setAdapter(productAdapter);
        }
    }
    private <T> void showDeleteConfirmationDialog(T item, int position, boolean isRestaurant) {
        new AlertDialog.Builder(context)
                .setTitle("Xóa khỏi danh sách yêu thích")
                .setMessage("Bạn có chắc chắn muốn xóa mục này khỏi danh sách yêu thích?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    if (isRestaurant) {
                        FavoriteManager.removeFavoriteRestaurant((Restaurant) item);
                    } else {
                        FavoriteManager.removeFavoriteProduct((Product) item);
                    }
                    notifyDataSetChanged(); // Cập nhật lại Adapter
                    Toast.makeText(context, "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    public int getItemCount() {
        return 2; // 2 tabs: Nhà hàng yêu thích & Sản phẩm yêu thích
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(), 2)); // Grid 2 cột
        }
    }
}