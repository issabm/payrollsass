import * as dayjs from 'dayjs';
import { INatureConfig } from 'app/entities/nature-config/nature-config.model';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { ISexe } from 'app/entities/sexe/sexe.model';

export interface INatureAbsence {
  id?: number;
  code?: string | null;
  libAr?: string | null;
  libEn?: string | null;
  util?: string | null;
  dateop?: dayjs.Dayjs | null;
  nbDays?: number | null;
  valuePaied?: number | null;
  modifiedBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  natureConfig?: INatureConfig | null;
  affilication?: IAffiliation | null;
  entreprise?: IEntreprise | null;
  groupe?: IGroupe | null;
  sexe?: ISexe | null;
}

export class NatureAbsence implements INatureAbsence {
  constructor(
    public id?: number,
    public code?: string | null,
    public libAr?: string | null,
    public libEn?: string | null,
    public util?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public nbDays?: number | null,
    public valuePaied?: number | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public natureConfig?: INatureConfig | null,
    public affilication?: IAffiliation | null,
    public entreprise?: IEntreprise | null,
    public groupe?: IGroupe | null,
    public sexe?: ISexe | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getNatureAbsenceIdentifier(natureAbsence: INatureAbsence): number | undefined {
  return natureAbsence.id;
}
