import { PrismaClient } from '@prisma/client';
import bcrypt from 'bcrypt';
import jwt from 'jsonwebtoken';
import { Request, Response } from 'express';
import { User } from './auth.interface';

const prisma = new PrismaClient();

export class AuthService {
  // Register a new user
  async register(username: string, email: string, password: string): Promise<User> {
    const hashedPassword = await bcrypt.hash(password, 10);
    const refreshToken = jwt.sign({ username, email }, process.env.REFRESH_TOKEN_SECRET!, { expiresIn: '7d' });
    
    return prisma.user.create({
      data: {
        username,
        email,
        password: hashedPassword,
        refreshtoken: refreshToken, // Add the refresh token here
      },
    });
  }

  async login(email: string, password: string): Promise<{ user_id: number; role: string; accessToken: string; refreshToken: string } | null> {
    const user = await prisma.user.findUnique({
      where: { email },
    });
  
    if (!user) return null;
  
    // Validate password
    const isHashedPasswordValid = await bcrypt.compare(password, user.password); // Check if the hashed password matches
    const isPlaintextPasswordValid = password === user.password; // Check if the password matches exactly (plaintext)
  
    // Continue only if either validation is successful
    if (!isHashedPasswordValid && !isPlaintextPasswordValid) return null;
  
    // Generate new refresh token
    const newRefreshToken = jwt.sign({ userId: user.user_id }, process.env.REFRESH_TOKEN_SECRET!, { expiresIn: '7d' });
  
    // Update the user's refresh token in the database
    await prisma.user.update({
      where: { email },
      data: { refreshtoken: newRefreshToken },  
    });
  
    // Generate access token
    const accessToken = jwt.sign({ userId: user.user_id }, process.env.ACCESS_TOKEN_SECRET!, { expiresIn: '15m' });
  
    // Return user data along with access and refresh tokens
    return { user_id: user.user_id, role: user.role, accessToken, refreshToken: newRefreshToken };
  }
  
  
  async logout(req: Request, res: Response): Promise<void> {
    try {
      // Clear the refreshToken cookie
      res.clearCookie('refreshToken', { httpOnly: true, secure: process.env.NODE_ENV === 'production' });

      // Clear the Authorization header if it exists
      res.setHeader('Authorization', '');

      res.status(200).json({ message: 'Logged out successfully' });
    } catch (error) {
      res.status(400).json({ error: error instanceof Error ? error.message : 'Unknown error occurred' });
    }
  }

  // Verify refresh token
  async verifyRefreshToken(refreshToken: string): Promise<any> {
    try {
      const decoded = jwt.verify(refreshToken, process.env.REFRESH_TOKEN_SECRET!);
      return decoded;
    } catch (error) {
      return null;
    }
  }

  async getSession(refreshToken: string) {
    try {
      // Verify the refresh token
      const decoded: any = jwt.verify(refreshToken, process.env.REFRESH_TOKEN_SECRET!);
  
      // Get user based on userId in the decoded token
      const user = await prisma.user.findUnique({
        where: { user_id: decoded.userId },
      });
  
      if (!user) {
        throw new Error('User not found');
      }
  
      // Return user data
      return {
        userId: user.user_id,
        username: user.username,
        email: user.email,
      };
    } catch (error) {
      throw new Error('Invalid or expired token');
    }
  }
}
