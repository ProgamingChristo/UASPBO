import { PrismaClient } from '@prisma/client';
const prisma = new PrismaClient();

export class PenjualanService {
  // Get all penjualan
  async getAllPenjualan() {
    return prisma.penjualan.findMany({
      include: {
        detailpenjualan: {
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

  // Get penjualan by ID
  async getPenjualanById(penjualanId: number) {
    return prisma.penjualan.findUnique({
      where: { PenjualanID: penjualanId },
      include: {
        detailpenjualan: {
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

  // Create a new penjualan
  async createPenjualan(newPenjualan: { pelangganID: number; detailPenjualan: Array<{ ProdukID: number; JumlahProduk: number; Subtotal: number }> }) {
    const totalHarga = newPenjualan.detailPenjualan.reduce((acc, detail) => {
      if (detail.Subtotal <= 0) throw new Error('Subtotal must be greater than zero');
      return acc + detail.Subtotal;
    }, 0);

    const createdPenjualan = await prisma.penjualan.create({
      data: {
        TanggalPenjualan: new Date(),
        TotalHarga: totalHarga,
        PelangganID: newPenjualan.pelangganID,
        detailpenjualan: {
          create: newPenjualan.detailPenjualan.map((detail) => ({
            ProdukID: detail.ProdukID,
            JumlahProduk: detail.JumlahProduk,
            Subtotal: detail.Subtotal
          }))
        }
      },
      include: {
        detailpenjualan: {
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

    return createdPenjualan;
  }

  // Update penjualan
  async updatePenjualan(penjualanId: number, updatedPenjualan: { TotalHarga?: number; PelangganID?: number }) {
    try {
      const existingPenjualan = await prisma.penjualan.findUnique({
        where: { PenjualanID: penjualanId }
      });

      if (!existingPenjualan) {
        throw new Error(`Penjualan with ID ${penjualanId} not found`);
      }

      const updatedResult = await prisma.penjualan.update({
        where: { PenjualanID: penjualanId },
        data: updatedPenjualan,
        include: {
          detailpenjualan: {
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
      throw new Error('Error occurred while updating penjualan');
    }
  }
}
