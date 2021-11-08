import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INiveauScolaire, getNiveauScolaireIdentifier } from '../niveau-scolaire.model';

export type EntityResponseType = HttpResponse<INiveauScolaire>;
export type EntityArrayResponseType = HttpResponse<INiveauScolaire[]>;

@Injectable({ providedIn: 'root' })
export class NiveauScolaireService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/niveau-scolaires');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(niveauScolaire: INiveauScolaire): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(niveauScolaire);
    return this.http
      .post<INiveauScolaire>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(niveauScolaire: INiveauScolaire): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(niveauScolaire);
    return this.http
      .put<INiveauScolaire>(`${this.resourceUrl}/${getNiveauScolaireIdentifier(niveauScolaire) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(niveauScolaire: INiveauScolaire): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(niveauScolaire);
    return this.http
      .patch<INiveauScolaire>(`${this.resourceUrl}/${getNiveauScolaireIdentifier(niveauScolaire) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INiveauScolaire>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INiveauScolaire[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNiveauScolaireToCollectionIfMissing(
    niveauScolaireCollection: INiveauScolaire[],
    ...niveauScolairesToCheck: (INiveauScolaire | null | undefined)[]
  ): INiveauScolaire[] {
    const niveauScolaires: INiveauScolaire[] = niveauScolairesToCheck.filter(isPresent);
    if (niveauScolaires.length > 0) {
      const niveauScolaireCollectionIdentifiers = niveauScolaireCollection.map(
        niveauScolaireItem => getNiveauScolaireIdentifier(niveauScolaireItem)!
      );
      const niveauScolairesToAdd = niveauScolaires.filter(niveauScolaireItem => {
        const niveauScolaireIdentifier = getNiveauScolaireIdentifier(niveauScolaireItem);
        if (niveauScolaireIdentifier == null || niveauScolaireCollectionIdentifiers.includes(niveauScolaireIdentifier)) {
          return false;
        }
        niveauScolaireCollectionIdentifiers.push(niveauScolaireIdentifier);
        return true;
      });
      return [...niveauScolairesToAdd, ...niveauScolaireCollection];
    }
    return niveauScolaireCollection;
  }

  protected convertDateFromClient(niveauScolaire: INiveauScolaire): INiveauScolaire {
    return Object.assign({}, niveauScolaire, {
      dateop: niveauScolaire.dateop?.isValid() ? niveauScolaire.dateop.toJSON() : undefined,
      createdDate: niveauScolaire.createdDate?.isValid() ? niveauScolaire.createdDate.toJSON() : undefined,
      modifiedDate: niveauScolaire.modifiedDate?.isValid() ? niveauScolaire.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((niveauScolaire: INiveauScolaire) => {
        niveauScolaire.dateop = niveauScolaire.dateop ? dayjs(niveauScolaire.dateop) : undefined;
        niveauScolaire.createdDate = niveauScolaire.createdDate ? dayjs(niveauScolaire.createdDate) : undefined;
        niveauScolaire.modifiedDate = niveauScolaire.modifiedDate ? dayjs(niveauScolaire.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
