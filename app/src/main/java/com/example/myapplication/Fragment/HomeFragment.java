package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.APIInterface;
import com.example.myapplication.Activity.AddProductActivity;
import com.example.myapplication.Activity.CategoryActivity;
import com.example.myapplication.Activity.RecyclerViewActivity;
import com.example.myapplication.Adapter.HomeAdapter;
import com.example.myapplication.Adapter.ViewPagerAdapter;
import com.example.myapplication.ProductModel;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;

    HomeAdapter homeAdapter;

    List<ProductModel> productList  = new ArrayList<>();
    List<String> listImg = new ArrayList<>();

    int currentPage = 0;
    int NUM_PAGES = 0;

    private TextView[] dots;
    int colorsActive;
    int colorsInactive;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TITLE = "mTitle";

    // TODO: Rename and change types of parameters
    private String mTitle;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String mTitle) {
        HomeFragment fragment = new HomeFragment();
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
//        return inflater.inflate(R.layout.fragment_home, container, false);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        init();

        return view;

    }

    public void init() {
        binding.viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RecyclerViewActivity.class));
            }
        });
        binding.viewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CategoryActivity.class));
            }
        });
        binding.btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddProductActivity.class));
            }
        });
        getProductHome();

        listImg.add("https://www.papayatop.com/wp-content/uploads/2022/06/shopee-banner-2.jpg");
        listImg.add("https://www.papayatop.com/wp-content/uploads/2022/08/shopee-banner-3.jpg");
        listImg.add("https://static.trueplookpanya.com/cmsblog/1684/89684/banner_file.jpg");
        listImg.add("https://shop.thairubik.com/wp-content/uploads/2020/04/banner-to-shopee-from-website.jpg");

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(listImg);
        binding.viewPager.setAdapter(viewPagerAdapter);

        NUM_PAGES = listImg.size();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                binding.viewPager.setCurrentItem(currentPage++,true);
            }
        },100,2*1000);

        colorsActive = requireActivity().getColor(R.color.white);
        colorsInactive = requireActivity().getColor(R.color.light_gray);

        addBottomDots(currentPage, NUM_PAGES);

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeBottomDots(position);
            }
        });

    }

    private void addBottomDots(int currentPage, int viewPagerSize) {
        dots = new TextView[viewPagerSize];
        binding.layoutDots.removeAllViews();
        for (int i = 0; i < dots.length; i++){
            dots[i] = new TextView(requireActivity());
            dots[i].setText(Html.fromHtml("&#8226;", Html.FROM_HTML_MODE_LEGACY));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive);
            binding.layoutDots.addView(dots[i]);
        }
        if (dots.length > 0){
            dots[currentPage].setTextColor(colorsActive);
        }
    }

    private void changeBottomDots(int currentPage) {
        for (int i = 0; i < dots.length; i++) {
            if (i == currentPage){
                dots[i].setTextColor(colorsActive);
            } else {
                dots[i].setTextColor(colorsInactive);
            }
        }
    }
    private void getProductHome(){
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
                if (response != null && productList.size() != 0) {
                    productList = random(productList);

                    binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    homeAdapter = new HomeAdapter(productList);
                    binding.recyclerview.setAdapter(homeAdapter);

                    homeAdapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                            int position = viewHolder.getAdapterPosition();

                            Bundle bundle = new Bundle();
                            bundle.putInt("product_id", productList.get(position).getId());
                            Navigation.findNavController(view).navigate(R.id.action_menu1_to_home2, bundle);
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

    private List<ProductModel> random(List<ProductModel> list) {
        Random random = new Random();
        List<ProductModel> randomList = new ArrayList<>();

        int size = 5;
        if (list.size() < size) {
            size = list.size() - 1;
        }

        for (int i = 0; i < size; i++) {
            randomList.add(list.get(random.nextInt(list.size())));
        }
        return randomList;
    }

}