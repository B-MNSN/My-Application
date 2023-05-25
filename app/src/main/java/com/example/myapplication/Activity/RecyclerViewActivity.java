package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.APIInterface;
import com.example.myapplication.Adapter.RecyclerAdapter;
import com.example.myapplication.DbHandler;
import com.example.myapplication.ProductModel;
import com.example.myapplication.databinding.ActivityRecyclerViewBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerViewActivity extends AppCompatActivity {

    ActivityRecyclerViewBinding binding;
    List<ProductModel> productList  = new ArrayList<>();

    RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list_view);
        binding = ActivityRecyclerViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getProductList();
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent != null) {
            List<ProductModel> currentList = (List<ProductModel>) intent.getSerializableExtra("listProduct");
            if (currentList != null ) {
                recyclerAdapter.updateCart(currentList);
            }
        }
    }

    public void init() {
//        productList = new ArrayList<>();
//        for (int i = 0; i < 20; i++){
//            productList.add(new ProductModel(i,"a","aaaaa","aa", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.goodhousekeeping.com%2Flife%2Fpets%2Fg4531%2Fcutest-dog-breeds%2F&psig=AOvVaw12DVY0AgJm22n1inaYOYNm&ust=1682656871549000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCMCRlbufyf4CFQAAAAAdAAAAABAE", 10.0));
//        }

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerAdapter = new RecyclerAdapter(productList);
        binding.recyclerview.setAdapter(recyclerAdapter);

//        ArrayAdapter adapter = new ArrayAdapter<>(this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, list);
//        binding.recyclerview.setAdapter(adapter);

        binding.btnViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ProductModel> selectProduct = recyclerAdapter.getAddToCartList();
                if (selectProduct != null) {
                    Intent intent = new Intent(RecyclerViewActivity.this, CartActivity.class);
                    intent.putExtra("listProduct", (Serializable) selectProduct);
                    startActivity(intent);
                }
            }
        });

        recyclerAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int position = viewHolder.getAdapterPosition();

//                Toast.makeText(RecyclerViewActivity.this, "Click : " + productList.get(position).getId(), Toast.LENGTH_SHORT).show();

                boolean isAdded = recyclerAdapter.addToChart(position);
                if (!isAdded) {
                    Toast.makeText(RecyclerViewActivity.this, "ADD PRODUCT" , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RecyclerViewActivity.this, "REMOVE PRODUCT" , Toast.LENGTH_SHORT).show();
                }

            }
        });

        recyclerAdapter.setLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int position = viewHolder.getAdapterPosition();

                Toast.makeText(RecyclerViewActivity.this, "Long Click : " + productList.get(position).getId(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void getProductList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface service = retrofit.create(APIInterface.class);
        Call<List<ProductModel>> call = service.getAllProduct();
        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                List<ProductModel> body = response.body();
                if (body != null && body.size() != 0) {
                    for (ProductModel obj : body){
                        productList.add(new ProductModel(obj.getId(), obj.getTitle(), obj.getDescription(), obj.getCategory(), obj.getImage(), obj.getPrice()));
                    }
                    getProductDb();
                    recyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                Toast.makeText(RecyclerViewActivity.this, "STATUS : Failure" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProductDb(){
        DbHandler db = new DbHandler(RecyclerViewActivity.this);
        ArrayList<ProductModel> list = db.GetProducts();
        productList.addAll(list);
        recyclerAdapter.notifyDataSetChanged();

    }
}