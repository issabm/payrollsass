import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIdentite, getIdentiteIdentifier } from '../identite.model';

export type EntityResponseType = HttpResponse<IIdentite>;
export type EntityArrayResponseType = HttpResponse<IIdentite[]>;

@Injectable({ providedIn: 'root' })
export class IdentiteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/identites');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(identite: IIdentite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(identite);
    return this.http
      .post<IIdentite>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(identite: IIdentite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(identite);
    return this.http
      .put<IIdentite>(`${this.resourceUrl}/${getIdentiteIdentifier(identite) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(identite: IIdentite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(identite);
    return this.http
      .patch<IIdentite>(`${this.resourceUrl}/${getIdentiteIdentifier(identite) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIdentite>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIdentite[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addIdentiteToCollectionIfMissing(identiteCollection: IIdentite[], ...identitesToCheck: (IIdentite | null | undefined)[]): IIdentite[] {
    const identites: IIdentite[] = identitesToCheck.filter(isPresent);
    if (identites.length > 0) {
      const identiteCollectionIdentifiers = identiteCollection.map(identiteItem => getIdentiteIdentifier(identiteItem)!);
      const identitesToAdd = identites.filter(identiteItem => {
        const identiteIdentifier = getIdentiteIdentifier(identiteItem);
        if (identiteIdentifier == null || identiteCollectionIdentifiers.includes(identiteIdentifier)) {
          return false;
        }
        identiteCollectionIdentifiers.push(identiteIdentifier);
        return true;
      });
      return [...identitesToAdd, ...identiteCollection];
    }
    return identiteCollection;
  }

  protected convertDateFromClient(identite: IIdentite): IIdentite {
    return Object.assign({}, identite, {
      dateIssued: identite.dateIssued?.isValid() ? identite.dateIssued.format(DATE_FORMAT) : undefined,
      dateVld: identite.dateVld?.isValid() ? identite.dateVld.format(DATE_FORMAT) : undefined,
      dateop: identite.dateop?.isValid() ? identite.dateop.toJSON() : undefined,
      createdDate: identite.createdDate?.isValid() ? identite.createdDate.toJSON() : undefined,
      modifiedDate: identite.modifiedDate?.isValid() ? identite.modifiedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateIssued = res.body.dateIssued ? dayjs(res.body.dateIssued) : undefined;
      res.body.dateVld = res.body.dateVld ? dayjs(res.body.dateVld) : undefined;
      res.body.dateop = res.body.dateop ? dayjs(res.body.dateop) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.modifiedDate = res.body.modifiedDate ? dayjs(res.body.modifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((identite: IIdentite) => {
        identite.dateIssued = identite.dateIssued ? dayjs(identite.dateIssued) : undefined;
        identite.dateVld = identite.dateVld ? dayjs(identite.dateVld) : undefined;
        identite.dateop = identite.dateop ? dayjs(identite.dateop) : undefined;
        identite.createdDate = identite.createdDate ? dayjs(identite.createdDate) : undefined;
        identite.modifiedDate = identite.modifiedDate ? dayjs(identite.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
