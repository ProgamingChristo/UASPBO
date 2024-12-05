import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

export class ProdukService {
  // Get all products
  async getAllProduk() {
    return prisma.produk.findMany();
  }

  // Get a product by ID
  async getProdukById(produkId: number) {
    return prisma.produk.findUnique({
      where: { ProdukID: produkId },
    });
  }

  // Create a new product
  async createProduk(newProduk: { NamaProduk: string; Harga: number; Stok: number }) {
    return prisma.produk.create({
      data: {
        NamaProduk: newProduk.NamaProduk,
        Harga: newProduk.Harga,
        Stok: newProduk.Stok,
      },
    });
  }

  // Update an existing product
  async updateProduk(produkId: number, updatedProduk: { NamaProduk?: string; Harga?: number; Stok?: number }) {
    return prisma.produk.update({
      where: { ProdukID: produkId },
      data: updatedProduk,
    });
  }

  // Delete a product
  async deleteProduk(id: number): Promise<boolean> {
    try {
      const produk = await prisma.produk.findUnique({
        where: { ProdukID: id },
      });
      if (!produk) {
        return false;
      }

      await prisma.produk.delete({
        where: { ProdukID: id },
      });

      return true;
    } catch (error) {
      throw new Error('Error occurred while deleting product');
    }
  }
}
