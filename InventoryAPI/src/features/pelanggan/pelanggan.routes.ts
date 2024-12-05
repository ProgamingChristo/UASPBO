import { Router } from 'express';
import { PelangganController } from './pelanggan.controller';

const pelangganRouter = Router();
const pelangganController = new PelangganController();

// Get all customers route
pelangganRouter.get('/', pelangganController.getAllPelanggan.bind(pelangganController));

// Get customer by ID route
pelangganRouter.get('/:id', pelangganController.getPelangganById.bind(pelangganController));

// Create new customer route
pelangganRouter.post('/', pelangganController.createPelanggan.bind(pelangganController));

// Update customer route
pelangganRouter.put('/:id', pelangganController.updatePelanggan.bind(pelangganController));

// Delete customer route
pelangganRouter.delete('/:id', pelangganController.deletePelanggan.bind(pelangganController));

export default pelangganRouter;
