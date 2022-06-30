package com.example.dianascanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    ImageView imageView;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Fungsi untuk menyembunyikan navbar

        setContentView(R.layout.activity_splash_screen);
        imageView= (ImageView) findViewById(R.id.ic_splash);
        textView= (TextView) findViewById(R.id.text_splash);
        hideNavigationBar();
        animateIcon();
        animateText();



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                finish();
            }
        }, 2000L);

    }

    private void animateText() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.tambahkecil);
        textView.startAnimation(animation);

    }

    private void animateIcon() {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.tambahbesar);

        imageView.startAnimation(animation);
    }

    private void hideNavigationBar() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}