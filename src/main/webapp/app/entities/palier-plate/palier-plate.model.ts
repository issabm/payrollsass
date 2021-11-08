import * as dayjs from 'dayjs';
import { IPays } from 'app/entities/pays/pays.model';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { IPlate } from 'app/entities/plate/plate.model';

export interface IPalierPlate {
  id?: number;
  code?: string | null;
  libEn?: string | null;
  libAr?: string | null;
  annee?: number | null;
  effectiValue?: number | null;
  util?: string | null;
  dateop?: dayjs.Dayjs | null;
  dateModif?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  pays?: IPays | null;
  affilication?: IAffiliation | null;
  entreprise?: IEntreprise | null;
  groupe?: IGroupe | null;
  plateTarget?: IPlate | null;
  platBaseCalcul?: IPlate | null;
}

export class PalierPlate implements IPalierPlate {
  constructor(
    public id?: number,
    public code?: string | null,
    public libEn?: string | null,
    public libAr?: string | null,
    public annee?: number | null,
    public effectiValue?: number | null,
    public util?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public dateModif?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public pays?: IPays | null,
    public affilication?: IAffiliation | null,
    public entreprise?: IEntreprise | null,
    public groupe?: IGroupe | null,
    public plateTarget?: IPlate | null,
    public platBaseCalcul?: IPlate | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getPalierPlateIdentifier(palierPlate: IPalierPlate): number | undefined {
  return palierPlate.id;
}
