package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ProductModel;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>{

    private List<String> listImg;
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.image);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.adapter_view_pager_layout, parent, false);

        return new ViewPagerAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String value = listImg.get(position);
        Picasso.get().load(value).error(R.mipmap.ic_launcher_round).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return listImg.size();
    }

    public ViewPagerAdapter(List<String> listImg) {
        this.listImg = listImg;
    }
}
