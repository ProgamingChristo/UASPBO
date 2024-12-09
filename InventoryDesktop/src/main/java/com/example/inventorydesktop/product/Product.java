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

    @SerializedName("img_url")
    private final StringProperty imgUrl;

    public Product(int produkID, String namaProduk, double harga, int stok, String imgUrl) {
        this.produkID = new SimpleIntegerProperty(produkID);
        this.namaProduk = new SimpleStringProperty(namaProduk);
        this.harga = new SimpleDoubleProperty(harga);
        this.stok = new SimpleIntegerProperty(stok);
        this.imgUrl = new SimpleStringProperty(imgUrl);
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

    public String getimgUrl() {
        return imgUrl.get();
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

    public StringProperty imageUrlProperty() {
        return imgUrl;
    }
}
