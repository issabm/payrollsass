import * as dayjs from 'dayjs';
import { IEmploye } from 'app/entities/employe/employe.model';
import { IEntityAdhesion } from 'app/entities/entity-adhesion/entity-adhesion.model';

export interface IAdhesion {
  id?: number;
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
  entityAdhesion?: IEntityAdhesion | null;
}

export class Adhesion implements IAdhesion {
  constructor(
    public id?: number,
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
    public entityAdhesion?: IEntityAdhesion | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getAdhesionIdentifier(adhesion: IAdhesion): number | undefined {
  return adhesion.id;
}
