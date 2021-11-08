import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDevise, Devise } from '../devise.model';

import { DeviseService } from './devise.service';

describe('Devise Service', () => {
  let service: DeviseService;
  let httpMock: HttpTestingController;
  let elemDefault: IDevise;
  let expectedResult: IDevise | IDevise[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DeviseService);
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

    it('should create a Devise', () => {
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

      service.create(new Devise()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Devise', () => {
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

    it('should partial update a Devise', () => {
      const patchObject = Object.assign(
        {
          libEn: 'BBBBBB',
          util: 'BBBBBB',
          modifiedBy: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
        },
        new Devise()
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

    it('should return a list of Devise', () => {
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

    it('should delete a Devise', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDeviseToCollectionIfMissing', () => {
      it('should add a Devise to an empty array', () => {
        const devise: IDevise = { id: 123 };
        expectedResult = service.addDeviseToCollectionIfMissing([], devise);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(devise);
      });

      it('should not add a Devise to an array that contains it', () => {
        const devise: IDevise = { id: 123 };
        const deviseCollection: IDevise[] = [
          {
            ...devise,
          },
          { id: 456 },
        ];
        expectedResult = service.addDeviseToCollectionIfMissing(deviseCollection, devise);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Devise to an array that doesn't contain it", () => {
        const devise: IDevise = { id: 123 };
        const deviseCollection: IDevise[] = [{ id: 456 }];
        expectedResult = service.addDeviseToCollectionIfMissing(deviseCollection, devise);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(devise);
      });

      it('should add only unique Devise to an array', () => {
        const deviseArray: IDevise[] = [{ id: 123 }, { id: 456 }, { id: 34285 }];
        const deviseCollection: IDevise[] = [{ id: 123 }];
        expectedResult = service.addDeviseToCollectionIfMissing(deviseCollection, ...deviseArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const devise: IDevise = { id: 123 };
        const devise2: IDevise = { id: 456 };
        expectedResult = service.addDeviseToCollectionIfMissing([], devise, devise2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(devise);
        expect(expectedResult).toContain(devise2);
      });

      it('should accept null and undefined values', () => {
        const devise: IDevise = { id: 123 };
        expectedResult = service.addDeviseToCollectionIfMissing([], null, devise, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(devise);
      });

      it('should return initial array if no Devise is added', () => {
        const deviseCollection: IDevise[] = [{ id: 123 }];
        expectedResult = service.addDeviseToCollectionIfMissing(deviseCollection, undefined, null);
        expect(expectedResult).toEqual(deviseCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
