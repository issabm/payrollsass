import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPayrollInfo, getPayrollInfoIdentifier } from '../payroll-info.model';

export type EntityResponseType = HttpResponse<IPayrollInfo>;
export type EntityArrayResponseType = HttpResponse<IPayrollInfo[]>;

@Injectable({ providedIn: 'root' })
export class PayrollInfoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/payroll-infos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(payrollInfo: IPayrollInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(payrollInfo);
    return this.http
      .post<IPayrollInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(payrollInfo: IPayrollInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(payrollInfo);
    return this.http
      .put<IPayrollInfo>(`${this.resourceUrl}/${getPayrollInfoIdentifier(payrollInfo) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(payrollInfo: IPayrollInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(payrollInfo);
    return this.http
      .patch<IPayrollInfo>(`${this.resourceUrl}/${getPayrollInfoIdentifier(payrollInfo) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPayrollInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPayrollInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPayrollInfoToCollectionIfMissing(
    payrollInfoCollection: IPayrollInfo[],
    ...payrollInfosToCheck: (IPayrollInfo | null | undefined)[]
  ): IPayrollInfo[] {
    const payrollInfos: IPayrollInfo[] = payrollInfosToCheck.filter(isPresent);
    if (payrollInfos.length > 0) {
      const payrollInfoCollectionIdentifiers = payrollInfoCollection.map(payrollInfoItem => getPayrollInfoIdentifier(payrollInfoItem)!);
      const payrollInfosToAdd = payrollInfos.filter(payrollInfoItem => {
        const payrollInfoIdentifier = getPayrollInfoIdentifier(payrollInfoItem);
        if (payrollInfoIdentifier == null || payrollInfoCollectionIdentifiers.includes(payrollInfoIdentifier)) {
          return false;
        }
        payrollInfoCollectionIdentifiers.push(payrollInfoIdentifier);
        return true;
      });
      return [...payrollInfosToAdd, ...payrollInfoCollection];
    }
    return payrollInfoCollection;
  }

  protected convertDateFromClient(payrollInfo: IPayrollInfo): IPayrollInfo {
    return Object.assign({}, payrollInfo, {
      dateCalcul: payrollInfo.dateCalcul?.isValid() ? payrollInfo.dateCalcul.format(DATE_FORMAT) : undefined,
      dateEffect: payrollInfo.dateEffect?.isValid() ? payrollInfo.dateEffect.format(DATE_FORMAT) : undefined,
      dateSituation: payrollInfo.dateSituation?.isValid() ? payrollInfo.dateSituation.format(DATE_FORMAT) : undefined,
      dateop: payrollInfo.dateop?.isValid() ? payrollInfo.dateop.toJSON() : undefined,
      createdDate: payrollInfo.createdDate?.isValid() ? payrollInfo.createdDate.toJSON() : undefined,
      modifiedDate: payrollInfo.modifiedDate?.isValid() ? payrollInfo.modifiedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCalcul = res.body.dateCalcul ? dayjs(res.body.dateCalcul) : undefined;
      res.body.dateEffect = res.body.dateEffect ? dayjs(res.body.dateEffect) : undefined;
      res.body.dateSituation = res.body.dateSituation ? dayjs(res.body.dateSituation) : undefined;
      res.body.dateop = res.body.dateop ? dayjs(res.body.dateop) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.modifiedDate = res.body.modifiedDate ? dayjs(res.body.modifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((payrollInfo: IPayrollInfo) => {
        payrollInfo.dateCalcul = payrollInfo.dateCalcul ? dayjs(payrollInfo.dateCalcul) : undefined;
        payrollInfo.dateEffect = payrollInfo.dateEffect ? dayjs(payrollInfo.dateEffect) : undefined;
        payrollInfo.dateSituation = payrollInfo.dateSituation ? dayjs(payrollInfo.dateSituation) : undefined;
        payrollInfo.dateop = payrollInfo.dateop ? dayjs(payrollInfo.dateop) : undefined;
        payrollInfo.createdDate = payrollInfo.createdDate ? dayjs(payrollInfo.createdDate) : undefined;
        payrollInfo.modifiedDate = payrollInfo.modifiedDate ? dayjs(payrollInfo.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
