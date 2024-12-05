export interface CreatePembelianDto {
  pelangganID: number;
  detailPembelian: Array<{
    ProdukID: number;
    JumlahProduk: number;
    Subtotal: number;
  }>;
}

export interface UpdatePembelianDto {
  TotalHarga?: number;
  PelangganID?: number;
}
