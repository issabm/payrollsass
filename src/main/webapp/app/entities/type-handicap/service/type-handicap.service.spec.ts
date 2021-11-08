import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITypeHandicap, TypeHandicap } from '../type-handicap.model';

import { TypeHandicapService } from './type-handicap.service';

describe('TypeHandicap Service', () => {
  let service: TypeHandicapService;
  let httpMock: HttpTestingController;
  let elemDefault: ITypeHandicap;
  let expectedResult: ITypeHandicap | ITypeHandicap[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TypeHandicapService);
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

    it('should create a TypeHandicap', () => {
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

      service.create(new TypeHandicap()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TypeHandicap', () => {
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

    it('should partial update a TypeHandicap', () => {
      const patchObject = Object.assign(
        {
          code: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new TypeHandicap()
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

    it('should return a list of TypeHandicap', () => {
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

    it('should delete a TypeHandicap', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTypeHandicapToCollectionIfMissing', () => {
      it('should add a TypeHandicap to an empty array', () => {
        const typeHandicap: ITypeHandicap = { id: 123 };
        expectedResult = service.addTypeHandicapToCollectionIfMissing([], typeHandicap);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(typeHandicap);
      });

      it('should not add a TypeHandicap to an array that contains it', () => {
        const typeHandicap: ITypeHandicap = { id: 123 };
        const typeHandicapCollection: ITypeHandicap[] = [
          {
            ...typeHandicap,
          },
          { id: 456 },
        ];
        expectedResult = service.addTypeHandicapToCollectionIfMissing(typeHandicapCollection, typeHandicap);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TypeHandicap to an array that doesn't contain it", () => {
        const typeHandicap: ITypeHandicap = { id: 123 };
        const typeHandicapCollection: ITypeHandicap[] = [{ id: 456 }];
        expectedResult = service.addTypeHandicapToCollectionIfMissing(typeHandicapCollection, typeHandicap);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(typeHandicap);
      });

      it('should add only unique TypeHandicap to an array', () => {
        const typeHandicapArray: ITypeHandicap[] = [{ id: 123 }, { id: 456 }, { id: 54495 }];
        const typeHandicapCollection: ITypeHandicap[] = [{ id: 123 }];
        expectedResult = service.addTypeHandicapToCollectionIfMissing(typeHandicapCollection, ...typeHandicapArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const typeHandicap: ITypeHandicap = { id: 123 };
        const typeHandicap2: ITypeHandicap = { id: 456 };
        expectedResult = service.addTypeHandicapToCollectionIfMissing([], typeHandicap, typeHandicap2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(typeHandicap);
        expect(expectedResult).toContain(typeHandicap2);
      });

      it('should accept null and undefined values', () => {
        const typeHandicap: ITypeHandicap = { id: 123 };
        expectedResult = service.addTypeHandicapToCollectionIfMissing([], null, typeHandicap, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(typeHandicap);
      });

      it('should return initial array if no TypeHandicap is added', () => {
        const typeHandicapCollection: ITypeHandicap[] = [{ id: 123 }];
        expectedResult = service.addTypeHandicapToCollectionIfMissing(typeHandicapCollection, undefined, null);
        expect(expectedResult).toEqual(typeHandicapCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
