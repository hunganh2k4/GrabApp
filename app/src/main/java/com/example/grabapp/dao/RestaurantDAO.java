package com.example.grabapp.dao;

import com.example.grabapp.model.Product;
import com.example.grabapp.model.Restaurant;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantDAO {
    private FirebaseFirestore db;
    private CollectionReference restaurantCollection;

    public RestaurantDAO() {
        db = FirebaseFirestore.getInstance();
        restaurantCollection = db.collection("restaurants");
    }

    // Lưu nhà hàng vào Firestore
    public void addRestaurant(Restaurant restaurant) {
        String restaurantId = restaurant.getId(); // Lấy ID nhà hàng

        // Tạo bản đồ dữ liệu cho nhà hàng (không lưu danh sách sản phẩm tại đây)
        Map<String, Object> restaurantData = new HashMap<>();
        restaurantData.put("id", restaurant.getId());
        restaurantData.put("name", restaurant.getName());
        restaurantData.put("imageResId", restaurant.getImageResId());
        restaurantData.put("rating", restaurant.getRating());
        restaurantData.put("soldQuantity", restaurant.getSoldQuantity());

        // Lưu nhà hàng vào Firestore
        restaurantCollection.document(restaurantId).set(restaurantData)
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Nhà hàng đã được lưu thành công");

                    // Lưu danh sách sản phẩm vào subcollection "productList"
                    List<Product> productList = restaurant.getProductList();
                    if (productList != null) {
                        for (Product product : productList) {
                            saveProduct(restaurantId, product);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    System.err.println("Lỗi khi lưu nhà hàng: " + e.getMessage());
                });
    }

    // Lưu sản phẩm vào Firestore trong subcollection "productList"
    public void saveProduct(String restaurantId, Product product) {
        restaurantCollection.document(restaurantId)
                .collection("productList")
                .document(product.getId())  // Dùng ID sản phẩm làm document ID
                .set(product)
                .addOnSuccessListener(aVoid -> System.out.println("Sản phẩm " + product.getName() + " đã lưu thành công"))
                .addOnFailureListener(e -> System.err.println("Lỗi khi lưu sản phẩm: " + e.getMessage()));
    }


    // Lấy danh sách nhà hàng từ Firestore
    public void getAllRestaurants(OnFirestoreDataListener<List<Restaurant>> listener) {
        restaurantCollection.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Restaurant> restaurantList = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        Restaurant restaurant = document.toObject(Restaurant.class);
                        if (restaurant != null) {
                            String restaurantId = restaurant.getId();

                            // Gọi hàm lấy danh sách sản phẩm cho nhà hàng này
                            getProductList(restaurantId, new OnFirestoreDataListener<List<Product>>() {
                                @Override
                                public void onSuccess(List<Product> productList) {
                                    restaurant.setProductList(productList);
                                    restaurantList.add(restaurant);

                                    // Kiểm tra nếu tất cả nhà hàng đã tải xong sản phẩm thì trả về danh sách
                                    if (restaurantList.size() == queryDocumentSnapshots.size()) {
                                        listener.onSuccess(restaurantList);
                                    }
                                }

                                @Override
                                public void onFailure(String errorMessage) {
                                    listener.onFailure(errorMessage);
                                }
                            });
                        }
                    }
                })
                .addOnFailureListener(e -> listener.onFailure(e.getMessage()));
    }


    public void getProductList(String restaurantId, OnFirestoreDataListener<List<Product>> listener) {
        restaurantCollection.document(restaurantId)
                .collection("productList")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Product> productList = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        Product product = document.toObject(Product.class);
                        productList.add(product);
                    }
                    listener.onSuccess(productList);
                })
                .addOnFailureListener(e -> listener.onFailure(e.getMessage()));
    }

    // Interface callback để truyền dữ liệu
    public interface OnFirestoreDataListener<T> {
        void onSuccess(T data);
        void onFailure(String errorMessage);
    }
}
