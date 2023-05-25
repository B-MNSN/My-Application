package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.APIInterface;
import com.example.myapplication.AuthModel;
import com.example.myapplication.CircleTransform;
import com.example.myapplication.R;
import com.example.myapplication.RegisterModel;
import com.example.myapplication.databinding.ActivityConfirmRegisterBinding;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfirmRegisterActivity extends AppCompatActivity {
    ActivityConfirmRegisterBinding binding;
    Boolean isFemale = false;
    Uri imageUri;
    RegisterModel model;
    AuthModel authModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);

        binding = ActivityConfirmRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        model = (RegisterModel) getIntent().getSerializableExtra("registerModel");
        ArrayList<String> hobbyList = model.getHobbyList();
        String imgUri = model.getImageURI();
        Boolean gender = model.isGender();
        String title = model.getTitle();

        if (imgUri != null) {
            imageUri = Uri.parse(imgUri);
        }

        Picasso.get()
                .load(imageUri)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .transform(new CircleTransform())
                .into(binding.imgProfile, new com.squareup.picasso.Callback() {

                    @Override
                    public void onSuccess() {
                        Log.d("success: ", "success");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("Error: ", e.getMessage());
                    }
                });


        StringBuilder result = new StringBuilder();
        result.append("Hobby: ");

        binding.prefix.setText(title);

        StringBuilder firstNameBuilder = new StringBuilder();
        firstNameBuilder.append("First name: ").append(model.getFirstname());
        binding.firstname.setText(firstNameBuilder);

        StringBuilder lastNameBuilder = new StringBuilder();
        lastNameBuilder.append("Last name: ").append(model.getLastname());
        binding.lastname.setText(lastNameBuilder);

        StringBuilder birthdayBuilder = new StringBuilder();
        birthdayBuilder.append("Birthday: ").append(model.getBirthday());
        binding.birthday.setText(birthdayBuilder);

        StringBuilder ageBuilder = new StringBuilder();
        ageBuilder.append("Age: ").append(model.getAge());
        binding.age.setText(ageBuilder);

        StringBuilder addressBuilder = new StringBuilder();
        addressBuilder.append("Address: ").append(model.getAddress());
        binding.address.setText(addressBuilder);

        StringBuilder provinceBuilder = new StringBuilder();
        provinceBuilder.append("Province: ").append(model.getProvince());
        binding.province.setText(provinceBuilder);

        StringBuilder postcodeBuilder = new StringBuilder();
        postcodeBuilder.append("Postcode: ").append(model.getPostcode());
        binding.postcode.setText(postcodeBuilder);

        StringBuilder university = new StringBuilder();
        university.append("University: ").append(model.getUniversity());
        binding.university.setText(university);

//        }

        if (hobbyList != null && hobbyList.size() != 0) {
            for (int i = 0; i < hobbyList.size(); i++) {
                result.append(hobbyList.get(i));
                result.append(", ");
            }

            binding.textHobby.setText(result);
        }

        if (gender) {
            binding.gender.setText("Gender: Female");
        } else {
            binding.gender.setText("Gender: Male");
        }

        if (binding.switchOnOff.isChecked()) {
            binding.infoUser.setVisibility(View.VISIBLE);
        } else {
            binding.infoUser.setVisibility(View.GONE);
        }

        binding.switchOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.switchOnOff.isChecked()) {
                    binding.infoUser.setVisibility(View.VISIBLE);
                } else {
                    binding.infoUser.setVisibility(View.GONE);
                }
            }
        });

        binding.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                SharedPreferences mPrefs = getSharedPreferences("MySharedPreference", MODE_PRIVATE);
//
//                SharedPreferences.Editor prefsEditor = mPrefs.edit();
//                Gson gson = new Gson();
//                String json = gson.toJson(model);
//                prefsEditor.putString("MyObject", json);
//                prefsEditor.apply();
//
//                startActivity(new Intent(ConfirmRegisterActivity.this, LoginActivity.class));
//                finish();

                apiUser(model.getEmail(), model.getPassword());
            }
        });


    }

    private void apiUser(String email, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.REGISTER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface service = retrofit.create(APIInterface.class);
        Call<AuthModel> call = service.postResister(email,password);
        call.enqueue(new Callback<AuthModel>() {
            @Override
            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                if (response.code() == 200) {
                    authModel = response.body();
                    SharedPreferences mPrefs = getSharedPreferences("MySharedPreference", MODE_PRIVATE);

                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(model);
                    prefsEditor.putString("MyObject", json);
                    prefsEditor.putString("token", authModel.getToken());
                    prefsEditor.putInt("id", authModel.getId());
                    prefsEditor.apply();

                    startActivity(new Intent(ConfirmRegisterActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(ConfirmRegisterActivity.this, "STATUS : Failure " + response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AuthModel> call, Throwable t) {
                Toast.makeText(ConfirmRegisterActivity.this, "STATUS : Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }


}