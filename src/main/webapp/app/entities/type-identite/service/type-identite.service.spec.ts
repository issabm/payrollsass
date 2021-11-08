import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITypeIdentite, TypeIdentite } from '../type-identite.model';

import { TypeIdentiteService } from './type-identite.service';

describe('TypeIdentite Service', () => {
  let service: TypeIdentiteService;
  let httpMock: HttpTestingController;
  let elemDefault: ITypeIdentite;
  let expectedResult: ITypeIdentite | ITypeIdentite[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TypeIdentiteService);
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

    it('should create a TypeIdentite', () => {
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

      service.create(new TypeIdentite()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TypeIdentite', () => {
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

    it('should partial update a TypeIdentite', () => {
      const patchObject = Object.assign(
        {
          code: 'BBBBBB',
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new TypeIdentite()
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

    it('should return a list of TypeIdentite', () => {
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

    it('should delete a TypeIdentite', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTypeIdentiteToCollectionIfMissing', () => {
      it('should add a TypeIdentite to an empty array', () => {
        const typeIdentite: ITypeIdentite = { id: 123 };
        expectedResult = service.addTypeIdentiteToCollectionIfMissing([], typeIdentite);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(typeIdentite);
      });

      it('should not add a TypeIdentite to an array that contains it', () => {
        const typeIdentite: ITypeIdentite = { id: 123 };
        const typeIdentiteCollection: ITypeIdentite[] = [
          {
            ...typeIdentite,
          },
          { id: 456 },
        ];
        expectedResult = service.addTypeIdentiteToCollectionIfMissing(typeIdentiteCollection, typeIdentite);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TypeIdentite to an array that doesn't contain it", () => {
        const typeIdentite: ITypeIdentite = { id: 123 };
        const typeIdentiteCollection: ITypeIdentite[] = [{ id: 456 }];
        expectedResult = service.addTypeIdentiteToCollectionIfMissing(typeIdentiteCollection, typeIdentite);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(typeIdentite);
      });

      it('should add only unique TypeIdentite to an array', () => {
        const typeIdentiteArray: ITypeIdentite[] = [{ id: 123 }, { id: 456 }, { id: 58899 }];
        const typeIdentiteCollection: ITypeIdentite[] = [{ id: 123 }];
        expectedResult = service.addTypeIdentiteToCollectionIfMissing(typeIdentiteCollection, ...typeIdentiteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const typeIdentite: ITypeIdentite = { id: 123 };
        const typeIdentite2: ITypeIdentite = { id: 456 };
        expectedResult = service.addTypeIdentiteToCollectionIfMissing([], typeIdentite, typeIdentite2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(typeIdentite);
        expect(expectedResult).toContain(typeIdentite2);
      });

      it('should accept null and undefined values', () => {
        const typeIdentite: ITypeIdentite = { id: 123 };
        expectedResult = service.addTypeIdentiteToCollectionIfMissing([], null, typeIdentite, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(typeIdentite);
      });

      it('should return initial array if no TypeIdentite is added', () => {
        const typeIdentiteCollection: ITypeIdentite[] = [{ id: 123 }];
        expectedResult = service.addTypeIdentiteToCollectionIfMissing(typeIdentiteCollection, undefined, null);
        expect(expectedResult).toEqual(typeIdentiteCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
