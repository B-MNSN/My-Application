package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.CircleTransform;
import com.example.myapplication.ProductModel;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.ViewHolder>{
    private List<ProductModel> productList;
    private  View.OnClickListener onClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_heading, txt_detail, price, category, discount;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_heading = itemView.findViewById(R.id.heading);
            txt_detail = itemView.findViewById(R.id.detail);
            price = itemView.findViewById(R.id.price);
            category = itemView.findViewById(R.id.category);
            img = itemView.findViewById(R.id.img_product);
            discount = itemView.findViewById(R.id.discount);

            itemView.setTag(this);
            itemView.setOnClickListener(onClickListener);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.adapter_discount_layout, parent, false);

        return new DiscountAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel value = productList.get(position);
        holder.txt_heading.setText(value.getTitle());
        holder.txt_detail.setText(value.getDescription());
        holder.price.setText(value.getPrice().toString());
        holder.category.setText(value.getCategory());
        Picasso.get().load(value.getImage()).error(R.mipmap.ic_launcher_round).into(holder.img);
        holder.discount.setText(calculateDiscount(value.getPrice()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public DiscountAdapter(List<ProductModel> productList) {
        this.productList = productList;
    }

    private String calculateDiscount(double price){
        price =(price*(100-10))/100;
        String discount = String.format("%.2f", price);
        return discount;
    }
}
