package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.APIInterface;
import com.example.myapplication.AuthModel;
import com.example.myapplication.RegisterModel;
import com.example.myapplication.databinding.ActivitySplashScreenBinding;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashScreenActivity extends AppCompatActivity {

    ActivitySplashScreenBinding binding;

    AuthModel authModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash_screen);

        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();

                checkToken();
            }
        }, 10*1000);
    }


    private void checkToken() {
        SharedPreferences mPrefs = getSharedPreferences("MySharedPreference", MODE_PRIVATE);
        String token = mPrefs.getString("token", "");

        Intent intent;
        if (!token.isEmpty()) {
            intent = new Intent(SplashScreenActivity.this, MainMenuActivity.class);
        } else {
            intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        }

        startActivity(intent);
        finish();
    }

}