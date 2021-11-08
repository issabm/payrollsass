import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPayroll, getPayrollIdentifier } from '../payroll.model';

export type EntityResponseType = HttpResponse<IPayroll>;
export type EntityArrayResponseType = HttpResponse<IPayroll[]>;

@Injectable({ providedIn: 'root' })
export class PayrollService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/payrolls');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(payroll: IPayroll): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(payroll);
    return this.http
      .post<IPayroll>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(payroll: IPayroll): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(payroll);
    return this.http
      .put<IPayroll>(`${this.resourceUrl}/${getPayrollIdentifier(payroll) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(payroll: IPayroll): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(payroll);
    return this.http
      .patch<IPayroll>(`${this.resourceUrl}/${getPayrollIdentifier(payroll) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPayroll>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPayroll[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPayrollToCollectionIfMissing(payrollCollection: IPayroll[], ...payrollsToCheck: (IPayroll | null | undefined)[]): IPayroll[] {
    const payrolls: IPayroll[] = payrollsToCheck.filter(isPresent);
    if (payrolls.length > 0) {
      const payrollCollectionIdentifiers = payrollCollection.map(payrollItem => getPayrollIdentifier(payrollItem)!);
      const payrollsToAdd = payrolls.filter(payrollItem => {
        const payrollIdentifier = getPayrollIdentifier(payrollItem);
        if (payrollIdentifier == null || payrollCollectionIdentifiers.includes(payrollIdentifier)) {
          return false;
        }
        payrollCollectionIdentifiers.push(payrollIdentifier);
        return true;
      });
      return [...payrollsToAdd, ...payrollCollection];
    }
    return payrollCollection;
  }

  protected convertDateFromClient(payroll: IPayroll): IPayroll {
    return Object.assign({}, payroll, {
      dateCalcul: payroll.dateCalcul?.isValid() ? payroll.dateCalcul.format(DATE_FORMAT) : undefined,
      dateValid: payroll.dateValid?.isValid() ? payroll.dateValid.format(DATE_FORMAT) : undefined,
      datePayroll: payroll.datePayroll?.isValid() ? payroll.datePayroll.format(DATE_FORMAT) : undefined,
      dateSituation: payroll.dateSituation?.isValid() ? payroll.dateSituation.format(DATE_FORMAT) : undefined,
      dateop: payroll.dateop?.isValid() ? payroll.dateop.toJSON() : undefined,
      createdDate: payroll.createdDate?.isValid() ? payroll.createdDate.toJSON() : undefined,
      modifiedDate: payroll.modifiedDate?.isValid() ? payroll.modifiedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCalcul = res.body.dateCalcul ? dayjs(res.body.dateCalcul) : undefined;
      res.body.dateValid = res.body.dateValid ? dayjs(res.body.dateValid) : undefined;
      res.body.datePayroll = res.body.datePayroll ? dayjs(res.body.datePayroll) : undefined;
      res.body.dateSituation = res.body.dateSituation ? dayjs(res.body.dateSituation) : undefined;
      res.body.dateop = res.body.dateop ? dayjs(res.body.dateop) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.modifiedDate = res.body.modifiedDate ? dayjs(res.body.modifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((payroll: IPayroll) => {
        payroll.dateCalcul = payroll.dateCalcul ? dayjs(payroll.dateCalcul) : undefined;
        payroll.dateValid = payroll.dateValid ? dayjs(payroll.dateValid) : undefined;
        payroll.datePayroll = payroll.datePayroll ? dayjs(payroll.datePayroll) : undefined;
        payroll.dateSituation = payroll.dateSituation ? dayjs(payroll.dateSituation) : undefined;
        payroll.dateop = payroll.dateop ? dayjs(payroll.dateop) : undefined;
        payroll.createdDate = payroll.createdDate ? dayjs(payroll.createdDate) : undefined;
        payroll.modifiedDate = payroll.modifiedDate ? dayjs(payroll.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
