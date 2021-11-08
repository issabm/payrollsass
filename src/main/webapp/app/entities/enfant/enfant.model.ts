import * as dayjs from 'dayjs';
import { ITypeHandicap } from 'app/entities/type-handicap/type-handicap.model';
import { ISexe } from 'app/entities/sexe/sexe.model';
import { INiveauScolaire } from 'app/entities/niveau-scolaire/niveau-scolaire.model';
import { IFamille } from 'app/entities/famille/famille.model';

export interface IEnfant {
  id?: number;
  nomAr?: string | null;
  prenomAr?: string | null;
  nomEn?: string | null;
  prenomEn?: string | null;
  dateNaiss?: dayjs.Dayjs | null;
  util?: string | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  typeHandicap?: ITypeHandicap | null;
  sexe?: ISexe | null;
  nivScolaire?: INiveauScolaire | null;
  famille?: IFamille | null;
}

export class Enfant implements IEnfant {
  constructor(
    public id?: number,
    public nomAr?: string | null,
    public prenomAr?: string | null,
    public nomEn?: string | null,
    public prenomEn?: string | null,
    public dateNaiss?: dayjs.Dayjs | null,
    public util?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public typeHandicap?: ITypeHandicap | null,
    public sexe?: ISexe | null,
    public nivScolaire?: INiveauScolaire | null,
    public famille?: IFamille | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getEnfantIdentifier(enfant: IEnfant): number | undefined {
  return enfant.id;
}
