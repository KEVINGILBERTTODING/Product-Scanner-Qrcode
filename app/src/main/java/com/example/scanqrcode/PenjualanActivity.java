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

public class PenjualanActivity extends AppCompatActivity {

    EditText kd_brg, nm_brg, hrg_brg;
    Button btn_simpan;

    String kode_brg, nama_brg, harga_brg;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refPenjualan = database.getReference("Penjualan");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);

        initilize();

        Intent intent = getIntent();
        kd_brg.setText(intent.getStringExtra("kd_brg2"));
        nm_brg.setText(intent.getStringExtra("nm_brg2"));
        hrg_brg.setText(intent.getStringExtra("hrg_brg2"));

        kode_brg = kd_brg.getText().toString();
        nama_brg = nm_brg.getText().toString();
        harga_brg = hrg_brg.getText().toString();


    }

    private void initilize() {
        kd_brg = findViewById(R.id.inKdBrg);
        nm_brg = findViewById(R.id.inNmBrg);
        hrg_brg = findViewById(R.id.inHrg);
    }

    public void simpandata(View view) {
        simpanPenjualan(kode_brg, nama_brg, harga_brg);
        finish();

    }

    private void simpanPenjualan(String kode_brg, String nama_brg, String harga_brg) {
        String penjualan = refPenjualan.push().getKey();

        refPenjualan.child(penjualan).child("kode").setValue(kode_brg);
        refPenjualan.child(penjualan).child("nama").setValue(nama_brg);
        refPenjualan.child(penjualan).child("harga").setValue(harga_brg);
        Toast.makeText(PenjualanActivity.this, "Berhasil menambahkan data ke firebase", Toast.LENGTH_LONG).show();
    }
}