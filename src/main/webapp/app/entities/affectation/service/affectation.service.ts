import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAffectation, getAffectationIdentifier } from '../affectation.model';

export type EntityResponseType = HttpResponse<IAffectation>;
export type EntityArrayResponseType = HttpResponse<IAffectation[]>;

@Injectable({ providedIn: 'root' })
export class AffectationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/affectations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(affectation: IAffectation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(affectation);
    return this.http
      .post<IAffectation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(affectation: IAffectation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(affectation);
    return this.http
      .put<IAffectation>(`${this.resourceUrl}/${getAffectationIdentifier(affectation) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(affectation: IAffectation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(affectation);
    return this.http
      .patch<IAffectation>(`${this.resourceUrl}/${getAffectationIdentifier(affectation) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAffectation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAffectation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAffectationToCollectionIfMissing(
    affectationCollection: IAffectation[],
    ...affectationsToCheck: (IAffectation | null | undefined)[]
  ): IAffectation[] {
    const affectations: IAffectation[] = affectationsToCheck.filter(isPresent);
    if (affectations.length > 0) {
      const affectationCollectionIdentifiers = affectationCollection.map(affectationItem => getAffectationIdentifier(affectationItem)!);
      const affectationsToAdd = affectations.filter(affectationItem => {
        const affectationIdentifier = getAffectationIdentifier(affectationItem);
        if (affectationIdentifier == null || affectationCollectionIdentifiers.includes(affectationIdentifier)) {
          return false;
        }
        affectationCollectionIdentifiers.push(affectationIdentifier);
        return true;
      });
      return [...affectationsToAdd, ...affectationCollection];
    }
    return affectationCollection;
  }

  protected convertDateFromClient(affectation: IAffectation): IAffectation {
    return Object.assign({}, affectation, {
      dateDebut: affectation.dateDebut?.isValid() ? affectation.dateDebut.format(DATE_FORMAT) : undefined,
      dateFin: affectation.dateFin?.isValid() ? affectation.dateFin.format(DATE_FORMAT) : undefined,
      dateop: affectation.dateop?.isValid() ? affectation.dateop.toJSON() : undefined,
      createdDate: affectation.createdDate?.isValid() ? affectation.createdDate.toJSON() : undefined,
      modifiedDate: affectation.modifiedDate?.isValid() ? affectation.modifiedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDebut = res.body.dateDebut ? dayjs(res.body.dateDebut) : undefined;
      res.body.dateFin = res.body.dateFin ? dayjs(res.body.dateFin) : undefined;
      res.body.dateop = res.body.dateop ? dayjs(res.body.dateop) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.modifiedDate = res.body.modifiedDate ? dayjs(res.body.modifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((affectation: IAffectation) => {
        affectation.dateDebut = affectation.dateDebut ? dayjs(affectation.dateDebut) : undefined;
        affectation.dateFin = affectation.dateFin ? dayjs(affectation.dateFin) : undefined;
        affectation.dateop = affectation.dateop ? dayjs(affectation.dateop) : undefined;
        affectation.createdDate = affectation.createdDate ? dayjs(affectation.createdDate) : undefined;
        affectation.modifiedDate = affectation.modifiedDate ? dayjs(affectation.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
