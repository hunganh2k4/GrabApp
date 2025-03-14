package com.example.grabapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grabapp.adapter.CartAdapter;
import com.example.grabapp.model.CartManager;
import com.example.grabapp.model.Product;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnTotalPriceChangeListener {

    private ListView listViewCart;
    private TextView txtTotalPrice;
    private Button btnCheckout;
    private CartAdapter cartAdapter;

    private Button btnRemove;

    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listViewCart = findViewById(R.id.listViewCart);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        btnCheckout = findViewById(R.id.btnCheckout);
        btnRemove = findViewById(R.id.btnRemove);
        btnBack = findViewById(R.id.btnBack);

        // Sử dụng this như một OnTotalPriceChangeListener
        cartAdapter = new CartAdapter(this, CartManager.getCartProducts(), this);
        listViewCart.setAdapter(cartAdapter);




        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kết thúc Activity hiện tại và quay lại Activity trước đó
            }
        });



//        btnCheckout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (CartManager.getCartProducts().isEmpty()) {
//                    Toast.makeText(CartActivity.this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                int totalPrice = (int) CartManager.getTotalPrice();
//                String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
//
//                // Thêm đơn hàng vào database
//                DatabaseHelper dbHelper = new DatabaseHelper(CartActivity.this);
//                dbHelper.addOrder(totalPrice, currentDate, CartManager.getCartProducts());
//
//                CartManager.clear();
//                cartAdapter.notifyDataSetChanged();
//                txtTotalPrice.setText("Tổng: 0 VND");
//                Toast.makeText(CartActivity.this, "Đơn hàng đã được lưu!", Toast.LENGTH_SHORT).show();
//            }
//        });




//        btnRemove.setOnClickListener(v -> {
//            List<Product> selectedProducts = cartAdapter.getSelectedProducts();
//            if (selectedProducts.isEmpty()) {
//                Toast.makeText(CartActivity.this, "Không có sản phẩm nào được chọn!", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            // Xóa sản phẩm được chọn khỏi giỏ hàng
//            CartManager.getCartProducts().removeAll(selectedProducts);
//            cartAdapter = new CartAdapter(CartActivity.this, CartManager.getCartProducts(), CartActivity.this);
//            listViewCart.setAdapter(cartAdapter);
//
//            onTotalPriceChanged(cartAdapter.calculateTotalPrice());
//            Toast.makeText(CartActivity.this, "Đã xóa sản phẩm đã chọn!", Toast.LENGTH_SHORT).show();
//        });
    }

    // Cập nhật tổng giá khi giá thay đổi
    @Override
    public void onTotalPriceChanged(int totalPrice) {
        txtTotalPrice.setText("Tổng: " + totalPrice + " VND");
    }
}