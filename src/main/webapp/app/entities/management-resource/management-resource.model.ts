import * as dayjs from 'dayjs';
import { ISituation } from 'app/entities/situation/situation.model';
import { IEmploye } from 'app/entities/employe/employe.model';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';

export interface IManagementResource {
  id?: number;
  util?: string | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  createdBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  situation?: ISituation | null;
  employe?: IEmploye | null;
  entreprise?: IEntreprise | null;
  groupe?: IGroupe | null;
  affiliation?: IAffiliation | null;
}

export class ManagementResource implements IManagementResource {
  constructor(
    public id?: number,
    public util?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public createdBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public situation?: ISituation | null,
    public employe?: IEmploye | null,
    public entreprise?: IEntreprise | null,
    public groupe?: IGroupe | null,
    public affiliation?: IAffiliation | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getManagementResourceIdentifier(managementResource: IManagementResource): number | undefined {
  return managementResource.id;
}
