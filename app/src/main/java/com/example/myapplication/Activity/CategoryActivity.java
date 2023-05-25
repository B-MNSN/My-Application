package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.APIInterface;
import com.example.myapplication.Adapter.CategoryAdapter;
import com.example.myapplication.Adapter.DiscountAdapter;
import com.example.myapplication.Adapter.RecyclerAdapter;
import com.example.myapplication.DbHandler;
import com.example.myapplication.Fragment.HomeFragment2;
import com.example.myapplication.ProductModel;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityCategoryBinding;
import com.example.myapplication.databinding.ActivityRecyclerViewBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryActivity extends AppCompatActivity {

    ActivityCategoryBinding binding;
    CategoryAdapter categoryAdapter;
    List<ProductModel> productList  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_category);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();


    }
    public void init(){
        getProductList();
        getProductCategory();


        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu1) {
                    categoryAdapter.sort("asc");
                    return true;
                } else if (item.getItemId() == R.id.menu2){
                    categoryAdapter.sort("desc");
                    return true;
                } else {
                    return false;
                }
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
                productList = response.body();
                if (productList != null && productList.size() != 0) {
                    binding.recyclerview.setLayoutManager(new LinearLayoutManager(CategoryActivity.this, LinearLayoutManager.VERTICAL, false));
                    categoryAdapter = new CategoryAdapter(productList);
                    binding.recyclerview.setAdapter(categoryAdapter);
                }
                getProductDb();

            }


            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                Toast.makeText(CategoryActivity.this, "STATUS : Failure" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProductCategory() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface service = retrofit.create(APIInterface.class);
        Call<List<String>> call = service.getProductCategory();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                List<String> categories = response.body();
                if (categories != null && categories.size() != 0) {
                    Menu menu = binding.navigation.getMenu();

//                    DbHandler db = new DbHandler(CategoryActivity.this);
//                    ArrayList<String> listCatDb = db.GetCategory();
//                    categories.addAll(listCatDb);
                    ArrayList<String> listCat = getCate();
                    categories.addAll(listCat);
                    menu.add("ALL");
                    for (String category : categories) {
                        menu.add(category);
                    }


                    binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                                binding.drawerLayout.closeDrawer(GravityCompat.START);
                            } else {
                                binding.drawerLayout.openDrawer(GravityCompat.START);
                            }
                        }
                    });

                    binding.navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            categoryAdapter.getFilter().filter(item.getTitle());
                            binding.drawerLayout.closeDrawer(GravityCompat.START);
                            return true;
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }

    private void getProductDb(){
        DbHandler db = new DbHandler(CategoryActivity.this);
        ArrayList<ProductModel> list = db.GetProducts();
        productList.addAll(list);
        categoryAdapter.notifyDataSetChanged();

    }

    private ArrayList<String> getCate(){
        DbHandler db = new DbHandler(CategoryActivity.this);
        ArrayList<String> listCatDb = db.GetCategory();
        return listCatDb;
    }
}