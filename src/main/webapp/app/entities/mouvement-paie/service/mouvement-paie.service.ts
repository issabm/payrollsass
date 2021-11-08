import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMouvementPaie, getMouvementPaieIdentifier } from '../mouvement-paie.model';

export type EntityResponseType = HttpResponse<IMouvementPaie>;
export type EntityArrayResponseType = HttpResponse<IMouvementPaie[]>;

@Injectable({ providedIn: 'root' })
export class MouvementPaieService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mouvement-paies');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(mouvementPaie: IMouvementPaie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mouvementPaie);
    return this.http
      .post<IMouvementPaie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(mouvementPaie: IMouvementPaie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mouvementPaie);
    return this.http
      .put<IMouvementPaie>(`${this.resourceUrl}/${getMouvementPaieIdentifier(mouvementPaie) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(mouvementPaie: IMouvementPaie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mouvementPaie);
    return this.http
      .patch<IMouvementPaie>(`${this.resourceUrl}/${getMouvementPaieIdentifier(mouvementPaie) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMouvementPaie>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMouvementPaie[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMouvementPaieToCollectionIfMissing(
    mouvementPaieCollection: IMouvementPaie[],
    ...mouvementPaiesToCheck: (IMouvementPaie | null | undefined)[]
  ): IMouvementPaie[] {
    const mouvementPaies: IMouvementPaie[] = mouvementPaiesToCheck.filter(isPresent);
    if (mouvementPaies.length > 0) {
      const mouvementPaieCollectionIdentifiers = mouvementPaieCollection.map(
        mouvementPaieItem => getMouvementPaieIdentifier(mouvementPaieItem)!
      );
      const mouvementPaiesToAdd = mouvementPaies.filter(mouvementPaieItem => {
        const mouvementPaieIdentifier = getMouvementPaieIdentifier(mouvementPaieItem);
        if (mouvementPaieIdentifier == null || mouvementPaieCollectionIdentifiers.includes(mouvementPaieIdentifier)) {
          return false;
        }
        mouvementPaieCollectionIdentifiers.push(mouvementPaieIdentifier);
        return true;
      });
      return [...mouvementPaiesToAdd, ...mouvementPaieCollection];
    }
    return mouvementPaieCollection;
  }

  protected convertDateFromClient(mouvementPaie: IMouvementPaie): IMouvementPaie {
    return Object.assign({}, mouvementPaie, {
      dateCalcul: mouvementPaie.dateCalcul?.isValid() ? mouvementPaie.dateCalcul.format(DATE_FORMAT) : undefined,
      dateValid: mouvementPaie.dateValid?.isValid() ? mouvementPaie.dateValid.format(DATE_FORMAT) : undefined,
      datePayroll: mouvementPaie.datePayroll?.isValid() ? mouvementPaie.datePayroll.format(DATE_FORMAT) : undefined,
      dateSituation: mouvementPaie.dateSituation?.isValid() ? mouvementPaie.dateSituation.format(DATE_FORMAT) : undefined,
      dateop: mouvementPaie.dateop?.isValid() ? mouvementPaie.dateop.toJSON() : undefined,
      createdDate: mouvementPaie.createdDate?.isValid() ? mouvementPaie.createdDate.toJSON() : undefined,
      modifiedDate: mouvementPaie.modifiedDate?.isValid() ? mouvementPaie.modifiedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCalcul = res.body.dateCalcul ? dayjs(res.body.dateCalcul) : undefined;
      res.body.dateValid = res.body.dateValid ? dayjs(res.body.dateValid) : undefined;
      res.body.datePayroll = res.body.datePayroll ? dayjs(res.body.datePayroll) : undefined;
      res.body.dateSituation = res.body.dateSituation ? dayjs(res.body.dateSituation) : undefined;
      res.body.dateop = res.body.dateop ? dayjs(res.body.dateop) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.modifiedDate = res.body.modifiedDate ? dayjs(res.body.modifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((mouvementPaie: IMouvementPaie) => {
        mouvementPaie.dateCalcul = mouvementPaie.dateCalcul ? dayjs(mouvementPaie.dateCalcul) : undefined;
        mouvementPaie.dateValid = mouvementPaie.dateValid ? dayjs(mouvementPaie.dateValid) : undefined;
        mouvementPaie.datePayroll = mouvementPaie.datePayroll ? dayjs(mouvementPaie.datePayroll) : undefined;
        mouvementPaie.dateSituation = mouvementPaie.dateSituation ? dayjs(mouvementPaie.dateSituation) : undefined;
        mouvementPaie.dateop = mouvementPaie.dateop ? dayjs(mouvementPaie.dateop) : undefined;
        mouvementPaie.createdDate = mouvementPaie.createdDate ? dayjs(mouvementPaie.createdDate) : undefined;
        mouvementPaie.modifiedDate = mouvementPaie.modifiedDate ? dayjs(mouvementPaie.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
