import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICarriere, Carriere } from '../carriere.model';

import { CarriereService } from './carriere.service';

describe('Carriere Service', () => {
  let service: CarriereService;
  let httpMock: HttpTestingController;
  let elemDefault: ICarriere;
  let expectedResult: ICarriere | ICarriere[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CarriereService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      dateDebut: currentDate,
      dateFin: currentDate,
      dateEmploi: currentDate,
      dateEchlon: currentDate,
      dateCategorie: currentDate,
      dateop: currentDate,
      util: 'AAAAAAA',
      modifiedBy: 'AAAAAAA',
      op: 'AAAAAAA',
      isDeleted: false,
      createdDate: currentDate,
      modifiedDate: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateDebut: currentDate.format(DATE_FORMAT),
          dateFin: currentDate.format(DATE_FORMAT),
          dateEmploi: currentDate.format(DATE_FORMAT),
          dateEchlon: currentDate.format(DATE_FORMAT),
          dateCategorie: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Carriere', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateDebut: currentDate.format(DATE_FORMAT),
          dateFin: currentDate.format(DATE_FORMAT),
          dateEmploi: currentDate.format(DATE_FORMAT),
          dateEchlon: currentDate.format(DATE_FORMAT),
          dateCategorie: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateDebut: currentDate,
          dateFin: currentDate,
          dateEmploi: currentDate,
          dateEchlon: currentDate,
          dateCategorie: currentDate,
          dateop: currentDate,
          createdDate: currentDate,
          modifiedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new Carriere()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Carriere', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dateDebut: currentDate.format(DATE_FORMAT),
          dateFin: currentDate.format(DATE_FORMAT),
          dateEmploi: currentDate.format(DATE_FORMAT),
          dateEchlon: currentDate.format(DATE_FORMAT),
          dateCategorie: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          util: 'BBBBBB',
          modifiedBy: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateDebut: currentDate,
          dateFin: currentDate,
          dateEmploi: currentDate,
          dateEchlon: currentDate,
          dateCategorie: currentDate,
          dateop: currentDate,
          createdDate: currentDate,
          modifiedDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Carriere', () => {
      const patchObject = Object.assign(
        {
          dateEmploi: currentDate.format(DATE_FORMAT),
          dateEchlon: currentDate.format(DATE_FORMAT),
          dateCategorie: currentDate.format(DATE_FORMAT),
          util: 'BBBBBB',
          modifiedBy: 'BBBBBB',
          isDeleted: true,
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new Carriere()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateDebut: currentDate,
          dateFin: currentDate,
          dateEmploi: currentDate,
          dateEchlon: currentDate,
          dateCategorie: currentDate,
          dateop: currentDate,
          createdDate: currentDate,
          modifiedDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Carriere', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dateDebut: currentDate.format(DATE_FORMAT),
          dateFin: currentDate.format(DATE_FORMAT),
          dateEmploi: currentDate.format(DATE_FORMAT),
          dateEchlon: currentDate.format(DATE_FORMAT),
          dateCategorie: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          util: 'BBBBBB',
          modifiedBy: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateDebut: currentDate,
          dateFin: currentDate,
          dateEmploi: currentDate,
          dateEchlon: currentDate,
          dateCategorie: currentDate,
          dateop: currentDate,
          createdDate: currentDate,
          modifiedDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Carriere', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCarriereToCollectionIfMissing', () => {
      it('should add a Carriere to an empty array', () => {
        const carriere: ICarriere = { id: 123 };
        expectedResult = service.addCarriereToCollectionIfMissing([], carriere);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(carriere);
      });

      it('should not add a Carriere to an array that contains it', () => {
        const carriere: ICarriere = { id: 123 };
        const carriereCollection: ICarriere[] = [
          {
            ...carriere,
          },
          { id: 456 },
        ];
        expectedResult = service.addCarriereToCollectionIfMissing(carriereCollection, carriere);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Carriere to an array that doesn't contain it", () => {
        const carriere: ICarriere = { id: 123 };
        const carriereCollection: ICarriere[] = [{ id: 456 }];
        expectedResult = service.addCarriereToCollectionIfMissing(carriereCollection, carriere);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(carriere);
      });

      it('should add only unique Carriere to an array', () => {
        const carriereArray: ICarriere[] = [{ id: 123 }, { id: 456 }, { id: 4959 }];
        const carriereCollection: ICarriere[] = [{ id: 123 }];
        expectedResult = service.addCarriereToCollectionIfMissing(carriereCollection, ...carriereArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const carriere: ICarriere = { id: 123 };
        const carriere2: ICarriere = { id: 456 };
        expectedResult = service.addCarriereToCollectionIfMissing([], carriere, carriere2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(carriere);
        expect(expectedResult).toContain(carriere2);
      });

      it('should accept null and undefined values', () => {
        const carriere: ICarriere = { id: 123 };
        expectedResult = service.addCarriereToCollectionIfMissing([], null, carriere, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(carriere);
      });

      it('should return initial array if no Carriere is added', () => {
        const carriereCollection: ICarriere[] = [{ id: 123 }];
        expectedResult = service.addCarriereToCollectionIfMissing(carriereCollection, undefined, null);
        expect(expectedResult).toEqual(carriereCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
