package com.example.inventorydesktop.pembelian;

import com.example.inventorydesktop.pembelian.DetailPembelian;
import com.example.inventorydesktop.pembelian.Pelanggan;

import java.time.LocalDateTime;
import java.util.List;

public class Pembelian {
    private int pembelianID;
    private LocalDateTime tanggalPembelian;
    private Double totalHarga;  // Change from String to Double
    private List<DetailPembelian> detailPembelian;
    private Pelanggan pelanggan;

    // Getters and setters
    public int getPembelianID() {
        return pembelianID;
    }

    public void setPembelianID(int pembelianID) {
        this.pembelianID = pembelianID;
    }

    public LocalDateTime getTanggalPembelian() {
        return tanggalPembelian;
    }

    public void setTanggalPembelian(LocalDateTime tanggalPembelian) {
        this.tanggalPembelian = tanggalPembelian;
    }

    public Double getTotalHarga() {  // Make sure this returns Double
        return totalHarga;
    }

    public void setTotalHarga(Double totalHarga) {  // Update setter accordingly
        this.totalHarga = totalHarga;
    }

    public List<DetailPembelian> getDetailPembelian() {
        return detailPembelian;
    }

    public void setDetailPembelian(List<DetailPembelian> detailPembelian) {
        this.detailPembelian = detailPembelian;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public void setPelanggan(Pelanggan pelanggan) {
        this.pelanggan = pelanggan;
    }
}

