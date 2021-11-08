import * as dayjs from 'dayjs';
import { IManagementResource } from 'app/entities/management-resource/management-resource.model';

export interface IManagementResourceFun {
  id?: number;
  libEn?: string | null;
  profile?: string | null;
  enableAdd?: boolean | null;
  enableCnst?: boolean | null;
  enableDel?: boolean | null;
  enableEd?: boolean | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  createdBy?: string | null;
  op?: string | null;
  util?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  ressourceManage?: IManagementResource | null;
}

export class ManagementResourceFun implements IManagementResourceFun {
  constructor(
    public id?: number,
    public libEn?: string | null,
    public profile?: string | null,
    public enableAdd?: boolean | null,
    public enableCnst?: boolean | null,
    public enableDel?: boolean | null,
    public enableEd?: boolean | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public createdBy?: string | null,
    public op?: string | null,
    public util?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public ressourceManage?: IManagementResource | null
  ) {
    this.enableAdd = this.enableAdd ?? false;
    this.enableCnst = this.enableCnst ?? false;
    this.enableDel = this.enableDel ?? false;
    this.enableEd = this.enableEd ?? false;
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getManagementResourceFunIdentifier(managementResourceFun: IManagementResourceFun): number | undefined {
  return managementResourceFun.id;
}
