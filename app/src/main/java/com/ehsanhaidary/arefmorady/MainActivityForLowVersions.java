package com.ehsanhaidary.arefmorady;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivityForLowVersions extends AppCompatActivity {

    ImageView homeFragmentImageView, favoriteFragmentImageView, aboutFragmentImageView;


    HomeFragment homeFragment;
    FavoriteFragment favoriteFragment;
    SettingsFragment settingsFragment;

    Boolean onBackPressedOnce;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_for_low_versions);


        homeFragmentImageView = findViewById(R.id.home_fragment_button);
        favoriteFragmentImageView = findViewById(R.id.favorite_fragment_button);
        aboutFragmentImageView = findViewById(R.id.about_fragment_button);

        onBackPressedOnce = false;

        if (savedInstanceState == null){
            homeFragment = new HomeFragment();
            favoriteFragment = new FavoriteFragment();
            settingsFragment = new SettingsFragment();
        }



        homeFragmentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_1, homeFragment).commit();
            }
        });



        favoriteFragmentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_1, favoriteFragment).commit();
            }
        });



        aboutFragmentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_1, settingsFragment).commit();
            }
        });

    }

    @Override
    public void onBackPressed() {
        try {
            getSupportFragmentManager().beginTransaction().remove(homeFragment).commit();
        }catch (Exception e){
        }

        try {
            getSupportFragmentManager().beginTransaction().remove(favoriteFragment).commit();
        }catch (Exception e){
        }

        try {
            getSupportFragmentManager().beginTransaction().remove(settingsFragment).commit();
        }catch (Exception e){
        }

        if (onBackPressedOnce){
            super.onBackPressed();
        }

        onBackPressedOnce = true;
        Toast.makeText(this, "جهت خروج از برنامه، دکمه برگشت را دوباره فشار دهید.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onBackPressedOnce = false;
            }
        }, 2000);

    }
}