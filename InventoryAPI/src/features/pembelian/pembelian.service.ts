import { PrismaClient } from '@prisma/client';
const prisma = new PrismaClient();

export class PembelianService {
  // Get all pembelian
  async getAllPembelian() {
    return prisma.pembelian.findMany({
      include: {
        detailpembelian: {
          include: {
            produk: {
              select: {
                ProdukID: true,
                NamaProduk: true
              }
            }
          }
        },
        pelanggan: true
      }
    });
  }

  // Get pembelian by ID
  async getPembelianById(pembelianId: number) {
    return prisma.pembelian.findUnique({
      where: { pembelianID: pembelianId },
      include: {
        detailpembelian: {
          include: {
            produk: {
              select: {
                ProdukID: true,
                NamaProduk: true
              }
            }
          }
        },
        pelanggan: true
      }
    });
  }

  // Create a new pembelian
  async createPembelian(newPembelian: { pelangganID: number; detailPembelian: Array<{ ProdukID: number; JumlahProduk: number; Subtotal: number }> }) {
    const totalHarga = newPembelian.detailPembelian.reduce((acc, detail) => acc + detail.Subtotal, 0);

    const createdPembelian = await prisma.pembelian.create({
      data: {
        Tanggalpembelian: new Date(),
        TotalHarga: totalHarga,
        PelangganID: newPembelian.pelangganID,
        detailpembelian: {
          create: newPembelian.detailPembelian.map((detail) => ({
            ProdukID: detail.ProdukID,
            JumlahProduk: detail.JumlahProduk,
            Subtotal: detail.Subtotal
          }))
        }
      },
      include: {
        detailpembelian: {
          include: {
            produk: {
              select: {
                ProdukID: true,
                NamaProduk: true
              }
            }
          }
        },
        pelanggan: true
      }
    });

    return createdPembelian;
  }

  // Update pembelian
  async updatePembelian(pembelianId: number, updatedPembelian: { TotalHarga?: number; PelangganID?: number }) {
    try {
      const existingPembelian = await prisma.pembelian.findUnique({
        where: { pembelianID: pembelianId }
      });

      if (!existingPembelian) {
        throw new Error(`Pembelian with ID ${pembelianId} not found`);
      }

      const updatedResult = await prisma.pembelian.update({
        where: { pembelianID: pembelianId },
        data: updatedPembelian,
        include: {
          detailpembelian: {
            include: {
              produk: {
                select: {
                  ProdukID: true,
                  NamaProduk: true
                }
              }
            }
          },
          pelanggan: true
        }
      });

      return updatedResult;
    } catch (error) {
      throw new Error('Error occurred while updating pembelian');
    }
  }
}
