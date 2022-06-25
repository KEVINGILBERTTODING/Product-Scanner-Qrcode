package com.example.scanqrcode.Model;

import com.example.scanqrcode.Utill.ServerAPI;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.net.URL;

public class TransaksiModel implements Serializable {
    @SerializedName("kode")
    String kode;
    @SerializedName("nama_barang")
    String nama;
    @SerializedName("harga")
    String harga;
    @SerializedName("image")
    String image;



    public TransaksiModel(String kode, String nama, String harga, String image) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.image = image;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }
    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga() {
        return harga;
    }
    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getImage() {
        return ServerAPI.URL +"/assets/images/"+ image;
    }
    public String getImgName() { return image;  }

    public void setImage(String image) {
        this.image = image;
    }
}