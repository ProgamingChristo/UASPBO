package com.example.inventorydesktop.pembelian;

public class DetailPembelian {
    private int DetailID;
    private int pembelianID;
    private int ProdukID;
    private int JumlahProduk;
    private String Subtotal;
    private Produk produk;

    // Getters and setters
    public int getDetailID() { return DetailID; }
    public void setDetailID(int detailID) { this.DetailID = detailID; }

    public int getPembelianID() { return pembelianID; }
    public void setPembelianID(int pembelianID) { this.pembelianID = pembelianID; }

    public int getProdukID() { return ProdukID; }
    public void setProdukID(int produkID) { this.ProdukID = produkID; }

    public int getJumlahProduk() { return JumlahProduk; }
    public void setJumlahProduk(int jumlahProduk) { this.JumlahProduk = jumlahProduk; }

    public String getSubtotal() { return Subtotal; }
    public void setSubtotal(String subtotal) { this.Subtotal = subtotal; }

    public Produk getProduk() { return produk; }
    public void setProduk(Produk produk) { this.produk = produk; }
}
