package com.example.grabapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.grabapp.R;
import com.example.grabapp.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends BaseAdapter {
    private Context context;
    private List<Product> productList;
    private boolean[] selectedItems;
    private OnTotalPriceChangeListener totalPriceChangeListener;

    public CartAdapter(Context context, List<Product> productList, OnTotalPriceChangeListener listener) {
        this.context = context;
        this.productList = productList;
        this.totalPriceChangeListener = listener;
        this.selectedItems = new boolean[productList.size()];
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
            holder = new ViewHolder();
            holder.chkSelectProduct = convertView.findViewById(R.id.chkSelectProduct);
            holder.imgCartProduct = convertView.findViewById(R.id.imgCartProduct);
            holder.txtCartProductName = convertView.findViewById(R.id.txtCartProductName);
            holder.txtCartProductPrice = convertView.findViewById(R.id.txtCartProductPrice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product product = productList.get(position);
        holder.txtCartProductName.setText(product.getName());
        holder.txtCartProductPrice.setText("Giá: " + product.getPrice() + " VND");
        holder.imgCartProduct.setImageResource(product.getImageResId());

        holder.chkSelectProduct.setChecked(selectedItems[position]);
        holder.chkSelectProduct.setOnCheckedChangeListener((buttonView, isChecked) -> {
            selectedItems[position] = isChecked;
            if (totalPriceChangeListener != null) {
                totalPriceChangeListener.onTotalPriceChanged(calculateTotalPrice());
            }
        });

        return convertView;
    }

    public int calculateTotalPrice() {
        int total = 0;
        for (int i = 0; i < productList.size(); i++) {
            if (selectedItems[i]) {
                total += productList.get(i).getPrice();
            }
        }
        return total;
    }


    public List<Product> getSelectedProducts() {
        List<Product> selectedProducts = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++) {
            if (selectedItems[i]) {
                selectedProducts.add(productList.get(i));
            }
        }
        return selectedProducts;
    }




    private static class ViewHolder {
        CheckBox chkSelectProduct;
        ImageView imgCartProduct;
        TextView txtCartProductName;
        TextView txtCartProductPrice;
    }

    public interface OnTotalPriceChangeListener {
        void onTotalPriceChanged(int totalPrice);
    }



}
