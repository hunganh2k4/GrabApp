package com.example.grabapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grabapp.DatabaseHelper;
import com.example.grabapp.R;
import com.example.grabapp.model.Order;
import com.example.grabapp.model.OrderItem;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orderList;
    private DatabaseHelper databaseHelper;

    public OrderAdapter(List<Order> orderList, DatabaseHelper databaseHelper) {
        this.orderList = orderList;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.tvOrderId.setText("Order ID: #" + order.getId());
        holder.tvOrderDate.setText("Date: " + order.getDate());
        holder.tvOrderTotal.setText("Total Price: " + order.getTotalPrice() + "đ");

        // Lấy danh sách sản phẩm của đơn hàng
        List<OrderItem> orderItems = databaseHelper.getOrderItemsByOrderId(order.getId());

        // Gán Adapter cho RecyclerView con
        OrderItemAdapter itemAdapter = new OrderItemAdapter(orderItems);
        holder.recyclerViewOrderItems.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.recyclerViewOrderItems.setAdapter(itemAdapter);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvOrderDate, tvOrderTotal;
        RecyclerView recyclerViewOrderItems;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.txtOrderId);
            tvOrderDate = itemView.findViewById(R.id.txtDate);
            tvOrderTotal = itemView.findViewById(R.id.txtTotalPrice);
            recyclerViewOrderItems = itemView.findViewById(R.id.recyclerViewOrderItems);
        }
    }
}
