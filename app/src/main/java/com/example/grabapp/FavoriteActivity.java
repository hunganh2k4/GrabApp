package com.example.grabapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grabapp.adapter.ProductAdapter;
import com.example.grabapp.adapter.RestaurantAdapter;
import com.example.grabapp.model.FavoriteManager;
import com.example.grabapp.model.Restaurant;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import com.example.grabapp.model.Product;

public class FavoriteActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TabLayout tabLayout;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        btnBack = findViewById(R.id.btnBack);

        tabLayout = findViewById(R.id.tabLayout);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Hiển thị danh sách nhà hàng yêu thích ban đầu
        showFavoriteRestaurants();
//
//        // Xử lý khi chuyển tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    showFavoriteRestaurants();
                } else {
                    showFavoriteProducts();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kết thúc Activity hiện tại và quay lại Activity trước đó
            }
        });
    }

//    // Hiển thị danh sách nhà hàng yêu thích
    private void showFavoriteRestaurants() {
        List<Restaurant> favoriteRestaurants = FavoriteManager.getFavoriteRestaurants();
        RestaurantAdapter adapter = new RestaurantAdapter(this, favoriteRestaurants, restaurant ->
                Toast.makeText(this, "Clicked: " + restaurant.getName(), Toast.LENGTH_SHORT).show()
        );


        adapter.setOnItemLongClickListener((restaurant, position) -> {
            showDeleteRestaurantDialog(restaurant, position);
        });
        recyclerView.setAdapter(adapter);
    }

//     Hiển thị danh sách sản phẩm yêu thích
    private void showFavoriteProducts() {
        List<Product> favoriteProducts = FavoriteManager.getFavoriteProducts();
        ProductAdapter adapter = new ProductAdapter(this, favoriteProducts, product ->
                Toast.makeText(this, "Clicked: " + product.getName(), Toast.LENGTH_SHORT).show()
        );

        // Xử lý khi nhấn giữ (long click) vào sản phẩm
        adapter.setOnItemLongClickListener((product, position) -> {
            showDeleteConfirmationDialog(product, position);
        });

        recyclerView.setAdapter(adapter);
    }


    private void showDeleteConfirmationDialog(Product product, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa sản phẩm yêu thích")
                .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này khỏi danh sách yêu thích?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    FavoriteManager.removeFavoriteProduct(product); // Xóa sản phẩm khỏi danh sách
                    showFavoriteProducts(); // Cập nhật danh sách sản phẩm sau khi xóa
                    Toast.makeText(this, "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    // Hiển thị hộp thoại xác nhận xóa nhà hàng yêu thích
    private void showDeleteRestaurantDialog(Restaurant restaurant, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa nhà hàng yêu thích")
                .setMessage("Bạn có chắc chắn muốn xóa nhà hàng này khỏi danh sách yêu thích?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    FavoriteManager.removeFavoriteRestaurant(restaurant);
                    showFavoriteRestaurants();
                    Toast.makeText(this, "Đã xóa nhà hàng khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }



}
