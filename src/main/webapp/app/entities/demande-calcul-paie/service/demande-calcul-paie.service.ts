import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDemandeCalculPaie, getDemandeCalculPaieIdentifier } from '../demande-calcul-paie.model';

export type EntityResponseType = HttpResponse<IDemandeCalculPaie>;
export type EntityArrayResponseType = HttpResponse<IDemandeCalculPaie[]>;

@Injectable({ providedIn: 'root' })
export class DemandeCalculPaieService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/demande-calcul-paies');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(demandeCalculPaie: IDemandeCalculPaie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandeCalculPaie);
    return this.http
      .post<IDemandeCalculPaie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(demandeCalculPaie: IDemandeCalculPaie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandeCalculPaie);
    return this.http
      .put<IDemandeCalculPaie>(`${this.resourceUrl}/${getDemandeCalculPaieIdentifier(demandeCalculPaie) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(demandeCalculPaie: IDemandeCalculPaie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandeCalculPaie);
    return this.http
      .patch<IDemandeCalculPaie>(`${this.resourceUrl}/${getDemandeCalculPaieIdentifier(demandeCalculPaie) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDemandeCalculPaie>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDemandeCalculPaie[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDemandeCalculPaieToCollectionIfMissing(
    demandeCalculPaieCollection: IDemandeCalculPaie[],
    ...demandeCalculPaiesToCheck: (IDemandeCalculPaie | null | undefined)[]
  ): IDemandeCalculPaie[] {
    const demandeCalculPaies: IDemandeCalculPaie[] = demandeCalculPaiesToCheck.filter(isPresent);
    if (demandeCalculPaies.length > 0) {
      const demandeCalculPaieCollectionIdentifiers = demandeCalculPaieCollection.map(
        demandeCalculPaieItem => getDemandeCalculPaieIdentifier(demandeCalculPaieItem)!
      );
      const demandeCalculPaiesToAdd = demandeCalculPaies.filter(demandeCalculPaieItem => {
        const demandeCalculPaieIdentifier = getDemandeCalculPaieIdentifier(demandeCalculPaieItem);
        if (demandeCalculPaieIdentifier == null || demandeCalculPaieCollectionIdentifiers.includes(demandeCalculPaieIdentifier)) {
          return false;
        }
        demandeCalculPaieCollectionIdentifiers.push(demandeCalculPaieIdentifier);
        return true;
      });
      return [...demandeCalculPaiesToAdd, ...demandeCalculPaieCollection];
    }
    return demandeCalculPaieCollection;
  }

  protected convertDateFromClient(demandeCalculPaie: IDemandeCalculPaie): IDemandeCalculPaie {
    return Object.assign({}, demandeCalculPaie, {
      dateCalcul: demandeCalculPaie.dateCalcul?.isValid() ? demandeCalculPaie.dateCalcul.format(DATE_FORMAT) : undefined,
      dateValid: demandeCalculPaie.dateValid?.isValid() ? demandeCalculPaie.dateValid.format(DATE_FORMAT) : undefined,
      datePayroll: demandeCalculPaie.datePayroll?.isValid() ? demandeCalculPaie.datePayroll.format(DATE_FORMAT) : undefined,
      dateSituation: demandeCalculPaie.dateSituation?.isValid() ? demandeCalculPaie.dateSituation.format(DATE_FORMAT) : undefined,
      dateop: demandeCalculPaie.dateop?.isValid() ? demandeCalculPaie.dateop.toJSON() : undefined,
      createdDate: demandeCalculPaie.createdDate?.isValid() ? demandeCalculPaie.createdDate.toJSON() : undefined,
      modifiedDate: demandeCalculPaie.modifiedDate?.isValid() ? demandeCalculPaie.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((demandeCalculPaie: IDemandeCalculPaie) => {
        demandeCalculPaie.dateCalcul = demandeCalculPaie.dateCalcul ? dayjs(demandeCalculPaie.dateCalcul) : undefined;
        demandeCalculPaie.dateValid = demandeCalculPaie.dateValid ? dayjs(demandeCalculPaie.dateValid) : undefined;
        demandeCalculPaie.datePayroll = demandeCalculPaie.datePayroll ? dayjs(demandeCalculPaie.datePayroll) : undefined;
        demandeCalculPaie.dateSituation = demandeCalculPaie.dateSituation ? dayjs(demandeCalculPaie.dateSituation) : undefined;
        demandeCalculPaie.dateop = demandeCalculPaie.dateop ? dayjs(demandeCalculPaie.dateop) : undefined;
        demandeCalculPaie.createdDate = demandeCalculPaie.createdDate ? dayjs(demandeCalculPaie.createdDate) : undefined;
        demandeCalculPaie.modifiedDate = demandeCalculPaie.modifiedDate ? dayjs(demandeCalculPaie.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
