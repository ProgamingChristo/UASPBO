import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

export class pelangganuserService {
  // Create a new pelanggan (customer)
  async createPelanggan(namaPelanggan: string, alamat: string, nomorTelepon: string) {
    const newPelanggan = await prisma.pelanggan.create({
      data: {
        NamaPelanggan: namaPelanggan,
        Alamat: alamat,
        NomorTelepon: nomorTelepon,
      },
    });

    return newPelanggan;
  }

  // Create a new pelanggan_user and sync it with the user
  async createPelangganUser(PelangganID: number, user_id: number) {
    const newPelangganUser = await prisma.pelanggan_user.create({
      data: {
        PelangganID,
        user_id,
      },
    });

    return newPelangganUser;
  }

  // Update a pelanggan (customer) by PelangganID
  async updatePelanggan(id: string, namaPelanggan: string, alamat: string, nomorTelepon: string) {
    // Check if the pelanggan exists
    const pelangganExists = await prisma.pelanggan.findUnique({
      where: { PelangganID: parseInt(id) },
    });

    if (!pelangganExists) {
      throw new Error(`Pelanggan with ID ${id} not found`);
    }

    // Proceed with the update if the pelanggan exists
    const updatedPelanggan = await prisma.pelanggan.update({
      where: { PelangganID: parseInt(id) },
      data: {
        NamaPelanggan: namaPelanggan,
        Alamat: alamat,
        NomorTelepon: nomorTelepon,
      },
    });

    return updatedPelanggan;
  }

  // Update the pelanggan_user table
  async updatePelangganUser(PelangganID: number, user_id: number) {
    // Check if the link exists before updating
    const existingLink = await prisma.pelanggan_user.findFirst({
      where: {
        PelangganID,
        user_id,
      },
    });

    if (existingLink) {
      const updatedPelangganUser = await prisma.pelanggan_user.update({
        where: { pelangganUserID: existingLink.pelangganUserID },
        data: { PelangganID, user_id },
      });
      return updatedPelangganUser;
    } else {
      const newPelangganUser = await prisma.pelanggan_user.create({
        data: { PelangganID, user_id },
      });
      return newPelangganUser;
    }
  }

  // Get a pelanggan_user by user_id
  async getPelangganUserByUserId(user_id: number) {
    return prisma.pelanggan_user.findFirst({
      where: { user_id },
      include: {
        pelanggan: true, // Optionally include the related 'pelanggan' data
      },
    });
  }

  // Check if a user is already linked to a pelanggan
  async findPelangganUserByUserId(user_id: number) {
    return prisma.pelanggan_user.findFirst({
      where: { user_id },
    });
  }
}
