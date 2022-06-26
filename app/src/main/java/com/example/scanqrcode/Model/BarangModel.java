package com.example.scanqrcode.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BarangModel implements Serializable {
    @SerializedName("kode")
    String kode;
    @SerializedName("nama_barang")
    String nama;
    @SerializedName("satuan")
    String satuan;
    @SerializedName("jumlah")
    String jumlah;
    @SerializedName("harga")
    String harga;


    public BarangModel(String kode, String nama, String harga, String jumlah, String satuan) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.jumlah = jumlah;
        this.satuan = satuan;
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

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getHarga() {
        return harga;
    }
    public void setHarga(String harga) {
        this.harga = harga;
    }


}