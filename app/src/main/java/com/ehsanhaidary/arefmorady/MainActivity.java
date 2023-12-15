package com.ehsanhaidary.arefmorady;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    ImageView imageView;
    TextView textView;
    Animation duration, bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.logo);
        textView = findViewById(R.id.splash_screen_text);

        duration = AnimationUtils.loadAnimation(this, R.anim.duration_anim);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);
        imageView.setAnimation(duration);
        textView.setAnimation(bottom);

        Intent intent = new Intent(this, MainActivity2.class);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            intent = new Intent(this, MainActivityForLowVersions.class);
        }



        final Intent finalIntent = intent;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(finalIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}