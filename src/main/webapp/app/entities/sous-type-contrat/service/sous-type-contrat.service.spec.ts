import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISousTypeContrat, SousTypeContrat } from '../sous-type-contrat.model';

import { SousTypeContratService } from './sous-type-contrat.service';

describe('SousTypeContrat Service', () => {
  let service: SousTypeContratService;
  let httpMock: HttpTestingController;
  let elemDefault: ISousTypeContrat;
  let expectedResult: ISousTypeContrat | ISousTypeContrat[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SousTypeContratService);
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

    it('should create a SousTypeContrat', () => {
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

      service.create(new SousTypeContrat()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SousTypeContrat', () => {
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

    it('should partial update a SousTypeContrat', () => {
      const patchObject = Object.assign(
        {
          libAr: 'BBBBBB',
          libEn: 'BBBBBB',
          util: 'BBBBBB',
          modifiedBy: 'BBBBBB',
          op: 'BBBBBB',
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new SousTypeContrat()
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

    it('should return a list of SousTypeContrat', () => {
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

    it('should delete a SousTypeContrat', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSousTypeContratToCollectionIfMissing', () => {
      it('should add a SousTypeContrat to an empty array', () => {
        const sousTypeContrat: ISousTypeContrat = { id: 123 };
        expectedResult = service.addSousTypeContratToCollectionIfMissing([], sousTypeContrat);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sousTypeContrat);
      });

      it('should not add a SousTypeContrat to an array that contains it', () => {
        const sousTypeContrat: ISousTypeContrat = { id: 123 };
        const sousTypeContratCollection: ISousTypeContrat[] = [
          {
            ...sousTypeContrat,
          },
          { id: 456 },
        ];
        expectedResult = service.addSousTypeContratToCollectionIfMissing(sousTypeContratCollection, sousTypeContrat);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SousTypeContrat to an array that doesn't contain it", () => {
        const sousTypeContrat: ISousTypeContrat = { id: 123 };
        const sousTypeContratCollection: ISousTypeContrat[] = [{ id: 456 }];
        expectedResult = service.addSousTypeContratToCollectionIfMissing(sousTypeContratCollection, sousTypeContrat);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sousTypeContrat);
      });

      it('should add only unique SousTypeContrat to an array', () => {
        const sousTypeContratArray: ISousTypeContrat[] = [{ id: 123 }, { id: 456 }, { id: 34726 }];
        const sousTypeContratCollection: ISousTypeContrat[] = [{ id: 123 }];
        expectedResult = service.addSousTypeContratToCollectionIfMissing(sousTypeContratCollection, ...sousTypeContratArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sousTypeContrat: ISousTypeContrat = { id: 123 };
        const sousTypeContrat2: ISousTypeContrat = { id: 456 };
        expectedResult = service.addSousTypeContratToCollectionIfMissing([], sousTypeContrat, sousTypeContrat2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sousTypeContrat);
        expect(expectedResult).toContain(sousTypeContrat2);
      });

      it('should accept null and undefined values', () => {
        const sousTypeContrat: ISousTypeContrat = { id: 123 };
        expectedResult = service.addSousTypeContratToCollectionIfMissing([], null, sousTypeContrat, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sousTypeContrat);
      });

      it('should return initial array if no SousTypeContrat is added', () => {
        const sousTypeContratCollection: ISousTypeContrat[] = [{ id: 123 }];
        expectedResult = service.addSousTypeContratToCollectionIfMissing(sousTypeContratCollection, undefined, null);
        expect(expectedResult).toEqual(sousTypeContratCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
