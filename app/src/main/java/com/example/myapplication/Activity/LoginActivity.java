package com.example.myapplication.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.APIInterface;
import com.example.myapplication.AuthModel;
import com.example.myapplication.RegisterModel;
import com.example.myapplication.databinding.ActivityLoginBinding;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    AuthModel authModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main3);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    public void init(){
        binding.username.setText("michael.lawson@reqres.in");
        binding.password.setText("1234");
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.username.getText().toString();
                String password = binding.password.getText().toString();

                if ("".equals(username) || "".equals(password)) {
                    Toast.makeText(LoginActivity.this, "Username or password Empty", Toast.LENGTH_SHORT).show();
                } else {
                    boolean login = getSharedPreference(username, password);
//
                    if (login) {
                        apiUser(username,password);

//                        startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
//                        finish();
                    } else {
//                    Toast.makeText(MainActivity3.this, "Username or password Incorrect", Toast.LENGTH_SHORT).show();

                        dialog();
                    }


                }
            }
        });

        binding.regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

    }

    private boolean getSharedPreference(String username, String password) {
        SharedPreferences mPrefs = getSharedPreferences("MySharedPreference", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = mPrefs.getString("MyObject", "");
        RegisterModel obj = gson.fromJson(json, RegisterModel.class);

        boolean isLoggedIn = false;
        if (obj != null) {
            if (password.equals(obj.getPassword())) {
                isLoggedIn = true;
            }
        }
        return isLoggedIn;
    }

    private void apiUser(String email, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.REGISTER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface service = retrofit.create(APIInterface.class);
        Call<AuthModel> call = service.postAuth(email,password);
        call.enqueue(new Callback<AuthModel>() {
            @Override
            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                if (response.code() == 200 ){
                    authModel = response.body();

                    SharedPreferences mPrefs = getSharedPreferences("MySharedPreference", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    prefsEditor.putString("token", authModel.getToken());
//                    prefsEditor.putInt("id", authModel.id);
                    prefsEditor.apply();

                    startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "STATUS : Failure " + response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AuthModel> call, Throwable t) {
//                Toast.makeText(LoginActivity.this, "STATUS : Failure" , Toast.LENGTH_SHORT).show();
                dialog();
            }
        });
    }

    private void dialog(){
        AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Login")
                .setMessage("Incorrect username or password")
                .setPositiveButton("OK", ((dialog1, which) -> dialog1.dismiss()))
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Username or password Incorrect", Toast.LENGTH_SHORT).show();
                    }
                })
                .create();

        dialog.show();
    }
}