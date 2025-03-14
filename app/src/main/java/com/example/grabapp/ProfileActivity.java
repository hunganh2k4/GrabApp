package com.example.grabapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {

    TextView editUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        editUserName=findViewById(R.id.editUserName);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Chưa có username");
        String password = sharedPreferences.getString("password", "Chưa có password");




        editUserName.setText(username);




        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {


            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear(); // Xóa toàn bộ dữ liệu đăng nhập
            editor.apply();


            // Chuyển về màn hình đăng nhập
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });



    }
}