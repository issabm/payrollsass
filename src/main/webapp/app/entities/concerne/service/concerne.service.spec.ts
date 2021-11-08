import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IConcerne, Concerne } from '../concerne.model';

import { ConcerneService } from './concerne.service';

describe('Concerne Service', () => {
  let service: ConcerneService;
  let httpMock: HttpTestingController;
  let elemDefault: IConcerne;
  let expectedResult: IConcerne | IConcerne[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ConcerneService);
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

    it('should create a Concerne', () => {
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

      service.create(new Concerne()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Concerne', () => {
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

    it('should partial update a Concerne', () => {
      const patchObject = Object.assign(
        {
          libAr: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new Concerne()
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

    it('should return a list of Concerne', () => {
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

    it('should delete a Concerne', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addConcerneToCollectionIfMissing', () => {
      it('should add a Concerne to an empty array', () => {
        const concerne: IConcerne = { id: 123 };
        expectedResult = service.addConcerneToCollectionIfMissing([], concerne);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(concerne);
      });

      it('should not add a Concerne to an array that contains it', () => {
        const concerne: IConcerne = { id: 123 };
        const concerneCollection: IConcerne[] = [
          {
            ...concerne,
          },
          { id: 456 },
        ];
        expectedResult = service.addConcerneToCollectionIfMissing(concerneCollection, concerne);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Concerne to an array that doesn't contain it", () => {
        const concerne: IConcerne = { id: 123 };
        const concerneCollection: IConcerne[] = [{ id: 456 }];
        expectedResult = service.addConcerneToCollectionIfMissing(concerneCollection, concerne);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(concerne);
      });

      it('should add only unique Concerne to an array', () => {
        const concerneArray: IConcerne[] = [{ id: 123 }, { id: 456 }, { id: 37074 }];
        const concerneCollection: IConcerne[] = [{ id: 123 }];
        expectedResult = service.addConcerneToCollectionIfMissing(concerneCollection, ...concerneArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const concerne: IConcerne = { id: 123 };
        const concerne2: IConcerne = { id: 456 };
        expectedResult = service.addConcerneToCollectionIfMissing([], concerne, concerne2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(concerne);
        expect(expectedResult).toContain(concerne2);
      });

      it('should accept null and undefined values', () => {
        const concerne: IConcerne = { id: 123 };
        expectedResult = service.addConcerneToCollectionIfMissing([], null, concerne, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(concerne);
      });

      it('should return initial array if no Concerne is added', () => {
        const concerneCollection: IConcerne[] = [{ id: 123 }];
        expectedResult = service.addConcerneToCollectionIfMissing(concerneCollection, undefined, null);
        expect(expectedResult).toEqual(concerneCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
