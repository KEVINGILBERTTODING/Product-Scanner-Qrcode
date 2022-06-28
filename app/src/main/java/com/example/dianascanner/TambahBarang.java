package com.example.dianascanner;

import androidx.annotation.NonNull;
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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dianascanner.Model.BarangModel;
import com.example.dianascanner.Utill.AppController;
import com.example.dianascanner.Utill.DataApi;
import com.example.dianascanner.Utill.InterfaceBarang;
import com.example.dianascanner.Utill.ServerAPI;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kosalgeek.android.photoutil.GalleryPhoto;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahBarang extends AppCompatActivity {
    EditText xkd_brg, xnm_brg, xhrg_brg, xjml_brg, xsatuan_brg;
    InterfaceBarang interfaceBarang;
    Button btnImage;
    ImageView imageView;
    ProgressDialog pd;

    Bitmap bitmap;

    GalleryPhoto mGalery;
    private final int TAG_GALLERY = 2222;
    String selected_photo = null;

    public static final int progress_bar_type = 0;
    private ProgressDialog pDialog;



    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refBarang = database.getReference("Barang");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_barang);


        xkd_brg = findViewById(R.id.xkode);
        xnm_brg = findViewById(R.id.xnmbrg);
        xhrg_brg = findViewById(R.id.xharga);
        xjml_brg = findViewById(R.id.xjumlah);
        xsatuan_brg = findViewById(R.id.xsatuan);
        btnImage    =   findViewById(R.id.btn_img);
        imageView   =   findViewById(R.id.inp_gambar);
        interfaceBarang = DataApi.getClient().create(InterfaceBarang.class);


        pd = new ProgressDialog(TambahBarang.this);
        mGalery = new GalleryPhoto(getApplicationContext());

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivityForResult(mGalery.openGalleryIntent(),TAG_GALLERY);
                ActivityCompat.requestPermissions(
                        TambahBarang.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        TAG_GALLERY
                );
            }
        });
    }


    public void simpandata(View view) {
        String kode_brg = xkd_brg.getText().toString();
        String nama_brg = xnm_brg.getText().toString();
        String harga_brg = xhrg_brg.getText().toString();
        String jumlah_brg = xjml_brg.getText().toString();
        String satuan_brg = xsatuan_brg.getText().toString();


        tambahData(kode_brg, nama_brg, harga_brg, jumlah_brg, satuan_brg);
        simpanGambar();

        Call<BarangModel> postBarang = interfaceBarang.postBarang(kode_brg,
                nama_brg, harga_brg, jumlah_brg, satuan_brg);
        postBarang.enqueue(new Callback<BarangModel>() {
            @Override
            public void onResponse(Call<BarangModel> call, Response<BarangModel>
                    response) {
                Toast.makeText(TambahBarang.this, "Save data Success",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TambahBarang.this,MainActivity.class);
                startActivity(intent);



            }
            @Override
            public void onFailure(Call<BarangModel> call, Throwable t) {
                Toast.makeText(TambahBarang.this, "Save data not Success",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void tambahData(String kode, String nama, String harga, String jumlah, String satuan) {
        String barang = refBarang.push().getKey();

        refBarang.child(barang).child("kode").setValue(kode);
        refBarang.child(barang).child("nama").setValue(nama);
        refBarang.child(barang).child("harga").setValue(harga);
        refBarang.child(barang).child("jumlah").setValue(jumlah);
        refBarang.child(barang).child("satuan").setValue(satuan);
        Toast.makeText(TambahBarang.this, "Berhasil menambahkan data ke firebase", Toast.LENGTH_LONG).show();
    }

    private void simpanGambar(){
        pd.setMessage("Mengirim Data");
        pd.setCancelable(false);
        pd.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        StringRequest sendData = new StringRequest(Request.Method.POST, ServerAPI.URL_SIMPAN_GAMBAR,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        pd.cancel();
                        Log.d("TAG", "onResponse: "+response);
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(TambahBarang.this, "pesan : " +
                                    res.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(getApplicationContext(), TambahBarang.class);

                        startActivity(intent);
                        finish();
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Toast.makeText(TambahBarang.this, "pesan : Gagal Kirim Data", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {


                Map<String, String> map = new HashMap<>();
                map.put("kode", xkd_brg.getText().toString());
                map.put("gambar", imageString);
                return map;
            }
        };


        AppController.getInstance().addToRequestQueue(sendData);


    }


    public void btnback(View view) {
        startActivity(new Intent(TambahBarang.this,MainActivity.class));
        finish();
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
                Toast.makeText(this, "Tidak ada perizinan untuk mengakses gambar", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode ==  RESULT_OK && requestCode == TAG_GALLERY && data != null && data.getData() != null){

            Uri uri_path = data.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri_path);
                imageView.setImageBitmap(bitmap);

                Snackbar.make(findViewById(android.R.id.content), "Success Loader Image", Snackbar.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();

                Snackbar.make(findViewById(android.R.id.content), "Something Wrong", Snackbar.LENGTH_SHORT).show();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
