package com.example.dianascanner;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class AboutMe extends AppCompatActivity {

    ImageView imgDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        imgDetail   = findViewById(R.id.det_image);

        hideNavigationBar();


        // Load image background using glide

        Glide.with(AboutMe.this)
                .load(R.drawable.frik)
                // set image blur
                .apply(bitmapTransform(new BlurTransformation(20)))
                .into(imgDetail);

    }

    private void hideNavigationBar() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }


    public void kembali(View view) {
        startActivity(new Intent(AboutMe.this, DashboardActivity.class));
    }

    public void btnBack(View view) {
        startActivity(new Intent(AboutMe.this, DashboardActivity.class));
    }
}