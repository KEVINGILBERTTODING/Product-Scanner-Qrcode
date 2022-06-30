package com.example.dianascanner.Utill;

import com.example.dianascanner.Model.BarangModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

    public interface InterfaceBarang {
        @GET("qrcode/")
        Call<List<BarangModel>> getBarang();
        @FormUrlEncoded
        @POST("qrcode/")
        Call<BarangModel> postBarang(@Field("kode") String kode,
                                      @Field("nama_barang") String nama,
                                      @Field("harga") String harga,
                                      @Field("jumlah") String jumlah,
                                      @Field("satuan") String satuan,
                                      @Field("gambar") String image);


        @DELETE("qrcode/")
        Call<BarangModel> deleteBarang(@Query("kode") String kode);
    }