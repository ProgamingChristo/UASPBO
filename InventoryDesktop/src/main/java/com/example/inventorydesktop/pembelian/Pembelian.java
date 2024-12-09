package com.example.inventorydesktop.pembelian;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

public class Pembelian {
    private int pembelianID;
    private String Tanggalpembelian;
    private String TotalHarga;
    private Pelanggan pelanggan;
    private List<DetailPembelian> detailpembelian;

    // Getters and setters
    public int getPembelianID() { return pembelianID; }
    public void setPembelianID(int pembelianID) { this.pembelianID = pembelianID; }

    public String getTanggalPembelian() { return Tanggalpembelian; }
    public void setTanggalPembelian(String tanggalPembelian) { this.Tanggalpembelian = tanggalPembelian; }

    public String getTotalHarga() { return TotalHarga; }
    public void setTotalHarga(String totalHarga) { this.TotalHarga = totalHarga; }

    public Pelanggan getPelanggan() { return pelanggan; }
    public void setPelanggan(Pelanggan pelanggan) { this.pelanggan = pelanggan; }

    public List<DetailPembelian> getDetailPembelian() { return detailpembelian; }
    public void setDetailPembelian(List<DetailPembelian> detailPembelian) { this.detailpembelian = detailPembelian; }

    // Binding untuk nama pelanggan
    public StringProperty getPelangganProperty() {
        return pelanggan != null ? new SimpleStringProperty(pelanggan.getNamaPelanggan()) : new SimpleStringProperty("Unknown");
    }
}
