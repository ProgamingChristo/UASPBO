package com.example.inventorydesktop.customer;

import com.google.gson.annotations.SerializedName;

public class CreateCustomerDTO {

    @SerializedName("namaPelanggan")
    private String namaPelanggan;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("nomorTelepon")
    private String nomorTelepon;

    // Constructor
    public CreateCustomerDTO(String namaPelanggan, String alamat, String nomorTelepon) {
        this.namaPelanggan = namaPelanggan;
        this.alamat = alamat;
        this.nomorTelepon = nomorTelepon;

    }

    // Getters and Setters
    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }
}
