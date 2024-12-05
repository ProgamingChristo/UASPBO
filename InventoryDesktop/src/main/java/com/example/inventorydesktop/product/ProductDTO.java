package com.example.inventorydesktop.product;

import com.google.gson.annotations.SerializedName;

public class ProductDTO {
    @SerializedName("ProdukID")
    private int produkID;

    @SerializedName("NamaProduk")
    private String namaProduk;

    @SerializedName("Harga")
    private double harga;

    @SerializedName("Stok")
    private int stok;

    // Constructor untuk pembuatan produk baru (tanpa ProdukID)
    public ProductDTO(String namaProduk, double harga, int stok) {
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.stok = stok;
    }

    // Constructor untuk update produk (dengan ProdukID)
    public ProductDTO(int produkID, String namaProduk, double harga, int stok) {
        this.produkID = produkID;
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.stok = stok;
    }

    // Getters dan Setters
    public int getProdukID() {
        return produkID;
    }

    public void setProdukID(int produkID) {
        this.produkID = produkID;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }
}
