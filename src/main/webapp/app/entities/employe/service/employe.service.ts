import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmploye, getEmployeIdentifier } from '../employe.model';

export type EntityResponseType = HttpResponse<IEmploye>;
export type EntityArrayResponseType = HttpResponse<IEmploye[]>;

@Injectable({ providedIn: 'root' })
export class EmployeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/employes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(employe: IEmploye): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employe);
    return this.http
      .post<IEmploye>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(employe: IEmploye): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employe);
    return this.http
      .put<IEmploye>(`${this.resourceUrl}/${getEmployeIdentifier(employe) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(employe: IEmploye): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employe);
    return this.http
      .patch<IEmploye>(`${this.resourceUrl}/${getEmployeIdentifier(employe) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmploye>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmploye[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEmployeToCollectionIfMissing(employeCollection: IEmploye[], ...employesToCheck: (IEmploye | null | undefined)[]): IEmploye[] {
    const employes: IEmploye[] = employesToCheck.filter(isPresent);
    if (employes.length > 0) {
      const employeCollectionIdentifiers = employeCollection.map(employeItem => getEmployeIdentifier(employeItem)!);
      const employesToAdd = employes.filter(employeItem => {
        const employeIdentifier = getEmployeIdentifier(employeItem);
        if (employeIdentifier == null || employeCollectionIdentifiers.includes(employeIdentifier)) {
          return false;
        }
        employeCollectionIdentifiers.push(employeIdentifier);
        return true;
      });
      return [...employesToAdd, ...employeCollection];
    }
    return employeCollection;
  }

  protected convertDateFromClient(employe: IEmploye): IEmploye {
    return Object.assign({}, employe, {
      dateNaiss: employe.dateNaiss?.isValid() ? employe.dateNaiss.format(DATE_FORMAT) : undefined,
      dateop: employe.dateop?.isValid() ? employe.dateop.toJSON() : undefined,
      dateModif: employe.dateModif?.isValid() ? employe.dateModif.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateNaiss = res.body.dateNaiss ? dayjs(res.body.dateNaiss) : undefined;
      res.body.dateop = res.body.dateop ? dayjs(res.body.dateop) : undefined;
      res.body.dateModif = res.body.dateModif ? dayjs(res.body.dateModif) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((employe: IEmploye) => {
        employe.dateNaiss = employe.dateNaiss ? dayjs(employe.dateNaiss) : undefined;
        employe.dateop = employe.dateop ? dayjs(employe.dateop) : undefined;
        employe.dateModif = employe.dateModif ? dayjs(employe.dateModif) : undefined;
      });
    }
    return res;
  }
}
