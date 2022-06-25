package com.example.scanqrcode.Utill;

import com.example.scanqrcode.Model.TransaksiModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InterfaceTransaksi {
    @GET("qrcode/")
    Call<List<TransaksiModel>> getBarang2();
    @FormUrlEncoded
    @POST("qrcode/")
    Call<TransaksiModel> postBarang(@Field("kode") String kode,
                                 @Field("nama") String nama_barang,
                                 @Field("harga") String harga);
    @DELETE("qrcode/")
    Call<TransaksiModel> deleteBarang(@Query("kode") String kode);
}
