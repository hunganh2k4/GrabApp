package com.example.grabapp.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.grabapp.model.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class UserDAO {
    private FirebaseFirestore db;

    public UserDAO() {
        db = FirebaseFirestore.getInstance();
    }

    // Interface để xử lý callback khi lấy User thành công
    public interface FirestoreUserCallback {
        void onUserRetrieved(User user);
        void onFailure(Exception e);
    }

    // Lấy user từ Firestore bằng email
    public void getUserByEmail(String email, FirestoreUserCallback callback) {
        db.collection("users")
                .whereEqualTo("email", email) // Tìm kiếm theo email
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (!querySnapshot.isEmpty()) {
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                User user = document.toObject(User.class);
                                callback.onUserRetrieved(user);
                                return; // Chỉ lấy user đầu tiên tìm được
                            }
                        } else {
                            callback.onUserRetrieved(null); // Không tìm thấy user
                        }
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }
}
