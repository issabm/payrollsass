import * as dayjs from 'dayjs';
import { IEmploye } from 'app/entities/employe/employe.model';
import { ISituationFamiliale } from 'app/entities/situation-familiale/situation-familiale.model';
import { IConjoint } from 'app/entities/conjoint/conjoint.model';

export interface IFamille {
  id?: number;
  dateSituation?: dayjs.Dayjs | null;
  dateop?: dayjs.Dayjs | null;
  util?: string | null;
  modifiedBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  employe?: IEmploye | null;
  situationFamiliale?: ISituationFamiliale | null;
  conjoint?: IConjoint | null;
}

export class Famille implements IFamille {
  constructor(
    public id?: number,
    public dateSituation?: dayjs.Dayjs | null,
    public dateop?: dayjs.Dayjs | null,
    public util?: string | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public employe?: IEmploye | null,
    public situationFamiliale?: ISituationFamiliale | null,
    public conjoint?: IConjoint | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getFamilleIdentifier(famille: IFamille): number | undefined {
  return famille.id;
}
