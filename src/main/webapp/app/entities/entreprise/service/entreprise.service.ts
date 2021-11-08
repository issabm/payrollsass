import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEntreprise, getEntrepriseIdentifier } from '../entreprise.model';

export type EntityResponseType = HttpResponse<IEntreprise>;
export type EntityArrayResponseType = HttpResponse<IEntreprise[]>;

@Injectable({ providedIn: 'root' })
export class EntrepriseService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/entreprises');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(entreprise: IEntreprise): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entreprise);
    return this.http
      .post<IEntreprise>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(entreprise: IEntreprise): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entreprise);
    return this.http
      .put<IEntreprise>(`${this.resourceUrl}/${getEntrepriseIdentifier(entreprise) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(entreprise: IEntreprise): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entreprise);
    return this.http
      .patch<IEntreprise>(`${this.resourceUrl}/${getEntrepriseIdentifier(entreprise) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEntreprise>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEntreprise[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEntrepriseToCollectionIfMissing(
    entrepriseCollection: IEntreprise[],
    ...entreprisesToCheck: (IEntreprise | null | undefined)[]
  ): IEntreprise[] {
    const entreprises: IEntreprise[] = entreprisesToCheck.filter(isPresent);
    if (entreprises.length > 0) {
      const entrepriseCollectionIdentifiers = entrepriseCollection.map(entrepriseItem => getEntrepriseIdentifier(entrepriseItem)!);
      const entreprisesToAdd = entreprises.filter(entrepriseItem => {
        const entrepriseIdentifier = getEntrepriseIdentifier(entrepriseItem);
        if (entrepriseIdentifier == null || entrepriseCollectionIdentifiers.includes(entrepriseIdentifier)) {
          return false;
        }
        entrepriseCollectionIdentifiers.push(entrepriseIdentifier);
        return true;
      });
      return [...entreprisesToAdd, ...entrepriseCollection];
    }
    return entrepriseCollection;
  }

  protected convertDateFromClient(entreprise: IEntreprise): IEntreprise {
    return Object.assign({}, entreprise, {
      dateop: entreprise.dateop?.isValid() ? entreprise.dateop.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateop = res.body.dateop ? dayjs(res.body.dateop) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((entreprise: IEntreprise) => {
        entreprise.dateop = entreprise.dateop ? dayjs(entreprise.dateop) : undefined;
      });
    }
    return res;
  }
}
