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

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<ProductModel> mList;
    private  View.OnClickListener onClickListener;

    private View.OnLongClickListener longClickListener;

    private List<ProductModel> mSelectList = new ArrayList<>();


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_heading, txt_detail, price, category;
        ImageView img;
        Button addToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_heading = itemView.findViewById(R.id.heading);
            txt_detail = itemView.findViewById(R.id.detail);
            price = itemView.findViewById(R.id.price);
            category = itemView.findViewById(R.id.category);
            img = itemView.findViewById(R.id.img_product);
            addToCart = itemView.findViewById(R.id.add_to_cart);

            addToCart.setTag(this);
            addToCart.setOnClickListener(onClickListener);

            //            itemView.setTag(this);
//            itemView.setOnClickListener(onClickListener);

//            txt_heading.setTag(this);
//            txt_heading.setOnClickListener(onClickListener);
//            txt_heading.setOnLongClickListener(longClickListener);
        }
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setLongClickListener(View.OnLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public RecyclerAdapter(List<ProductModel> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.adapter_recycler_layout, parent, false);

        return new RecyclerAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel value = mList.get(position);
        holder.txt_heading.setText(value.getTitle());
        holder.txt_detail.setText(value.getDescription());
        holder.price.setText(value.getPrice().toString());
        holder.category.setText(value.getCategory());
        Picasso.get().load(value.getImage()).error(R.mipmap.ic_launcher_round).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public  boolean addToChart(int position) {
        boolean isAdded = false;

        for (ProductModel obj : mSelectList) {
            if (mList.get(position).equals(obj)) {
                isAdded = true;
                break;
            }
        }

        if ( isAdded ) {
            mSelectList.remove(mList.get(position));
        } else {
            mSelectList.add(mList.get(position));
        }
        return isAdded;
    }

    public List<ProductModel> getAddToCartList() {
        return mSelectList;
    }


    public void updateCart(List<ProductModel> currentList) {
        mSelectList = currentList;
        notifyDataSetChanged();
    }


}
