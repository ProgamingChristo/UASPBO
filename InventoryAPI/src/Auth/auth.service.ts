import { PrismaClient } from '@prisma/client';
import bcrypt from 'bcrypt';
import jwt from 'jsonwebtoken';
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

  // Login a user
  async login(email: string, password: string): Promise<{role: string; accessToken: string; refreshToken: string } | null> {
    const user = await prisma.user.findUnique({
      where: { email },
    });

    if (!user) return null;

    const isPasswordValid = await bcrypt.compare(password, user.password);
    if (!isPasswordValid) return null;

    const accessToken = jwt.sign({ userId: user.user_id }, process.env.ACCESS_TOKEN_SECRET!, { expiresIn: '15m' });
    const refreshToken = jwt.sign({ userId: user.user_id }, process.env.REFRESH_TOKEN_SECRET!, { expiresIn: '7d' });

    return {  role: user.role,  accessToken, refreshToken };
  }

  async logout(refreshToken: string): Promise<void> {
  
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

  async getSession(token: string) {
    try {
      // Verifikasi token JWT
      const decoded: any = jwt.verify(token, process.env.REFRESH_TOKEN_SECRET!);
  
      // Ambil user berdasarkan userId yang ada di dalam token
      const user = await prisma.user.findUnique({
        where: { user_id: decoded.userId },
      });
  
      if (!user) {
        throw new Error('User not found');
      }
  
      // Mengembalikan data pengguna
      return {
        userId: user.user_id,
        username: user.username,
        email: user.email,
        // Tambahkan data lainnya sesuai dengan skema user Anda
      };
    } catch (error) {
      throw new Error('Invalid or expired token');
    }
  }
  
}
