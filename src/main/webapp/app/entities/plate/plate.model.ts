import * as dayjs from 'dayjs';
import { IPays } from 'app/entities/pays/pays.model';
import { INatureConfig } from 'app/entities/nature-config/nature-config.model';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { IModeCalcul } from 'app/entities/mode-calcul/mode-calcul.model';

export interface IPlate {
  id?: number;
  priorite?: number | null;
  valueCalcul?: number | null;
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
  pays?: IPays | null;
  natureConfig?: INatureConfig | null;
  affilication?: IAffiliation | null;
  entreprise?: IEntreprise | null;
  groupe?: IGroupe | null;
  modeCalcul?: IModeCalcul | null;
}

export class Plate implements IPlate {
  constructor(
    public id?: number,
    public priorite?: number | null,
    public valueCalcul?: number | null,
    public code?: string | null,
    public libAr?: string | null,
    public libEn?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public util?: string | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public pays?: IPays | null,
    public natureConfig?: INatureConfig | null,
    public affilication?: IAffiliation | null,
    public entreprise?: IEntreprise | null,
    public groupe?: IGroupe | null,
    public modeCalcul?: IModeCalcul | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getPlateIdentifier(plate: IPlate): number | undefined {
  return plate.id;
}
