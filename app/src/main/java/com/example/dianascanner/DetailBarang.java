package com.example.dianascanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

public class DetailBarang extends AppCompatActivity {

    ImageView imgBarang;
    TextView kd_brg, nm_brg, hrg_brg;
    String kode, nama, harga;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);

        imgBarang = findViewById(R.id.det_gambar_produk);
        kd_brg = findViewById(R.id.det_kode_barang);
        nm_brg = findViewById(R.id.det_nama_barang);
        hrg_brg = findViewById(R.id.det_harga);
        btnBack =   findViewById(R.id.btnBack);


        Intent intent = getIntent();
        kode = intent.getStringExtra("kd_brg");
        nama = intent.getStringExtra("nm_brg");
        harga = intent.getStringExtra("hrg_brg");

        kd_brg.setText(kode);
        nm_brg.setText(nama);
        hrg_brg.setText(harga);

        Glide.with(this)
                .load("http://192.168.11.19/qrcode/image_product/"+kode+".png")
                .thumbnail(0.5f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imgBarang);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailBarang.this, DashboardActivity.class);
                startActivity(intent);
            }
        });



    }
}