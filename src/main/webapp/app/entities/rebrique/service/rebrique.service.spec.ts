import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IRebrique, Rebrique } from '../rebrique.model';

import { RebriqueService } from './rebrique.service';

describe('Rebrique Service', () => {
  let service: RebriqueService;
  let httpMock: HttpTestingController;
  let elemDefault: IRebrique;
  let expectedResult: IRebrique | IRebrique[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RebriqueService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      priorite: 0,
      code: 'AAAAAAA',
      libAr: 'AAAAAAA',
      libFr: 'AAAAAAA',
      libEn: 'AAAAAAA',
      inTax: false,
      minValue: 0,
      maxValue: 0,
      dateSituation: currentDate,
      dateop: currentDate,
      modifiedBy: 'AAAAAAA',
      createdBy: 'AAAAAAA',
      util: 'AAAAAAA',
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
          dateSituation: currentDate.format(DATE_FORMAT),
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

    it('should create a Rebrique', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateSituation: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateSituation: currentDate,
          dateop: currentDate,
          createdDate: currentDate,
          modifiedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new Rebrique()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Rebrique', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          priorite: 1,
          code: 'BBBBBB',
          libAr: 'BBBBBB',
          libFr: 'BBBBBB',
          libEn: 'BBBBBB',
          inTax: true,
          minValue: 1,
          maxValue: 1,
          dateSituation: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
          util: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateSituation: currentDate,
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

    it('should partial update a Rebrique', () => {
      const patchObject = Object.assign(
        {
          priorite: 1,
          code: 'BBBBBB',
          inTax: true,
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new Rebrique()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateSituation: currentDate,
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

    it('should return a list of Rebrique', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          priorite: 1,
          code: 'BBBBBB',
          libAr: 'BBBBBB',
          libFr: 'BBBBBB',
          libEn: 'BBBBBB',
          inTax: true,
          minValue: 1,
          maxValue: 1,
          dateSituation: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
          util: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateSituation: currentDate,
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

    it('should delete a Rebrique', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRebriqueToCollectionIfMissing', () => {
      it('should add a Rebrique to an empty array', () => {
        const rebrique: IRebrique = { id: 123 };
        expectedResult = service.addRebriqueToCollectionIfMissing([], rebrique);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(rebrique);
      });

      it('should not add a Rebrique to an array that contains it', () => {
        const rebrique: IRebrique = { id: 123 };
        const rebriqueCollection: IRebrique[] = [
          {
            ...rebrique,
          },
          { id: 456 },
        ];
        expectedResult = service.addRebriqueToCollectionIfMissing(rebriqueCollection, rebrique);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Rebrique to an array that doesn't contain it", () => {
        const rebrique: IRebrique = { id: 123 };
        const rebriqueCollection: IRebrique[] = [{ id: 456 }];
        expectedResult = service.addRebriqueToCollectionIfMissing(rebriqueCollection, rebrique);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(rebrique);
      });

      it('should add only unique Rebrique to an array', () => {
        const rebriqueArray: IRebrique[] = [{ id: 123 }, { id: 456 }, { id: 54151 }];
        const rebriqueCollection: IRebrique[] = [{ id: 123 }];
        expectedResult = service.addRebriqueToCollectionIfMissing(rebriqueCollection, ...rebriqueArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const rebrique: IRebrique = { id: 123 };
        const rebrique2: IRebrique = { id: 456 };
        expectedResult = service.addRebriqueToCollectionIfMissing([], rebrique, rebrique2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(rebrique);
        expect(expectedResult).toContain(rebrique2);
      });

      it('should accept null and undefined values', () => {
        const rebrique: IRebrique = { id: 123 };
        expectedResult = service.addRebriqueToCollectionIfMissing([], null, rebrique, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(rebrique);
      });

      it('should return initial array if no Rebrique is added', () => {
        const rebriqueCollection: IRebrique[] = [{ id: 123 }];
        expectedResult = service.addRebriqueToCollectionIfMissing(rebriqueCollection, undefined, null);
        expect(expectedResult).toEqual(rebriqueCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
