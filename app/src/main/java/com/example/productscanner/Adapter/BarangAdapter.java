package com.example.productscanner.Adapter;

import static com.example.productscanner.Utill.ServerAPI.Base_url;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.productscanner.DetailBarang;
import com.example.productscanner.Model.BarangModel;
import com.example.productscanner.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.MyViewHolder> {
    Context context;
    List<BarangModel> barangModels;



    public BarangAdapter (Context context,  List<BarangModel> barangModels) {
        this.context = context;
        this.barangModels= barangModels;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data_barang, parent,
                false);

        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {


        holder.kd_brg.setText(barangModels.get(position).getKode());
        holder.nm_brg.setText(barangModels.get(position).getNama());
        holder.hrg_brg.setText(formatRupiah(Double.parseDouble(barangModels.get(position).getHarga())));

        Glide.with(context)
                .load( Base_url + "qrcode/image_product/"+barangModels.get(position).getKode()+".png")
                .thumbnail(0.5f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imgBarang);


    }

    @Override
    public int getItemCount() {
        return barangModels.size();
    }

    public void filterList(ArrayList<BarangModel> filteredList) {

        barangModels = filteredList;
        notifyDataSetChanged();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView kd_brg,nm_brg,hrg_brg;
        ImageView imgBarang;

        public MyViewHolder(View itemView) {
            super(itemView);
            kd_brg = itemView.findViewById(R.id.tv_kdbrg);
            nm_brg = itemView.findViewById(R.id.tv_nmbrg);
            hrg_brg = itemView.findViewById(R.id.tv_harga);
            imgBarang = itemView.findViewById(R.id.img_barang);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), DetailBarang.class);
            intent.putExtra("kd_brg", kd_brg.getText().toString());
            intent.putExtra("nm_brg", nm_brg.getText().toString());
            intent.putExtra("hrg_brg", hrg_brg.getText().toString());
            intent.putExtra("img_brg", imgBarang.getDrawable().toString());
            view.getContext().startActivity(intent);

        }
    }

    private String formatRupiah(Double number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);

    }
}