package com.example.grabapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.grabapp.model.Comment;
import com.example.grabapp.model.Order;
import com.example.grabapp.model.OrderItem;
import com.example.grabapp.model.Product;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "grab_app.db";
    private static final int DATABASE_VERSION = 1;

    // Bảng nhà hàng
    private static final String TABLE_RESTAURANTS = "restaurants";
    private static final String COLUMN_RESTAURANT_ID = "id";
    private static final String COLUMN_RESTAURANT_NAME = "name";
    private static final String COLUMN_RESTAURANT_IMAGE = "image";
    private static final String COLUMN_RESTAURANT_RATING = "rating";
    private static final String COLUMN_RESTAURANT_SOLD_QUANTITY = "sold_quantity";

    // Bảng sản phẩm
    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_PRODUCT_ID = "id";
    private static final String COLUMN_PRODUCT_NAME = "name";
    private static final String COLUMN_PRODUCT_PRICE = "price";
    private static final String COLUMN_PRODUCT_IMAGE = "image";
    private static final String COLUMN_PRODUCT_DESCRIPTION = "description";
    private static final String COLUMN_PRODUCT_RESTAURANT_ID = "restaurant_id";



    // Bảng đơn hàng
    private static final String TABLE_ORDERS = "orders";
    private static final String COLUMN_ORDER_ID = "id";
    private static final String COLUMN_ORDER_TOTAL_PRICE = "total_price";
    private static final String COLUMN_ORDER_DATE = "date";

    // Bảng chi tiết đơn hàng
    private static final String TABLE_ORDER_ITEMS = "order_items";
    private static final String COLUMN_ORDER_ITEM_ID = "id";
    private static final String COLUMN_ORDER_ITEM_ORDER_ID = "order_id";
    private static final String COLUMN_ORDER_ITEM_PRODUCT_NAME = "product_name";
    private static final String COLUMN_ORDER_ITEM_QUANTITY = "quantity";
    private static final String COLUMN_ORDER_ITEM_IMAGE = "product_image";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createRestaurantTable = "CREATE TABLE " + TABLE_RESTAURANTS + " ("
                + COLUMN_RESTAURANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_RESTAURANT_NAME + " TEXT NOT NULL, "
                + COLUMN_RESTAURANT_IMAGE + " INTEGER, "
                + COLUMN_RESTAURANT_RATING + " REAL, "
                + COLUMN_RESTAURANT_SOLD_QUANTITY + " INTEGER);";

        String createProductTable = "CREATE TABLE " + TABLE_PRODUCTS + " ("
                + COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + COLUMN_PRODUCT_PRICE + " INTEGER, "
                + COLUMN_PRODUCT_IMAGE + " INTEGER, "
                + COLUMN_PRODUCT_DESCRIPTION + " TEXT, "
                + COLUMN_PRODUCT_RESTAURANT_ID + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_PRODUCT_RESTAURANT_ID + ") REFERENCES " + TABLE_RESTAURANTS + "(" + COLUMN_RESTAURANT_ID + ") ON DELETE CASCADE);";


        String createOrderTable = "CREATE TABLE " + TABLE_ORDERS + " ("
                + COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ORDER_TOTAL_PRICE + " INTEGER, "
                + COLUMN_ORDER_DATE + " TEXT NOT NULL);";

        String createOrderItemsTable = "CREATE TABLE " + TABLE_ORDER_ITEMS + " ("
                + COLUMN_ORDER_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ORDER_ITEM_ORDER_ID + " INTEGER, "
                + COLUMN_ORDER_ITEM_PRODUCT_NAME + " TEXT, "
                + COLUMN_ORDER_ITEM_QUANTITY + " INTEGER, "
                + COLUMN_ORDER_ITEM_IMAGE + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_ORDER_ITEM_ORDER_ID + ") REFERENCES " + TABLE_ORDERS + "(" + COLUMN_ORDER_ID + ") ON DELETE CASCADE, "
                + "FOREIGN KEY(" + COLUMN_ORDER_ITEM_PRODUCT_NAME + ") REFERENCES " + TABLE_PRODUCTS + "(" + COLUMN_PRODUCT_NAME + ") ON DELETE CASCADE);";

        db.execSQL(createRestaurantTable);
        db.execSQL(createProductTable);
        db.execSQL(createOrderTable);
        db.execSQL(createOrderItemsTable);

        db.execSQL("INSERT INTO " + TABLE_RESTAURANTS + " (" + COLUMN_RESTAURANT_NAME + ", " + COLUMN_RESTAURANT_IMAGE + ", " + COLUMN_RESTAURANT_RATING + ", " + COLUMN_RESTAURANT_SOLD_QUANTITY + ") VALUES " +
                "('Bee Lattea', " + R.drawable.bee_lattea + ", 4.5, 1500), " +
                "('Hoa Sua Bun Dau', " + R.drawable.bun_dau + ", 4.8, 2300),"+
                "('Lotteria', " + R.drawable.lotteria + ", 4.2, 40000);"
        );

        // Thêm dữ liệu mẫu vào bảng sản phẩm
        db.execSQL("INSERT INTO " + TABLE_PRODUCTS + " (" + COLUMN_PRODUCT_NAME + ", " + COLUMN_PRODUCT_PRICE + ", " + COLUMN_PRODUCT_IMAGE + ", " + COLUMN_PRODUCT_DESCRIPTION + ", " + COLUMN_PRODUCT_RESTAURANT_ID + ") VALUES " +
                "('Trà Sữa Trân Châu', 50000, " + R.drawable.bee_lattea + ", 'Trà sữa thơm béo kết hợp với trân châu dai giòn.', 1), " +
                "('Trà Đào Cam Sả', 45000, " + R.drawable.bee_lattea + ", 'Trà đào tươi mát kết hợp với cam và sả.', 1), " +
                "('Bún Đậu Mắm Tôm', 60000, " + R.drawable.bun_dau + ", 'Món ăn đặc trưng miền Bắc với bún, đậu hũ giòn.', 2), " +
                "('Nem Chua Rán', 40000, " + R.drawable.bun_dau + ", 'Nem chua rán giòn rụm, chấm cùng tương ớt cay nồng.', 2),"+
                "('Gà rán', 59000, " + R.drawable.garan + ", 'Gà rán ai rán mà giòn', 3),"+
                "('Khoai tây chiên', 49000, " + R.drawable.khoaitaychien + ", 'Khoai tây chiên – Món ăn vặt giòn rụm, vàng ươm với lớp vỏ ngoài giòn tan và bên trong mềm mịn.', 3),"+
                "('Kem tươi', 5000, " + R.drawable.kem + ", 'Kem tươi mát lạnh.', 3); "



        );


        String createCommentsTable = "CREATE TABLE comments (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "product_name TEXT NOT NULL, " +
                "user_name TEXT NOT NULL, " +
                "content TEXT NOT NULL)";
        db.execSQL(createCommentsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS);
        onCreate(db);
    }

    // Lấy danh sách sản phẩm theo ID nhà hàng
