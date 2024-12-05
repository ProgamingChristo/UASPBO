package com.example.inventorydesktop.pembelian;

import com.google.gson.annotations.SerializedName;

public class DetailPembelian {

    @SerializedName("DetailID")
    private int DetailID;

    @SerializedName("pembelianID")
    private int pembelianID;

    @SerializedName("ProdukID")
    private int ProdukID;

    @SerializedName("JumlahProduk")
    private int JumlahProduk;

    @SerializedName("Subtotal")
    private double Subtotal;

    @SerializedName("produk")
    private Produk produk;

    public DetailPembelian(int detailID, int pembelianID, int produkID, int jumlahProduk, double subtotal, Produk produk) {
        this.DetailID = detailID;
        this.pembelianID = pembelianID;
        this.ProdukID = produkID;
        this.JumlahProduk = jumlahProduk;
        this.Subtotal = subtotal;
        this.produk = produk;
    }

    public int getDetailID() {
        return DetailID;
    }

    public int getPembelianID() {
        return pembelianID;
    }

    public int getProdukID() {
        return ProdukID;
    }

    public int getJumlahProduk() {
        return JumlahProduk;
    }

    public double getSubtotal() {
        return Subtotal;
    }

    public Produk getProduk() {
        return produk;
    }
}
