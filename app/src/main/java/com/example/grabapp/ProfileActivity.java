package com.example.grabapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TextView editUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);


        editUserName=findViewById(R.id.editUserName);

//        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
//        String username = sharedPreferences.getString("username", "Chưa có username");
//        String password = sharedPreferences.getString("password", "Chưa có password");


        mAuth = FirebaseAuth.getInstance();






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