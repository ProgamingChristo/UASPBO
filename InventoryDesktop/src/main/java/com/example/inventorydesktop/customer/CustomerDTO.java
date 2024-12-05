package com.example.inventorydesktop.customer;

public class CustomerDTO {

    private int PelangganID;  // Match the JSON field "PelangganID"
    private String NamaPelanggan; // Match the JSON field "NamaPelanggan"
    private String Alamat; // Match the JSON field "Alamat"
    private String NomorTelepon; // Match the JSON field "NomorTelepon"

    public CustomerDTO(String namaPelanggan, String alamat, String nomorTelepon) {
        this.NamaPelanggan = namaPelanggan;
        this.Alamat = alamat;
        this.NomorTelepon = nomorTelepon;
    }

    public CustomerDTO(int pelangganID, String namaPelanggan, String alamat, String nomorTelepon) {
        this.PelangganID = pelangganID;
        this.NamaPelanggan = namaPelanggan;
        this.Alamat = alamat;
        this.NomorTelepon = nomorTelepon;
    }

    // Getters and Setters
    public int getPelangganID() {
        return PelangganID;
    }

    public void setPelangganID(int pelangganID) {
        PelangganID = pelangganID;
    }

    public String getNamaPelanggan() {
        return NamaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        NamaPelanggan = namaPelanggan;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    public String getNomorTelepon() {
        return NomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        NomorTelepon = nomorTelepon;
    }
}
