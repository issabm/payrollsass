import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISousTypeContrat, getSousTypeContratIdentifier } from '../sous-type-contrat.model';

export type EntityResponseType = HttpResponse<ISousTypeContrat>;
export type EntityArrayResponseType = HttpResponse<ISousTypeContrat[]>;

@Injectable({ providedIn: 'root' })
export class SousTypeContratService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sous-type-contrats');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(sousTypeContrat: ISousTypeContrat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sousTypeContrat);
    return this.http
      .post<ISousTypeContrat>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sousTypeContrat: ISousTypeContrat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sousTypeContrat);
    return this.http
      .put<ISousTypeContrat>(`${this.resourceUrl}/${getSousTypeContratIdentifier(sousTypeContrat) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(sousTypeContrat: ISousTypeContrat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sousTypeContrat);
    return this.http
      .patch<ISousTypeContrat>(`${this.resourceUrl}/${getSousTypeContratIdentifier(sousTypeContrat) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISousTypeContrat>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISousTypeContrat[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSousTypeContratToCollectionIfMissing(
    sousTypeContratCollection: ISousTypeContrat[],
    ...sousTypeContratsToCheck: (ISousTypeContrat | null | undefined)[]
  ): ISousTypeContrat[] {
    const sousTypeContrats: ISousTypeContrat[] = sousTypeContratsToCheck.filter(isPresent);
    if (sousTypeContrats.length > 0) {
      const sousTypeContratCollectionIdentifiers = sousTypeContratCollection.map(
        sousTypeContratItem => getSousTypeContratIdentifier(sousTypeContratItem)!
      );
      const sousTypeContratsToAdd = sousTypeContrats.filter(sousTypeContratItem => {
        const sousTypeContratIdentifier = getSousTypeContratIdentifier(sousTypeContratItem);
        if (sousTypeContratIdentifier == null || sousTypeContratCollectionIdentifiers.includes(sousTypeContratIdentifier)) {
          return false;
        }
        sousTypeContratCollectionIdentifiers.push(sousTypeContratIdentifier);
        return true;
      });
      return [...sousTypeContratsToAdd, ...sousTypeContratCollection];
    }
    return sousTypeContratCollection;
  }

  protected convertDateFromClient(sousTypeContrat: ISousTypeContrat): ISousTypeContrat {
    return Object.assign({}, sousTypeContrat, {
      dateop: sousTypeContrat.dateop?.isValid() ? sousTypeContrat.dateop.toJSON() : undefined,
      createdDate: sousTypeContrat.createdDate?.isValid() ? sousTypeContrat.createdDate.toJSON() : undefined,
      modifiedDate: sousTypeContrat.modifiedDate?.isValid() ? sousTypeContrat.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((sousTypeContrat: ISousTypeContrat) => {
        sousTypeContrat.dateop = sousTypeContrat.dateop ? dayjs(sousTypeContrat.dateop) : undefined;
        sousTypeContrat.createdDate = sousTypeContrat.createdDate ? dayjs(sousTypeContrat.createdDate) : undefined;
        sousTypeContrat.modifiedDate = sousTypeContrat.modifiedDate ? dayjs(sousTypeContrat.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
