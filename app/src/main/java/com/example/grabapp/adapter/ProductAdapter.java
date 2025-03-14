package com.example.grabapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.grabapp.CartActivity;
import com.example.grabapp.DetailProductActivity;
import com.example.grabapp.R;
import com.example.grabapp.model.CartManager;
import com.example.grabapp.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> productList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    private OnItemLongClickListener onItemLongClickListener;
    public interface OnItemLongClickListener {
        void onItemLongClick(Product product, int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    public ProductAdapter(Context context, List<Product> productList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.productList = productList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.txtProductName.setText(product.getName());
        holder.txtPrice.setText(product.getPrice() + " VND");
        holder.imgProduct.setImageResource(product.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailProductActivity.class);
            intent.putExtra("selected_product", product);
            context.startActivity(intent);
        });

        holder.btnAddToCart.setOnClickListener(v -> {
            CartManager.addProduct(product);
            Intent intent = new Intent(context, CartActivity.class);
            context.startActivity(intent);
        });


        // Long click vào sản phẩm
        holder.itemView.setOnLongClickListener(v -> {
            if (onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClick(product, position);
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductName, txtPrice;
        ImageView imgProduct;
        ImageButton btnAddToCart;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}