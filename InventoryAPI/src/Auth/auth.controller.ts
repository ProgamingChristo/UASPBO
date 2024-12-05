import { Request, Response } from 'express';
import { AuthService } from './auth.service';

export class AuthController {
  private authService: AuthService;

  constructor() {
    this.authService = new AuthService();
  }

  // Register route
  async register(req: Request, res: Response): Promise<void> {
    const { username, email, password } = req.body;
    try {
      const newUser = await this.authService.register(username, email, password);
      res.status(201).json({ message: 'User registered successfully', user: newUser });
    } catch (error) {
      res.status(400).json({ error: error instanceof Error ? error.message : 'Unknown error occurred' });
    }
  }

  // Login route
  async login(req: Request, res: Response): Promise<void> {
    const { email, password } = req.body;
    try {
      const tokens = await this.authService.login(email, password);
      if (tokens) {
        res.status(200).json(tokens);
      } else {
        res.status(401).json({ error: 'Invalid credentials' });
      }
    } catch (error) {
      res.status(400).json({ error: error instanceof Error ? error.message : 'Unknown error occurred' });
    }
  }

  // Logout route
  async logout(req: Request, res: Response): Promise<void> {
    const { refreshToken } = req.body;
    try {
      await this.authService.logout(refreshToken);
      res.status(200).json({ message: 'Logged out successfully' });
    } catch (error) {
      res.status(400).json({ error: error instanceof Error ? error.message : 'Unknown error occurred' });
    }
  }

  async getSession(req: Request, res: Response): Promise<Response> {
    const token = req.headers['authorization']?.split(' ')[1]; // Ambil token dari header Authorization

    // Jika token tidak ada, kembalikan error
    if (!token) {
      return res.status(400).json({ error: 'Token is required' });
    }

    try {
      // Mendapatkan data pengguna berdasarkan token yang diberikan
      const userData = await this.authService.getSession(token);

      // Jika berhasil, kirimkan data pengguna dalam respons
      return res.status(200).json({ user: userData });
    } catch (error) {
      // Menangani error jika token tidak valid atau expired
      return res.status(401).json({
        error: error instanceof Error ? error.message : 'Unknown error occurred'
      });
    }
  }

  
}
