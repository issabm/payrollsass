import * as dayjs from 'dayjs';

export interface IPays {
  id?: number;
  code?: string | null;
  libAr?: string | null;
  libEn?: string | null;
  dateop?: dayjs.Dayjs | null;
  util?: string | null;
  modifiedBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
}

export class Pays implements IPays {
  constructor(
    public id?: number,
    public code?: string | null,
    public libAr?: string | null,
    public libEn?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public util?: string | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getPaysIdentifier(pays: IPays): number | undefined {
  return pays.id;
}
