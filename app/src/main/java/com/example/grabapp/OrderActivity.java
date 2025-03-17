package com.example.grabapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grabapp.adapter.OrderAdapter;
import com.example.grabapp.dao.OrderDAO;
import com.example.grabapp.dao.UserDAO;
import com.example.grabapp.model.Order;
import com.example.grabapp.model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private OrderDAO orderDAO;

    ImageButton btnBack;

    private UserDAO userDAO;
    private FirebaseAuth mAuth;

    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);

        btnBack = findViewById(R.id.btnBack);

        mAuth = FirebaseAuth.getInstance();
        userDAO = new UserDAO();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kết thúc Activity hiện tại và quay lại Activity trước đó
            }
        });

        recyclerView = findViewById(R.id.recyclerViewOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        if (mAuth.getCurrentUser() != null) {
            String email = mAuth.getCurrentUser().getEmail();

            // Gọi UserDAO để lấy thông tin user từ Firestore
            userDAO.getUserByEmail(email, new UserDAO.FirestoreUserCallback() {
                @Override
                public void onUserRetrieved(User fetchedUser) {
                    if (fetchedUser != null) {
                        userId=fetchedUser.getId();
//                        Toast.makeText(OrderActivity.this, "user "+userId, Toast.LENGTH_SHORT).show();
                        fetchOrders();

//
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(OrderActivity.this, "Lỗi khi lấy user", Toast.LENGTH_SHORT).show();
                }
            });
        }


//        orderDAO = new OrderDAO();
//
//
//        // Gọi phương thức lấy đơn hàng theo userId = "1"
//        orderDAO.getOrdersByUserId("1", new OrderDAO.OnOrdersFetchedListener() {
//            @Override
//            public void onSuccess(List<Order> orders) {
//                if (orders.isEmpty()) {
//                    Toast.makeText(OrderActivity.this, "Không có đơn hàng nào!", Toast.LENGTH_SHORT).show();
//                } else {
//                    orderAdapter = new OrderAdapter(orders);
//                    recyclerView.setAdapter(orderAdapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                Toast.makeText(OrderActivity.this, "Lỗi khi lấy đơn hàng!", Toast.LENGTH_SHORT).show();
//            }
//        });



    }

    private void fetchOrders() {
        // Gọi phương thức lấy đơn hàng theo userId
        orderDAO = new OrderDAO();

        // Kiểm tra nếu userId có giá trị hợp lệ
        if (userId != null && !userId.isEmpty()) {
            orderDAO.getOrdersByUserId(userId, new OrderDAO.OnOrdersFetchedListener() {
                @Override
                public void onSuccess(List<Order> orders) {
                    if (orders.isEmpty()) {
                        Toast.makeText(OrderActivity.this, "Không có đơn hàng nào!", Toast.LENGTH_SHORT).show();
                    } else {
                        orderAdapter = new OrderAdapter(orders);
                        recyclerView.setAdapter(orderAdapter);
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(OrderActivity.this, "Lỗi khi lấy đơn hàng!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(OrderActivity.this, "Không tìm thấy userId", Toast.LENGTH_SHORT).show();
        }
    }
}