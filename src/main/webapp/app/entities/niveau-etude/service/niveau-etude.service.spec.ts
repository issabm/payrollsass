import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { INiveauEtude, NiveauEtude } from '../niveau-etude.model';

import { NiveauEtudeService } from './niveau-etude.service';

describe('NiveauEtude Service', () => {
  let service: NiveauEtudeService;
  let httpMock: HttpTestingController;
  let elemDefault: INiveauEtude;
  let expectedResult: INiveauEtude | INiveauEtude[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NiveauEtudeService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      orderLevel: 0,
      code: 'AAAAAAA',
      libAr: 'AAAAAAA',
      libEn: 'AAAAAAA',
      util: 'AAAAAAA',
      dateop: currentDate,
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

    it('should create a NiveauEtude', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateop: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateop: currentDate,
          createdDate: currentDate,
          modifiedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new NiveauEtude()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NiveauEtude', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          orderLevel: 1,
          code: 'BBBBBB',
          libAr: 'BBBBBB',
          libEn: 'BBBBBB',
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
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

    it('should partial update a NiveauEtude', () => {
      const patchObject = Object.assign(
        {
          code: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new NiveauEtude()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
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

    it('should return a list of NiveauEtude', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          orderLevel: 1,
          code: 'BBBBBB',
          libAr: 'BBBBBB',
          libEn: 'BBBBBB',
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
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

    it('should delete a NiveauEtude', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addNiveauEtudeToCollectionIfMissing', () => {
      it('should add a NiveauEtude to an empty array', () => {
        const niveauEtude: INiveauEtude = { id: 123 };
        expectedResult = service.addNiveauEtudeToCollectionIfMissing([], niveauEtude);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(niveauEtude);
      });

      it('should not add a NiveauEtude to an array that contains it', () => {
        const niveauEtude: INiveauEtude = { id: 123 };
        const niveauEtudeCollection: INiveauEtude[] = [
          {
            ...niveauEtude,
          },
          { id: 456 },
        ];
        expectedResult = service.addNiveauEtudeToCollectionIfMissing(niveauEtudeCollection, niveauEtude);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NiveauEtude to an array that doesn't contain it", () => {
        const niveauEtude: INiveauEtude = { id: 123 };
        const niveauEtudeCollection: INiveauEtude[] = [{ id: 456 }];
        expectedResult = service.addNiveauEtudeToCollectionIfMissing(niveauEtudeCollection, niveauEtude);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(niveauEtude);
      });

      it('should add only unique NiveauEtude to an array', () => {
        const niveauEtudeArray: INiveauEtude[] = [{ id: 123 }, { id: 456 }, { id: 49554 }];
        const niveauEtudeCollection: INiveauEtude[] = [{ id: 123 }];
        expectedResult = service.addNiveauEtudeToCollectionIfMissing(niveauEtudeCollection, ...niveauEtudeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const niveauEtude: INiveauEtude = { id: 123 };
        const niveauEtude2: INiveauEtude = { id: 456 };
        expectedResult = service.addNiveauEtudeToCollectionIfMissing([], niveauEtude, niveauEtude2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(niveauEtude);
        expect(expectedResult).toContain(niveauEtude2);
      });

      it('should accept null and undefined values', () => {
        const niveauEtude: INiveauEtude = { id: 123 };
        expectedResult = service.addNiveauEtudeToCollectionIfMissing([], null, niveauEtude, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(niveauEtude);
      });

      it('should return initial array if no NiveauEtude is added', () => {
        const niveauEtudeCollection: INiveauEtude[] = [{ id: 123 }];
        expectedResult = service.addNiveauEtudeToCollectionIfMissing(niveauEtudeCollection, undefined, null);
        expect(expectedResult).toEqual(niveauEtudeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
