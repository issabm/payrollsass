import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IFrequence, Frequence } from '../frequence.model';

import { FrequenceService } from './frequence.service';

describe('Frequence Service', () => {
  let service: FrequenceService;
  let httpMock: HttpTestingController;
  let elemDefault: IFrequence;
  let expectedResult: IFrequence | IFrequence[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FrequenceService);
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

    it('should create a Frequence', () => {
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

      service.create(new Frequence()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Frequence', () => {
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

    it('should partial update a Frequence', () => {
      const patchObject = Object.assign(
        {
          code: 'BBBBBB',
          libAr: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new Frequence()
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

    it('should return a list of Frequence', () => {
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

    it('should delete a Frequence', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addFrequenceToCollectionIfMissing', () => {
      it('should add a Frequence to an empty array', () => {
        const frequence: IFrequence = { id: 123 };
        expectedResult = service.addFrequenceToCollectionIfMissing([], frequence);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(frequence);
      });

      it('should not add a Frequence to an array that contains it', () => {
        const frequence: IFrequence = { id: 123 };
        const frequenceCollection: IFrequence[] = [
          {
            ...frequence,
          },
          { id: 456 },
        ];
        expectedResult = service.addFrequenceToCollectionIfMissing(frequenceCollection, frequence);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Frequence to an array that doesn't contain it", () => {
        const frequence: IFrequence = { id: 123 };
        const frequenceCollection: IFrequence[] = [{ id: 456 }];
        expectedResult = service.addFrequenceToCollectionIfMissing(frequenceCollection, frequence);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(frequence);
      });

      it('should add only unique Frequence to an array', () => {
        const frequenceArray: IFrequence[] = [{ id: 123 }, { id: 456 }, { id: 69926 }];
        const frequenceCollection: IFrequence[] = [{ id: 123 }];
        expectedResult = service.addFrequenceToCollectionIfMissing(frequenceCollection, ...frequenceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const frequence: IFrequence = { id: 123 };
        const frequence2: IFrequence = { id: 456 };
        expectedResult = service.addFrequenceToCollectionIfMissing([], frequence, frequence2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(frequence);
        expect(expectedResult).toContain(frequence2);
      });

      it('should accept null and undefined values', () => {
        const frequence: IFrequence = { id: 123 };
        expectedResult = service.addFrequenceToCollectionIfMissing([], null, frequence, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(frequence);
      });

      it('should return initial array if no Frequence is added', () => {
        const frequenceCollection: IFrequence[] = [{ id: 123 }];
        expectedResult = service.addFrequenceToCollectionIfMissing(frequenceCollection, undefined, null);
        expect(expectedResult).toEqual(frequenceCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
