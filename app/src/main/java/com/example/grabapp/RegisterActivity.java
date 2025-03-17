package com.example.grabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grabapp.model.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText edtEmail, edtPassword, edtUsername;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtUsername = findViewById(R.id.edtUserName);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        MaterialButton btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String username = edtUsername.getText().toString();

                if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    registerWithFirebase(email, password, username);
                }
            }
        });
    }

    private void registerWithFirebase(String email, String password, String username) {
        // Kiểm tra xem email đã được sử dụng chưa
        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().getSignInMethods().isEmpty()) {
                            // Email chưa được đăng ký, tiến hành đăng ký người dùng mới
                            mAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(this, task1 -> {
                                        if (task1.isSuccessful()) {
                                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                            if (firebaseUser != null) {
                                                // Tạo đối tượng User để lưu vào Firestore
                                                User user = new User(firebaseUser.getUid(), email, password, username, R.drawable.avartar);

                                                // Lưu thông tin người dùng vào Firestore
                                                db.collection("users").document(firebaseUser.getUid())
                                                        .set(user)
                                                        .addOnSuccessListener(aVoid -> {
                                                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                                            goToMainActivity();
                                                        })
                                                        .addOnFailureListener(e -> {
                                                            Toast.makeText(RegisterActivity.this, "Lỗi khi lưu thông tin người dùng: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                                        });
                                            }
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại: " + task1.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                        } else {
                            // Email đã được sử dụng
                            Toast.makeText(RegisterActivity.this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Lỗi khi kiểm tra email: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    // Chuyển đến MainActivity và đóng RegisterActivity
    private void goToMainActivity() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Đóng RegisterActivity
    }
}
