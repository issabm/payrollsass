import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEmploi, Emploi } from '../emploi.model';

import { EmploiService } from './emploi.service';

describe('Emploi Service', () => {
  let service: EmploiService;
  let httpMock: HttpTestingController;
  let elemDefault: IEmploi;
  let expectedResult: IEmploi | IEmploi[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EmploiService);
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

    it('should create a Emploi', () => {
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

      service.create(new Emploi()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Emploi', () => {
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

    it('should partial update a Emploi', () => {
      const patchObject = Object.assign(
        {
          dateop: currentDate.format(DATE_TIME_FORMAT),
          op: 'BBBBBB',
        },
        new Emploi()
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

    it('should return a list of Emploi', () => {
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

    it('should delete a Emploi', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addEmploiToCollectionIfMissing', () => {
      it('should add a Emploi to an empty array', () => {
        const emploi: IEmploi = { id: 123 };
        expectedResult = service.addEmploiToCollectionIfMissing([], emploi);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(emploi);
      });

      it('should not add a Emploi to an array that contains it', () => {
        const emploi: IEmploi = { id: 123 };
        const emploiCollection: IEmploi[] = [
          {
            ...emploi,
          },
          { id: 456 },
        ];
        expectedResult = service.addEmploiToCollectionIfMissing(emploiCollection, emploi);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Emploi to an array that doesn't contain it", () => {
        const emploi: IEmploi = { id: 123 };
        const emploiCollection: IEmploi[] = [{ id: 456 }];
        expectedResult = service.addEmploiToCollectionIfMissing(emploiCollection, emploi);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(emploi);
      });

      it('should add only unique Emploi to an array', () => {
        const emploiArray: IEmploi[] = [{ id: 123 }, { id: 456 }, { id: 50694 }];
        const emploiCollection: IEmploi[] = [{ id: 123 }];
        expectedResult = service.addEmploiToCollectionIfMissing(emploiCollection, ...emploiArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const emploi: IEmploi = { id: 123 };
        const emploi2: IEmploi = { id: 456 };
        expectedResult = service.addEmploiToCollectionIfMissing([], emploi, emploi2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(emploi);
        expect(expectedResult).toContain(emploi2);
      });

      it('should accept null and undefined values', () => {
        const emploi: IEmploi = { id: 123 };
        expectedResult = service.addEmploiToCollectionIfMissing([], null, emploi, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(emploi);
      });

      it('should return initial array if no Emploi is added', () => {
        const emploiCollection: IEmploi[] = [{ id: 123 }];
        expectedResult = service.addEmploiToCollectionIfMissing(emploiCollection, undefined, null);
        expect(expectedResult).toEqual(emploiCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
