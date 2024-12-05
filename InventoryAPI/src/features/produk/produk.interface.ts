export interface CreateProdukDto {
  namaProduk: string;
  harga: number;
  stok: number;
}

export interface UpdateProdukDto {
  namaProduk?: string;
  harga?: number;
  stok?: number;
}
