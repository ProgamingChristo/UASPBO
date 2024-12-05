export interface CreatePelangganDto {
  namaPelanggan: string;
  alamat: string;
  nomorTelepon: string;
}

export interface UpdatePelangganDto {
  namaPelanggan?: string;
  alamat?: string;
  nomorTelepon?: string;
}
