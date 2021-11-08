import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDevise, getDeviseIdentifier } from '../devise.model';

export type EntityResponseType = HttpResponse<IDevise>;
export type EntityArrayResponseType = HttpResponse<IDevise[]>;

@Injectable({ providedIn: 'root' })
export class DeviseService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/devises');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(devise: IDevise): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(devise);
    return this.http
      .post<IDevise>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(devise: IDevise): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(devise);
    return this.http
      .put<IDevise>(`${this.resourceUrl}/${getDeviseIdentifier(devise) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(devise: IDevise): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(devise);
    return this.http
      .patch<IDevise>(`${this.resourceUrl}/${getDeviseIdentifier(devise) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDevise>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDevise[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDeviseToCollectionIfMissing(deviseCollection: IDevise[], ...devisesToCheck: (IDevise | null | undefined)[]): IDevise[] {
    const devises: IDevise[] = devisesToCheck.filter(isPresent);
    if (devises.length > 0) {
      const deviseCollectionIdentifiers = deviseCollection.map(deviseItem => getDeviseIdentifier(deviseItem)!);
      const devisesToAdd = devises.filter(deviseItem => {
        const deviseIdentifier = getDeviseIdentifier(deviseItem);
        if (deviseIdentifier == null || deviseCollectionIdentifiers.includes(deviseIdentifier)) {
          return false;
        }
        deviseCollectionIdentifiers.push(deviseIdentifier);
        return true;
      });
      return [...devisesToAdd, ...deviseCollection];
    }
    return deviseCollection;
  }

  protected convertDateFromClient(devise: IDevise): IDevise {
    return Object.assign({}, devise, {
      dateop: devise.dateop?.isValid() ? devise.dateop.toJSON() : undefined,
      createdDate: devise.createdDate?.isValid() ? devise.createdDate.toJSON() : undefined,
      modifiedDate: devise.modifiedDate?.isValid() ? devise.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((devise: IDevise) => {
        devise.dateop = devise.dateop ? dayjs(devise.dateop) : undefined;
        devise.createdDate = devise.createdDate ? dayjs(devise.createdDate) : undefined;
        devise.modifiedDate = devise.modifiedDate ? dayjs(devise.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
