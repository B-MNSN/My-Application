package com.example.myapplication.Activity;

import static com.example.myapplication.APIInterface.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapplication.APIInterface;
import com.example.myapplication.Adapter.CartListAdapter;
import com.example.myapplication.CartModel;
import com.example.myapplication.ProductModel;
import com.example.myapplication.databinding.ActivityCartBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartActivity extends AppCompatActivity {
    ActivityCartBinding binding;
    List<ProductModel> products = new ArrayList<>();
    CartListAdapter cartListAdapter;

    Double totalPrice = 0.0;

//    int user_id;
    Date date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cart);

        binding = ActivityCartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


//        products = (ArrayList<ProductModel>) getIntent().getSerializableExtra("listProduct");
//        for(ProductModel product : products){
//            binding.title.setText(product.getTitle());
//            binding.category.setText(product.getCategory());
//            binding.price.setText(product.getPrice().toString());
//            Picasso.get().load(product.getImage()).error(R.mipmap.ic_launcher_round).transform(new CircleTransform()).into(binding.imgProduct);
//
//        }

        init();
//        cart();
    }
    public void init() {
        products = (ArrayList<ProductModel>) getIntent().getSerializableExtra("listProduct");
        binding.listCart.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        cartListAdapter = new CartListAdapter(products);
        binding.listCart.setAdapter(cartListAdapter);

        for(ProductModel product : products){
            totalPrice += product.getPrice();

        }
        binding.totalPrice.setText(totalPrice.toString());

        SharedPreferences mPrefs = getSharedPreferences("MySharedPreference", MODE_PRIVATE);
        int user_id = mPrefs.getInt("id", 0);

        cartListAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartListAdapter.ViewHolder viewHolder =(CartListAdapter.ViewHolder) view.getTag();
                int position = viewHolder.getAdapterPosition();

                cartListAdapter.removeFormCart(position);
                cartListAdapter.notifyDataSetChanged();
            }
        });

        binding.confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart(user_id);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (cartListAdapter != null) {
            List<ProductModel> currentProduct = cartListAdapter.getCartList();
            Intent intent = new Intent(CartActivity.this, RecyclerViewActivity.class);
            intent.putExtra("listProduct", (Serializable) currentProduct);
            startActivity(intent);
            finish();
        }
    }

    public void cart(int user_id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface service = retrofit.create(APIInterface.class);

//        SharedPreferences mPrefs = getSharedPreferences("MySharedPreference", MODE_PRIVATE);
//        int user_id = mPrefs.getInt("user_id", 0);
//
//        Intent intent = getIntent();
//        if (intent != null){
//            List<ProductModel> productList = (List<ProductModel>) intent.getSerializableExtra("productList");
//            getUerInfo(user_id, productList);
//        }

        CartModel cartModel = new CartModel(0, user_id, new Date(), products);

        Call<CartModel> call = service.postCarts(cartModel);
        call.enqueue(new Callback<CartModel>() {
            @Override
            public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                if (response.code() == 200 && response.body() != null) {
                    CartModel body = response.body();

                    Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                    intent.putExtra("productList", (Serializable) body.getProducts());
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<CartModel> call, Throwable t) {
                Log.d("error", t.getMessage());
            }
        });
    }



}