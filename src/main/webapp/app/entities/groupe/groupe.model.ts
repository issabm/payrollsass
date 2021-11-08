import * as dayjs from 'dayjs';
import { IPays } from 'app/entities/pays/pays.model';
import { IDevise } from 'app/entities/devise/devise.model';

export interface IGroupe {
  id?: number;
  code?: string | null;
  libAr?: string | null;
  libEn?: string | null;
  addresseAr?: string | null;
  addresseEn?: string | null;
  tel?: string | null;
  email?: string | null;
  fax?: string | null;
  util?: string | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  pays?: IPays | null;
  devise?: IDevise | null;
}

export class Groupe implements IGroupe {
  constructor(
    public id?: number,
    public code?: string | null,
    public libAr?: string | null,
    public libEn?: string | null,
    public addresseAr?: string | null,
    public addresseEn?: string | null,
    public tel?: string | null,
    public email?: string | null,
    public fax?: string | null,
    public util?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public pays?: IPays | null,
    public devise?: IDevise | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getGroupeIdentifier(groupe: IGroupe): number | undefined {
  return groupe.id;
}
