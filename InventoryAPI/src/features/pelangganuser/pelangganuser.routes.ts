import { Router } from 'express';
import { PelangganController } from './pelangganuser.controller';

const Pelangganuserrouter = Router();
const pelangganController = new PelangganController();

// Create a new pelanggan and sync to pelanggan_user
Pelangganuserrouter.post('/', pelangganController.createPelangganUser.bind(pelangganController));

// Update a pelanggan and sync to pelanggan_user
Pelangganuserrouter.put('/:id', pelangganController.updatePelangganUser.bind(pelangganController));

// Get a pelanggan_user by user_id
Pelangganuserrouter.get('/user/:userId', pelangganController.getPelangganUserByUserId.bind(pelangganController));

export default Pelangganuserrouter;
