import * as dayjs from 'dayjs';
import { IContrat } from 'app/entities/contrat/contrat.model';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';
import { ISituation } from 'app/entities/situation/situation.model';

export interface IAffectation {
  id?: number;
  matricule?: string | null;
  dateDebut?: dayjs.Dayjs | null;
  dateFin?: dayjs.Dayjs | null;
  dateop?: dayjs.Dayjs | null;
  util?: string | null;
  modifiedBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  contrat?: IContrat | null;
  groupe?: IGroupe | null;
  entreprise?: IEntreprise | null;
  affiliation?: IAffiliation | null;
  situation?: ISituation | null;
}

export class Affectation implements IAffectation {
  constructor(
    public id?: number,
    public matricule?: string | null,
    public dateDebut?: dayjs.Dayjs | null,
    public dateFin?: dayjs.Dayjs | null,
    public dateop?: dayjs.Dayjs | null,
    public util?: string | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public contrat?: IContrat | null,
    public groupe?: IGroupe | null,
    public entreprise?: IEntreprise | null,
    public affiliation?: IAffiliation | null,
    public situation?: ISituation | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getAffectationIdentifier(affectation: IAffectation): number | undefined {
  return affectation.id;
}
