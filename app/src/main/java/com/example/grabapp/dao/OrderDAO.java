package com.example.grabapp.dao;

import com.example.grabapp.model.Order;
import com.example.grabapp.model.OrderItem;
import com.google.firebase.firestore.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDAO {
    private final FirebaseFirestore db;
    private final CollectionReference orderCollection;

    public OrderDAO() {
        db = FirebaseFirestore.getInstance();
        orderCollection = db.collection("orders");
    }

    // 1. Lưu đơn hàng vào Firestore
    public void saveOrder(Order order, OnOrderSavedListener listener) {
        orderCollection.document(String.valueOf(order.getId()))
                .set(order)
                .addOnSuccessListener(aVoid -> listener.onSuccess())
                .addOnFailureListener(listener::onFailure);
    }

    // 3. Lấy đơn hàng theo ID
    public void getOrderById(int orderId, OnOrderFetchedListener listener) {
        orderCollection.document(String.valueOf(orderId))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Order order = document.toObject(Order.class);
                            listener.onSuccess(order);
                        } else {
                            listener.onFailure(new Exception("Đơn hàng không tồn tại!"));
                        }
                    } else {
                        listener.onFailure(task.getException());
                    }
                });
    }

    // 4. Lấy danh sách đơn hàng theo User ID
    public void getOrdersByUserId(String userId, OnOrdersFetchedListener listener) {
        orderCollection.whereEqualTo("userId", userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                List<Order> orders = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Order order = document.toObject(Order.class);

                    // Chuyển đổi danh sách items từ Firestore
                    List<OrderItem> orderItems = new ArrayList<>();
                    List<Map<String, Object>> itemsData = (List<Map<String, Object>>) document.get("items");

                    if (itemsData != null) {
                        for (Map<String, Object> itemMap : itemsData) {
                            OrderItem item = new OrderItem(
                                    (String) itemMap.get("id"),
                                    ((Long) itemMap.get("productImage")).intValue(),
                                    ((Long) itemMap.get("quantity")).intValue(),
                                    (String) itemMap.get("productName")
                            );
                            orderItems.add(item);
                        }
                    }

                    order.setItems(orderItems);
                    orders.add(order);
                }
                listener.onSuccess(orders);
            } else {
                listener.onFailure(task.getException());
            }
        });
    }

    public void getOrders(OnOrdersFetchedListener listener) {
        orderCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                List<Order> orders = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Order order = document.toObject(Order.class);

                    // Chuyển đổi danh sách items từ Firestore
                    List<OrderItem> orderItems = new ArrayList<>();
                    List<Map<String, Object>> itemsData = (List<Map<String, Object>>) document.get("items");

                    if (itemsData != null) {
                        for (Map<String, Object> itemMap : itemsData) {
                            OrderItem item = new OrderItem(
                                    (String) itemMap.get("id"),
                                    ((Long) itemMap.get("productImage")).intValue(),
                                    ((Long) itemMap.get("quantity")).intValue(),
                                    (String) itemMap.get("productName")
                            );
                            orderItems.add(item);
                        }
                    }

                    order.setItems(orderItems);
                    orders.add(order);
                }
                listener.onSuccess(orders);
            } else {
                listener.onFailure(task.getException());
            }
        });
    }






    // 5. Xóa đơn hàng theo ID
    public void deleteOrder(int orderId, OnOrderDeletedListener listener) {
        orderCollection.document(String.valueOf(orderId))
                .delete()
                .addOnSuccessListener(aVoid -> listener.onSuccess())
                .addOnFailureListener(listener::onFailure);
    }

    // Interfaces cho callback
    public interface OnOrderSavedListener {
        void onSuccess();
        void onFailure(Exception e);
    }

    public interface OnOrdersFetchedListener {
        void onSuccess(List<Order> orders);
        void onFailure(Exception e);
    }

    public interface OnOrderFetchedListener {
        void onSuccess(Order order);
        void onFailure(Exception e);
    }

    public interface OnOrderDeletedListener {
        void onSuccess();
        void onFailure(Exception e);
    }

    public interface OnOrderItemsFetchedListener {
        void onSuccess(List<OrderItem> orderItems);
        void onFailure(Exception e);
    }
}
