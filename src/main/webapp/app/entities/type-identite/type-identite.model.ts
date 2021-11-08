import * as dayjs from 'dayjs';
import { IPays } from 'app/entities/pays/pays.model';

export interface ITypeIdentite {
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
  pays?: IPays | null;
}

export class TypeIdentite implements ITypeIdentite {
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
    public pays?: IPays | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getTypeIdentiteIdentifier(typeIdentite: ITypeIdentite): number | undefined {
  return typeIdentite.id;
}
