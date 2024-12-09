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
        // Set the refreshToken in the cookie
        res.cookie('refreshToken', tokens.refreshToken, { httpOnly: true, secure: process.env.NODE_ENV === 'production', maxAge: 7 * 24 * 60 * 60 * 1000 }); // 7 days
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
    try {
      res.clearCookie('refreshToken'); // Clear the refreshToken cookie
      res.status(200).json({ message: 'Logged out successfully' });
    } catch (error) {
      res.status(400).json({ error: error instanceof Error ? error.message : 'Unknown error occurred' });
    }
  }

  // Get Session route (Reads refreshToken from cookies)
  async getSession(req: Request, res: Response): Promise<Response> {
    const refreshToken = req.cookies.refreshToken; // Retrieve the refreshToken from cookies
  
    if (!refreshToken) {
      return res.status(400).json({ error: 'Refresh token is required' });
    }
  
    try {
      const userData = await this.authService.getSession(refreshToken);
      return res.status(200).json({ user: userData });
    } catch (error) {
      return res.status(401).json({
        error: error instanceof Error ? error.message : 'Unknown error occurred'
      });
    }
  }
}
