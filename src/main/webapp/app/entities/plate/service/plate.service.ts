import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPlate, getPlateIdentifier } from '../plate.model';

export type EntityResponseType = HttpResponse<IPlate>;
export type EntityArrayResponseType = HttpResponse<IPlate[]>;

@Injectable({ providedIn: 'root' })
export class PlateService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/plates');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(plate: IPlate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plate);
    return this.http
      .post<IPlate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(plate: IPlate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plate);
    return this.http
      .put<IPlate>(`${this.resourceUrl}/${getPlateIdentifier(plate) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(plate: IPlate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plate);
    return this.http
      .patch<IPlate>(`${this.resourceUrl}/${getPlateIdentifier(plate) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPlate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPlateToCollectionIfMissing(plateCollection: IPlate[], ...platesToCheck: (IPlate | null | undefined)[]): IPlate[] {
    const plates: IPlate[] = platesToCheck.filter(isPresent);
    if (plates.length > 0) {
      const plateCollectionIdentifiers = plateCollection.map(plateItem => getPlateIdentifier(plateItem)!);
      const platesToAdd = plates.filter(plateItem => {
        const plateIdentifier = getPlateIdentifier(plateItem);
        if (plateIdentifier == null || plateCollectionIdentifiers.includes(plateIdentifier)) {
          return false;
        }
        plateCollectionIdentifiers.push(plateIdentifier);
        return true;
      });
      return [...platesToAdd, ...plateCollection];
    }
    return plateCollection;
  }

  protected convertDateFromClient(plate: IPlate): IPlate {
    return Object.assign({}, plate, {
      dateop: plate.dateop?.isValid() ? plate.dateop.toJSON() : undefined,
      createdDate: plate.createdDate?.isValid() ? plate.createdDate.toJSON() : undefined,
      modifiedDate: plate.modifiedDate?.isValid() ? plate.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((plate: IPlate) => {
        plate.dateop = plate.dateop ? dayjs(plate.dateop) : undefined;
        plate.createdDate = plate.createdDate ? dayjs(plate.createdDate) : undefined;
        plate.modifiedDate = plate.modifiedDate ? dayjs(plate.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
