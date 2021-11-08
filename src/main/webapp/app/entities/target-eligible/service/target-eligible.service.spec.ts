import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITargetEligible, TargetEligible } from '../target-eligible.model';

import { TargetEligibleService } from './target-eligible.service';

describe('TargetEligible Service', () => {
  let service: TargetEligibleService;
  let httpMock: HttpTestingController;
  let elemDefault: ITargetEligible;
  let expectedResult: ITargetEligible | ITargetEligible[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TargetEligibleService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      code: 'AAAAAAA',
      libEn: 'AAAAAAA',
      libAr: 'AAAAAAA',
      libFr: 'AAAAAAA',
      dateop: currentDate,
      modifiedBy: 'AAAAAAA',
      createdBy: 'AAAAAAA',
      op: 'AAAAAAA',
      util: 'AAAAAAA',
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

    it('should create a TargetEligible', () => {
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

      service.create(new TargetEligible()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TargetEligible', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          libEn: 'BBBBBB',
          libAr: 'BBBBBB',
          libFr: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
          op: 'BBBBBB',
          util: 'BBBBBB',
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

    it('should partial update a TargetEligible', () => {
      const patchObject = Object.assign(
        {
          dateop: currentDate.format(DATE_TIME_FORMAT),
          op: 'BBBBBB',
          isDeleted: true,
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new TargetEligible()
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

    it('should return a list of TargetEligible', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          libEn: 'BBBBBB',
          libAr: 'BBBBBB',
          libFr: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
          op: 'BBBBBB',
          util: 'BBBBBB',
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

    it('should delete a TargetEligible', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTargetEligibleToCollectionIfMissing', () => {
      it('should add a TargetEligible to an empty array', () => {
        const targetEligible: ITargetEligible = { id: 123 };
        expectedResult = service.addTargetEligibleToCollectionIfMissing([], targetEligible);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(targetEligible);
      });

      it('should not add a TargetEligible to an array that contains it', () => {
        const targetEligible: ITargetEligible = { id: 123 };
        const targetEligibleCollection: ITargetEligible[] = [
          {
            ...targetEligible,
          },
          { id: 456 },
        ];
        expectedResult = service.addTargetEligibleToCollectionIfMissing(targetEligibleCollection, targetEligible);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TargetEligible to an array that doesn't contain it", () => {
        const targetEligible: ITargetEligible = { id: 123 };
        const targetEligibleCollection: ITargetEligible[] = [{ id: 456 }];
        expectedResult = service.addTargetEligibleToCollectionIfMissing(targetEligibleCollection, targetEligible);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(targetEligible);
      });

      it('should add only unique TargetEligible to an array', () => {
        const targetEligibleArray: ITargetEligible[] = [{ id: 123 }, { id: 456 }, { id: 81457 }];
        const targetEligibleCollection: ITargetEligible[] = [{ id: 123 }];
        expectedResult = service.addTargetEligibleToCollectionIfMissing(targetEligibleCollection, ...targetEligibleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const targetEligible: ITargetEligible = { id: 123 };
        const targetEligible2: ITargetEligible = { id: 456 };
        expectedResult = service.addTargetEligibleToCollectionIfMissing([], targetEligible, targetEligible2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(targetEligible);
        expect(expectedResult).toContain(targetEligible2);
      });

      it('should accept null and undefined values', () => {
        const targetEligible: ITargetEligible = { id: 123 };
        expectedResult = service.addTargetEligibleToCollectionIfMissing([], null, targetEligible, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(targetEligible);
      });

      it('should return initial array if no TargetEligible is added', () => {
        const targetEligibleCollection: ITargetEligible[] = [{ id: 123 }];
        expectedResult = service.addTargetEligibleToCollectionIfMissing(targetEligibleCollection, undefined, null);
        expect(expectedResult).toEqual(targetEligibleCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
