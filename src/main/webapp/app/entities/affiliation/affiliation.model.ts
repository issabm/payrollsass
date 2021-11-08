import * as dayjs from 'dayjs';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { IPays } from 'app/entities/pays/pays.model';
import { IDevise } from 'app/entities/devise/devise.model';

export interface IAffiliation {
  id?: number;
  code?: string | null;
  libAr?: string | null;
  libEn?: string | null;
  tel?: string | null;
  email?: string | null;
  fax?: string | null;
  util?: string | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  entreprise?: IEntreprise | null;
  direction?: IAffiliation | null;
  pays?: IPays | null;
  devise?: IDevise | null;
}

export class Affiliation implements IAffiliation {
  constructor(
    public id?: number,
    public code?: string | null,
    public libAr?: string | null,
    public libEn?: string | null,
    public tel?: string | null,
    public email?: string | null,
    public fax?: string | null,
    public util?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public entreprise?: IEntreprise | null,
    public direction?: IAffiliation | null,
    public pays?: IPays | null,
    public devise?: IDevise | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getAffiliationIdentifier(affiliation: IAffiliation): number | undefined {
  return affiliation.id;
}
