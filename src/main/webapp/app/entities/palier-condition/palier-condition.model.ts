import * as dayjs from 'dayjs';
import { IPalierPlate } from 'app/entities/palier-plate/palier-plate.model';

export interface IPalierCondition {
  id?: number;
  code?: string | null;
  libEn?: string | null;
  libAr?: string | null;
  annee?: number | null;
  minVal?: number | null;
  maxVal?: number | null;
  util?: string | null;
  dateop?: dayjs.Dayjs | null;
  dateModif?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  palierPlate?: IPalierPlate | null;
}

export class PalierCondition implements IPalierCondition {
  constructor(
    public id?: number,
    public code?: string | null,
    public libEn?: string | null,
    public libAr?: string | null,
    public annee?: number | null,
    public minVal?: number | null,
    public maxVal?: number | null,
    public util?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public dateModif?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public palierPlate?: IPalierPlate | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getPalierConditionIdentifier(palierCondition: IPalierCondition): number | undefined {
  return palierCondition.id;
}
