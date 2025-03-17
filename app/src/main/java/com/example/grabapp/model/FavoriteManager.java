package com.example.grabapp.model;

import java.util.ArrayList;
import java.util.List;

public class FavoriteManager {
    private static final List<Restaurant> favoriteRestaurants = new ArrayList<>();
    private static final List<Product> favoriteProducts = new ArrayList<>();

    public static List<Restaurant> getFavoriteRestaurants() {
        return favoriteRestaurants;
    }

    public static List<Product> getFavoriteProducts() {
        return favoriteProducts;
    }

    public static void addRestaurant(Restaurant restaurant) {
        if (!favoriteRestaurants.contains(restaurant)) {
            favoriteRestaurants.add(restaurant);
        }
    }

    public static void addProduct(Product product) {
        if (!favoriteProducts.contains(product)) {
            favoriteProducts.add(product);
        }
    }


    public static void removeFavoriteRestaurant(Restaurant restaurant) {
        favoriteRestaurants.remove(restaurant);
    }

    public static void removeFavoriteProduct(Product product) {
        favoriteProducts.remove(product);
    }
}
