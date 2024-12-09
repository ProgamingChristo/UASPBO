import { Request, Response } from 'express';
import { pelangganuserService } from './pelangganuser.service';

export class PelangganController {
  private pelangganuserService: pelangganuserService;

  constructor() {
    this.pelangganuserService = new pelangganuserService();
  }

  // Create a new pelanggan and sync it to pelanggan_user
  async createPelangganUser(req: Request, res: Response): Promise<void> {
    const { namaPelanggan, alamat, nomorTelepon, user_id } = req.body;

    try {
      // Check if the user is already linked to a pelanggan
      const existingLink = await this.pelangganuserService.findPelangganUserByUserId(user_id);
      if (existingLink) {
        res.status(400).json({ error: 'User is already linked to a pelanggan.' });
        return;
      }

      // Create the pelanggan (customer)
      const newPelanggan = await this.pelangganuserService.createPelanggan(namaPelanggan, alamat, nomorTelepon);

      // Link the created pelanggan to the user in the pelanggan_user table
      const newPelangganUser = await this.pelangganuserService.createPelangganUser(newPelanggan.PelangganID, user_id);

      res.status(201).json({
        pelanggan: newPelanggan,
        pelangganUser: newPelangganUser,
      });
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(400).json({ error: message });
    }
  }

  // Update a pelanggan and sync it to pelanggan_user
  async updatePelangganUser(req: Request, res: Response): Promise<void> {
    const { id } = req.params; // This is the user_id from URL
    const { namaPelanggan, alamat, nomorTelepon, user_id } = req.body;

    try {
      // Find the pelanggan_user by user_id
      const pelangganUser = await this.pelangganuserService.findPelangganUserByUserId(parseInt(id));

      if (!pelangganUser) {
        res.status(404).json({ error: 'Pelanggan user link not found for the provided user_id' });
        return;
      }

      // Update the pelanggan (customer) information
      const updatedPelanggan = await this.pelangganuserService.updatePelanggan(
        pelangganUser.PelangganID.toString(), // Use PelangganID from pelanggan_user
        namaPelanggan,
        alamat,
        nomorTelepon
      );

      // Sync the update to the pelanggan_user table
      const updatedPelangganUser = await this.pelangganuserService.updatePelangganUser(updatedPelanggan.PelangganID, user_id);

      res.status(200).json({
        pelanggan: updatedPelanggan,
        pelangganUser: updatedPelangganUser,
      });
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(400).json({ error: message });
    }
  }

  // Get a pelanggan_user by user_id
  async getPelangganUserByUserId(req: Request, res: Response): Promise<void> {
    const userId = parseInt(req.params.userId, 10);
    try {
      const result = await this.pelangganuserService.getPelangganUserByUserId(userId);
      if (result) {
        res.status(200).json(result);
      } else {
        res.status(404).json({ error: 'PelangganUser not found for the given user_id' });
      }
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(400).json({ error: message });
    }
  }
}
