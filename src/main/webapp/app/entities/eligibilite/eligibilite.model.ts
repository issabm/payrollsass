import * as dayjs from 'dayjs';
import { IPays } from 'app/entities/pays/pays.model';
import { INatureConfig } from 'app/entities/nature-config/nature-config.model';
import { IEchlon } from 'app/entities/echlon/echlon.model';
import { ICategory } from 'app/entities/category/category.model';
import { IEmploi } from 'app/entities/emploi/emploi.model';
import { ITypeHandicap } from 'app/entities/type-handicap/type-handicap.model';
import { ISexe } from 'app/entities/sexe/sexe.model';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { ITypeContrat } from 'app/entities/type-contrat/type-contrat.model';
import { ISousTypeContrat } from 'app/entities/sous-type-contrat/sous-type-contrat.model';
import { INiveauEtude } from 'app/entities/niveau-etude/niveau-etude.model';
import { INatureEligibilite } from 'app/entities/nature-eligibilite/nature-eligibilite.model';
import { IRebrique } from 'app/entities/rebrique/rebrique.model';
import { INatureAbsence } from 'app/entities/nature-absence/nature-absence.model';
import { IPlate } from 'app/entities/plate/plate.model';
import { ITargetEligible } from 'app/entities/target-eligible/target-eligible.model';

export interface IEligibilite {
  id?: number;
  priorite?: number | null;
  annee?: number | null;
  mois?: number | null;
  nbEnt?: number | null;
  ageEnt?: number | null;
  matricule?: string | null;
  code?: string | null;
  libEn?: string | null;
  libAr?: string | null;
  libFr?: string | null;
  valPayroll?: number | null;
  nbDaysLeave?: number | null;
  pourValPayroll?: number | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  createdBy?: string | null;
  op?: string | null;
  util?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  pays?: IPays | null;
  natureConfig?: INatureConfig | null;
  echlon?: IEchlon | null;
  category?: ICategory | null;
  emploi?: IEmploi | null;
  typeHandicap?: ITypeHandicap | null;
  nationalite?: IPays | null;
  sexe?: ISexe | null;
  affilication?: IAffiliation | null;
  entreprise?: IEntreprise | null;
  groupe?: IGroupe | null;
  typeContrat?: ITypeContrat | null;
  sousTypeContrat?: ISousTypeContrat | null;
  niveauEtude?: INiveauEtude | null;
  natureEligible?: INatureEligibilite | null;
  rebrique?: IRebrique | null;
  natureAbsence?: INatureAbsence | null;
  plate?: IPlate | null;
  targetEnt?: ITargetEligible | null;
}

export class Eligibilite implements IEligibilite {
  constructor(
    public id?: number,
    public priorite?: number | null,
    public annee?: number | null,
    public mois?: number | null,
    public nbEnt?: number | null,
    public ageEnt?: number | null,
    public matricule?: string | null,
    public code?: string | null,
    public libEn?: string | null,
    public libAr?: string | null,
    public libFr?: string | null,
    public valPayroll?: number | null,
    public nbDaysLeave?: number | null,
    public pourValPayroll?: number | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public createdBy?: string | null,
    public op?: string | null,
    public util?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public pays?: IPays | null,
    public natureConfig?: INatureConfig | null,
    public echlon?: IEchlon | null,
    public category?: ICategory | null,
    public emploi?: IEmploi | null,
    public typeHandicap?: ITypeHandicap | null,
    public nationalite?: IPays | null,
    public sexe?: ISexe | null,
    public affilication?: IAffiliation | null,
    public entreprise?: IEntreprise | null,
    public groupe?: IGroupe | null,
    public typeContrat?: ITypeContrat | null,
    public sousTypeContrat?: ISousTypeContrat | null,
    public niveauEtude?: INiveauEtude | null,
    public natureEligible?: INatureEligibilite | null,
    public rebrique?: IRebrique | null,
    public natureAbsence?: INatureAbsence | null,
    public plate?: IPlate | null,
    public targetEnt?: ITargetEligible | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getEligibiliteIdentifier(eligibilite: IEligibilite): number | undefined {
  return eligibilite.id;
}
