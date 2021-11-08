import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISexe, getSexeIdentifier } from '../sexe.model';

export type EntityResponseType = HttpResponse<ISexe>;
export type EntityArrayResponseType = HttpResponse<ISexe[]>;

@Injectable({ providedIn: 'root' })
export class SexeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sexes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(sexe: ISexe): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sexe);
    return this.http
      .post<ISexe>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sexe: ISexe): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sexe);
    return this.http
      .put<ISexe>(`${this.resourceUrl}/${getSexeIdentifier(sexe) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(sexe: ISexe): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sexe);
    return this.http
      .patch<ISexe>(`${this.resourceUrl}/${getSexeIdentifier(sexe) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISexe>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISexe[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSexeToCollectionIfMissing(sexeCollection: ISexe[], ...sexesToCheck: (ISexe | null | undefined)[]): ISexe[] {
    const sexes: ISexe[] = sexesToCheck.filter(isPresent);
    if (sexes.length > 0) {
      const sexeCollectionIdentifiers = sexeCollection.map(sexeItem => getSexeIdentifier(sexeItem)!);
      const sexesToAdd = sexes.filter(sexeItem => {
        const sexeIdentifier = getSexeIdentifier(sexeItem);
        if (sexeIdentifier == null || sexeCollectionIdentifiers.includes(sexeIdentifier)) {
          return false;
        }
        sexeCollectionIdentifiers.push(sexeIdentifier);
        return true;
      });
      return [...sexesToAdd, ...sexeCollection];
    }
    return sexeCollection;
  }

  protected convertDateFromClient(sexe: ISexe): ISexe {
    return Object.assign({}, sexe, {
      dateop: sexe.dateop?.isValid() ? sexe.dateop.toJSON() : undefined,
      createdDate: sexe.createdDate?.isValid() ? sexe.createdDate.toJSON() : undefined,
      modifiedDate: sexe.modifiedDate?.isValid() ? sexe.modifiedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateop = res.body.dateop ? dayjs(res.body.dateop) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.modifiedDate = res.body.modifiedDate ? dayjs(res.body.modifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((sexe: ISexe) => {
        sexe.dateop = sexe.dateop ? dayjs(sexe.dateop) : undefined;
        sexe.createdDate = sexe.createdDate ? dayjs(sexe.createdDate) : undefined;
        sexe.modifiedDate = sexe.modifiedDate ? dayjs(sexe.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
