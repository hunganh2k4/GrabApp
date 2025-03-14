package com.example.grabapp.model;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final ArrayList<Product> cartProducts = new ArrayList<>();

    // Lấy danh sách sản phẩm trong giỏ hàng
    public static ArrayList<Product> getCartProducts() {
        return cartProducts;
    }

    // Thêm sản phẩm vào giỏ hàng (tránh trùng lặp)
    public static void addProduct(Product product) {
        if (!cartProducts.contains(product)) {
            cartProducts.add(product);
        }
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public static void removeProduct(Product product) {
        cartProducts.remove(product);
    }

    // Xóa toàn bộ giỏ hàng
    public static void clear() {
        cartProducts.clear();
    }

    // Xóa sản phẩm đã chọn khỏi giỏ hàng
    public static void removeSelectedProducts(List<Product> selectedProducts) {
        cartProducts.removeAll(selectedProducts);
    }

    public static double getTotalPrice() {
        double total = 0;
        for (Product product : cartProducts) {
            total += product.getPrice(); // Giả sử Product có phương thức getPrice()
        }
        return total;
    }
}
