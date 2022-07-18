package com.example.productscanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.productscanner.Model.ProfileModel;
import com.example.productscanner.Utill.DataApi;
import com.example.productscanner.Utill.InterfaceProfile;
import com.google.android.material.snackbar.Snackbar;
import com.kosalgeek.android.photoutil.GalleryPhoto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class EditProfile extends AppCompatActivity {
    Button btnSimpan;
    EditText etAbout;
    ImageView imgProfile;
    ProgressDialog progressDialog;
    Bitmap bitmap;
    TextView tv_gallery;

    GalleryPhoto mGalery;
    private final int TAG_GALLERY = 2222;
    String selected_photo = null;

    public static final int progress_bar_type = 0;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        btnSimpan = findViewById(R.id.btn_simpan);
        etAbout = findViewById(R.id.etAbout);
        imgProfile = findViewById(R.id.imageview_profile);
        tv_gallery =  findViewById(R.id.tv_gallery);


        progressDialog = new ProgressDialog(EditProfile.this);
        mGalery = new GalleryPhoto(getApplicationContext());

        tv_gallery.setOnClickListener(view -> {
            ActivityCompat.requestPermissions(
                    EditProfile.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    TAG_GALLERY
            );
        });

        getProfile();


    }

    // Method untuk mendapatkan about user

    private void getProfile() {
       ProgressDialog progressDialog = new ProgressDialog(EditProfile.this);
         progressDialog.setMessage("Sedang mengambil data...");
            progressDialog.show();

        InterfaceProfile interfaceProfile = DataApi.getClient().create(InterfaceProfile.class);
        Call<List<ProfileModel>> call = interfaceProfile.getUser(2);
        call.enqueue(new retrofit2.Callback<List<ProfileModel>>() {
            @Override
            public void onResponse(Call<List<ProfileModel>> call, retrofit2.Response<List<ProfileModel>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    List<ProfileModel> profileModels = response.body();
                    for (ProfileModel profileModel : profileModels){
                        etAbout.setText(profileModel.getAbout());
                        Glide.with(EditProfile.this).load(profileModel.getImage()).into(imgProfile);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ProfileModel>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditProfile.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == TAG_GALLERY){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //gallery intent
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, TAG_GALLERY);
            }
            else {
                Toast.makeText(this, "Tidak ada akses gallery", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode ==  RESULT_OK && requestCode == TAG_GALLERY && data != null && data.getData() != null){

            Uri uri_path = data.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri_path);
                imgProfile.setImageBitmap(bitmap);

                Snackbar.make(findViewById(android.R.id.content), "Berhasil memuat gambar", Snackbar.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();

                Snackbar.make(findViewById(android.R.id.content), "Something Wrong", Snackbar.LENGTH_SHORT).show();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}