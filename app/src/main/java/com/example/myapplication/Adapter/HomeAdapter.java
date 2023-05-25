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

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{
    private List<ProductModel> listProduct;
    private View.OnClickListener onClickListener;

    public HomeAdapter(List<ProductModel> listProduct) {
        this.listProduct = listProduct;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.list_product_home, parent, false);

        return new HomeAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        ProductModel value = listProduct.get(position);
        holder.title.setText(value.getTitle());
        holder.category.setText(value.getCategory());
        holder.price.setText(value.getPrice().toString());
        Picasso.get().load(value.getImage()).error(R.mipmap.ic_launcher_round).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, category, price;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            category = itemView.findViewById(R.id.category);
            img = itemView.findViewById(R.id.img_product);

            itemView.setTag(this);
            itemView.setOnClickListener(onClickListener);
        }
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}
