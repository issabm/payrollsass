import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPalierPlate, PalierPlate } from '../palier-plate.model';

import { PalierPlateService } from './palier-plate.service';

describe('PalierPlate Service', () => {
  let service: PalierPlateService;
  let httpMock: HttpTestingController;
  let elemDefault: IPalierPlate;
  let expectedResult: IPalierPlate | IPalierPlate[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PalierPlateService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      code: 'AAAAAAA',
      libEn: 'AAAAAAA',
      libAr: 'AAAAAAA',
      annee: 0,
      effectiValue: 0,
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

    it('should create a PalierPlate', () => {
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

      service.create(new PalierPlate()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PalierPlate', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          libEn: 'BBBBBB',
          libAr: 'BBBBBB',
          annee: 1,
          effectiValue: 1,
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

    it('should partial update a PalierPlate', () => {
      const patchObject = Object.assign(
        {
          libEn: 'BBBBBB',
          libAr: 'BBBBBB',
          annee: 1,
          effectiValue: 1,
          dateop: currentDate.format(DATE_TIME_FORMAT),
          dateModif: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
        },
        new PalierPlate()
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

    it('should return a list of PalierPlate', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          libEn: 'BBBBBB',
          libAr: 'BBBBBB',
          annee: 1,
          effectiValue: 1,
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

    it('should delete a PalierPlate', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPalierPlateToCollectionIfMissing', () => {
      it('should add a PalierPlate to an empty array', () => {
        const palierPlate: IPalierPlate = { id: 123 };
        expectedResult = service.addPalierPlateToCollectionIfMissing([], palierPlate);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(palierPlate);
      });

      it('should not add a PalierPlate to an array that contains it', () => {
        const palierPlate: IPalierPlate = { id: 123 };
        const palierPlateCollection: IPalierPlate[] = [
          {
            ...palierPlate,
          },
          { id: 456 },
        ];
        expectedResult = service.addPalierPlateToCollectionIfMissing(palierPlateCollection, palierPlate);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PalierPlate to an array that doesn't contain it", () => {
        const palierPlate: IPalierPlate = { id: 123 };
        const palierPlateCollection: IPalierPlate[] = [{ id: 456 }];
        expectedResult = service.addPalierPlateToCollectionIfMissing(palierPlateCollection, palierPlate);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(palierPlate);
      });

      it('should add only unique PalierPlate to an array', () => {
        const palierPlateArray: IPalierPlate[] = [{ id: 123 }, { id: 456 }, { id: 48630 }];
        const palierPlateCollection: IPalierPlate[] = [{ id: 123 }];
        expectedResult = service.addPalierPlateToCollectionIfMissing(palierPlateCollection, ...palierPlateArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const palierPlate: IPalierPlate = { id: 123 };
        const palierPlate2: IPalierPlate = { id: 456 };
        expectedResult = service.addPalierPlateToCollectionIfMissing([], palierPlate, palierPlate2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(palierPlate);
        expect(expectedResult).toContain(palierPlate2);
      });

      it('should accept null and undefined values', () => {
        const palierPlate: IPalierPlate = { id: 123 };
        expectedResult = service.addPalierPlateToCollectionIfMissing([], null, palierPlate, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(palierPlate);
      });

      it('should return initial array if no PalierPlate is added', () => {
        const palierPlateCollection: IPalierPlate[] = [{ id: 123 }];
        expectedResult = service.addPalierPlateToCollectionIfMissing(palierPlateCollection, undefined, null);
        expect(expectedResult).toEqual(palierPlateCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
