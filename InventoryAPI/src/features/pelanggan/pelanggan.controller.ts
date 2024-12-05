import { Request, Response } from 'express';
import { PelangganService } from './pelanggan.service';
import { CreatePelangganDto, UpdatePelangganDto } from './pelanggan.interface';

export class PelangganController {
  private pelangganService: PelangganService;

  constructor() {
    this.pelangganService = new PelangganService();
  }

  // Get all customers
  async getAllPelanggan(req: Request, res: Response): Promise<void> {
    try {
      const pelanggan = await this.pelangganService.getAllPelanggan();
      res.status(200).json(pelanggan);
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(400).json({ error: message });
    }
  }

  // Get customer by ID
  async getPelangganById(req: Request, res: Response): Promise<void> {
    const pelangganId = parseInt(req.params.id, 10);
    try {
      const pelanggan = await this.pelangganService.getPelangganById(pelangganId);
      if (pelanggan) {
        res.status(200).json(pelanggan);
      } else {
        res.status(404).json({ error: 'Customer not found' });
      }
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(400).json({ error: message });
    }
  }

  // Create a new customer
  async createPelanggan(req: Request, res: Response): Promise<void> {
    try {
      const newPelanggan: CreatePelangganDto = req.body;

      // Normalizing input field names
      const normalizedPelanggan = {
        namaPelanggan: newPelanggan.namaPelanggan,
        alamat: newPelanggan.alamat,
        nomorTelepon: newPelanggan.nomorTelepon,
      };

      // Check if necessary fields are provided
      if (!normalizedPelanggan.namaPelanggan || !normalizedPelanggan.alamat || !normalizedPelanggan.nomorTelepon) {
        throw new Error("Missing required fields");
      }

      const result = await this.pelangganService.createPelanggan(normalizedPelanggan);
      res.status(201).json(result);
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(400).json({ error: message });
    }
  }

  // Update an existing customer
  async updatePelanggan(req: Request, res: Response): Promise<void> {
    const pelangganId = parseInt(req.params.id, 10);
    try {
      const updatedPelanggan: UpdatePelangganDto = req.body;

      // Normalizing input field names
      const normalizedUpdatedPelanggan = {
        namaPelanggan: updatedPelanggan.namaPelanggan,
        alamat: updatedPelanggan.alamat,
        nomorTelepon: updatedPelanggan.nomorTelepon,
      };

      const result = await this.pelangganService.updatePelanggan(pelangganId, normalizedUpdatedPelanggan);
      if (result) {
        res.status(200).json(result);
      } else {
        res.status(404).json({ error: 'Customer not found' });
      }
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(400).json({ error: message });
    }
  }

  // Delete a customer
  async deletePelanggan(req: Request, res: Response): Promise<void> {
    const { id } = req.params;
    try {
      const pelangganId = parseInt(id, 10);
      const result = await this.pelangganService.deletePelanggan(pelangganId);
      if (!result) {
        res.status(404).json({ error: 'Customer not found' });
        return;
      }
      res.status(200).json({ message: 'Customer deleted successfully' });
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(500).json({ error: message });
    }
  }
}
