import * as dayjs from 'dayjs';
import { ISituation } from 'app/entities/situation/situation.model';
import { IPays } from 'app/entities/pays/pays.model';
import { ITypeHandicap } from 'app/entities/type-handicap/type-handicap.model';

export interface IEmploye {
  id?: number;
  matricule?: string | null;
  nomAr?: string | null;
  nomJfAr?: string | null;
  prenomAr?: string | null;
  nomEn?: string | null;
  nomJfEn?: string | null;
  prenomEn?: string | null;
  nomPereAr?: string | null;
  nomPereEn?: string | null;
  nomMereAr?: string | null;
  nomMereEn?: string | null;
  nomGpAr?: string | null;
  nomGpEn?: string | null;
  dateNaiss?: dayjs.Dayjs | null;
  rib?: string | null;
  banque?: string | null;
  dateRib?: string | null;
  dateBanque?: string | null;
  imgContentType?: string | null;
  img?: string | null;
  util?: string | null;
  dateop?: dayjs.Dayjs | null;
  dateModif?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  situation?: ISituation | null;
  nationalite?: IPays | null;
  typeHandicap?: ITypeHandicap | null;
}

export class Employe implements IEmploye {
  constructor(
    public id?: number,
    public matricule?: string | null,
    public nomAr?: string | null,
    public nomJfAr?: string | null,
    public prenomAr?: string | null,
    public nomEn?: string | null,
    public nomJfEn?: string | null,
    public prenomEn?: string | null,
    public nomPereAr?: string | null,
    public nomPereEn?: string | null,
    public nomMereAr?: string | null,
    public nomMereEn?: string | null,
    public nomGpAr?: string | null,
    public nomGpEn?: string | null,
    public dateNaiss?: dayjs.Dayjs | null,
    public rib?: string | null,
    public banque?: string | null,
    public dateRib?: string | null,
    public dateBanque?: string | null,
    public imgContentType?: string | null,
    public img?: string | null,
    public util?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public dateModif?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public situation?: ISituation | null,
    public nationalite?: IPays | null,
    public typeHandicap?: ITypeHandicap | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getEmployeIdentifier(employe: IEmploye): number | undefined {
  return employe.id;
}
