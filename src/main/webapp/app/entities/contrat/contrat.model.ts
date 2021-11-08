import * as dayjs from 'dayjs';
import { IEmploye } from 'app/entities/employe/employe.model';
import { ISousTypeContrat } from 'app/entities/sous-type-contrat/sous-type-contrat.model';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';
import { IDevise } from 'app/entities/devise/devise.model';

export interface IContrat {
  id?: number;
  ref?: string | null;
  matricule?: string | null;
  dateDebut?: dayjs.Dayjs | null;
  dateFin?: dayjs.Dayjs | null;
  util?: string | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  employe?: IEmploye | null;
  sousType?: ISousTypeContrat | null;
  groupe?: IGroupe | null;
  entreprise?: IEntreprise | null;
  affiliation?: IAffiliation | null;
  devise?: IDevise | null;
}

export class Contrat implements IContrat {
  constructor(
    public id?: number,
    public ref?: string | null,
    public matricule?: string | null,
    public dateDebut?: dayjs.Dayjs | null,
    public dateFin?: dayjs.Dayjs | null,
    public util?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public employe?: IEmploye | null,
    public sousType?: ISousTypeContrat | null,
    public groupe?: IGroupe | null,
    public entreprise?: IEntreprise | null,
    public affiliation?: IAffiliation | null,
    public devise?: IDevise | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getContratIdentifier(contrat: IContrat): number | undefined {
  return contrat.id;
}
