import { Request, Response } from 'express';
import { StatistikService } from './statistik.service';

export class StatistikController {
  private statistikService: StatistikService;

  constructor() {
    this.statistikService = new StatistikService();
  }

  // Get total count per table
  async getTotalCount(req: Request, res: Response): Promise<void> {
    try {
      const counts = await this.statistikService.getTotalCount();
      res.status(200).json(counts);
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Unknown error occurred';
      res.status(400).json({ error: message });
    }
  }
}
