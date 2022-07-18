package com.example.productscanner;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class AboutMe extends AppCompatActivity {

    ImageView imgDetail;
    SharedPreferences sharedpreferences;
    Button updatePassword, btn_edit_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        imgDetail   = findViewById(R.id.det_image);
        Button btnLogout = findViewById(R.id.btn_logout);
        btn_edit_profile = findViewById(R.id.btn_edit_profile);
        updatePassword = findViewById(R.id.btn_udpate_password);

        hideNavigationBar();


        // Load image background using glide

        Glide.with(AboutMe.this)
                .load(R.drawable.frik)
                // set image blur
                .apply(bitmapTransform(new BlurTransformation(20)))
                .into(imgDetail);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences =getSharedPreferences("logindata", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                finish();
            }
        });
        updatePassword.setOnClickListener(view ->{
            startActivity(new Intent(AboutMe.this, UpdatePassword.class));
        });

        btn_edit_profile.setOnClickListener(view -> {
            startActivity(new Intent(AboutMe.this, EditProfile.class));
        });

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