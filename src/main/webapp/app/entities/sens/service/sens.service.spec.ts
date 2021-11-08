import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISens, Sens } from '../sens.model';

import { SensService } from './sens.service';

describe('Sens Service', () => {
  let service: SensService;
  let httpMock: HttpTestingController;
  let elemDefault: ISens;
  let expectedResult: ISens | ISens[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SensService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
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

    it('should create a Sens', () => {
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

      service.create(new Sens()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Sens', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
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

    it('should partial update a Sens', () => {
      const patchObject = Object.assign(
        {
          code: 'BBBBBB',
          libEn: 'BBBBBB',
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new Sens()
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

    it('should return a list of Sens', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
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

    it('should delete a Sens', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSensToCollectionIfMissing', () => {
      it('should add a Sens to an empty array', () => {
        const sens: ISens = { id: 123 };
        expectedResult = service.addSensToCollectionIfMissing([], sens);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sens);
      });

      it('should not add a Sens to an array that contains it', () => {
        const sens: ISens = { id: 123 };
        const sensCollection: ISens[] = [
          {
            ...sens,
          },
          { id: 456 },
        ];
        expectedResult = service.addSensToCollectionIfMissing(sensCollection, sens);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Sens to an array that doesn't contain it", () => {
        const sens: ISens = { id: 123 };
        const sensCollection: ISens[] = [{ id: 456 }];
        expectedResult = service.addSensToCollectionIfMissing(sensCollection, sens);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sens);
      });

      it('should add only unique Sens to an array', () => {
        const sensArray: ISens[] = [{ id: 123 }, { id: 456 }, { id: 36212 }];
        const sensCollection: ISens[] = [{ id: 123 }];
        expectedResult = service.addSensToCollectionIfMissing(sensCollection, ...sensArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sens: ISens = { id: 123 };
        const sens2: ISens = { id: 456 };
        expectedResult = service.addSensToCollectionIfMissing([], sens, sens2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sens);
        expect(expectedResult).toContain(sens2);
      });

      it('should accept null and undefined values', () => {
        const sens: ISens = { id: 123 };
        expectedResult = service.addSensToCollectionIfMissing([], null, sens, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sens);
      });

      it('should return initial array if no Sens is added', () => {
        const sensCollection: ISens[] = [{ id: 123 }];
        expectedResult = service.addSensToCollectionIfMissing(sensCollection, undefined, null);
        expect(expectedResult).toEqual(sensCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
