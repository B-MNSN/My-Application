package com.example.myapplication.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainMenuBinding;

public class MainMenuActivity extends AppCompatActivity {

    ActivityMainMenuBinding binding;

    NavController navController;

    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_menu);

        binding = ActivityMainMenuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();


    }

    public void init() {
//        binding.fabWeb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainMenuActivity.this, RecyclerViewActivity.class));
//            }
//        });

        navController = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);


       dialog = new AlertDialog.Builder(MainMenuActivity.this)
                .setTitle("Exit")
                .setMessage("Do you want to exit an application?")
                .setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Clear token
                        SharedPreferences mPrefs = getSharedPreferences("MySharedPreference", MODE_PRIVATE);
                        SharedPreferences.Editor prefsEditor = mPrefs.edit();
                        prefsEditor.putString("token", "");
                        prefsEditor.apply();

                        Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", (dialog1, which) -> dialog1.dismiss())
                .create();

    }

    @Override
    public void onBackPressed() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        int backStackEntryCount = navHostFragment.getChildFragmentManager().getBackStackEntryCount();



        if (backStackEntryCount == 0 ){
            dialog.show();
        } else {
            super.onBackPressed();
        }

    }
}