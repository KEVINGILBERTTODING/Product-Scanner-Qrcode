package com.example.scanqrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PenjualanActivity extends AppCompatActivity {

    EditText kd_brg, nm_brg, hrg_brg, jml_brg, satuan_brg;
    String kode_brg, nama_brg, harga_brg, jumlah_brg, satuan_barg, tanggal, waktu;

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
    }

    public void simpandata(View view) {
        simpanPenjualan(kode_brg, nama_brg, harga_brg, jumlah_brg, satuan_barg, tanggal, waktu);
        startActivity(new Intent(PenjualanActivity.this, TransaksiActivity.class));

    }

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