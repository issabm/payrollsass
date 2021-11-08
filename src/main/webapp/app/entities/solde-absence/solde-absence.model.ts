import * as dayjs from 'dayjs';
import { IEmploye } from 'app/entities/employe/employe.model';
import { INatureAbsence } from 'app/entities/nature-absence/nature-absence.model';

export interface ISoldeAbsence {
  id?: number;
  annee?: number | null;
  nbDaysRight?: number | null;
  nbDaysConsumed?: number | null;
  nbDaysUnavailble?: number | null;
  nbDaysAvailble?: number | null;
  nbDaysLeft?: number | null;
  util?: string | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  employe?: IEmploye | null;
  natureAbsence?: INatureAbsence | null;
}

export class SoldeAbsence implements ISoldeAbsence {
  constructor(
    public id?: number,
    public annee?: number | null,
    public nbDaysRight?: number | null,
    public nbDaysConsumed?: number | null,
    public nbDaysUnavailble?: number | null,
    public nbDaysAvailble?: number | null,
    public nbDaysLeft?: number | null,
    public util?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public employe?: IEmploye | null,
    public natureAbsence?: INatureAbsence | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getSoldeAbsenceIdentifier(soldeAbsence: ISoldeAbsence): number | undefined {
  return soldeAbsence.id;
}
