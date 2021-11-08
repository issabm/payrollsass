import * as dayjs from 'dayjs';

export interface IDemandeCalculPaie {
  id?: number;
  code?: string | null;
  lib?: string | null;
  annee?: number | null;
  mois?: number | null;
  dateCalcul?: dayjs.Dayjs | null;
  dateValid?: dayjs.Dayjs | null;
  datePayroll?: dayjs.Dayjs | null;
  totalNet?: number | null;
  totalNetDevise?: number | null;
  tauxChange?: number | null;
  calculBy?: string | null;
  effectBy?: string | null;
  dateSituation?: dayjs.Dayjs | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  createdBy?: string | null;
  op?: string | null;
  util?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
}

export class DemandeCalculPaie implements IDemandeCalculPaie {
  constructor(
    public id?: number,
    public code?: string | null,
    public lib?: string | null,
    public annee?: number | null,
    public mois?: number | null,
    public dateCalcul?: dayjs.Dayjs | null,
    public dateValid?: dayjs.Dayjs | null,
    public datePayroll?: dayjs.Dayjs | null,
    public totalNet?: number | null,
    public totalNetDevise?: number | null,
    public tauxChange?: number | null,
    public calculBy?: string | null,
    public effectBy?: string | null,
    public dateSituation?: dayjs.Dayjs | null,
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

export function getDemandeCalculPaieIdentifier(demandeCalculPaie: IDemandeCalculPaie): number | undefined {
  return demandeCalculPaie.id;
}
