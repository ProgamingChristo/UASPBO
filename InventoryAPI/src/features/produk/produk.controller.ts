import { Request, Response } from 'express';
import { ProdukService } from './produk.service';
import { CreateProdukDto, UpdateProdukDto } from './produk.interface';

export class ProdukController {
  private produkService: ProdukService;

  constructor() {
    this.produkService = new ProdukService();
  }

  // Get all products
  async getAllProduk(req: Request, res: Response): Promise<void> {
    try {
      const produk = await this.produkService.getAllProduk();
      res.status(200).json(produk);
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(400).json({ error: message });
    }
  }

  // Get a product by ID
  async getProdukById(req: Request, res: Response): Promise<void> {
    const produkId = parseInt(req.params.id, 10);
    try {
      const produk = await this.produkService.getProdukById(produkId);
      if (produk) {
        res.status(200).json(produk);
      } else {
        res.status(404).json({ error: 'Product not found' });
      }
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(400).json({ error: message });
    }
  }

  async createProduk(req: Request, res: Response): Promise<void> {
    try {
      const newProduk: CreateProdukDto = req.body;
  
      // Normalizing input field names
      const normalizedProduk = {
        NamaProduk: newProduk.namaProduk,
        Harga: newProduk.harga,
        Stok: newProduk.stok,
        ImgUrl: newProduk.imgUrl || "https://placehold.co/150x145", 
      };
  
      // Validasi input
      if (!normalizedProduk.NamaProduk || !normalizedProduk.Harga || !normalizedProduk.Stok) {
        throw new Error("Missing required fields");
      }
  
      const result = await this.produkService.createProduk(normalizedProduk);
      res.status(201).json(result);
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(400).json({ error: message });
    }
  }
  

  // Update an existing product
  async updateProduk(req: Request, res: Response): Promise<void> {
    const produkId = parseInt(req.params.id, 10);
    try {
      const updatedProduk: UpdateProdukDto = req.body;

      // Normalize input field names
      const normalizedUpdatedProduk = {
        NamaProduk: updatedProduk.namaProduk,
        Harga: updatedProduk.harga,
        Stok: updatedProduk.stok,
        ImgUrl: updatedProduk.imgUrl || undefined,  // Ensure imgUrl is only set if provided
      };

      const result = await this.produkService.updateProduk(produkId, normalizedUpdatedProduk);
      if (result) {
        res.status(200).json(result);
      } else {
        res.status(404).json({ error: 'Product not found' });
      }
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(400).json({ error: message });
    }
  }
 

  // Delete a product
  async deleteProduk(req: Request, res: Response): Promise<void> {
    const { id } = req.params;
    try {
      const produkId = parseInt(id, 10);
      const result = await this.produkService.deleteProduk(produkId);
      if (!result) {
        res.status(404).json({ error: 'Product not found' });
        return;
      }
      res.status(200).json({ message: 'Product deleted successfully' });
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(500).json({ error: message });
    }
  }
}
