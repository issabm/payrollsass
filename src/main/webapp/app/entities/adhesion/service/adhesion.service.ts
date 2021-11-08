import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdhesion, getAdhesionIdentifier } from '../adhesion.model';

export type EntityResponseType = HttpResponse<IAdhesion>;
export type EntityArrayResponseType = HttpResponse<IAdhesion[]>;

@Injectable({ providedIn: 'root' })
export class AdhesionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/adhesions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(adhesion: IAdhesion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(adhesion);
    return this.http
      .post<IAdhesion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(adhesion: IAdhesion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(adhesion);
    return this.http
      .put<IAdhesion>(`${this.resourceUrl}/${getAdhesionIdentifier(adhesion) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(adhesion: IAdhesion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(adhesion);
    return this.http
      .patch<IAdhesion>(`${this.resourceUrl}/${getAdhesionIdentifier(adhesion) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAdhesion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAdhesion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAdhesionToCollectionIfMissing(adhesionCollection: IAdhesion[], ...adhesionsToCheck: (IAdhesion | null | undefined)[]): IAdhesion[] {
    const adhesions: IAdhesion[] = adhesionsToCheck.filter(isPresent);
    if (adhesions.length > 0) {
      const adhesionCollectionIdentifiers = adhesionCollection.map(adhesionItem => getAdhesionIdentifier(adhesionItem)!);
      const adhesionsToAdd = adhesions.filter(adhesionItem => {
        const adhesionIdentifier = getAdhesionIdentifier(adhesionItem);
        if (adhesionIdentifier == null || adhesionCollectionIdentifiers.includes(adhesionIdentifier)) {
          return false;
        }
        adhesionCollectionIdentifiers.push(adhesionIdentifier);
        return true;
      });
      return [...adhesionsToAdd, ...adhesionCollection];
    }
    return adhesionCollection;
  }

  protected convertDateFromClient(adhesion: IAdhesion): IAdhesion {
    return Object.assign({}, adhesion, {
      dateDebut: adhesion.dateDebut?.isValid() ? adhesion.dateDebut.format(DATE_FORMAT) : undefined,
      dateFin: adhesion.dateFin?.isValid() ? adhesion.dateFin.format(DATE_FORMAT) : undefined,
      dateop: adhesion.dateop?.isValid() ? adhesion.dateop.toJSON() : undefined,
      createdDate: adhesion.createdDate?.isValid() ? adhesion.createdDate.toJSON() : undefined,
      modifiedDate: adhesion.modifiedDate?.isValid() ? adhesion.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((adhesion: IAdhesion) => {
        adhesion.dateDebut = adhesion.dateDebut ? dayjs(adhesion.dateDebut) : undefined;
        adhesion.dateFin = adhesion.dateFin ? dayjs(adhesion.dateFin) : undefined;
        adhesion.dateop = adhesion.dateop ? dayjs(adhesion.dateop) : undefined;
        adhesion.createdDate = adhesion.createdDate ? dayjs(adhesion.createdDate) : undefined;
        adhesion.modifiedDate = adhesion.modifiedDate ? dayjs(adhesion.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
