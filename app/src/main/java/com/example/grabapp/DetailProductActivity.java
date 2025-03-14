package com.example.grabapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grabapp.adapter.CommentAdapter;
import com.example.grabapp.model.Comment;
import com.example.grabapp.model.Product;

import java.util.List;

public class DetailProductActivity extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName, productPrice, productDescription;
    private Button buyButton;

    private ImageButton btnAddReview;

    private ImageButton favorite_btn;
    private DatabaseHelper dbHelper;

    private Product product;

    private RecyclerView recyclerComments;
    private CommentAdapter commentAdapter;

    private List<Comment> commentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_product);

        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        productDescription = findViewById(R.id.product_description);
        buyButton = findViewById(R.id.buy_button);
        favorite_btn= findViewById(R.id.favorite_btn);

        btnAddReview = findViewById(R.id.btn_add_review);

        // Nhận dữ liệu Product từ Intent
        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("selected_product");

        if (product != null) {
            productName.setText(product.getName());
            productPrice.setText(product.getPrice() + " VND");
            productImage.setImageResource(product.getImageResId());
            productDescription.setText(product.getDescription());
        }

        // Xử lý khi nhấn nút "Mua ngay"
//        buyButton.setOnClickListener(v -> {
//            CartManager.addProduct(product);
//            Intent cartIntent = new Intent(DetailProductActivity.this, CartActivity.class);
//            startActivity(cartIntent);
//        });
//
//
//        favorite_btn.setOnClickListener(v -> {
//            FavoriteManager.addProduct(product);
//            Intent cartIntent = new Intent(DetailProductActivity.this, FavoriteActivity.class);
//            startActivity(cartIntent);
//        });


        dbHelper = new DatabaseHelper(this);

        recyclerComments = findViewById(R.id.review_list);
        recyclerComments.setLayoutManager(new LinearLayoutManager(this));

//        loadComments(); // Gọi để cập nhật danh sách bình luận

        commentList = dbHelper.getCommentsByProduct(product.getName());

        commentAdapter = new CommentAdapter(commentList);
        recyclerComments.setAdapter(commentAdapter);




        // Xử lý sự kiện khi nhấn nút "Thêm Bình Luận"
        btnAddReview.setOnClickListener(view -> showAddCommentDialog());


    }

    private void showAddCommentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm bình luận");

        // Tạo EditText để nhập bình luận
        final EditText input = new EditText(this);
        input.setHint("Nhập bình luận...");
        builder.setView(input);

        builder.setPositiveButton("Gửi", (dialog, which) -> {
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String userName = sharedPreferences.getString("username", "Chưa có username");
            String comment = input.getText().toString().trim();

            if (comment.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập nội dung bình luận!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Thêm vào database
            dbHelper.addComment(product.getName(), userName, comment);
            Toast.makeText(this, "Đã thêm bình luận!", Toast.LENGTH_SHORT).show();

            refreshComments();

        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        builder.show();
    }


    private void refreshComments() {
        commentList.clear(); // Xóa danh sách cũ
        commentList.addAll(dbHelper.getCommentsByProduct(product.getName())); // Lấy danh sách mới
        commentAdapter.notifyDataSetChanged(); // Cập nhật adapter
    }
}