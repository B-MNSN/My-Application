package com.example.myapplication.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.APIInterface;
import com.example.myapplication.Adapter.DiscountAdapter;
import com.example.myapplication.ProductModel;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentDiscountBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiscountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscountFragment extends Fragment {
    FragmentDiscountBinding binding;

    List<ProductModel> productList;

    DiscountAdapter discountAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TITLE = "mTitle";

    // TODO: Rename and change types of parameters
    private String mTitle;

    public DiscountFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DiscountFragment newInstance(String mTitle) {
        DiscountFragment fragment = new DiscountFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_discount, container, false);
        binding = FragmentDiscountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        getProductAll();

        return view;
    }

    private void getProductAll() {
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
                    binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    discountAdapter = new DiscountAdapter(productList);
                    binding.recyclerview.setAdapter(discountAdapter);

                    discountAdapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                            int position = viewHolder.getAdapterPosition();

                            Bundle bundle = new Bundle();
                            bundle.putInt("product_id", productList.get(position).getId());
                            Navigation.findNavController(view).navigate(R.id.action_menu2_to_home2, bundle);

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                Toast.makeText(getActivity(), "STATUS : Failure" , Toast.LENGTH_SHORT).show();
            }
        });
    }


}