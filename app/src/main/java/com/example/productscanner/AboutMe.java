package com.example.productscanner;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.productscanner.Model.ProfileModel;
import com.example.productscanner.Utill.DataApi;
import com.example.productscanner.Utill.InterfaceProfile;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;

public class AboutMe extends AppCompatActivity {

    ImageView imgDetail;
    SharedPreferences sharedpreferences;
    Button updatePassword, btn_edit_profile, btnLogout;
    TextView tv_username, tv_desc;
    ImageView photo_profile, photo_sampul;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        initilize();

        hideNavigationBar();


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

        getProfile();

    }

    private void initilize() {
        btnLogout = findViewById(R.id.btn_logout);
        btn_edit_profile = findViewById(R.id.btn_edit_profile);
        updatePassword = findViewById(R.id.btn_udpate_password);
        tv_username =  findViewById(R.id.tv_det_username);
        tv_desc = findViewById(R.id.tv_desc);
        photo_profile = findViewById(R.id.photo_profile);
        photo_sampul = findViewById(R.id.photo_sampul);

    }


    private void hideNavigationBar() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    // Method untuk mendapatkan about user

    private void getProfile() {

        SharedPreferences sharedPreferences=getSharedPreferences("logindata",MODE_PRIVATE);
        Boolean counter=sharedPreferences.getBoolean("logincounter",Boolean.valueOf(String.valueOf(MODE_PRIVATE)));
        String username=sharedPreferences.getString("useremail",String.valueOf(MODE_PRIVATE));

        tv_username.setText(username);



        ProgressDialog progressDialog = new ProgressDialog(AboutMe.this);
        progressDialog.setMessage("Sedang mengambil data...");
        progressDialog.show();

        InterfaceProfile interfaceProfile = DataApi.getClient().create(InterfaceProfile.class);
        Call<List<ProfileModel>> call = interfaceProfile.getUser(username);
        call.enqueue(new retrofit2.Callback<List<ProfileModel>>() {
            @Override
            public void onResponse(Call<List<ProfileModel>> call, retrofit2.Response<List<ProfileModel>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    List<ProfileModel> profileModels = response.body();
                    for (ProfileModel profileModel : profileModels){
                        tv_desc.setText(profileModel.getAbout());

//                         Load photo profile

                        Glide.with(AboutMe.this)
                                .load(profileModel.getImage())
                                .skipMemoryCache(true)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .into(photo_profile);

                        // Load foto sampul with blurred  image

                        Glide.with(AboutMe.this)
                                .load(profileModel.getImage())
                                .skipMemoryCache(true)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                // set image blur
                                .apply(bitmapTransform(new BlurTransformation(20)))
                                .into(photo_sampul);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ProfileModel>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AboutMe.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void kembali(View view) {
        startActivity(new Intent(AboutMe.this, DashboardActivity.class));
    }

    public void btnBack(View view) {
        startActivity(new Intent(AboutMe.this, DashboardActivity.class));
    }

}