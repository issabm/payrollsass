import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISoldeAbsence, getSoldeAbsenceIdentifier } from '../solde-absence.model';

export type EntityResponseType = HttpResponse<ISoldeAbsence>;
export type EntityArrayResponseType = HttpResponse<ISoldeAbsence[]>;

@Injectable({ providedIn: 'root' })
export class SoldeAbsenceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/solde-absences');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(soldeAbsence: ISoldeAbsence): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soldeAbsence);
    return this.http
      .post<ISoldeAbsence>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(soldeAbsence: ISoldeAbsence): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soldeAbsence);
    return this.http
      .put<ISoldeAbsence>(`${this.resourceUrl}/${getSoldeAbsenceIdentifier(soldeAbsence) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(soldeAbsence: ISoldeAbsence): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soldeAbsence);
    return this.http
      .patch<ISoldeAbsence>(`${this.resourceUrl}/${getSoldeAbsenceIdentifier(soldeAbsence) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISoldeAbsence>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISoldeAbsence[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSoldeAbsenceToCollectionIfMissing(
    soldeAbsenceCollection: ISoldeAbsence[],
    ...soldeAbsencesToCheck: (ISoldeAbsence | null | undefined)[]
  ): ISoldeAbsence[] {
    const soldeAbsences: ISoldeAbsence[] = soldeAbsencesToCheck.filter(isPresent);
    if (soldeAbsences.length > 0) {
      const soldeAbsenceCollectionIdentifiers = soldeAbsenceCollection.map(
        soldeAbsenceItem => getSoldeAbsenceIdentifier(soldeAbsenceItem)!
      );
      const soldeAbsencesToAdd = soldeAbsences.filter(soldeAbsenceItem => {
        const soldeAbsenceIdentifier = getSoldeAbsenceIdentifier(soldeAbsenceItem);
        if (soldeAbsenceIdentifier == null || soldeAbsenceCollectionIdentifiers.includes(soldeAbsenceIdentifier)) {
          return false;
        }
        soldeAbsenceCollectionIdentifiers.push(soldeAbsenceIdentifier);
        return true;
      });
      return [...soldeAbsencesToAdd, ...soldeAbsenceCollection];
    }
    return soldeAbsenceCollection;
  }

  protected convertDateFromClient(soldeAbsence: ISoldeAbsence): ISoldeAbsence {
    return Object.assign({}, soldeAbsence, {
      dateop: soldeAbsence.dateop?.isValid() ? soldeAbsence.dateop.toJSON() : undefined,
      createdDate: soldeAbsence.createdDate?.isValid() ? soldeAbsence.createdDate.toJSON() : undefined,
      modifiedDate: soldeAbsence.modifiedDate?.isValid() ? soldeAbsence.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((soldeAbsence: ISoldeAbsence) => {
        soldeAbsence.dateop = soldeAbsence.dateop ? dayjs(soldeAbsence.dateop) : undefined;
        soldeAbsence.createdDate = soldeAbsence.createdDate ? dayjs(soldeAbsence.createdDate) : undefined;
        soldeAbsence.modifiedDate = soldeAbsence.modifiedDate ? dayjs(soldeAbsence.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
