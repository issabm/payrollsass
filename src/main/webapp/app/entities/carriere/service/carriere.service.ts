import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICarriere, getCarriereIdentifier } from '../carriere.model';

export type EntityResponseType = HttpResponse<ICarriere>;
export type EntityArrayResponseType = HttpResponse<ICarriere[]>;

@Injectable({ providedIn: 'root' })
export class CarriereService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/carrieres');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(carriere: ICarriere): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(carriere);
    return this.http
      .post<ICarriere>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(carriere: ICarriere): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(carriere);
    return this.http
      .put<ICarriere>(`${this.resourceUrl}/${getCarriereIdentifier(carriere) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(carriere: ICarriere): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(carriere);
    return this.http
      .patch<ICarriere>(`${this.resourceUrl}/${getCarriereIdentifier(carriere) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICarriere>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICarriere[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCarriereToCollectionIfMissing(carriereCollection: ICarriere[], ...carrieresToCheck: (ICarriere | null | undefined)[]): ICarriere[] {
    const carrieres: ICarriere[] = carrieresToCheck.filter(isPresent);
    if (carrieres.length > 0) {
      const carriereCollectionIdentifiers = carriereCollection.map(carriereItem => getCarriereIdentifier(carriereItem)!);
      const carrieresToAdd = carrieres.filter(carriereItem => {
        const carriereIdentifier = getCarriereIdentifier(carriereItem);
        if (carriereIdentifier == null || carriereCollectionIdentifiers.includes(carriereIdentifier)) {
          return false;
        }
        carriereCollectionIdentifiers.push(carriereIdentifier);
        return true;
      });
      return [...carrieresToAdd, ...carriereCollection];
    }
    return carriereCollection;
  }

  protected convertDateFromClient(carriere: ICarriere): ICarriere {
    return Object.assign({}, carriere, {
      dateDebut: carriere.dateDebut?.isValid() ? carriere.dateDebut.format(DATE_FORMAT) : undefined,
      dateFin: carriere.dateFin?.isValid() ? carriere.dateFin.format(DATE_FORMAT) : undefined,
      dateEmploi: carriere.dateEmploi?.isValid() ? carriere.dateEmploi.format(DATE_FORMAT) : undefined,
      dateEchlon: carriere.dateEchlon?.isValid() ? carriere.dateEchlon.format(DATE_FORMAT) : undefined,
      dateCategorie: carriere.dateCategorie?.isValid() ? carriere.dateCategorie.format(DATE_FORMAT) : undefined,
      dateop: carriere.dateop?.isValid() ? carriere.dateop.toJSON() : undefined,
      createdDate: carriere.createdDate?.isValid() ? carriere.createdDate.toJSON() : undefined,
      modifiedDate: carriere.modifiedDate?.isValid() ? carriere.modifiedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDebut = res.body.dateDebut ? dayjs(res.body.dateDebut) : undefined;
      res.body.dateFin = res.body.dateFin ? dayjs(res.body.dateFin) : undefined;
      res.body.dateEmploi = res.body.dateEmploi ? dayjs(res.body.dateEmploi) : undefined;
      res.body.dateEchlon = res.body.dateEchlon ? dayjs(res.body.dateEchlon) : undefined;
      res.body.dateCategorie = res.body.dateCategorie ? dayjs(res.body.dateCategorie) : undefined;
      res.body.dateop = res.body.dateop ? dayjs(res.body.dateop) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.modifiedDate = res.body.modifiedDate ? dayjs(res.body.modifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((carriere: ICarriere) => {
        carriere.dateDebut = carriere.dateDebut ? dayjs(carriere.dateDebut) : undefined;
        carriere.dateFin = carriere.dateFin ? dayjs(carriere.dateFin) : undefined;
        carriere.dateEmploi = carriere.dateEmploi ? dayjs(carriere.dateEmploi) : undefined;
        carriere.dateEchlon = carriere.dateEchlon ? dayjs(carriere.dateEchlon) : undefined;
        carriere.dateCategorie = carriere.dateCategorie ? dayjs(carriere.dateCategorie) : undefined;
        carriere.dateop = carriere.dateop ? dayjs(carriere.dateop) : undefined;
        carriere.createdDate = carriere.createdDate ? dayjs(carriere.createdDate) : undefined;
        carriere.modifiedDate = carriere.modifiedDate ? dayjs(carriere.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
