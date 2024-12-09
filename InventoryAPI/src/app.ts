import express from 'express';
import Accountrouter from './features/account/account.routes';
import cookieParser from 'cookie-parser';
import { authMiddleware } from './middleware/auth.middleware';
import produkRouter from './features/produk/produk.routes';
import pelangganRouter from './features/pelanggan/pelanggan.routes';
import penjualanRouter from './features/penjualan/penjualan.routes';
import pembelianRouter from './features/pembelian/pembelian.routes';
import authRouter from './Auth/auth.routes';
import statistikRouter from './features/statistik/statistik.routes';
import Pelangganuserrouter from './features/pelangganuser/pelangganuser.routes';

const app = express();
const port = 3030;

app.use(express.json());
app.use(cookieParser());  

app.use('/api/auth', authRouter);

// app.use('/api/admin', authMiddleware);
app.use('/api/admin/users', Accountrouter);
app.use('/api/pelanggan-user', Pelangganuserrouter);

// kasir & admin
app.use('/api/produk', produkRouter);
app.use('/api/pelanggan', pelangganRouter);
app.use('/api/penjualan', penjualanRouter);
app.use('/api/pembelian', pembelianRouter);
app.use('/api/statistik', statistikRouter);


app.listen(port, '0.0.0.0', () => {
  console.log('Server is running on port 3030');
});
