import { plainToClass } from 'class-transformer';
import { validate } from 'class-validator';
import { Request, Response, NextFunction } from 'express';

export const validationMiddleware = (dto: any) => {
  return async (req: Request, res: Response, next: NextFunction) => {
    const dtoInstance = plainToClass(dto, req.body);
    const errors = await validate(dtoInstance);
    if (errors.length > 0) {
      return res.status(400).json({ errors: errors.map(err => err.constraints) });
    }
    next();
  };
};
