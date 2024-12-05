import { PrismaClient } from '@prisma/client';
const prisma = new PrismaClient();

export class StatistikService {
  // Get total count of each table
  async getTotalCount() {
    const totalPenjualan = await prisma.penjualan.count();  // Count Penjualan
    const totalDetailPenjualan = await prisma.detailpenjualan.count();  // Count DetailPenjualan
    const totalProduk = await prisma.produk.count();  // Count Produk
    const totalPelanggan = await prisma.pelanggan.count();  // Count Pelanggan
    const totalPembelian = await prisma.pembelian.count();  // Count Pembelian
    const totalDetailPembelian = await prisma.detailpembelian.count();  // Count DetailPembelian

    return {
      penjualan: totalPenjualan,
      detailPenjualan: totalDetailPenjualan,
      produk: totalProduk,
      pelanggan: totalPelanggan,
      pembelian: totalPembelian,
      detailPembelian: totalDetailPembelian,
    };
  }
}
