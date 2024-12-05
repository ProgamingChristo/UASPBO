import { Router } from 'express';
import { AuthController } from './auth.controller';

const authRouter = Router();
const authController = new AuthController();

// Register route
authRouter.post('/register', authController.register.bind(authController));

// Login route
authRouter.post('/login', authController.login.bind(authController));

// Logout route
authRouter.post('/logout', authController.logout.bind(authController));

authRouter.get('/sess', async (req, res) => {
    await authController.getSession(req, res);  // Panggil fungsi getSession langsung
  });
export default authRouter;
