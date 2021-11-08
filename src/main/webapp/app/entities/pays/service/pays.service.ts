import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPays, getPaysIdentifier } from '../pays.model';

export type EntityResponseType = HttpResponse<IPays>;
export type EntityArrayResponseType = HttpResponse<IPays[]>;

@Injectable({ providedIn: 'root' })
export class PaysService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pays');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pays: IPays): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pays);
    return this.http
      .post<IPays>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pays: IPays): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pays);
    return this.http
      .put<IPays>(`${this.resourceUrl}/${getPaysIdentifier(pays) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(pays: IPays): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pays);
    return this.http
      .patch<IPays>(`${this.resourceUrl}/${getPaysIdentifier(pays) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPays>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPays[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPaysToCollectionIfMissing(paysCollection: IPays[], ...paysToCheck: (IPays | null | undefined)[]): IPays[] {
    const pays: IPays[] = paysToCheck.filter(isPresent);
    if (pays.length > 0) {
      const paysCollectionIdentifiers = paysCollection.map(paysItem => getPaysIdentifier(paysItem)!);
      const paysToAdd = pays.filter(paysItem => {
        const paysIdentifier = getPaysIdentifier(paysItem);
        if (paysIdentifier == null || paysCollectionIdentifiers.includes(paysIdentifier)) {
          return false;
        }
        paysCollectionIdentifiers.push(paysIdentifier);
        return true;
      });
      return [...paysToAdd, ...paysCollection];
    }
    return paysCollection;
  }

  protected convertDateFromClient(pays: IPays): IPays {
    return Object.assign({}, pays, {
      dateop: pays.dateop?.isValid() ? pays.dateop.toJSON() : undefined,
      createdDate: pays.createdDate?.isValid() ? pays.createdDate.toJSON() : undefined,
      modifiedDate: pays.modifiedDate?.isValid() ? pays.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((pays: IPays) => {
        pays.dateop = pays.dateop ? dayjs(pays.dateop) : undefined;
        pays.createdDate = pays.createdDate ? dayjs(pays.createdDate) : undefined;
        pays.modifiedDate = pays.modifiedDate ? dayjs(pays.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
