import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmploi, getEmploiIdentifier } from '../emploi.model';

export type EntityResponseType = HttpResponse<IEmploi>;
export type EntityArrayResponseType = HttpResponse<IEmploi[]>;

@Injectable({ providedIn: 'root' })
export class EmploiService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/emplois');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(emploi: IEmploi): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emploi);
    return this.http
      .post<IEmploi>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(emploi: IEmploi): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emploi);
    return this.http
      .put<IEmploi>(`${this.resourceUrl}/${getEmploiIdentifier(emploi) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(emploi: IEmploi): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emploi);
    return this.http
      .patch<IEmploi>(`${this.resourceUrl}/${getEmploiIdentifier(emploi) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmploi>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmploi[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEmploiToCollectionIfMissing(emploiCollection: IEmploi[], ...emploisToCheck: (IEmploi | null | undefined)[]): IEmploi[] {
    const emplois: IEmploi[] = emploisToCheck.filter(isPresent);
    if (emplois.length > 0) {
      const emploiCollectionIdentifiers = emploiCollection.map(emploiItem => getEmploiIdentifier(emploiItem)!);
      const emploisToAdd = emplois.filter(emploiItem => {
        const emploiIdentifier = getEmploiIdentifier(emploiItem);
        if (emploiIdentifier == null || emploiCollectionIdentifiers.includes(emploiIdentifier)) {
          return false;
        }
        emploiCollectionIdentifiers.push(emploiIdentifier);
        return true;
      });
      return [...emploisToAdd, ...emploiCollection];
    }
    return emploiCollection;
  }

  protected convertDateFromClient(emploi: IEmploi): IEmploi {
    return Object.assign({}, emploi, {
      dateop: emploi.dateop?.isValid() ? emploi.dateop.toJSON() : undefined,
      createdDate: emploi.createdDate?.isValid() ? emploi.createdDate.toJSON() : undefined,
      modifiedDate: emploi.modifiedDate?.isValid() ? emploi.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((emploi: IEmploi) => {
        emploi.dateop = emploi.dateop ? dayjs(emploi.dateop) : undefined;
        emploi.createdDate = emploi.createdDate ? dayjs(emploi.createdDate) : undefined;
        emploi.modifiedDate = emploi.modifiedDate ? dayjs(emploi.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
