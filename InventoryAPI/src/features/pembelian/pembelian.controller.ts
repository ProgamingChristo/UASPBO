import { Request, Response } from 'express';
import { PembelianService } from './pembelian.service';
import { CreatePembelianDto, UpdatePembelianDto } from './pembelian.interface';

export class PembelianController {
  private pembelianService: PembelianService;

  constructor() {
    this.pembelianService = new PembelianService();
  }

  // Get all pembelian
  async getAllPembelian(req: Request, res: Response): Promise<void> {
    try {
      const pembelian = await this.pembelianService.getAllPembelian();
      res.status(200).json(pembelian);
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(400).json({ error: message });
    }
  }

  // Get pembelian by ID
  async getPembelianById(req: Request, res: Response): Promise<void> {
    const pembelianId = parseInt(req.params.id, 10);
    try {
      const pembelian = await this.pembelianService.getPembelianById(pembelianId);
      if (pembelian) {
        res.status(200).json(pembelian);
      } else {
        res.status(404).json({ error: `Pembelian with ID ${pembelianId} not found` });
      }
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(400).json({ error: message });
    }
  }

  // Create new pembelian
  async createPembelian(req: Request, res: Response): Promise<void> {
    try {
      const newPembelian: CreatePembelianDto = req.body;
      if (!newPembelian.pelangganID || !newPembelian.detailPembelian || newPembelian.detailPembelian.length === 0) {
        throw new Error('Missing required fields: pelangganID and detailPembelian');
      }
      const result = await this.pembelianService.createPembelian(newPembelian);
      res.status(201).json(result);
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(400).json({ error: message });
    }
  }

  // Update pembelian
  async updatePembelian(req: Request, res: Response): Promise<void> {
    const pembelianId = parseInt(req.params.id, 10);
    try {
      const updatedPembelian: UpdatePembelianDto = req.body;
      const result = await this.pembelianService.updatePembelian(pembelianId, updatedPembelian);
      if (result) {
        res.status(200).json(result);
      } else {
        res.status(404).json({ error: `Pembelian with ID ${pembelianId} not found` });
      }
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(400).json({ error: message });
    }
  }
}
