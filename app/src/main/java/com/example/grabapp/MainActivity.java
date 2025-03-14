package com.example.grabapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grabapp.adapter.HotSpotAdapter;
import com.example.grabapp.adapter.ProductAdapter;
import com.example.grabapp.adapter.RestaurantAdapter;
import com.example.grabapp.dao.RestaurantDAO;
import com.example.grabapp.model.HotSpot;
import com.example.grabapp.model.Product;
import com.example.grabapp.model.Restaurant;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private EditText edtSearch;
    private final String[] words = {"Tìm món", "Tìm địa điểm"};
    private int wordIndex = 0;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable charRunnable;
    private boolean isAnimating = true; // Kiểm soát trạng thái chạy hiệu ứng



    private RecyclerView recyclerView;
    private HotSpotAdapter adapter;
    private List<HotSpot> hotSpotList;

    private DatabaseHelper databaseHelper;
    private List<Restaurant> restaurantList;

    private List<Product> productList;

    private RestaurantDAO restaurantDAO;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


//        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
//        String username = sharedPreferences.getString("username", "Chưa có username");
//        String password = sharedPreferences.getString("password", "Chưa có password");
//
//        // Hiển thị thông tin đăng nhập
//        Toast.makeText(this, "Username: " + username + "\nPassword: " + password, Toast.LENGTH_LONG).show();


        ImageView imgUserIcon = findViewById(R.id.imageUserIcon);
        imgUserIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        edtSearch = findViewById(R.id.edtSearch);



//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//
//        hotSpotList = new ArrayList<>();
//        hotSpotList.add(new HotSpot("AEON MALL", 25, R.drawable.aeonmall));
//        hotSpotList.add(new HotSpot("Saigon Centre", 15, R.drawable.saigoncenter));
//        hotSpotList.add(new HotSpot("SC VivoCity", 20, R.drawable.vivocity));
//
//        adapter = new HotSpotAdapter(this, hotSpotList);
//        recyclerView.setAdapter(adapter);



//        //recycle view quan an
//        List<Product> beeLatteaProducts = new ArrayList<>();
//        beeLatteaProducts.add(new Product(UUID.randomUUID().toString(),"Trà Sữa Trân Châu", 50000, R.drawable.bee_lattea, "Trà sữa thơm béo kết hợp với trân châu dai giòn, mang đến hương vị ngọt ngào và sảng khoái.", 4.8f, 100));
//        beeLatteaProducts.add(new Product(UUID.randomUUID().toString(),"Trà Đào Cam Sả", 45000, R.drawable.bee_lattea, "Trà đào tươi mát kết hợp với cam và sả, tạo nên một thức uống thanh nhiệt, thơm ngon, giúp giải khát hiệu quả.",4.5f, 200));
//
//        List<Product> hoaSuaProducts = new ArrayList<>();
//        hoaSuaProducts.add(new Product("Bún Đậu Mắm Tôm", 60000, R.drawable.bun_dau, "Món ăn đặc trưng miền Bắc với bún, đậu hũ giòn, thịt luộc, chả cốm, ăn kèm mắm tôm đậm đà."));
//        hoaSuaProducts.add(new Product("Nem Chua Rán", 40000, R.drawable.bun_dau, "Nem chua rán giòn rụm, thơm ngon, chấm cùng tương ớt cay nồng tạo nên hương vị khó quên."));
//
//        List<Restaurant> restaurantList = new ArrayList<>();
//        restaurantList.add(new Restaurant("Bee Lattea", R.drawable.bee_lattea, 4.5f, 1500, beeLatteaProducts));
//        restaurantList.add(new Restaurant("Hoa Sua Bun Dau", R.drawable.bun_dau, 4.8f, 2300, hoaSuaProducts));


        restaurantDAO = new RestaurantDAO();
//        Restaurant restaurant = new Restaurant(UUID.randomUUID().toString(),"Bee Lattea", R.drawable.bee_lattea, 4.5f, 1500, beeLatteaProducts);
//        restaurantDAO.addRestaurant(restaurant);




