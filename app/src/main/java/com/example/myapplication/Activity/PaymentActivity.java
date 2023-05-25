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
import com.example.myapplication.Adapter.PaymentAdapter;
import com.example.myapplication.ProductModel;
import com.example.myapplication.UserModel;
import com.example.myapplication.databinding.ActivityPaymentBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentActivity extends AppCompatActivity {

    ActivityPaymentBinding binding;

    PaymentAdapter paymentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_payment);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        SharedPreferences mPrefs = getSharedPreferences("MySharedPreference", MODE_PRIVATE);
        int user_id = mPrefs.getInt("id", 0);

        Intent intent = getIntent();
        if (intent != null) {
            List<ProductModel> productList = (List<ProductModel>) intent.getSerializableExtra("productList");
            getUserInfo(user_id, productList);
        }

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentActivity.this, MainMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getUserInfo(int user_id, List<ProductModel> productList) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface service = retrofit.create(APIInterface.class);
        Call<UserModel> call = service.getUseById(user_id);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.code() == 200 && response.body() != null) {
                    UserModel body = response.body();
                    setInfo(body, productList);
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("error", t.getMessage());
            }
        });
    }

    private void setInfo(UserModel userModel,List<ProductModel> productList){

        binding.name.setText(userModel.getName().getFirstname().concat(" ").concat(userModel.getName().getLastname()));
        binding.email.setText(userModel.getEmail());
        binding.phone.setText(userModel.getPhone());

        binding.address.setText(String.valueOf(userModel.getAddress().getNumber()).concat(" ")
                .concat(userModel.getAddress().getStreet()).concat(" ")
                .concat(userModel.getAddress().getCity()).concat(" ")
                .concat(userModel.getAddress().getZipcode()));

        binding.listProduct.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        paymentAdapter = new PaymentAdapter(productList);
        binding.listProduct.setAdapter(paymentAdapter);


    }
}