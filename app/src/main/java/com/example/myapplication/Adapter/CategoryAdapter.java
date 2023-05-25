package com.example.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ProductModel;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> implements Filterable {

    private List<ProductModel> productList;
    private List<ProductModel> productAllList;
    private  View.OnClickListener onClickListener;
    Context context;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String keyword = charSequence.toString();
                if (keyword.equalsIgnoreCase("all")){
                    productList = productAllList;
                } else {
                    List<ProductModel> list = new ArrayList<>();
                    for (ProductModel productModel : productAllList) {
                        if (productModel.getCategory().equalsIgnoreCase(keyword)) {
                            list.add(productModel);
                        }
                    }
                    productList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = productList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productList = (List<ProductModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };

    }

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
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.adapter_discount_layout, parent, false);

        return new CategoryAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel value = productList.get(position);
        holder.txt_heading.setText(value.getTitle());
        holder.txt_detail.setText(value.getDescription());
        holder.discount.setText(value.getPrice().toString());
        holder.category.setText(value.getCategory());
        Picasso.get().load(value.getImage()).error(R.mipmap.ic_launcher_round).into(holder.img);
        holder.price.setVisibility(View.GONE);
        holder.discount.setTextColor(context.getColor(R.color.black));
        holder.discount.setCompoundDrawableTintList(ColorStateList.valueOf(context.getColor(R.color.black)));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public CategoryAdapter(List<ProductModel> productList) {
        this.productList = productList;
        this.productAllList = productList;
    }

    public void sort(String sort) {
        if (sort.equalsIgnoreCase("asc")){
            productList.sort(new Comparator<ProductModel>() {
                @Override
                public int compare(ProductModel obj1, ProductModel obj2) {
                    //less to more
                    return obj1.getPrice().compareTo(obj2.getPrice());
                }
            });
        } else {
            productList.sort(new Comparator<ProductModel>() {
                @Override
                public int compare(ProductModel obj1, ProductModel obj2) {
                    //more to less
                    return obj2.getPrice().compareTo(obj1.getPrice());
                }
            });
        }
        notifyDataSetChanged();
    }

    public CategoryAdapter(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
