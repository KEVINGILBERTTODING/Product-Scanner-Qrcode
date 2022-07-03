package com.example.dianascanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dianascanner.Adapter.AdapterTransaksi;
import com.example.dianascanner.Model.TransaksiModel;
import com.example.dianascanner.Utill.DataApi;
import com.example.dianascanner.Utill.InterfaceTransaksi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiActivity extends AppCompatActivity  {

    private RecyclerView.LayoutManager layoutManager;
    private AdapterTransaksi adapterTrans;
    private List<TransaksiModel> transaksiModelList;
    private InterfaceTransaksi interfaceTransaksi;
    SearchView searchView;
    RecyclerView recyclerView;
    FloatingActionButton fabAdd;
    private ImageButton btnBack;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ShimmerRecyclerView mShimmerRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        // Method hidenavbar

        hideNavbar();

        // Method initilize

        initilize();

        // Mengatur warna tint fab

        fabAdd.setColorFilter(getResources().getColor(R.color.white));



        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tampilkanData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        setmShimmerRecyclerView();


        interfaceTransaksi = DataApi.getClient().create(InterfaceTransaksi.class);
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
            startActivity(new Intent(TransaksiActivity.this, MediaBarcode.class));
        });

        btnBack.setOnClickListener(view -> {
            startActivity(new Intent(TransaksiActivity.this, DashboardActivity.class));
        });


    }

    // Method untuk realtime searchview

    private void filter(String newText) {

        ArrayList<TransaksiModel> filteredList = new ArrayList<>();

        for (TransaksiModel item : transaksiModelList) {
            if (item.getNama().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);

            }
        }


        adapterTrans.filterList(filteredList);


        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show();
        } else {
            adapterTrans.filterList(filteredList);
        }


    }


    private void refreshItem() {
        tampilkanData();
    }

    // Method untuk memanggi data json

    private void tampilkanData() {
        Call<List<TransaksiModel>> call = interfaceTransaksi.getBarang2();

        call.enqueue(new Callback<List<TransaksiModel>>() {

            @Override
            public void onResponse(Call<List<TransaksiModel>> call, Response<List<TransaksiModel>> response) {

                transaksiModelList = response.body();
                adapterTrans = new AdapterTransaksi(TransaksiActivity.this, transaksiModelList);
                mShimmerRecyclerView.setAdapter(adapterTrans);
                mSwipeRefreshLayout.setRefreshing(false);
            }


            @Override
            public void onFailure(Call<List<TransaksiModel>> call, Throwable t) {

                // Menampilkan toast saat no connection

                Toast.makeText(TransaksiActivity.this, "No connection, please try again", Toast.LENGTH_LONG).show();
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
        fabAdd = findViewById(R.id.btn_scanner);
        btnBack = findViewById(R.id.btnBack22);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefresh2);
        searchView = findViewById(R.id.search_barr22);
        mShimmerRecyclerView = findViewById(R.id.rcylrBarang2);
    }

    private void setmShimmerRecyclerView() {


        mShimmerRecyclerView.setAdapter(adapterTrans);

        mShimmerRecyclerView.setLayoutManager(new LinearLayoutManager(this),
                R.layout.list_data_barang2);

        mShimmerRecyclerView.setItemViewType((type, position) -> {
            switch (type) {
                case ShimmerRecyclerView.LAYOUT_GRID:
                    return position % 2 == 0
                            ? R.layout.list_data_template
                            : R.layout.list_data_template;

                default:
                case ShimmerRecyclerView.LAYOUT_LIST:
                    return position == 0 || position % 2 == 0
                            ? R.layout.list_data_template
                            : R.layout.list_data_template;
            }
        });

        mShimmerRecyclerView.showShimmer();     // to start showing shimmer

        layoutManager = new LinearLayoutManager(this);
        mShimmerRecyclerView.setLayoutManager(layoutManager);
        mShimmerRecyclerView.setHasFixedSize(true);


    }






    }





