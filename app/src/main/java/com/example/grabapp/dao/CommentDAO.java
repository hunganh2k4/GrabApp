package com.example.grabapp.dao;

import android.util.Log;
import com.example.grabapp.model.Comment;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    private FirebaseFirestore db;
    private CollectionReference commentCollection;

    public CommentDAO() {
        db = FirebaseFirestore.getInstance();
        commentCollection = db.collection("comments");
    }

    // Thêm bình luận vào Firestore
    public void addComment(Comment comment) {
        String commentId = comment.getId(); // Lấy ID bình luận

        commentCollection.document(commentId).set(comment)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Bình luận đã được thêm thành công"))
                .addOnFailureListener(e -> Log.e("Firestore", "Lỗi khi thêm bình luận", e));
    }

    // Lấy tất cả bình luận
    public void getAllComments(OnFirestoreDataListener<List<Comment>> listener) {
        commentCollection.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Comment> commentList = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        Comment comment = document.toObject(Comment.class);
                        commentList.add(comment);
                    }
                    listener.onSuccess(commentList);
                })
                .addOnFailureListener(e -> listener.onFailure(e.getMessage()));
    }
    // Lấy tất cả bình luận theo productId
    public void getAllCommentsByProductId(String productId, OnFirestoreDataListener<List<Comment>> listener) {
        commentCollection.whereEqualTo("productId", productId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Comment> commentList = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        Comment comment = document.toObject(Comment.class);
                        commentList.add(comment);
                    }
                    listener.onSuccess(commentList);
                })
                .addOnFailureListener(e -> listener.onFailure(e.getMessage()));
    }

    // Lấy danh sách bình luận theo productId
    public void getCommentsByProductId(String productId, OnFirestoreDataListener<List<Comment>> listener) {
        commentCollection.whereEqualTo("productId", productId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Comment> commentList = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        Comment comment = document.toObject(Comment.class);
                        commentList.add(comment);
                    }
                    listener.onSuccess(commentList);
                })
                .addOnFailureListener(e -> listener.onFailure(e.getMessage()));
    }

    // Xóa bình luận bằng ID
    public void deleteComment(String commentId) {
        commentCollection.document(commentId)
                .delete()
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Bình luận đã bị xóa"))
                .addOnFailureListener(e -> Log.e("Firestore", "Lỗi khi xóa bình luận", e));
    }

    // Interface callback để truyền dữ liệu từ Firestore
    public interface OnFirestoreDataListener<T> {
        void onSuccess(T data);
        void onFailure(String errorMessage);
    }
}
