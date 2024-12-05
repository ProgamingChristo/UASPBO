package com.example.inventorydesktop.penjualan;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

public class Penjualan {
    private int PenjualanID;
    private String TanggalPenjualan;
    private String TotalHarga;
    private Pelanggan pelanggan;
    private List<DetailPenjualan> detailpenjualan;

    // Getters and setters to match API response capitalization
    public int getPenjualanID() {
        return PenjualanID;
    }

    public void setPenjualanID(int penjualanID) {
        this.PenjualanID = penjualanID;
    }

    public String getTanggalPenjualan() {
        return TanggalPenjualan;
    }

    public void setTanggalPenjualan(String tanggalPenjualan) {
        this.TanggalPenjualan = tanggalPenjualan;
    }

    public String getTotalHarga() {
        return TotalHarga;
    }

    public void setTotalHarga(String totalHarga) {
        this.TotalHarga = totalHarga;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public void setPelanggan(Pelanggan pelanggan) {
        this.pelanggan = pelanggan;
    }

    public List<DetailPenjualan> getDetailpenjualan() {
        return detailpenjualan;
    }

    public void setDetailpenjualan(List<DetailPenjualan> detailpenjualan) {
        this.detailpenjualan = detailpenjualan;
    }

    // Binding untuk nama pelanggan
    public StringProperty getPelangganProperty() {
        return pelanggan != null ? new SimpleStringProperty(pelanggan.getNamaPelanggan()) : new SimpleStringProperty("Unknown");
    }
}