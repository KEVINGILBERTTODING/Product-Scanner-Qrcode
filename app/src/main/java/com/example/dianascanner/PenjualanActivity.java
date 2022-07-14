package com.example.dianascanner;

import static com.example.dianascanner.Utill.ServerAPI.Base_url;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    EditText jumlah_penjualan;
    Integer total, jumlahPenjualan, totalBarang;
    String kode_brg, nama_brg, harga_brg, jumlah_brg, satuan_barg, tanggal, waktu, jmlPenjualan, stokBarang;
    String stokFinal;
    TextView tv_total, tvTotal2;
    ImageButton btn_back;
    ImageView img_qrcode, imgSuccces;
    TextView nameProduct;

    Button save;

    InterfaceTransaksi interfaceTransaksi;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refPenjualan = database.getReference("Penjualan");
    DatabaseReference refBarang = database.getReference("Barang");

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
                .load(Base_url + "qrcode/image_product/"+kode_brg+".png")
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

       validasiPenjualan();


    }

    private void validasiPenjualan() {
        jumlah_penjualan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                Intent intent = getIntent();
                jml_brg.setText(intent.getStringExtra("jml_brg2"));//



            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                jmlPenjualan = jumlah_penjualan.getText().toString();
                if (jumlah_penjualan.getText().toString().equals("")) {
                    Intent intent = getIntent();
                    jml_brg.setText(intent.getStringExtra("jml_brg2"));//
                    jmlPenjualan = "0";


                }


            }

            @Override
            public void afterTextChanged(Editable s) {

                if (jumlah_penjualan.getText().toString().equals("")) {


                    total = 0;
                    totalBarang = 0;
                    jumlahPenjualan = 0;

                    Intent intent = getIntent();
                    jml_brg.setText(intent.getStringExtra("jml_brg2"));//

                }
                else {
                    jumlahPenjualan = Integer.parseInt(jumlah_penjualan.getText().toString());
                    totalBarang = Integer.parseInt(jml_brg.getText().toString()) - jumlahPenjualan;


                    total = jumlahPenjualan * Integer.parseInt(hrg_brg.getText().toString());

                    tv_total.setText(String.valueOf(total));
                    jml_brg.setText(String.valueOf(totalBarang));

                }

                if (jumlah_penjualan.getText().toString().isEmpty()){
                    Intent intent = getIntent();
                    jml_brg.setText(jumlah_brg);//
                    jumlah_penjualan.setError("Jumlah penjualan tidak boleh kosong");
                    tv_total.setText(intent.getStringExtra("hrg_brg2"));//


                }



                if(Integer.parseInt(jml_brg.getText().toString()) < 0)  {
                    Intent intent = getIntent();
                    Toast.makeText(PenjualanActivity.this, "Jumlah penjulan melebihi stok!", Toast.LENGTH_LONG).show();

                    jml_brg.setText(intent.getStringExtra("jml_brg2"));//

                    jml_brg.setText(intent.getStringExtra("jml_brg2"));
                    tv_total.setText("Jumlah melebihi stok");
                    save.setClickable(false);
                    jumlah_penjualan.setError("Jumlah barang melebihi stok");
                }

                else {
                    save.setClickable(true);
                    tv_total.setText(String.valueOf(total));
                    jml_brg.setText(String.valueOf(totalBarang));
                    stokBarang = jml_brg.getText().toString();
                    tvTotal2.setText(String.valueOf(totalBarang));
                    tvTotal2.setVisibility(View.GONE);
                    stokFinal = tvTotal2.getText().toString();


                }

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
        jumlah_penjualan = findViewById(R.id.inJumlahPenjualan);
        imgSuccces = findViewById(R.id.img_success);
        tv_total = findViewById(R.id.tv_total);
        tvTotal2 =  findViewById(R.id.tv_total2);
        nameProduct = findViewById(R.id.nama_product);
    }

    public void simpandata(View view) {
        DataApi.getClient().create(InterfaceTransaksi.class).simpanPenjualan(kode_brg, jumlah_brg).enqueue(new Callback<TransaksiModel>() {
            @Override
            public void onResponse(Call<TransaksiModel> call, Response<TransaksiModel> response) {
                if (response.isSuccessful()) {


                    // Memanggil method simpan ke firebase
                    simpanPenjualan(kode_brg, nama_brg, harga_brg, totalBarang, jumlahPenjualan, total, satuan_barg, tanggal, waktu);

                    refBarang.child(kode_brg).child("jumlah").setValue(stokBarang);

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

    private void simpanPenjualan(String kode_brg, String nama_brg, String harga_brg, Integer total, Integer jumlah_penjualan, Integer totalPembelian, String satuan_barg,  String tanggal, String waktu) {
        String penjualan = refPenjualan.push().getKey();
        refPenjualan.child(penjualan).child("kode").setValue(kode_brg);
        refPenjualan.child(penjualan).child("nama").setValue(nama_brg);
        refPenjualan.child(penjualan).child("harga").setValue(harga_brg);
        refPenjualan.child(penjualan).child("Stok").setValue(total);
        refPenjualan.child(penjualan).child("Jumlah Penjualan").setValue(jumlah_penjualan);
        refPenjualan.child(penjualan).child("Total Pembelian").setValue(totalPembelian);
        refPenjualan.child(penjualan).child("satuan").setValue(satuan_barg);
        refPenjualan.child(penjualan).child("tanggal").setValue(tanggal);
        refPenjualan.child(penjualan).child("waktu").setValue(waktu);
        Toast.makeText(PenjualanActivity.this, "Berhasil menambahkan data ke firebase", Toast.LENGTH_LONG).show();
    }
}
