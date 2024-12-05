import { Router } from 'express';
import { PenjualanController } from './penjualan.controller';

const penjualanRouter = Router();
const penjualanController = new PenjualanController();

// Get all penjualan route
penjualanRouter.get('/', penjualanController.getAllPenjualan.bind(penjualanController));

// Get penjualan by ID route
penjualanRouter.get('/:id', penjualanController.getPenjualanById.bind(penjualanController));

// Create new penjualan route
penjualanRouter.post('/', penjualanController.createPenjualan.bind(penjualanController));

// Update penjualan route
penjualanRouter.put('/:id', penjualanController.updatePenjualan.bind(penjualanController));

export default penjualanRouter;
