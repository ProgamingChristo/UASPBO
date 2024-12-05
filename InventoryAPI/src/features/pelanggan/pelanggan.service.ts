import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

export class PelangganService {
  // Get all customers
  async getAllPelanggan() {
    return prisma.pelanggan.findMany();
  }

  // Get customer by ID
  async getPelangganById(pelangganId: number) {
    return prisma.pelanggan.findUnique({
      where: { PelangganID: pelangganId },
    });
  }

  // Create a new customer
  async createPelanggan(newPelanggan: { namaPelanggan: string; alamat: string; nomorTelepon: string }) {
    return prisma.pelanggan.create({
      data: {
        NamaPelanggan: newPelanggan.namaPelanggan,
        Alamat: newPelanggan.alamat,
        NomorTelepon: newPelanggan.nomorTelepon,
      },
    });
  }

  // Update an existing customer
  async updatePelanggan(pelangganId: number, updatedPelanggan: { namaPelanggan?: string; alamat?: string; nomorTelepon?: string }) {
    const existingPelanggan = await prisma.pelanggan.findUnique({
      where: { PelangganID: pelangganId },
    });

    if (!existingPelanggan) {
      throw new Error('Customer not found');
    }

    return prisma.pelanggan.update({
      where: { PelangganID: pelangganId },
      data: {
        NamaPelanggan: updatedPelanggan.namaPelanggan,
        Alamat: updatedPelanggan.alamat,
        NomorTelepon: updatedPelanggan.nomorTelepon,
      },
    });
  }

  // Delete a customer
  async deletePelanggan(id: number): Promise<boolean> {
    try {
      const pelanggan = await prisma.pelanggan.findUnique({
        where: { PelangganID: id },
      });
      if (!pelanggan) {
        return false;
      }

      await prisma.pelanggan.delete({
        where: { PelangganID: id },
      });

      return true;
    } catch (error) {
      throw new Error('Error occurred while deleting customer');
    }
  }
}
