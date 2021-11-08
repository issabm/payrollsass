import * as dayjs from 'dayjs';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { IPays } from 'app/entities/pays/pays.model';
import { IDevise } from 'app/entities/devise/devise.model';

export interface IEntreprise {
  id?: number;
  code?: string | null;
  matFiscal?: string | null;
  registreCommerce?: string | null;
  nomCommerceAr?: string | null;
  nomCommerceEn?: string | null;
  raisonSocialAr?: string | null;
  raisonSocialEn?: string | null;
  addresseAr?: string | null;
  addresseEn?: string | null;
  codePostal?: string | null;
  tel?: string | null;
  email?: string | null;
  fax?: string | null;
  util?: string | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  groupe?: IGroupe | null;
  pays?: IPays | null;
  mere?: IEntreprise | null;
  devise?: IDevise | null;
}

export class Entreprise implements IEntreprise {
  constructor(
    public id?: number,
    public code?: string | null,
    public matFiscal?: string | null,
    public registreCommerce?: string | null,
    public nomCommerceAr?: string | null,
    public nomCommerceEn?: string | null,
    public raisonSocialAr?: string | null,
    public raisonSocialEn?: string | null,
    public addresseAr?: string | null,
    public addresseEn?: string | null,
    public codePostal?: string | null,
    public tel?: string | null,
    public email?: string | null,
    public fax?: string | null,
    public util?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public groupe?: IGroupe | null,
    public pays?: IPays | null,
    public mere?: IEntreprise | null,
    public devise?: IDevise | null
  ) {}
}

export function getEntrepriseIdentifier(entreprise: IEntreprise): number | undefined {
  return entreprise.id;
}
