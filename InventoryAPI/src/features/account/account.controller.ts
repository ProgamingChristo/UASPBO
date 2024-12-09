import { Request, Response } from 'express';
import { AccountService } from './account.service';

export class AccountController {
  private accountService: AccountService;

  constructor() {
    this.accountService = new AccountService();
  }

  // Get all users
  async getAllUsers(req: Request, res: Response): Promise<void> {
    try {
      const users = await this.accountService.getAllUsers();
      res.status(200).json(users);
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(400).json({ error: message });
    }
  }

  // Get a user by ID
  async getUserById(req: Request, res: Response): Promise<void> {
    const userId = parseInt(req.params.id, 10);
    try {
      const user = await this.accountService.getUserById(userId);
      if (user) {
        res.status(200).json(user);
      } else {
        res.status(404).json({ error: 'User not found' });
      }
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(400).json({ error: message });
    }
  }

  // Create a new user
  async createUser(req: Request, res: Response): Promise<void> {
    try {
      const newUser = req.body;

      // Normalizing input field names
      const normalizedUser = {
        username: newUser.username,
        email: newUser.email,
        password: newUser.password,
        role: newUser.role,
      };

      // Check if necessary fields are provided
      if (!normalizedUser.username || !normalizedUser.email || !normalizedUser.password) {
        throw new Error('Missing required fields');
      }

      const result = await this.accountService.createUser(normalizedUser);
      res.status(201).json(result);
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(400).json({ error: message });
    }
  }

  // Update an existing user
  async updateUser(req: Request, res: Response): Promise<void> {
    const userId = parseInt(req.params.id, 10);
    try {
      const updatedUser = req.body;

      // Normalizing input field names
      const normalizedUpdatedUser = {
        username: updatedUser.username,
        email: updatedUser.email,
        password: updatedUser.password,
        refreshtoken: updatedUser.refreshtoken,
        role: updatedUser.role || 'user', 
      };

      const result = await this.accountService.updateUser(userId, normalizedUpdatedUser);
      if (result) {
        res.status(200).json(result);
      } else {
        res.status(404).json({ error: 'User not found' });
      }
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(400).json({ error: message });
    }
  }

  // Delete a user
  async deleteUser(req: Request, res: Response): Promise<void> {
    const { id } = req.params;
    try {
      const userId = parseInt(id, 10);
      const result = await this.accountService.deleteUser(userId);

      if (!result) {
        res.status(404).json({ error: 'User not found' });
        return;
      }

      res.status(200).json({ message: 'User deleted successfully' });
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(500).json({ error: message });
    }
  }
}
