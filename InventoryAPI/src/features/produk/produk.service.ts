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

  async createProduk(newProduk: { NamaProduk: string; Harga: number; Stok: number; ImgUrl: string }) {
    return prisma.produk.create({
      data: {
        NamaProduk: newProduk.NamaProduk,
        Harga: newProduk.Harga,
        Stok: newProduk.Stok,
        img_url: newProduk.ImgUrl,  
      },
    });
  }
  

  async updateProduk(produkId: number, updatedProduk: { NamaProduk?: string; Harga?: number; Stok?: number; ImgUrl?: string }) {
    // // Log input data
    // console.log("Updated Produk Data:", updatedProduk);
  
    const produk = await prisma.produk.findUnique({
      where: { ProdukID: produkId },
    });
  
    if (!produk) {
      throw new Error('Product not found');
    }
  
    const dataToUpdate: any = {
      NamaProduk: updatedProduk.NamaProduk,
      Harga: updatedProduk.Harga,
      Stok: updatedProduk.Stok,
    };
  
    if (updatedProduk.ImgUrl) {
      dataToUpdate.img_url = updatedProduk.ImgUrl; // Ensure this matches the column name in your DB
    }
  
    // // Log data before update
    // console.log("Data to update:", dataToUpdate);
  
    return prisma.produk.update({
      where: { ProdukID: produkId },
      data: dataToUpdate,
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
