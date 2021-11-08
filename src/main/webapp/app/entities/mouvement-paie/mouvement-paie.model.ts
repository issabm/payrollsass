import * as dayjs from 'dayjs';
import { ISituation } from 'app/entities/situation/situation.model';
import { IEmploye } from 'app/entities/employe/employe.model';
import { INatureMvtPaie } from 'app/entities/nature-mvt-paie/nature-mvt-paie.model';
import { IDemandeCalculPaie } from 'app/entities/demande-calcul-paie/demande-calcul-paie.model';

export interface IMouvementPaie {
  id?: number;
  code?: string | null;
  lib?: string | null;
  annee?: number | null;
  mois?: number | null;
  dateCalcul?: dayjs.Dayjs | null;
  dateValid?: dayjs.Dayjs | null;
  datePayroll?: dayjs.Dayjs | null;
  totalNet?: number | null;
  totalNetDevise?: number | null;
  tauxChange?: number | null;
  calculBy?: string | null;
  effectBy?: string | null;
  dateSituation?: dayjs.Dayjs | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  createdBy?: string | null;
  op?: string | null;
  util?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  situation?: ISituation | null;
  employe?: IEmploye | null;
  natureMvtPaie?: INatureMvtPaie | null;
  demandeCalculPaie?: IDemandeCalculPaie | null;
}

export class MouvementPaie implements IMouvementPaie {
  constructor(
    public id?: number,
    public code?: string | null,
    public lib?: string | null,
    public annee?: number | null,
    public mois?: number | null,
    public dateCalcul?: dayjs.Dayjs | null,
    public dateValid?: dayjs.Dayjs | null,
    public datePayroll?: dayjs.Dayjs | null,
    public totalNet?: number | null,
    public totalNetDevise?: number | null,
    public tauxChange?: number | null,
    public calculBy?: string | null,
    public effectBy?: string | null,
    public dateSituation?: dayjs.Dayjs | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public createdBy?: string | null,
    public op?: string | null,
    public util?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public situation?: ISituation | null,
    public employe?: IEmploye | null,
    public natureMvtPaie?: INatureMvtPaie | null,
    public demandeCalculPaie?: IDemandeCalculPaie | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getMouvementPaieIdentifier(mouvementPaie: IMouvementPaie): number | undefined {
  return mouvementPaie.id;
}
