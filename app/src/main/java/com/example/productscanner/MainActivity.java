package com.example.productscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.productscanner.Adapter.BarangAdapter;
import com.example.productscanner.Model.BarangModel;
import com.example.productscanner.Utill.DataApi;
import com.example.productscanner.Utill.InterfaceBarang;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private BarangAdapter barangAdapter;
    private List<BarangModel> barangModelList;
    private InterfaceBarang interfaceBarang;
    SearchView searchView;
    RecyclerView recyclerView;
    FloatingActionButton fabAdd;
    private ImageButton btnBack;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ShimmerRecyclerView mShimmerRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Method initilize

        initilize();

        // Mengatur warna tint fab

        fabAdd.setColorFilter(getResources().getColor(R.color.white));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItem();
            }
        });

        interfaceBarang = DataApi.getClient().create(InterfaceBarang.class);
        tampilkanData();
        // Fungsi saat memasukkan kata ke dalam searchview

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String querry) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        fabAdd.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, TambahBarang.class));
        });

        btnBack.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
        });



    }

    // Method untuk realtime searchview

    private void filter(String newText) {

        ArrayList<BarangModel> filteredList = new ArrayList<>();

        for (BarangModel item : barangModelList) {
            if (item.getNama().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);

            }
        }


        barangAdapter.filterList(filteredList);


        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show();
        } else {
            barangAdapter.filterList(filteredList);
        }


    }


    private void refreshItem() {
        tampilkanData();
    }

    // Method untuk memanggi data json

    private void tampilkanData() {
        Call<List<BarangModel>> call = interfaceBarang.getBarang();

        call.enqueue(new Callback<List<BarangModel>>() {

            @Override
            public void onResponse(Call<List<BarangModel>> call, Response<List<BarangModel>> response) {

                barangModelList = response.body();
                barangAdapter = new BarangAdapter(MainActivity.this, barangModelList);

                layoutManager = new LinearLayoutManager(getApplicationContext());
                GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(barangAdapter);
                recyclerView.setHasFixedSize(true);
                mSwipeRefreshLayout.setRefreshing(false);

            }


            @Override
            public void onFailure(Call<List<BarangModel>> call, Throwable t) {

                // Menampilkan toast saat no connection

                Toast.makeText(MainActivity.this, "No connection, please try again", Toast.LENGTH_LONG).show();
                mSwipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    private void hideNavbar() {

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void initilize() {
        fabAdd = findViewById(R.id.btn_add);
        btnBack = findViewById(R.id.btnBack2);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefresh);
        searchView = findViewById(R.id.search_barr);
        recyclerView = findViewById(R.id.rcylrBarang);
    }




}




