import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INatureMvtPaie, getNatureMvtPaieIdentifier } from '../nature-mvt-paie.model';

export type EntityResponseType = HttpResponse<INatureMvtPaie>;
export type EntityArrayResponseType = HttpResponse<INatureMvtPaie[]>;

@Injectable({ providedIn: 'root' })
export class NatureMvtPaieService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nature-mvt-paies');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(natureMvtPaie: INatureMvtPaie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natureMvtPaie);
    return this.http
      .post<INatureMvtPaie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(natureMvtPaie: INatureMvtPaie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natureMvtPaie);
    return this.http
      .put<INatureMvtPaie>(`${this.resourceUrl}/${getNatureMvtPaieIdentifier(natureMvtPaie) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(natureMvtPaie: INatureMvtPaie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natureMvtPaie);
    return this.http
      .patch<INatureMvtPaie>(`${this.resourceUrl}/${getNatureMvtPaieIdentifier(natureMvtPaie) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INatureMvtPaie>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INatureMvtPaie[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNatureMvtPaieToCollectionIfMissing(
    natureMvtPaieCollection: INatureMvtPaie[],
    ...natureMvtPaiesToCheck: (INatureMvtPaie | null | undefined)[]
  ): INatureMvtPaie[] {
    const natureMvtPaies: INatureMvtPaie[] = natureMvtPaiesToCheck.filter(isPresent);
    if (natureMvtPaies.length > 0) {
      const natureMvtPaieCollectionIdentifiers = natureMvtPaieCollection.map(
        natureMvtPaieItem => getNatureMvtPaieIdentifier(natureMvtPaieItem)!
      );
      const natureMvtPaiesToAdd = natureMvtPaies.filter(natureMvtPaieItem => {
        const natureMvtPaieIdentifier = getNatureMvtPaieIdentifier(natureMvtPaieItem);
        if (natureMvtPaieIdentifier == null || natureMvtPaieCollectionIdentifiers.includes(natureMvtPaieIdentifier)) {
          return false;
        }
        natureMvtPaieCollectionIdentifiers.push(natureMvtPaieIdentifier);
        return true;
      });
      return [...natureMvtPaiesToAdd, ...natureMvtPaieCollection];
    }
    return natureMvtPaieCollection;
  }

  protected convertDateFromClient(natureMvtPaie: INatureMvtPaie): INatureMvtPaie {
    return Object.assign({}, natureMvtPaie, {
      dateop: natureMvtPaie.dateop?.isValid() ? natureMvtPaie.dateop.toJSON() : undefined,
      createdDate: natureMvtPaie.createdDate?.isValid() ? natureMvtPaie.createdDate.toJSON() : undefined,
      modifiedDate: natureMvtPaie.modifiedDate?.isValid() ? natureMvtPaie.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((natureMvtPaie: INatureMvtPaie) => {
        natureMvtPaie.dateop = natureMvtPaie.dateop ? dayjs(natureMvtPaie.dateop) : undefined;
        natureMvtPaie.createdDate = natureMvtPaie.createdDate ? dayjs(natureMvtPaie.createdDate) : undefined;
        natureMvtPaie.modifiedDate = natureMvtPaie.modifiedDate ? dayjs(natureMvtPaie.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
