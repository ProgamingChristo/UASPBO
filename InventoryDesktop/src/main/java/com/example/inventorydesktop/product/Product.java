package com.example.inventorydesktop.product;

import com.google.gson.annotations.SerializedName;
import javafx.beans.property.*;

public class Product {
    @SerializedName("ProdukID")
    private final IntegerProperty produkID;

    @SerializedName("namaProduk")
    private final StringProperty namaProduk;

    @SerializedName("harga")
    private final DoubleProperty harga;

    @SerializedName("stok")
    private final IntegerProperty stok;

    public Product(int produkID, String namaProduk, double harga, int stok) {
        this.produkID = new SimpleIntegerProperty(produkID);
        this.namaProduk = new SimpleStringProperty(namaProduk);
        this.harga = new SimpleDoubleProperty(harga);
        this.stok = new SimpleIntegerProperty(stok);
    }

    public int getProdukID() {
        return produkID.get();
    }

    public String getNamaProduk() {
        return namaProduk.get();
    }

    public double getHarga() {
        return harga.get();
    }

    public int getStok() {
        return stok.get();
    }

    public IntegerProperty produkIDProperty() {
        return produkID;
    }

    public StringProperty namaProdukProperty() {
        return namaProduk;
    }

    public DoubleProperty hargaProperty() {
        return harga;
    }

    public IntegerProperty stokProperty() {
        return stok;
    }
}
