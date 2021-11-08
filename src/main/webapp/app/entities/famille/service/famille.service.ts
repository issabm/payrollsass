import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFamille, getFamilleIdentifier } from '../famille.model';

export type EntityResponseType = HttpResponse<IFamille>;
export type EntityArrayResponseType = HttpResponse<IFamille[]>;

@Injectable({ providedIn: 'root' })
export class FamilleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/familles');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(famille: IFamille): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(famille);
    return this.http
      .post<IFamille>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(famille: IFamille): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(famille);
    return this.http
      .put<IFamille>(`${this.resourceUrl}/${getFamilleIdentifier(famille) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(famille: IFamille): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(famille);
    return this.http
      .patch<IFamille>(`${this.resourceUrl}/${getFamilleIdentifier(famille) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFamille>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFamille[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFamilleToCollectionIfMissing(familleCollection: IFamille[], ...famillesToCheck: (IFamille | null | undefined)[]): IFamille[] {
    const familles: IFamille[] = famillesToCheck.filter(isPresent);
    if (familles.length > 0) {
      const familleCollectionIdentifiers = familleCollection.map(familleItem => getFamilleIdentifier(familleItem)!);
      const famillesToAdd = familles.filter(familleItem => {
        const familleIdentifier = getFamilleIdentifier(familleItem);
        if (familleIdentifier == null || familleCollectionIdentifiers.includes(familleIdentifier)) {
          return false;
        }
        familleCollectionIdentifiers.push(familleIdentifier);
        return true;
      });
      return [...famillesToAdd, ...familleCollection];
    }
    return familleCollection;
  }

  protected convertDateFromClient(famille: IFamille): IFamille {
    return Object.assign({}, famille, {
      dateSituation: famille.dateSituation?.isValid() ? famille.dateSituation.format(DATE_FORMAT) : undefined,
      dateop: famille.dateop?.isValid() ? famille.dateop.toJSON() : undefined,
      createdDate: famille.createdDate?.isValid() ? famille.createdDate.toJSON() : undefined,
      modifiedDate: famille.modifiedDate?.isValid() ? famille.modifiedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateSituation = res.body.dateSituation ? dayjs(res.body.dateSituation) : undefined;
      res.body.dateop = res.body.dateop ? dayjs(res.body.dateop) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.modifiedDate = res.body.modifiedDate ? dayjs(res.body.modifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((famille: IFamille) => {
        famille.dateSituation = famille.dateSituation ? dayjs(famille.dateSituation) : undefined;
        famille.dateop = famille.dateop ? dayjs(famille.dateop) : undefined;
        famille.createdDate = famille.createdDate ? dayjs(famille.createdDate) : undefined;
        famille.modifiedDate = famille.modifiedDate ? dayjs(famille.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
