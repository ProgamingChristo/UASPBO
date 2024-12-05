import { Request, Response } from 'express';
import { PenjualanService } from './penjualan.service';
import { CreatePenjualanDto, UpdatePenjualanDto } from './penjualan.interface';

export class PenjualanController {
  private penjualanService: PenjualanService;

  constructor() {
    this.penjualanService = new PenjualanService();
  }

  // Get all penjualan
  async getAllPenjualan(req: Request, res: Response): Promise<void> {
    try {
      const penjualan = await this.penjualanService.getAllPenjualan();
      res.status(200).json(penjualan);
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(400).json({ error: message });
    }
  }

  // Get penjualan by ID
  async getPenjualanById(req: Request, res: Response): Promise<void> {
    const penjualanId = parseInt(req.params.id, 10);
    try {
      const penjualan = await this.penjualanService.getPenjualanById(penjualanId);
      if (penjualan) {
        res.status(200).json(penjualan);
      } else {
        res.status(404).json({ error: `Penjualan with ID ${penjualanId} not found` });
      }
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(400).json({ error: message });
    }
  }

  // Create new penjualan
  async createPenjualan(req: Request, res: Response): Promise<void> {
    try {
      const newPenjualan: CreatePenjualanDto = req.body;
      if (!newPenjualan.pelangganID || !newPenjualan.detailPenjualan || newPenjualan.detailPenjualan.length === 0) {
        throw new Error('Missing required fields: pelangganID and detailPenjualan');
      }
      const result = await this.penjualanService.createPenjualan(newPenjualan);
      res.status(201).json(result);
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(400).json({ error: message });
    }
  }

  // Update penjualan
  async updatePenjualan(req: Request, res: Response): Promise<void> {
    const penjualanId = parseInt(req.params.id, 10);
    try {
      const updatedPenjualan: UpdatePenjualanDto = req.body;
      const result = await this.penjualanService.updatePenjualan(penjualanId, updatedPenjualan);
      if (result) {
        res.status(200).json(result);
      } else {
        res.status(404).json({ error: `Penjualan with ID ${penjualanId} not found` });
      }
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(400).json({ error: message });
    }
  }
}
