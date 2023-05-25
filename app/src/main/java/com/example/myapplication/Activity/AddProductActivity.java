package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.DbHandler;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityAddProductBinding;
import com.example.myapplication.databinding.ActivityPaymentBinding;

public class AddProductActivity extends AppCompatActivity {

    ActivityAddProductBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_product);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    public void init(){
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName = binding.editProductName.getText().toString();
                String description = binding.editProductDes.getText().toString();
                String category = binding.editProductCategory.getText().toString();
                String price = binding.editProductPrice.getText().toString();
                DbHandler dbHandler = new DbHandler(AddProductActivity.this);
                dbHandler.insertProductDetails(productName, description, category, price);
                Intent intent = new Intent(AddProductActivity.this, RecyclerViewActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "Success",Toast.LENGTH_LONG).show();
            }
        });
    }
}