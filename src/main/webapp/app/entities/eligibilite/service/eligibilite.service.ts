import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEligibilite, getEligibiliteIdentifier } from '../eligibilite.model';

export type EntityResponseType = HttpResponse<IEligibilite>;
export type EntityArrayResponseType = HttpResponse<IEligibilite[]>;

@Injectable({ providedIn: 'root' })
export class EligibiliteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/eligibilites');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(eligibilite: IEligibilite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eligibilite);
    return this.http
      .post<IEligibilite>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(eligibilite: IEligibilite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eligibilite);
    return this.http
      .put<IEligibilite>(`${this.resourceUrl}/${getEligibiliteIdentifier(eligibilite) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(eligibilite: IEligibilite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eligibilite);
    return this.http
      .patch<IEligibilite>(`${this.resourceUrl}/${getEligibiliteIdentifier(eligibilite) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEligibilite>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEligibilite[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEligibiliteToCollectionIfMissing(
    eligibiliteCollection: IEligibilite[],
    ...eligibilitesToCheck: (IEligibilite | null | undefined)[]
  ): IEligibilite[] {
    const eligibilites: IEligibilite[] = eligibilitesToCheck.filter(isPresent);
    if (eligibilites.length > 0) {
      const eligibiliteCollectionIdentifiers = eligibiliteCollection.map(eligibiliteItem => getEligibiliteIdentifier(eligibiliteItem)!);
      const eligibilitesToAdd = eligibilites.filter(eligibiliteItem => {
        const eligibiliteIdentifier = getEligibiliteIdentifier(eligibiliteItem);
        if (eligibiliteIdentifier == null || eligibiliteCollectionIdentifiers.includes(eligibiliteIdentifier)) {
          return false;
        }
        eligibiliteCollectionIdentifiers.push(eligibiliteIdentifier);
        return true;
      });
      return [...eligibilitesToAdd, ...eligibiliteCollection];
    }
    return eligibiliteCollection;
  }

  protected convertDateFromClient(eligibilite: IEligibilite): IEligibilite {
    return Object.assign({}, eligibilite, {
      dateop: eligibilite.dateop?.isValid() ? eligibilite.dateop.toJSON() : undefined,
      createdDate: eligibilite.createdDate?.isValid() ? eligibilite.createdDate.toJSON() : undefined,
      modifiedDate: eligibilite.modifiedDate?.isValid() ? eligibilite.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((eligibilite: IEligibilite) => {
        eligibilite.dateop = eligibilite.dateop ? dayjs(eligibilite.dateop) : undefined;
        eligibilite.createdDate = eligibilite.createdDate ? dayjs(eligibilite.createdDate) : undefined;
        eligibilite.modifiedDate = eligibilite.modifiedDate ? dayjs(eligibilite.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