//    public List<Product> getProductsByRestaurantId(int restaurantId) {
//        List<Product> products = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCT_RESTAURANT_ID + " = ?", new String[]{String.valueOf(restaurantId)});
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                String name = cursor.getString(1);
//                int price = cursor.getInt(2);
//                int image = cursor.getInt(3);
//                String description = cursor.getString(4);
//
//                products.add(new Product(name, price, image, description));
//            } while (cursor.moveToNext());
//            cursor.close();
//        }
//        return products;
//    }

    // Lấy tất cả sản phẩm
//    public List<Product> getAllProducts() {
//        List<Product> products = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                String name = cursor.getString(1);
//                int price = cursor.getInt(2);
//                int image = cursor.getInt(3);
//                String description = cursor.getString(4);
//
//                products.add(new Product(name, price, image, description)); // Chỉ thêm một lần
//            } while (cursor.moveToNext());
//            cursor.close();
//        }
//        return products;
//    }

    // Lấy danh sách nhà hàng
//    public List<Restaurant> getRestaurantList() {
//        List<Restaurant> restaurants = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RESTAURANTS, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                int id = cursor.getInt(0);
//                String name = cursor.getString(1);
//                int image = cursor.getInt(2);
//                float rating = cursor.getFloat(3);
//                int soldQuantity = cursor.getInt(4);
//                List<Product> productList = getProductsByRestaurantId(id);
//
//                restaurants.add(new Restaurant(name, image, rating, soldQuantity, productList));
//            } while (cursor.moveToNext());
//            cursor.close();
//        }
//        return restaurants;
//    }


