import { Router } from 'express';
import { PembelianController } from './pembelian.controller';

const pembelianRouter = Router();
const pembelianController = new PembelianController();

// Get all pembelian route
pembelianRouter.get('/', pembelianController.getAllPembelian.bind(pembelianController));

// Get pembelian by ID route
pembelianRouter.get('/:id', pembelianController.getPembelianById.bind(pembelianController));

// Create new pembelian route
pembelianRouter.post('/', pembelianController.createPembelian.bind(pembelianController));

// Update pembelian route
pembelianRouter.put('/:id', pembelianController.updatePembelian.bind(pembelianController));

export default pembelianRouter;
