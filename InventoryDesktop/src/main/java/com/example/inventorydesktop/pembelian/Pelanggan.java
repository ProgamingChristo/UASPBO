package com.example.inventorydesktop.pembelian;

import com.google.gson.annotations.SerializedName;

public class Pelanggan {

    @SerializedName("PelangganID")
    private int PelangganID;

    @SerializedName("NamaPelanggan")
    private String NamaPelanggan;

    @SerializedName("Alamat")
    private String Alamat;

    @SerializedName("NomorTelepon")
    private String NomorTelepon;

    public Pelanggan(int pelangganID, String namaPelanggan, String alamat, String nomorTelepon) {
        this.PelangganID = pelangganID;
        this.NamaPelanggan = namaPelanggan;
        this.Alamat = alamat;
        this.NomorTelepon = nomorTelepon;
    }

    public int getPelangganID() {
        return PelangganID;
    }

    public String getNamaPelanggan() {
        return NamaPelanggan;
    }

    public String getAlamat() {
        return Alamat;
    }

    public String getNomorTelepon() {
        return NomorTelepon;
    }
}
