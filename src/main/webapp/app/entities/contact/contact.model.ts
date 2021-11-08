import * as dayjs from 'dayjs';
import { IEmploye } from 'app/entities/employe/employe.model';

export interface IContact {
  id?: number;
  nomAr?: string | null;
  prenomAr?: string | null;
  nomEn?: string | null;
  prenomEn?: string | null;
  email?: string | null;
  tel?: string | null;
  phone?: string | null;
  adresse?: string | null;
  util?: string | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  employe?: IEmploye | null;
}

export class Contact implements IContact {
  constructor(
    public id?: number,
    public nomAr?: string | null,
    public prenomAr?: string | null,
    public nomEn?: string | null,
    public prenomEn?: string | null,
    public email?: string | null,
    public tel?: string | null,
    public phone?: string | null,
    public adresse?: string | null,
    public util?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public employe?: IEmploye | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getContactIdentifier(contact: IContact): number | undefined {
  return contact.id;
}
