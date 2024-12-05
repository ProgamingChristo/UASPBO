export interface CreatePenjualanDto {
  pelangganID: number;
  detailPenjualan: Array<{
    ProdukID: number;
    JumlahProduk: number;
    Subtotal: number;
  }>;
}

export interface UpdatePenjualanDto {
  TotalHarga?: number;
  PelangganID?: number;
}