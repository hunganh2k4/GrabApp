package com.example.grabapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grabapp.dao.UserDAO;
import com.example.grabapp.model.User;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TextView editUserName,edtEmail;
    ImageView avatar;


    private UserDAO userDAO;

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);


        editUserName=findViewById(R.id.editUserName);
        edtEmail=findViewById(R.id.edtEmail);
        avatar=findViewById(R.id.avatar);




        mAuth = FirebaseAuth.getInstance();
        userDAO = new UserDAO();


        // Lấy email của user đang đăng nhập
        if (mAuth.getCurrentUser() != null) {
            String email = mAuth.getCurrentUser().getEmail();

            // Gọi UserDAO để lấy thông tin user từ Firestore
            userDAO.getUserByEmail(email, new UserDAO.FirestoreUserCallback() {
                @Override
                public void onUserRetrieved(User fetchedUser) {
                    if (fetchedUser != null) {
                        user = fetchedUser;
                        // Cập nhật giao diện khi dữ liệu đã có
                        editUserName.setText(user.getUsername());
                        edtEmail.setText(user.getEmail());
                        if (user.getImageResId() != 0) {  // Kiểm tra nếu imageId hợp lệ
                            avatar.setImageResource(user.getImageResId());
                        }

                    } else {
                        user = new User();
                        editUserName.setText("Không có username");
                        edtEmail.setText("Không có email");
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(ProfileActivity.this, "Lỗi khi lấy user", Toast.LENGTH_SHORT).show();
                }
            });
        }










//        editUserName.setText(username);




        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            logout();
        });
    }


    private void logout() {
        mAuth.signOut(); // Đăng xuất khỏi Firebase

        // Chuyển về màn hình đăng nhập
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Đóng MainActivity
    }

}