import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISituation, Situation } from '../situation.model';

import { SituationService } from './situation.service';

describe('Situation Service', () => {
  let service: SituationService;
  let httpMock: HttpTestingController;
  let elemDefault: ISituation;
  let expectedResult: ISituation | ISituation[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SituationService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      code: 'AAAAAAA',
      entityTarget: 'AAAAAAA',
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

    it('should create a Situation', () => {
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

      service.create(new Situation()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Situation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          entityTarget: 'BBBBBB',
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

    it('should partial update a Situation', () => {
      const patchObject = Object.assign(
        {
          code: 'BBBBBB',
          entityTarget: 'BBBBBB',
          libEn: 'BBBBBB',
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          op: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new Situation()
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

    it('should return a list of Situation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          entityTarget: 'BBBBBB',
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

    it('should delete a Situation', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSituationToCollectionIfMissing', () => {
      it('should add a Situation to an empty array', () => {
        const situation: ISituation = { id: 123 };
        expectedResult = service.addSituationToCollectionIfMissing([], situation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(situation);
      });

      it('should not add a Situation to an array that contains it', () => {
        const situation: ISituation = { id: 123 };
        const situationCollection: ISituation[] = [
          {
            ...situation,
          },
          { id: 456 },
        ];
        expectedResult = service.addSituationToCollectionIfMissing(situationCollection, situation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Situation to an array that doesn't contain it", () => {
        const situation: ISituation = { id: 123 };
        const situationCollection: ISituation[] = [{ id: 456 }];
        expectedResult = service.addSituationToCollectionIfMissing(situationCollection, situation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(situation);
      });

      it('should add only unique Situation to an array', () => {
        const situationArray: ISituation[] = [{ id: 123 }, { id: 456 }, { id: 89025 }];
        const situationCollection: ISituation[] = [{ id: 123 }];
        expectedResult = service.addSituationToCollectionIfMissing(situationCollection, ...situationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const situation: ISituation = { id: 123 };
        const situation2: ISituation = { id: 456 };
        expectedResult = service.addSituationToCollectionIfMissing([], situation, situation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(situation);
        expect(expectedResult).toContain(situation2);
      });

      it('should accept null and undefined values', () => {
        const situation: ISituation = { id: 123 };
        expectedResult = service.addSituationToCollectionIfMissing([], null, situation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(situation);
      });

      it('should return initial array if no Situation is added', () => {
        const situationCollection: ISituation[] = [{ id: 123 }];
        expectedResult = service.addSituationToCollectionIfMissing(situationCollection, undefined, null);
        expect(expectedResult).toEqual(situationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
