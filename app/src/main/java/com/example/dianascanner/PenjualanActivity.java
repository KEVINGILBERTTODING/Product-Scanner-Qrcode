package com.example.dianascanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.dianascanner.Model.TransaksiModel;
import com.example.dianascanner.Utill.DataApi;
import com.example.dianascanner.Utill.InterfaceTransaksi;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenjualanActivity extends AppCompatActivity {

    TextView kd_brg;
    TextView nm_brg;
    TextView hrg_brg;
    TextView jml_brg;
    TextView satuan_brg;
    String kode_brg, nama_brg, harga_brg, jumlah_brg, satuan_barg, tanggal, waktu;
    ImageButton btn_back;
    ImageView img_qrcode, imgSuccces;
    TextView nameProduct;

    Button save;

    InterfaceTransaksi interfaceTransaksi;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refPenjualan = database.getReference("Penjualan");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);

        initilize();

        // Mengambil data menggunakan intent

        Intent intent = getIntent();
        kd_brg.setText(intent.getStringExtra("kd_brg2"));
        nm_brg.setText(intent.getStringExtra("nm_brg2"));
        hrg_brg.setText(intent.getStringExtra("hrg_brg2"));
        jml_brg.setText(intent.getStringExtra("jml_brg2"));
        satuan_brg.setText(intent.getStringExtra("satuan_brg2"));

        getDateTime();

        // Disable Edittext
        disableEdittext();

        // Set value ke edittexxt

        kode_brg = kd_brg.getText().toString();
        nama_brg = nm_brg.getText().toString();
        harga_brg = hrg_brg.getText().toString();
        jumlah_brg = jml_brg.getText().toString();
        satuan_barg = satuan_brg.getText().toString();


        // set image qrcode
        Glide.with(this)
                .load("http://adelaide.my.id/image_product/"+kode_brg+".png")
                .thumbnail(0.5f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(img_qrcode);

        // Animaasi image product
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        img_qrcode.startAnimation(animation);

        nameProduct.setText(nama_brg);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PenjualanActivity.this, TransaksiActivity.class);
                startActivity(intent);
            }
        });





    }

    private void disableEdittext() {
        kd_brg.setEnabled(false);
        nm_brg.setEnabled(false);
        hrg_brg.setEnabled(false);
        jml_brg.setEnabled(false);
        satuan_brg.setEnabled(false);
    }

    // Method Mengambil tanggal dan waktu saat ini

    private void getDateTime() {
        tanggal = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        waktu = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

    }

    private void initilize() {
        kd_brg = findViewById(R.id.inKdBrg);
        nm_brg = findViewById(R.id.inNmBrg);
        hrg_brg = findViewById(R.id.inHrgBrg);
        jml_brg = findViewById(R.id.inJumlahBrg);
        satuan_brg = findViewById(R.id.inSatuanBrg);
        save = findViewById(R.id.btnSave);
        img_qrcode = findViewById(R.id.img_qrcode);
        btn_back = findViewById(R.id.btnBack4);
        imgSuccces = findViewById(R.id.img_success);
        nameProduct = findViewById(R.id.nama_product);
    }

    public void simpandata(View view) {
        DataApi.getClient().create(InterfaceTransaksi.class).simpanPenjualan(kode_brg, jumlah_brg).enqueue(new Callback<TransaksiModel>() {
            @Override
            public void onResponse(Call<TransaksiModel> call, Response<TransaksiModel> response) {
                if (response.isSuccessful()) {

                    // Memanggil method simpan ke firebase
                    simpanPenjualan(kode_brg, nama_brg, harga_brg, jumlah_brg, satuan_barg, tanggal, waktu);

                    Toast.makeText(PenjualanActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PenjualanActivity.this, TransaksiActivity.class));
                } else {
                    Toast.makeText(PenjualanActivity.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<TransaksiModel> call, Throwable t) {
                Toast.makeText(PenjualanActivity.this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
            }
        });

    }


    // Method simpan data ke dalam firebase

    private void simpanPenjualan(String kode_brg, String nama_brg, String harga_brg, String jumlah_brg, String satuan_barg, String tanggal, String waktu) {
        String penjualan = refPenjualan.push().getKey();

        refPenjualan.child(penjualan).child("kode").setValue(kode_brg);
        refPenjualan.child(penjualan).child("nama").setValue(nama_brg);
        refPenjualan.child(penjualan).child("harga").setValue(harga_brg);
        refPenjualan.child(penjualan).child("jumlah").setValue(jumlah_brg);
        refPenjualan.child(penjualan).child("satuan").setValue(satuan_barg);
        refPenjualan.child(penjualan).child("tanggal").setValue(tanggal);
        refPenjualan.child(penjualan).child("waktu").setValue(waktu);
        Toast.makeText(PenjualanActivity.this, "Berhasil menambahkan data ke firebase", Toast.LENGTH_LONG).show();
    }
}
