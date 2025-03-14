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
import com.example.grabapp.model.Order;

import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private DatabaseHelper databaseHelper;

    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);

        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kết thúc Activity hiện tại và quay lại Activity trước đó
            }
        });

        recyclerView = findViewById(R.id.recyclerViewOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        databaseHelper = new DatabaseHelper(this);

        // Lấy danh sách đơn hàng từ database
        List<Order> orderList = databaseHelper.getAllOrders();
        if (orderList.isEmpty()) {
            Toast.makeText(this, "Không có đơn hàng nào!", Toast.LENGTH_SHORT).show();
        } else {
            orderAdapter = new OrderAdapter(orderList,databaseHelper);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(orderAdapter);
        }



    }
}