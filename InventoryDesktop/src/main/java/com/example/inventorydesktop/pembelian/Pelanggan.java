package com.example.inventorydesktop.pembelian;

public class Pelanggan {
    private int PelangganID;
    private String NamaPelanggan;
    private String Alamat;
    private String NomorTelepon;

    // Getters and setters
    public int getPelangganID() { return PelangganID; }
    public void setPelangganID(int pelangganID) { this.PelangganID = pelangganID; }

    public String getNamaPelanggan() { return NamaPelanggan; }
    public void setNamaPelanggan(String namaPelanggan) { this.NamaPelanggan = namaPelanggan; }

    public String getAlamat() { return Alamat; }
    public void setAlamat(String alamat) { this.Alamat = alamat; }

    public String getNomorTelepon() { return NomorTelepon; }
    public void setNomorTelepon(String nomorTelepon) { this.NomorTelepon = nomorTelepon; }
}
