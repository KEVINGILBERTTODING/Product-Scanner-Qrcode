package com.example.productscanner;

import static com.example.productscanner.Utill.DataApi.BASE_URL;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.productscanner.Adapter.BarangAdapter;
import com.example.productscanner.Model.BarangModel;
import com.example.productscanner.Model.ProfileModel;
import com.example.productscanner.Utill.DataApi;
import com.example.productscanner.Utill.InterfaceBarang;

import java.util.ArrayList;
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
    private List<ProfileModel> profileModels;
    private InterfaceBarang interfaceBarang;
    SearchView searchView;
    SwipeRefreshLayout swipeRefreshLayout;

    TextView tv_username;
    ImageView img_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        initilize();
        hideNavbar();
        btnListener();


        interfaceBarang = DataApi.getClient().create(InterfaceBarang.class);
        tampilkanData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tampilkanData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        img_profile.setOnClickListener(view ->{
            startActivity(new Intent(DashboardActivity.this, AboutMe.class));
        });

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

            }


            @Override
            public void onFailure(Call<List<BarangModel>> call, Throwable t) {

                // Menampilkan toast saat no connection

                Toast.makeText(DashboardActivity.this, "No connection, please try again", Toast.LENGTH_LONG).show();


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkuserstatus();
    }

    private void checkuserstatus() {

        SharedPreferences sharedPreferences=getSharedPreferences("logindata",MODE_PRIVATE);
        Boolean counter=sharedPreferences.getBoolean("logincounter",Boolean.valueOf(String.valueOf(MODE_PRIVATE)));
        String username=sharedPreferences.getString("useremail",String.valueOf(MODE_PRIVATE));
        if (counter){
            tv_username.setText("Welcome, "+username);

            Glide.with(this)
                    .load(BASE_URL + "qrcode/profile_image/" + username + ".png")
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(img_profile);
        }
        else{
            startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
            finish();
        }


    }
    private void hideNavbar() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

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



    }

    private void initilize() {
        btn_barang = findViewById(R.id.btn_barang);
        btn_transaksi = findViewById(R.id.btn_transaksi);
        btn_about = findViewById(R.id.btn_about);
        btn_map = findViewById(R.id.btn_map);
        searchView = findViewById(R.id.search_barr);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        tv_username = findViewById(R.id.tvt_1);
        img_profile = findViewById(R.id.profile_image);

        recyclerView = findViewById(R.id.rDashboard);
    }
}