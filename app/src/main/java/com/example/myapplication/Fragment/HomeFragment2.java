package com.example.myapplication.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.APIInterface;
import com.example.myapplication.ProductModel;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityLoginBinding;
import com.example.myapplication.databinding.FragmentHome2Binding;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment2 extends Fragment {

    FragmentHome2Binding binding;

    ProductModel products;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TITLE = "mTitle";
    private static final String ARG_PRODUCT_ID = "product_id";

    // TODO: Rename and change types of parameters
    private String mTitle;
    private int productId;

    public HomeFragment2() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment2 newInstance(String mTitle) {
        HomeFragment2 fragment = new HomeFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, mTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
            productId = getArguments().getInt(ARG_PRODUCT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home2, container, false);

        binding = FragmentHome2Binding.inflate(inflater, container, false);
        View view = binding.getRoot();

        getProductId(productId);

        binding.description.setMovementMethod(ScrollingMovementMethod.getInstance());

        return view;

    }

    private void getProductId(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface service = retrofit.create(APIInterface.class);
        Call<ProductModel> call = service.getProductId(id);
        call.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                if (response.code() == 200) {
                    products = response.body();
                    binding.title.setText(products.getTitle());
                    binding.description.setText(products.getDescription());
                    binding.category.setText(products.getCategory());
                    binding.price.setText(products.getPrice().toString());
                    Picasso.get().load(products.getImage()).error(R.mipmap.ic_launcher_round).into(binding.imgProduct);
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                Toast.makeText(requireActivity(), "STATUS : Failure" , Toast.LENGTH_SHORT).show();
            }
        });
    }
}