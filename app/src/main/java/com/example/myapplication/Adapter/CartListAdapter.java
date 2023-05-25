package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.CircleTransform;
import com.example.myapplication.ProductModel;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder>{
    private List<ProductModel> cartList;

    private  View.OnClickListener onClickListener;
    public CartListAdapter(List<ProductModel> cartList) {
        this.cartList = cartList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, category, price, total_product;
        ImageView img;
        Button btnRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            category = itemView.findViewById(R.id.category);
            img = itemView.findViewById(R.id.img_product);
            btnRemove = itemView.findViewById(R.id.remove_order);

            btnRemove.setTag(this);
            btnRemove.setOnClickListener(onClickListener);
        }
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public CartListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.list_cart_layout, parent, false);

        return new CartListAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListAdapter.ViewHolder holder, int position) {
        ProductModel value = cartList.get(position);
        holder.title.setText(value.getTitle());
        holder.category.setText(value.getCategory());
        holder.price.setText(value.getPrice().toString());
        Picasso.get().load(value.getImage()).error(R.mipmap.ic_launcher_round).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public void removeFormCart(int position) {
        cartList.remove(position);
    }

    public List<ProductModel> getCartList() {
        return cartList;
    }

    public void setCartList(List<ProductModel> cartList) {
        this.cartList = cartList;
    }
}
