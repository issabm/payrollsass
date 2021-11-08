import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISexe, Sexe } from '../sexe.model';

import { SexeService } from './sexe.service';

describe('Sexe Service', () => {
  let service: SexeService;
  let httpMock: HttpTestingController;
  let elemDefault: ISexe;
  let expectedResult: ISexe | ISexe[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SexeService);
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

    it('should create a Sexe', () => {
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

      service.create(new Sexe()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Sexe', () => {
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

    it('should partial update a Sexe', () => {
      const patchObject = Object.assign(
        {
          code: 'BBBBBB',
          libAr: 'BBBBBB',
          libEn: 'BBBBBB',
          modifiedBy: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new Sexe()
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

    it('should return a list of Sexe', () => {
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

    it('should delete a Sexe', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSexeToCollectionIfMissing', () => {
      it('should add a Sexe to an empty array', () => {
        const sexe: ISexe = { id: 123 };
        expectedResult = service.addSexeToCollectionIfMissing([], sexe);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sexe);
      });

      it('should not add a Sexe to an array that contains it', () => {
        const sexe: ISexe = { id: 123 };
        const sexeCollection: ISexe[] = [
          {
            ...sexe,
          },
          { id: 456 },
        ];
        expectedResult = service.addSexeToCollectionIfMissing(sexeCollection, sexe);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Sexe to an array that doesn't contain it", () => {
        const sexe: ISexe = { id: 123 };
        const sexeCollection: ISexe[] = [{ id: 456 }];
        expectedResult = service.addSexeToCollectionIfMissing(sexeCollection, sexe);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sexe);
      });

      it('should add only unique Sexe to an array', () => {
        const sexeArray: ISexe[] = [{ id: 123 }, { id: 456 }, { id: 4155 }];
        const sexeCollection: ISexe[] = [{ id: 123 }];
        expectedResult = service.addSexeToCollectionIfMissing(sexeCollection, ...sexeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sexe: ISexe = { id: 123 };
        const sexe2: ISexe = { id: 456 };
        expectedResult = service.addSexeToCollectionIfMissing([], sexe, sexe2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sexe);
        expect(expectedResult).toContain(sexe2);
      });

      it('should accept null and undefined values', () => {
        const sexe: ISexe = { id: 123 };
        expectedResult = service.addSexeToCollectionIfMissing([], null, sexe, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sexe);
      });

      it('should return initial array if no Sexe is added', () => {
        const sexeCollection: ISexe[] = [{ id: 123 }];
        expectedResult = service.addSexeToCollectionIfMissing(sexeCollection, undefined, null);
        expect(expectedResult).toEqual(sexeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
