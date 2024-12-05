import { Router } from 'express';
import { ProdukController } from './produk.controller';

const produkRouter = Router();
const produkController = new ProdukController();

// Get all products route
produkRouter.get('/', produkController.getAllProduk.bind(produkController));

// Get product by ID route
produkRouter.get('/:id', produkController.getProdukById.bind(produkController));

// Create new product route
produkRouter.post('/', produkController.createProduk.bind(produkController));

// Update product route
produkRouter.put('/:id', produkController.updateProduk.bind(produkController));

// Delete product route
produkRouter.delete('/:id', produkController.deleteProduk.bind(produkController));

export default produkRouter;
