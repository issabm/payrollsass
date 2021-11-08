import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPalierCondition, PalierCondition } from '../palier-condition.model';

import { PalierConditionService } from './palier-condition.service';

describe('PalierCondition Service', () => {
  let service: PalierConditionService;
  let httpMock: HttpTestingController;
  let elemDefault: IPalierCondition;
  let expectedResult: IPalierCondition | IPalierCondition[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PalierConditionService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      code: 'AAAAAAA',
      libEn: 'AAAAAAA',
      libAr: 'AAAAAAA',
      annee: 0,
      minVal: 0,
      maxVal: 0,
      util: 'AAAAAAA',
      dateop: currentDate,
      dateModif: currentDate,
      modifiedBy: 'AAAAAAA',
      op: 'AAAAAAA',
      isDeleted: false,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateop: currentDate.format(DATE_TIME_FORMAT),
          dateModif: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a PalierCondition', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateop: currentDate.format(DATE_TIME_FORMAT),
          dateModif: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateop: currentDate,
          dateModif: currentDate,
        },
        returnedFromService
      );

      service.create(new PalierCondition()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PalierCondition', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          libEn: 'BBBBBB',
          libAr: 'BBBBBB',
          annee: 1,
          minVal: 1,
          maxVal: 1,
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          dateModif: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateop: currentDate,
          dateModif: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PalierCondition', () => {
      const patchObject = Object.assign(
        {
          code: 'BBBBBB',
          libEn: 'BBBBBB',
          annee: 1,
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
        },
        new PalierCondition()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateop: currentDate,
          dateModif: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PalierCondition', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          libEn: 'BBBBBB',
          libAr: 'BBBBBB',
          annee: 1,
          minVal: 1,
          maxVal: 1,
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          dateModif: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateop: currentDate,
          dateModif: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a PalierCondition', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPalierConditionToCollectionIfMissing', () => {
      it('should add a PalierCondition to an empty array', () => {
        const palierCondition: IPalierCondition = { id: 123 };
        expectedResult = service.addPalierConditionToCollectionIfMissing([], palierCondition);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(palierCondition);
      });

      it('should not add a PalierCondition to an array that contains it', () => {
        const palierCondition: IPalierCondition = { id: 123 };
        const palierConditionCollection: IPalierCondition[] = [
          {
            ...palierCondition,
          },
          { id: 456 },
        ];
        expectedResult = service.addPalierConditionToCollectionIfMissing(palierConditionCollection, palierCondition);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PalierCondition to an array that doesn't contain it", () => {
        const palierCondition: IPalierCondition = { id: 123 };
        const palierConditionCollection: IPalierCondition[] = [{ id: 456 }];
        expectedResult = service.addPalierConditionToCollectionIfMissing(palierConditionCollection, palierCondition);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(palierCondition);
      });

      it('should add only unique PalierCondition to an array', () => {
        const palierConditionArray: IPalierCondition[] = [{ id: 123 }, { id: 456 }, { id: 19228 }];
        const palierConditionCollection: IPalierCondition[] = [{ id: 123 }];
        expectedResult = service.addPalierConditionToCollectionIfMissing(palierConditionCollection, ...palierConditionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const palierCondition: IPalierCondition = { id: 123 };
        const palierCondition2: IPalierCondition = { id: 456 };
        expectedResult = service.addPalierConditionToCollectionIfMissing([], palierCondition, palierCondition2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(palierCondition);
        expect(expectedResult).toContain(palierCondition2);
      });

      it('should accept null and undefined values', () => {
        const palierCondition: IPalierCondition = { id: 123 };
        expectedResult = service.addPalierConditionToCollectionIfMissing([], null, palierCondition, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(palierCondition);
      });

      it('should return initial array if no PalierCondition is added', () => {
        const palierConditionCollection: IPalierCondition[] = [{ id: 123 }];
        expectedResult = service.addPalierConditionToCollectionIfMissing(palierConditionCollection, undefined, null);
        expect(expectedResult).toEqual(palierConditionCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
