import * as dayjs from 'dayjs';

export interface ITypeHandicap {
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
}

export class TypeHandicap implements ITypeHandicap {
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
    public modifiedDate?: dayjs.Dayjs | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getTypeHandicapIdentifier(typeHandicap: ITypeHandicap): number | undefined {
  return typeHandicap.id;
}
