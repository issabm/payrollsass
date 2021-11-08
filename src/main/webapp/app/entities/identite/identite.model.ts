import * as dayjs from 'dayjs';
import { IEmploye } from 'app/entities/employe/employe.model';
import { ITypeIdentite } from 'app/entities/type-identite/type-identite.model';

export interface IIdentite {
  id?: number;
  code?: string | null;
  dateIssued?: dayjs.Dayjs | null;
  placeIssed?: string | null;
  dateVld?: dayjs.Dayjs | null;
  util?: string | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  employe?: IEmploye | null;
  typeIdentite?: ITypeIdentite | null;
}

export class Identite implements IIdentite {
  constructor(
    public id?: number,
    public code?: string | null,
    public dateIssued?: dayjs.Dayjs | null,
    public placeIssed?: string | null,
    public dateVld?: dayjs.Dayjs | null,
    public util?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public employe?: IEmploye | null,
    public typeIdentite?: ITypeIdentite | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getIdentiteIdentifier(identite: IIdentite): number | undefined {
  return identite.id;
}
