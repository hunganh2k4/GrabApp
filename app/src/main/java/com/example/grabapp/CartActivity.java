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
import com.example.grabapp.dao.OrderDAO;
import com.example.grabapp.dao.UserDAO;
import com.example.grabapp.model.CartManager;
import com.example.grabapp.model.Order;
import com.example.grabapp.model.OrderItem;
import com.example.grabapp.model.Product;
import com.example.grabapp.model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnTotalPriceChangeListener {

    private ListView listViewCart;
    private TextView txtTotalPrice;
    private Button btnCheckout;
    private CartAdapter cartAdapter;

    private Button btnRemove;

    ImageButton btnBack;

    private UserDAO userDAO;
    private FirebaseAuth mAuth;

    private User user;

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


        mAuth = FirebaseAuth.getInstance();
        userDAO = new UserDAO();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kết thúc Activity hiện tại và quay lại Activity trước đó
            }
        });

        if (mAuth.getCurrentUser() != null) {
            String email = mAuth.getCurrentUser().getEmail();

            // Gọi UserDAO để lấy thông tin user từ Firestore
            userDAO.getUserByEmail(email, new UserDAO.FirestoreUserCallback() {
                @Override
                public void onUserRetrieved(User fetchedUser) {
                    if (fetchedUser != null) {
                        user = fetchedUser;
                    } else {
                        user = new User();

                    }
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(CartActivity.this, "Lỗi khi lấy user", Toast.LENGTH_SHORT).show();
                }
            });
        }



        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CartManager.getCartProducts().isEmpty()) {
                    Toast.makeText(CartActivity.this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // ✅ Tạo orderId ngẫu nhiên
                String orderId = UUID.randomUUID().toString();
                // ✅ Lấy tổng giá đơn hàng
                int totalPrice = (int) CartManager.getTotalPrice();
                // ✅ Lấy thời gian hiện tại
                String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                // ✅ Giả sử userId lấy từ Firebase Auth hoặc SharedPreferences
                String userId = user.getId();

                // ✅ Chuyển sản phẩm trong giỏ hàng sang danh sách OrderItem
                List<OrderItem> orderItems = new ArrayList<>();
                for (Product product : CartManager.getCartProducts()) {
                    orderItems.add(new OrderItem(
                            product.getId(),
                            product.getImageResId(),
                            1,
                            product.getName()
                    ));
                }

                // ✅ Tạo đối tượng đơn hàng với orderId là UUID
                Order order = new Order(orderId, totalPrice, currentDate, userId, orderItems);

                // ✅ Lưu vào Firestore
                OrderDAO orderDAO = new OrderDAO();
                orderDAO.saveOrder(order, new OrderDAO.OnOrderSavedListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(CartActivity.this, "Đơn hàng đã được lưu!", Toast.LENGTH_SHORT).show();
                        CartManager.clear();
                        cartAdapter.notifyDataSetChanged();
                        txtTotalPrice.setText("Tổng: 0 VND");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(CartActivity.this, "Lỗi khi lưu đơn hàng!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });




        btnRemove.setOnClickListener(v -> {
            List<Product> selectedProducts = cartAdapter.getSelectedProducts();
            if (selectedProducts.isEmpty()) {
                Toast.makeText(CartActivity.this, "Không có sản phẩm nào được chọn!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Xóa sản phẩm được chọn khỏi giỏ hàng
            CartManager.getCartProducts().removeAll(selectedProducts);
            cartAdapter = new CartAdapter(CartActivity.this, CartManager.getCartProducts(), CartActivity.this);
            listViewCart.setAdapter(cartAdapter);

            onTotalPriceChanged(cartAdapter.calculateTotalPrice());
            Toast.makeText(CartActivity.this, "Đã xóa sản phẩm đã chọn!", Toast.LENGTH_SHORT).show();
        });
    }

    // Cập nhật tổng giá khi giá thay đổi
    @Override
    public void onTotalPriceChanged(int totalPrice) {
        txtTotalPrice.setText("Tổng: " + totalPrice + " VND");
    }
}