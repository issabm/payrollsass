import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEchlon, Echlon } from '../echlon.model';

import { EchlonService } from './echlon.service';

describe('Echlon Service', () => {
  let service: EchlonService;
  let httpMock: HttpTestingController;
  let elemDefault: IEchlon;
  let expectedResult: IEchlon | IEchlon[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EchlonService);
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

    it('should create a Echlon', () => {
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

      service.create(new Echlon()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Echlon', () => {
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

    it('should partial update a Echlon', () => {
      const patchObject = Object.assign(
        {
          libEn: 'BBBBBB',
          isDeleted: true,
        },
        new Echlon()
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

    it('should return a list of Echlon', () => {
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

    it('should delete a Echlon', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addEchlonToCollectionIfMissing', () => {
      it('should add a Echlon to an empty array', () => {
        const echlon: IEchlon = { id: 123 };
        expectedResult = service.addEchlonToCollectionIfMissing([], echlon);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(echlon);
      });

      it('should not add a Echlon to an array that contains it', () => {
        const echlon: IEchlon = { id: 123 };
        const echlonCollection: IEchlon[] = [
          {
            ...echlon,
          },
          { id: 456 },
        ];
        expectedResult = service.addEchlonToCollectionIfMissing(echlonCollection, echlon);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Echlon to an array that doesn't contain it", () => {
        const echlon: IEchlon = { id: 123 };
        const echlonCollection: IEchlon[] = [{ id: 456 }];
        expectedResult = service.addEchlonToCollectionIfMissing(echlonCollection, echlon);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(echlon);
      });

      it('should add only unique Echlon to an array', () => {
        const echlonArray: IEchlon[] = [{ id: 123 }, { id: 456 }, { id: 99855 }];
        const echlonCollection: IEchlon[] = [{ id: 123 }];
        expectedResult = service.addEchlonToCollectionIfMissing(echlonCollection, ...echlonArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const echlon: IEchlon = { id: 123 };
        const echlon2: IEchlon = { id: 456 };
        expectedResult = service.addEchlonToCollectionIfMissing([], echlon, echlon2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(echlon);
        expect(expectedResult).toContain(echlon2);
      });

      it('should accept null and undefined values', () => {
        const echlon: IEchlon = { id: 123 };
        expectedResult = service.addEchlonToCollectionIfMissing([], null, echlon, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(echlon);
      });

      it('should return initial array if no Echlon is added', () => {
        const echlonCollection: IEchlon[] = [{ id: 123 }];
        expectedResult = service.addEchlonToCollectionIfMissing(echlonCollection, undefined, null);
        expect(expectedResult).toEqual(echlonCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
