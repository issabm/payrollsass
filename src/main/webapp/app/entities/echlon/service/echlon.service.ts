import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEchlon, getEchlonIdentifier } from '../echlon.model';

export type EntityResponseType = HttpResponse<IEchlon>;
export type EntityArrayResponseType = HttpResponse<IEchlon[]>;

@Injectable({ providedIn: 'root' })
export class EchlonService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/echlons');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(echlon: IEchlon): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(echlon);
    return this.http
      .post<IEchlon>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(echlon: IEchlon): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(echlon);
    return this.http
      .put<IEchlon>(`${this.resourceUrl}/${getEchlonIdentifier(echlon) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(echlon: IEchlon): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(echlon);
    return this.http
      .patch<IEchlon>(`${this.resourceUrl}/${getEchlonIdentifier(echlon) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEchlon>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEchlon[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEchlonToCollectionIfMissing(echlonCollection: IEchlon[], ...echlonsToCheck: (IEchlon | null | undefined)[]): IEchlon[] {
    const echlons: IEchlon[] = echlonsToCheck.filter(isPresent);
    if (echlons.length > 0) {
      const echlonCollectionIdentifiers = echlonCollection.map(echlonItem => getEchlonIdentifier(echlonItem)!);
      const echlonsToAdd = echlons.filter(echlonItem => {
        const echlonIdentifier = getEchlonIdentifier(echlonItem);
        if (echlonIdentifier == null || echlonCollectionIdentifiers.includes(echlonIdentifier)) {
          return false;
        }
        echlonCollectionIdentifiers.push(echlonIdentifier);
        return true;
      });
      return [...echlonsToAdd, ...echlonCollection];
    }
    return echlonCollection;
  }

  protected convertDateFromClient(echlon: IEchlon): IEchlon {
    return Object.assign({}, echlon, {
      dateop: echlon.dateop?.isValid() ? echlon.dateop.toJSON() : undefined,
      createdDate: echlon.createdDate?.isValid() ? echlon.createdDate.toJSON() : undefined,
      modifiedDate: echlon.modifiedDate?.isValid() ? echlon.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((echlon: IEchlon) => {
        echlon.dateop = echlon.dateop ? dayjs(echlon.dateop) : undefined;
        echlon.createdDate = echlon.createdDate ? dayjs(echlon.createdDate) : undefined;
        echlon.modifiedDate = echlon.modifiedDate ? dayjs(echlon.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
