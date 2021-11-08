import * as dayjs from 'dayjs';
import { IPlate } from 'app/entities/plate/plate.model';
import { IRebrique } from 'app/entities/rebrique/rebrique.model';

export interface IPlateInfo {
  id?: number;
  code?: string | null;
  lib?: string | null;
  valTaken?: number | null;
  dateSituation?: dayjs.Dayjs | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  createdBy?: string | null;
  op?: string | null;
  util?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  plate?: IPlate | null;
  rebrique?: IRebrique | null;
}

export class PlateInfo implements IPlateInfo {
  constructor(
    public id?: number,
    public code?: string | null,
    public lib?: string | null,
    public valTaken?: number | null,
    public dateSituation?: dayjs.Dayjs | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public createdBy?: string | null,
    public op?: string | null,
    public util?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public plate?: IPlate | null,
    public rebrique?: IRebrique | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getPlateInfoIdentifier(plateInfo: IPlateInfo): number | undefined {
  return plateInfo.id;
}
