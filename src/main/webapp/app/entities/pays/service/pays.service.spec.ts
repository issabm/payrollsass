import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPays, Pays } from '../pays.model';

import { PaysService } from './pays.service';

describe('Pays Service', () => {
  let service: PaysService;
  let httpMock: HttpTestingController;
  let elemDefault: IPays;
  let expectedResult: IPays | IPays[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PaysService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      code: 'AAAAAAA',
      libAr: 'AAAAAAA',
      libEn: 'AAAAAAA',
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

    it('should create a Pays', () => {
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

      service.create(new Pays()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Pays', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          libAr: 'BBBBBB',
          libEn: 'BBBBBB',
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

    it('should partial update a Pays', () => {
      const patchObject = Object.assign(
        {
          code: 'BBBBBB',
          libEn: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
        },
        new Pays()
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

    it('should return a list of Pays', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          libAr: 'BBBBBB',
          libEn: 'BBBBBB',
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

    it('should delete a Pays', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPaysToCollectionIfMissing', () => {
      it('should add a Pays to an empty array', () => {
        const pays: IPays = { id: 123 };
        expectedResult = service.addPaysToCollectionIfMissing([], pays);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pays);
      });

      it('should not add a Pays to an array that contains it', () => {
        const pays: IPays = { id: 123 };
        const paysCollection: IPays[] = [
          {
            ...pays,
          },
          { id: 456 },
        ];
        expectedResult = service.addPaysToCollectionIfMissing(paysCollection, pays);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Pays to an array that doesn't contain it", () => {
        const pays: IPays = { id: 123 };
        const paysCollection: IPays[] = [{ id: 456 }];
        expectedResult = service.addPaysToCollectionIfMissing(paysCollection, pays);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pays);
      });

      it('should add only unique Pays to an array', () => {
        const paysArray: IPays[] = [{ id: 123 }, { id: 456 }, { id: 88461 }];
        const paysCollection: IPays[] = [{ id: 123 }];
        expectedResult = service.addPaysToCollectionIfMissing(paysCollection, ...paysArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pays: IPays = { id: 123 };
        const pays2: IPays = { id: 456 };
        expectedResult = service.addPaysToCollectionIfMissing([], pays, pays2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pays);
        expect(expectedResult).toContain(pays2);
      });

      it('should accept null and undefined values', () => {
        const pays: IPays = { id: 123 };
        expectedResult = service.addPaysToCollectionIfMissing([], null, pays, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pays);
      });

      it('should return initial array if no Pays is added', () => {
        const paysCollection: IPays[] = [{ id: 123 }];
        expectedResult = service.addPaysToCollectionIfMissing(paysCollection, undefined, null);
        expect(expectedResult).toEqual(paysCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
