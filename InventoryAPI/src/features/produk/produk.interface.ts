export interface CreateProdukDto {
  namaProduk: string;
  harga: number;
  stok: number;
  imgUrl?: string;
}

export interface UpdateProdukDto {
  namaProduk?: string;
  harga?: number;
  stok?: number;
  imgUrl?: string;
}
