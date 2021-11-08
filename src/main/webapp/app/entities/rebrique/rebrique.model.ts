import * as dayjs from 'dayjs';
import { IPays } from 'app/entities/pays/pays.model';
import { INatureConfig } from 'app/entities/nature-config/nature-config.model';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { IModeInput } from 'app/entities/mode-input/mode-input.model';
import { ISens } from 'app/entities/sens/sens.model';
import { IConcerne } from 'app/entities/concerne/concerne.model';
import { IFrequence } from 'app/entities/frequence/frequence.model';

export interface IRebrique {
  id?: number;
  priorite?: number | null;
  code?: string | null;
  libAr?: string | null;
  libFr?: string | null;
  libEn?: string | null;
  inTax?: boolean | null;
  minValue?: number | null;
  maxValue?: number | null;
  dateSituation?: dayjs.Dayjs | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  createdBy?: string | null;
  util?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  pays?: IPays | null;
  natureConfig?: INatureConfig | null;
  affilication?: IAffiliation | null;
  entreprise?: IEntreprise | null;
  groupe?: IGroupe | null;
  modeInput?: IModeInput | null;
  sens?: ISens | null;
  concerne?: IConcerne | null;
  frequence?: IFrequence | null;
}

export class Rebrique implements IRebrique {
  constructor(
    public id?: number,
    public priorite?: number | null,
    public code?: string | null,
    public libAr?: string | null,
    public libFr?: string | null,
    public libEn?: string | null,
    public inTax?: boolean | null,
    public minValue?: number | null,
    public maxValue?: number | null,
    public dateSituation?: dayjs.Dayjs | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public createdBy?: string | null,
    public util?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public pays?: IPays | null,
    public natureConfig?: INatureConfig | null,
    public affilication?: IAffiliation | null,
    public entreprise?: IEntreprise | null,
    public groupe?: IGroupe | null,
    public modeInput?: IModeInput | null,
    public sens?: ISens | null,
    public concerne?: IConcerne | null,
    public frequence?: IFrequence | null
  ) {
    this.inTax = this.inTax ?? false;
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getRebriqueIdentifier(rebrique: IRebrique): number | undefined {
  return rebrique.id;
}