//    public void addOrder(int totalPrice, String date, List<Product> orderItems) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues orderValues = new ContentValues();
//        orderValues.put(COLUMN_ORDER_TOTAL_PRICE, totalPrice);
//        orderValues.put(COLUMN_ORDER_DATE, date);
//
//        // Thêm đơn hàng vào bảng orders
//        long orderId = db.insert(TABLE_ORDERS, null, orderValues);
//
//        if (orderId != -1) { // Nếu thêm đơn hàng thành công
//            for (Product item : orderItems) {
//                ContentValues itemValues = new ContentValues();
//                itemValues.put(COLUMN_ORDER_ITEM_ORDER_ID, orderId);
//                itemValues.put(COLUMN_ORDER_ITEM_PRODUCT_NAME, item.getName());
//                itemValues.put(COLUMN_ORDER_ITEM_QUANTITY, 1);
//                itemValues.put(COLUMN_ORDER_ITEM_IMAGE, item.getImageResId());
//
//                // Thêm từng sản phẩm vào bảng order_items
//                db.insert(TABLE_ORDER_ITEMS, null, itemValues);
//            }
//        }
//
//        db.close();
//    }

//    public List<Order> getAllOrders() {
//        List<Order> orderList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ORDERS, null);
//
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                int id = cursor.getInt(0);
//                int totalPrice = cursor.getInt(1);
//                String date = cursor.getString(2);
//
//                // Lấy danh sách OrderItem cho Order này
//                List<OrderItem> orderItems = getOrderItemsByOrderId(id);
//
//                orderList.add(new Order(id, totalPrice, date, orderItems));
//            } while (cursor.moveToNext());
//            cursor.close();
//        }
//
//        return orderList;
//    }

    // Hàm lấy danh sách OrderItem theo OrderId
//    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
//        List<OrderItem> orderItems = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ORDER_ITEMS + " WHERE " + COLUMN_ORDER_ITEM_ORDER_ID + " = ?",
//                new String[]{String.valueOf(orderId)});
//
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                String productName = cursor.getString(2);
//                int quantity = cursor.getInt(3);
//                int productImage = cursor.getInt(4);
//
//                orderItems.add(new OrderItem(productName, quantity, productImage));
//            } while (cursor.moveToNext());
//            cursor.close();
//        }
//
//        return orderItems;
//
//    }


    // Thêm comment vào database (trả về void)
    public void addComment(String productName, String userName, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("product_name", productName);
        values.put("user_name", userName);
        values.put("content", content);

        try {
            db.insert("comments", null, values);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }


    public List<Comment> getCommentsByProduct(String productName) {
        List<Comment> comments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT product_name, user_name, content FROM Comments WHERE product_name = ?", new String[]{productName});

        if (cursor.moveToFirst()) {
            do {
                String product = cursor.getString(0);
                String userName = cursor.getString(1);
                String content = cursor.getString(2);
                comments.add(new Comment(product, userName, content));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return comments;
    }


}