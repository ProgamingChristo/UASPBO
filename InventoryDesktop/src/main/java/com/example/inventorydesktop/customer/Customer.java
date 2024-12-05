package com.example.inventorydesktop.customer;

import javafx.beans.property.*;

public class Customer {
    private final IntegerProperty customerID;
    private final StringProperty namaPelanggan;
    private final StringProperty alamat;
    private final StringProperty nomorTelepon;

    public Customer(int customerID, String namaPelanggan, String alamat, String nomorTelepon) {
        this.customerID = new SimpleIntegerProperty(customerID);
        this.namaPelanggan = new SimpleStringProperty(namaPelanggan);
        this.alamat = new SimpleStringProperty(alamat);
        this.nomorTelepon = new SimpleStringProperty(nomorTelepon);
    }

    public IntegerProperty customerIDProperty() {
        return customerID;
    }

    public StringProperty namaPelangganProperty() {
        return namaPelanggan;
    }

    public StringProperty alamatProperty() {
        return alamat;
    }

    public StringProperty nomorTeleponProperty() {
        return nomorTelepon;
    }

    public int getCustomerID() {
        return customerID.get();
    }

    public String getNamaPelanggan() {
        return namaPelanggan.get();
    }

    public String getAlamat() {
        return alamat.get();
    }

    public String getNomorTelepon() {
        return nomorTelepon.get();
    }
}
