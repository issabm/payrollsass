import * as dayjs from 'dayjs';
import { IEchlon } from 'app/entities/echlon/echlon.model';
import { ICategory } from 'app/entities/category/category.model';
import { IEmploi } from 'app/entities/emploi/emploi.model';
import { IAffectation } from 'app/entities/affectation/affectation.model';

export interface ICarriere {
  id?: number;
  dateDebut?: dayjs.Dayjs | null;
  dateFin?: dayjs.Dayjs | null;
  dateEmploi?: dayjs.Dayjs | null;
  dateEchlon?: dayjs.Dayjs | null;
  dateCategorie?: dayjs.Dayjs | null;
  dateop?: dayjs.Dayjs | null;
  util?: string | null;
  modifiedBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  echlon?: IEchlon | null;
  category?: ICategory | null;
  emploi?: IEmploi | null;
  affectation?: IAffectation | null;
}

export class Carriere implements ICarriere {
  constructor(
    public id?: number,
    public dateDebut?: dayjs.Dayjs | null,
    public dateFin?: dayjs.Dayjs | null,
    public dateEmploi?: dayjs.Dayjs | null,
    public dateEchlon?: dayjs.Dayjs | null,
    public dateCategorie?: dayjs.Dayjs | null,
    public dateop?: dayjs.Dayjs | null,
    public util?: string | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public echlon?: IEchlon | null,
    public category?: ICategory | null,
    public emploi?: IEmploi | null,
    public affectation?: IAffectation | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getCarriereIdentifier(carriere: ICarriere): number | undefined {
  return carriere.id;
}
