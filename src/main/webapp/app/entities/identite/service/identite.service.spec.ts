import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IIdentite, Identite } from '../identite.model';

import { IdentiteService } from './identite.service';

describe('Identite Service', () => {
  let service: IdentiteService;
  let httpMock: HttpTestingController;
  let elemDefault: IIdentite;
  let expectedResult: IIdentite | IIdentite[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(IdentiteService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      code: 'AAAAAAA',
      dateIssued: currentDate,
      placeIssed: 'AAAAAAA',
      dateVld: currentDate,
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
          dateIssued: currentDate.format(DATE_FORMAT),
          dateVld: currentDate.format(DATE_FORMAT),
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

    it('should create a Identite', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateIssued: currentDate.format(DATE_FORMAT),
          dateVld: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateIssued: currentDate,
          dateVld: currentDate,
          dateop: currentDate,
          createdDate: currentDate,
          modifiedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new Identite()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Identite', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          dateIssued: currentDate.format(DATE_FORMAT),
          placeIssed: 'BBBBBB',
          dateVld: currentDate.format(DATE_FORMAT),
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
          dateIssued: currentDate,
          dateVld: currentDate,
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

    it('should partial update a Identite', () => {
      const patchObject = Object.assign(
        {
          code: 'BBBBBB',
          dateIssued: currentDate.format(DATE_FORMAT),
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          op: 'BBBBBB',
          isDeleted: true,
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new Identite()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateIssued: currentDate,
          dateVld: currentDate,
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

    it('should return a list of Identite', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          dateIssued: currentDate.format(DATE_FORMAT),
          placeIssed: 'BBBBBB',
          dateVld: currentDate.format(DATE_FORMAT),
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
          dateIssued: currentDate,
          dateVld: currentDate,
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

    it('should delete a Identite', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addIdentiteToCollectionIfMissing', () => {
      it('should add a Identite to an empty array', () => {
        const identite: IIdentite = { id: 123 };
        expectedResult = service.addIdentiteToCollectionIfMissing([], identite);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(identite);
      });

      it('should not add a Identite to an array that contains it', () => {
        const identite: IIdentite = { id: 123 };
        const identiteCollection: IIdentite[] = [
          {
            ...identite,
          },
          { id: 456 },
        ];
        expectedResult = service.addIdentiteToCollectionIfMissing(identiteCollection, identite);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Identite to an array that doesn't contain it", () => {
        const identite: IIdentite = { id: 123 };
        const identiteCollection: IIdentite[] = [{ id: 456 }];
        expectedResult = service.addIdentiteToCollectionIfMissing(identiteCollection, identite);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(identite);
      });

      it('should add only unique Identite to an array', () => {
        const identiteArray: IIdentite[] = [{ id: 123 }, { id: 456 }, { id: 80481 }];
        const identiteCollection: IIdentite[] = [{ id: 123 }];
        expectedResult = service.addIdentiteToCollectionIfMissing(identiteCollection, ...identiteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const identite: IIdentite = { id: 123 };
        const identite2: IIdentite = { id: 456 };
        expectedResult = service.addIdentiteToCollectionIfMissing([], identite, identite2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(identite);
        expect(expectedResult).toContain(identite2);
      });

      it('should accept null and undefined values', () => {
        const identite: IIdentite = { id: 123 };
        expectedResult = service.addIdentiteToCollectionIfMissing([], null, identite, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(identite);
      });

      it('should return initial array if no Identite is added', () => {
        const identiteCollection: IIdentite[] = [{ id: 123 }];
        expectedResult = service.addIdentiteToCollectionIfMissing(identiteCollection, undefined, null);
        expect(expectedResult).toEqual(identiteCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
