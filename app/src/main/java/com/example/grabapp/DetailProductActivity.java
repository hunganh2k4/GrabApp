package com.example.grabapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grabapp.adapter.CommentAdapter;
import com.example.grabapp.adapter.OrderAdapter;
import com.example.grabapp.dao.CommentDAO;
import com.example.grabapp.dao.UserDAO;
import com.example.grabapp.model.CartManager;
import com.example.grabapp.model.Comment;
import com.example.grabapp.model.FavoriteManager;
import com.example.grabapp.model.Product;
import com.example.grabapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import java.util.List;
import java.util.UUID;

public class DetailProductActivity extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName, productPrice, productDescription;
    private Button buyButton;
    private ImageButton btnAddReview;

    private ImageButton favorite_btn;
    private RecyclerView recyclerComments;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList;
    private CommentDAO commentDAO;
    private Product product;

    private UserDAO userDAO;
    private FirebaseAuth mAuth;

    private User user;


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
        btnAddReview = findViewById(R.id.btn_add_review);
        favorite_btn= findViewById(R.id.favorite_btn);


        mAuth = FirebaseAuth.getInstance();
        userDAO = new UserDAO();

        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("selected_product");

        if (product != null) {
            productName.setText(product.getName());
            productPrice.setText(product.getPrice() + " VND");
            productImage.setImageResource(product.getImageResId());
            productDescription.setText(product.getDescription());
        }


        buyButton.setOnClickListener(v -> {
            CartManager.addProduct(product);
            Intent cartIntent = new Intent(DetailProductActivity.this, CartActivity.class);
            startActivity(cartIntent);
        });

        favorite_btn.setOnClickListener(v -> {
            FavoriteManager.addProduct(product);
            Intent cartIntent = new Intent(DetailProductActivity.this, FavoriteActivity.class);
            startActivity(cartIntent);
        });

        if (mAuth.getCurrentUser() != null) {
            String email = mAuth.getCurrentUser().getEmail();

            // Gọi UserDAO để lấy thông tin user từ Firestore
            userDAO.getUserByEmail(email, new UserDAO.FirestoreUserCallback() {
                @Override
                public void onUserRetrieved(User fetchedUser) {
                    if (fetchedUser != null) {
                        user = fetchedUser;
                    } else {
                        user = new User();

                    }
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(DetailProductActivity.this, "Lỗi khi lấy user", Toast.LENGTH_SHORT).show();
                }
            });
        }


        commentDAO = new CommentDAO();
        recyclerComments = findViewById(R.id.review_list);
        recyclerComments.setLayoutManager(new LinearLayoutManager(this));




        commentDAO.getAllCommentsByProductId(product.getId(),new CommentDAO.OnFirestoreDataListener<List<Comment>>() {
            @Override
            public void onSuccess(List<Comment> comments) {
                Toast.makeText(DetailProductActivity.this, "Vui lòng"+comments.size(), Toast.LENGTH_SHORT).show();
                commentAdapter = new CommentAdapter(comments);
                recyclerComments.setAdapter(commentAdapter);
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("Firestore", "Lỗi: " + errorMessage);
            }
        });


        btnAddReview.setOnClickListener(view -> showAddCommentDialog());
    }

    private void showAddCommentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm bình luận");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 20, 50, 20);

        final EditText input = new EditText(this);
        input.setHint("Nhập bình luận...");
        layout.addView(input);

        final RatingBar ratingBar = new RatingBar(this);
        ratingBar.setNumStars(5);
        ratingBar.setStepSize(0.5f);
        ratingBar.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        ratingBar.setScaleX(0.5f); // Điều chỉnh kích thước nếu cần
        ratingBar.setScaleY(0.5f);
        layout.addView(ratingBar);

        builder.setView(layout);
        builder.setPositiveButton("Gửi", (dialog, which) -> {
            if (user == null) {
                Toast.makeText(this, "Vui lòng đăng nhập để bình luận!", Toast.LENGTH_SHORT).show();
                return;
            }

            String userName = user.getUsername();
            String commentText = input.getText().toString().trim();
            float rating = ratingBar.getRating();

            if (commentText.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập nội dung bình luận!", Toast.LENGTH_SHORT).show();
                return;
            }

            String commentId = UUID.randomUUID().toString();

            // Thêm bình luận vào Firestore
            Comment newComment = new Comment(commentId,product.getId(), userName, commentText, rating);
            commentDAO.addComment(newComment);

            Toast.makeText(this, "Bình luận đã được thêm!", Toast.LENGTH_SHORT).show();

            commentDAO.getAllCommentsByProductId(product.getId(),new CommentDAO.OnFirestoreDataListener<List<Comment>>() {
                @Override
                public void onSuccess(List<Comment> comments) {
                    commentAdapter.updateComments(comments); // Cập nhật Adapter
                }

                @Override
                public void onFailure(String errorMessage) {
                    Log.e("Firestore", "Lỗi cập nhật danh sách bình luận: " + errorMessage);
                }
            });
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

}

