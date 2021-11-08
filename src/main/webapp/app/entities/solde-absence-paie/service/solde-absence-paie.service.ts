import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISoldeAbsencePaie, getSoldeAbsencePaieIdentifier } from '../solde-absence-paie.model';

export type EntityResponseType = HttpResponse<ISoldeAbsencePaie>;
export type EntityArrayResponseType = HttpResponse<ISoldeAbsencePaie[]>;

@Injectable({ providedIn: 'root' })
export class SoldeAbsencePaieService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/solde-absence-paies');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(soldeAbsencePaie: ISoldeAbsencePaie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soldeAbsencePaie);
    return this.http
      .post<ISoldeAbsencePaie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(soldeAbsencePaie: ISoldeAbsencePaie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soldeAbsencePaie);
    return this.http
      .put<ISoldeAbsencePaie>(`${this.resourceUrl}/${getSoldeAbsencePaieIdentifier(soldeAbsencePaie) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(soldeAbsencePaie: ISoldeAbsencePaie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soldeAbsencePaie);
    return this.http
      .patch<ISoldeAbsencePaie>(`${this.resourceUrl}/${getSoldeAbsencePaieIdentifier(soldeAbsencePaie) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISoldeAbsencePaie>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISoldeAbsencePaie[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSoldeAbsencePaieToCollectionIfMissing(
    soldeAbsencePaieCollection: ISoldeAbsencePaie[],
    ...soldeAbsencePaiesToCheck: (ISoldeAbsencePaie | null | undefined)[]
  ): ISoldeAbsencePaie[] {
    const soldeAbsencePaies: ISoldeAbsencePaie[] = soldeAbsencePaiesToCheck.filter(isPresent);
    if (soldeAbsencePaies.length > 0) {
      const soldeAbsencePaieCollectionIdentifiers = soldeAbsencePaieCollection.map(
        soldeAbsencePaieItem => getSoldeAbsencePaieIdentifier(soldeAbsencePaieItem)!
      );
      const soldeAbsencePaiesToAdd = soldeAbsencePaies.filter(soldeAbsencePaieItem => {
        const soldeAbsencePaieIdentifier = getSoldeAbsencePaieIdentifier(soldeAbsencePaieItem);
        if (soldeAbsencePaieIdentifier == null || soldeAbsencePaieCollectionIdentifiers.includes(soldeAbsencePaieIdentifier)) {
          return false;
        }
        soldeAbsencePaieCollectionIdentifiers.push(soldeAbsencePaieIdentifier);
        return true;
      });
      return [...soldeAbsencePaiesToAdd, ...soldeAbsencePaieCollection];
    }
    return soldeAbsencePaieCollection;
  }

  protected convertDateFromClient(soldeAbsencePaie: ISoldeAbsencePaie): ISoldeAbsencePaie {
    return Object.assign({}, soldeAbsencePaie, {
      dateop: soldeAbsencePaie.dateop?.isValid() ? soldeAbsencePaie.dateop.toJSON() : undefined,
      createdDate: soldeAbsencePaie.createdDate?.isValid() ? soldeAbsencePaie.createdDate.toJSON() : undefined,
      modifiedDate: soldeAbsencePaie.modifiedDate?.isValid() ? soldeAbsencePaie.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((soldeAbsencePaie: ISoldeAbsencePaie) => {
        soldeAbsencePaie.dateop = soldeAbsencePaie.dateop ? dayjs(soldeAbsencePaie.dateop) : undefined;
        soldeAbsencePaie.createdDate = soldeAbsencePaie.createdDate ? dayjs(soldeAbsencePaie.createdDate) : undefined;
        soldeAbsencePaie.modifiedDate = soldeAbsencePaie.modifiedDate ? dayjs(soldeAbsencePaie.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
