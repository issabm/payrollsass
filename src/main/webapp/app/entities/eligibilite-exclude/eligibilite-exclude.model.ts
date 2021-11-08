import * as dayjs from 'dayjs';

export interface IEligibiliteExclude {
  id?: number;
  anneeBegin?: number | null;
  moisBegin?: number | null;
  anneeEnd?: number | null;
  moisEnd?: number | null;
  matricule?: string | null;
  code?: string | null;
  libEn?: string | null;
  libAr?: string | null;
  libFr?: string | null;
  annee?: number | null;
  valPayroll?: number | null;
  nbDaysLeave?: number | null;
  pourValPayroll?: number | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  createdBy?: string | null;
  op?: string | null;
  util?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
}

export class EligibiliteExclude implements IEligibiliteExclude {
  constructor(
    public id?: number,
    public anneeBegin?: number | null,
    public moisBegin?: number | null,
    public anneeEnd?: number | null,
    public moisEnd?: number | null,
    public matricule?: string | null,
    public code?: string | null,
    public libEn?: string | null,
    public libAr?: string | null,
    public libFr?: string | null,
    public annee?: number | null,
    public valPayroll?: number | null,
    public nbDaysLeave?: number | null,
    public pourValPayroll?: number | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public createdBy?: string | null,
    public op?: string | null,
    public util?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getEligibiliteExcludeIdentifier(eligibiliteExclude: IEligibiliteExclude): number | undefined {
  return eligibiliteExclude.id;
}
