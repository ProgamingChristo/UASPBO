import { Router } from 'express';
import { StatistikController } from './statistik.controller';

const statistikRouter = Router();
const statistikController = new StatistikController();

// Get total count for each table
statistikRouter.get('/', statistikController.getTotalCount.bind(statistikController));

export default statistikRouter;
