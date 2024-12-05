import { Router } from 'express';
import { AccountController } from './account.controller';

const Accountrouter = Router();
const accountController = new AccountController();

// Get all users route
Accountrouter.get('/', accountController.getAllUsers.bind(accountController));

// Get user by ID route
Accountrouter.get('/:id', accountController.getUserById.bind(accountController));

// Create new user route
Accountrouter.post('/', accountController.createUser.bind(accountController));

// Update user route
Accountrouter.put('/:id', accountController.updateUser.bind(accountController));

// Delete user route
Accountrouter.delete('/:id', accountController.deleteUser.bind(accountController));

export default Accountrouter;
