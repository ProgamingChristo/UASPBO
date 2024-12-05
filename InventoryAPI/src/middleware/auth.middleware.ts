import { Request, Response, NextFunction } from 'express';
import jwt from 'jsonwebtoken';
import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

export const authMiddleware = async (req: Request, res: Response, next: NextFunction): Promise<void> => {
  const token = req.cookies['access_token'];  // Read the token from cookies

  if (!token) {
    res.status(401).json({ message: 'Authorization token is missing' });
    return;
  }

  // try {
  //   const secretKey = process.env.JWT_SECRET_KEY || 'default_secret_key';
    
  //   // Verifikasi token JWT
  //   const decoded = jwt.verify(token, secretKey) as { userId: number; username: string };

  //   // Verifikasi token di database
  //   const dbToken = await prisma.token.findFirst({
  //     where: { 
  //       iduser: decoded.userId,
  //       token: token,
  //     },
  //   });

  //   if (!dbToken) {
  //     res.status(403).json({ message: 'Token is invalid or not recognized' });
  //     return;
  //   }

  //   // Jika token valid dan ada di database, lanjutkan
  //   (req as any).user = decoded;

  //   next();
  // } catch (error) {
  //   if (error instanceof jwt.TokenExpiredError) {
  //     res.status(403).json({ message: 'Token has expired' });
  //   } else {
  //     res.status(403).json({ message: 'Invalid token' });
  //   }
  // }
};
