import * as dayjs from 'dayjs';
import { IPays } from 'app/entities/pays/pays.model';
import { INatureConfig } from 'app/entities/nature-config/nature-config.model';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { IGroupe } from 'app/entities/groupe/groupe.model';

export interface IEmploi {
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
  natureConfig?: INatureConfig | null;
  affilication?: IAffiliation | null;
  entreprise?: IEntreprise | null;
  groupe?: IGroupe | null;
}

export class Emploi implements IEmploi {
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
    public pays?: IPays | null,
    public natureConfig?: INatureConfig | null,
    public affilication?: IAffiliation | null,
    public entreprise?: IEntreprise | null,
    public groupe?: IGroupe | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getEmploiIdentifier(emploi: IEmploi): number | undefined {
  return emploi.id;
}
