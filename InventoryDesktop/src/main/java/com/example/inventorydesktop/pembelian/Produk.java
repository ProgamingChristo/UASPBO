package com.example.inventorydesktop.pembelian;

import com.google.gson.annotations.SerializedName;

public class Produk {

    @SerializedName("ProdukID")
    private int ProdukID;

    @SerializedName("NamaProduk")
    private String NamaProduk;

    public Produk(int produkID, String namaProduk) {
        this.ProdukID = produkID;
        this.NamaProduk = namaProduk;
    }

    public int getProdukID() {
        return ProdukID;
    }

    public String getNamaProduk() {
        return NamaProduk;
    }
}
