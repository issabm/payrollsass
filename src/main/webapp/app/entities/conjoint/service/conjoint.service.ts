import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IConjoint, getConjointIdentifier } from '../conjoint.model';

export type EntityResponseType = HttpResponse<IConjoint>;
export type EntityArrayResponseType = HttpResponse<IConjoint[]>;

@Injectable({ providedIn: 'root' })
export class ConjointService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/conjoints');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(conjoint: IConjoint): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(conjoint);
    return this.http
      .post<IConjoint>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(conjoint: IConjoint): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(conjoint);
    return this.http
      .put<IConjoint>(`${this.resourceUrl}/${getConjointIdentifier(conjoint) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(conjoint: IConjoint): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(conjoint);
    return this.http
      .patch<IConjoint>(`${this.resourceUrl}/${getConjointIdentifier(conjoint) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IConjoint>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IConjoint[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addConjointToCollectionIfMissing(conjointCollection: IConjoint[], ...conjointsToCheck: (IConjoint | null | undefined)[]): IConjoint[] {
    const conjoints: IConjoint[] = conjointsToCheck.filter(isPresent);
    if (conjoints.length > 0) {
      const conjointCollectionIdentifiers = conjointCollection.map(conjointItem => getConjointIdentifier(conjointItem)!);
      const conjointsToAdd = conjoints.filter(conjointItem => {
        const conjointIdentifier = getConjointIdentifier(conjointItem);
        if (conjointIdentifier == null || conjointCollectionIdentifiers.includes(conjointIdentifier)) {
          return false;
        }
        conjointCollectionIdentifiers.push(conjointIdentifier);
        return true;
      });
      return [...conjointsToAdd, ...conjointCollection];
    }
    return conjointCollection;
  }

  protected convertDateFromClient(conjoint: IConjoint): IConjoint {
    return Object.assign({}, conjoint, {
      dateNaiss: conjoint.dateNaiss?.isValid() ? conjoint.dateNaiss.format(DATE_FORMAT) : undefined,
      dateop: conjoint.dateop?.isValid() ? conjoint.dateop.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateNaiss = res.body.dateNaiss ? dayjs(res.body.dateNaiss) : undefined;
      res.body.dateop = res.body.dateop ? dayjs(res.body.dateop) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((conjoint: IConjoint) => {
        conjoint.dateNaiss = conjoint.dateNaiss ? dayjs(conjoint.dateNaiss) : undefined;
        conjoint.dateop = conjoint.dateop ? dayjs(conjoint.dateop) : undefined;
      });
    }
    return res;
  }
}
