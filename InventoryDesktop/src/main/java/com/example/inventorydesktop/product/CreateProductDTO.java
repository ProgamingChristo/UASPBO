package com.example.inventorydesktop.product;

import com.google.gson.annotations.SerializedName;

public class CreateProductDTO {
    @SerializedName("namaProduk")
    private String namaProduk;
    @SerializedName("harga")
    private double harga;
    @SerializedName("stok")
    private int stok;
    @SerializedName("imgUrl")
    private String imgUrl;

    public CreateProductDTO(String namaProduk, double harga, int stok, String imgUrl) {
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.stok = stok;
        this.imgUrl = imgUrl;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
