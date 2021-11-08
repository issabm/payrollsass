import * as dayjs from 'dayjs';
import { ITypeContrat } from 'app/entities/type-contrat/type-contrat.model';

export interface ISousTypeContrat {
  id?: number;
  code?: string | null;
  libAr?: string | null;
  libEn?: string | null;
  util?: string | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  typeContrat?: ITypeContrat | null;
}

export class SousTypeContrat implements ISousTypeContrat {
  constructor(
    public id?: number,
    public code?: string | null,
    public libAr?: string | null,
    public libEn?: string | null,
    public util?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public typeContrat?: ITypeContrat | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getSousTypeContratIdentifier(sousTypeContrat: ISousTypeContrat): number | undefined {
  return sousTypeContrat.id;
}
