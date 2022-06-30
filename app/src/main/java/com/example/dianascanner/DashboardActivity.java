package com.example.dianascanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dianascanner.Adapter.BarangAdapter;
import com.example.dianascanner.Adapter.DashboardAdapter;
import com.example.dianascanner.Model.BarangModel;
import com.example.dianascanner.Utill.DataApi;
import com.example.dianascanner.Utill.InterfaceBarang;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    ImageButton btn_barang, btn_transaksi, btn_about, btn_close, btn_map;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BarangAdapter barangAdapter;
    private List<BarangModel> barangModelList;
    private InterfaceBarang interfaceBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        initilize();
        hideNavbar();
        btnListener();


        interfaceBarang = DataApi.getClient().create(InterfaceBarang.class);
        tampilkanData();

    }

    private void tampilkanData() {
        Call<List<BarangModel>> call = interfaceBarang.getBarang();

        call.enqueue(new Callback<List<BarangModel>>() {

            @Override
            public void onResponse(Call<List<BarangModel>> call, Response<List<BarangModel>> response) {

                barangModelList = response.body();
                barangAdapter = new BarangAdapter(DashboardActivity.this, barangModelList);

                layoutManager = new LinearLayoutManager(getApplicationContext());
                GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(barangAdapter);
                recyclerView.setHasFixedSize(true);

            }


            @Override
            public void onFailure(Call<List<BarangModel>> call, Throwable t) {

                // Menampilkan toast saat no connection

                Toast.makeText(DashboardActivity.this, "No connection, please try again", Toast.LENGTH_LONG).show();


            }
        });
    }

    private void hideNavbar() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }


    private void btnListener() {

        btn_barang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, MainActivity.class));
            }
        });
        btn_transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, TransaksiActivity.class));
            }
        });

        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AboutMe.class));
            }
        });

        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, MyMap.class));
            }
        });


        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                builder.setTitle("Keluar Aplikasi");
                builder.setMessage("Apakah anda yakin ingin keluar dari aplikasi ini?");
                builder.setPositiveButton("Ya", (dialog, which) -> finish());
                builder.setNegativeButton("Tidak", (dialog, which) -> dialog.dismiss());
                builder.show();
            }
        });
    }

    private void initilize() {
        btn_barang = findViewById(R.id.btn_barang);
        btn_transaksi = findViewById(R.id.btn_transaksi);
        btn_about = findViewById(R.id.btn_about);
        btn_close = findViewById(R.id.btn_close);
        btn_map = findViewById(R.id.btn_map);

        recyclerView = findViewById(R.id.rDashboard);
    }
}