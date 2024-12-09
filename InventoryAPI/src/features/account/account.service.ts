import { PrismaClient, user_role } from '@prisma/client'; // Import the enum
import bcrypt from 'bcryptjs';

const prisma = new PrismaClient();

export class AccountService {
  // Get all users
  async getAllUsers() {
    return prisma.user.findMany();
  }

  // Get a user by ID
  async getUserById(userId: number) {
    return prisma.user.findUnique({
      where: { user_id: userId },
    });
  }

  // Create a new user
  async createUser(newUser: { username: string; email: string; password: string; role:user_role;}) {
    // Check if the email already exists
    const existingUser = await prisma.user.findUnique({
      where: { email: newUser.email },
    });

    if (existingUser) {
      throw new Error('Email is already taken');
    }

    const hashedPassword = await bcrypt.hash(newUser.password, 10);
    return prisma.user.create({
      data: {
        username: newUser.username,
        email: newUser.email,
        password: hashedPassword,
        refreshtoken: '', // Initial refresh token value can be empty
        role: newUser.role , // Default to 'user' role
      },
    });
  }

  // Update an existing user
  async updateUser(userId: number, updatedUser: { username?: string; email?: string; password?: string; refreshtoken?: string; role: user_role }) {
    // If password is updated, hash it
    if (updatedUser.password) {
      updatedUser.password = await bcrypt.hash(updatedUser.password, 10);
    }

    return prisma.user.update({
      where: { user_id: userId },
      data: updatedUser,
    });
  }

  // Delete a user by ID
  async deleteUser(id: number): Promise<boolean> {
    try {
      const user = await prisma.user.findUnique({
        where: { user_id: id },
      });

      if (!user) {
        return false; // User not found
      }

      await prisma.user.delete({
        where: { user_id: id },
      });

      return true; // User deleted successfully
    } catch (error) {
      throw new Error('Error occurred while deleting user');
    }
  }
}