//        databaseHelper = new DatabaseHelper(this);

        // Lấy danh sách nhà hàng từ database
//        restaurantList = databaseHelper.getRestaurantList();
//
////
//
////
//
////
        RecyclerView restaurantRecyclerView = findViewById(R.id.quananrecyclerView);
        restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        restaurantDAO.getAllRestaurants(new RestaurantDAO.OnFirestoreDataListener<List<Restaurant>>() {
            @Override
            public void onSuccess(List<Restaurant> data) {
                RestaurantAdapter adapter = new RestaurantAdapter(MainActivity.this, data, restaurant -> {
                  Intent intent = new Intent(MainActivity.this, FoodActivity.class);
                  intent.putExtra("restaurant", restaurant);
                  startActivity(intent);
                });
//
                restaurantRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(String errorMessage) {
                System.err.println("Lỗi khi lấy dữ liệu: " + errorMessage);
            }
        });
//
//        RestaurantAdapter adapter = new RestaurantAdapter(this, restaurantList, restaurant -> {
//            Intent intent = new Intent(MainActivity.this, FoodActivity.class);
//            intent.putExtra("restaurant", restaurant);
//            startActivity(intent);
//        });
//
//        restaurantRecyclerView.setAdapter(adapter);





        //animation
        animateText();

        // Khi người dùng nhấn vào EditText, dừng hiệu ứng và xóa nội dung
        edtSearch.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                stopAnimation();
                edtSearch.setText("");
            } else {
                isAnimating = true;
                animateText(); // Khi mất focus thì chạy lại hiệu ứng
            }
        });


//        productList=databaseHelper.getAllProducts();
//
//        Collections.shuffle(productList);
//
//        // Giới hạn danh sách tối đa 5 sản phẩm
//        List<Product> randomProducts = productList.size() > 5 ? productList.subList(0, 5) : productList;
//
//
//
//        ProductAdapter productAdapter = new ProductAdapter(this, randomProducts, product -> {
//            // Xử lý sự kiện khi chọn sản phẩm
////                Intent productIntent = new Intent(FoodActivity.this, ProductDetailActivity.class);
////                productIntent.putExtra("product", product);
////                startActivity(productIntent);
//        });
//
//        RecyclerView productRecyclerView = findViewById(R.id.randomrecyclerView);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        productRecyclerView.setLayoutManager(layoutManager);
//        productRecyclerView.setAdapter(productAdapter);



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                return true;
            } else if (itemId == R.id.nav_search) {
                startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
                return true;
            } else if (itemId == R.id.nav_messenger) {
//                Toast.makeText(MainActivity.this, "Tin nhắn", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, OrderActivity.class));
                return true;
            } else if (itemId == R.id.nav_cart) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                return true;
            }
            return false;
        });
    }

    // Hiển thị từng chữ cái
    private void animateText() {
        if (!isAnimating) return; // Nếu đang bị dừng thì không chạy

        final String word = words[wordIndex];
        edtSearch.setText("");
        final int[] charIndex = {0};

        charRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isAnimating) return; // Kiểm tra nếu bị dừng

                if (charIndex[0] < word.length()) {
                    edtSearch.append(Character.toString(word.charAt(charIndex[0])));
                    charIndex[0]++;
                    handler.postDelayed(this, 200);
                } else {
                    animateSize();
                }
            }
        };

        handler.post(charRunnable);
    }

    // Phóng to chữ
    private void animateSize() {
        if (!isAnimating) return; // Nếu dừng thì không chạy tiếp

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(edtSearch, "scaleX", 1f, 1.3f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(edtSearch, "scaleY", 1f, 1.3f, 1f);

        scaleX.setDuration(500);
        scaleY.setDuration(500);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleX, scaleY);
        set.start();

        handler.postDelayed(() -> {
            wordIndex = (wordIndex + 1) % words.length;
            animateText();
        }, 1000);
    }

    // Dừng hiệu ứng
    private void stopAnimation() {
        isAnimating = false;
        handler.removeCallbacks(charRunnable);
    }
}
