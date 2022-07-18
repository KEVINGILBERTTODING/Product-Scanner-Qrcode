package com.example.productscanner.Utill;

import com.example.productscanner.Model.ProfileModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InterfaceProfile {

    // Methode untuk menampilkan data profile

    @GET("qrcode/load_profile.php")

    // Menggunakan parameter id untuk mengambil data user
    Call<List<ProfileModel>> getUser(
            @Query("id") Integer id
    );

    @FormUrlEncoded
    @POST("qrcode/load_profile.php")
    Call<ProfileModel> simpanProfile(@Field("kode") String username,
                                     @Field("about") String about,
                                     @Field("image") String image);


}

