package com.example.grabapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grabapp.adapter.ProductAdapter;
import com.example.grabapp.model.FavoriteManager;
import com.example.grabapp.model.Product;
import com.example.grabapp.model.Restaurant;

import java.util.List;


public class FoodActivity extends AppCompatActivity {

    ImageButton btnBack;

    ImageButton btnFavorite;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_food);


        btnBack = findViewById(R.id.btnBack);
        btnFavorite = findViewById(R.id.btnLove);






        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kết thúc Activity hiện tại và quay lại Activity trước đó
            }
        });

        Intent intent = getIntent();
        Restaurant restaurant = (Restaurant) intent.getSerializableExtra("restaurant");

        if (restaurant != null) {
            Log.d("FoodActivity", "Received restaurant: " + restaurant.getName());

            TextView txtName = findViewById(R.id.txtRestaurantName);
            ImageView imgRestaurant = findViewById(R.id.imgRestaurant);
            TextView txtRating = findViewById(R.id.txtRating);
            TextView txtSoldQuantity = findViewById(R.id.txtSoldQuantity);
            ImageView imgBanner = findViewById(R.id.imgBanner);
            RecyclerView productRecyclerView = findViewById(R.id.productRecyclerView);

            txtName.setText(restaurant.getName());
            imgRestaurant.setImageResource(restaurant.getImageResId());
            imgBanner.setImageResource(restaurant.getImageResId());
            txtRating.setText("★ " + restaurant.getRating());
            txtSoldQuantity.setText(restaurant.getSoldQuantity() + " đã bán");

//             Hiển thị danh sách sản phẩm
            // Hiển thị danh sách sản phẩm
            List<Product> productList = restaurant.getProductList();
            ProductAdapter productAdapter = new ProductAdapter(this, productList, product -> {
                // Xử lý sự kiện khi chọn sản phẩm
//                Intent productIntent = new Intent(FoodActivity.this, ProductDetailActivity.class);
//                productIntent.putExtra("product", product);
//                startActivity(productIntent);
            });
            productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            productRecyclerView.setAdapter(productAdapter);
        }


        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FavoriteManager.addRestaurant(restaurant);
                Intent intent = new Intent(FoodActivity.this, FavoriteActivity.class);
                startActivity(intent);
            }
        });

    }
}