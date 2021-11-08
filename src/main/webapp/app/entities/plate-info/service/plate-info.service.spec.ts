import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPlateInfo, PlateInfo } from '../plate-info.model';

import { PlateInfoService } from './plate-info.service';

describe('PlateInfo Service', () => {
  let service: PlateInfoService;
  let httpMock: HttpTestingController;
  let elemDefault: IPlateInfo;
  let expectedResult: IPlateInfo | IPlateInfo[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PlateInfoService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      code: 'AAAAAAA',
      lib: 'AAAAAAA',
      valTaken: 0,
      dateSituation: currentDate,
      dateop: currentDate,
      modifiedBy: 'AAAAAAA',
      createdBy: 'AAAAAAA',
      op: 'AAAAAAA',
      util: 'AAAAAAA',
      isDeleted: false,
      createdDate: currentDate,
      modifiedDate: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateSituation: currentDate.format(DATE_FORMAT),
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

    it('should create a PlateInfo', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateSituation: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateSituation: currentDate,
          dateop: currentDate,
          createdDate: currentDate,
          modifiedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new PlateInfo()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PlateInfo', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          lib: 'BBBBBB',
          valTaken: 1,
          dateSituation: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
          op: 'BBBBBB',
          util: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateSituation: currentDate,
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

    it('should partial update a PlateInfo', () => {
      const patchObject = Object.assign(
        {
          code: 'BBBBBB',
          lib: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
          util: 'BBBBBB',
          isDeleted: true,
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new PlateInfo()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateSituation: currentDate,
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

    it('should return a list of PlateInfo', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          lib: 'BBBBBB',
          valTaken: 1,
          dateSituation: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
          op: 'BBBBBB',
          util: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateSituation: currentDate,
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

    it('should delete a PlateInfo', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPlateInfoToCollectionIfMissing', () => {
      it('should add a PlateInfo to an empty array', () => {
        const plateInfo: IPlateInfo = { id: 123 };
        expectedResult = service.addPlateInfoToCollectionIfMissing([], plateInfo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(plateInfo);
      });

      it('should not add a PlateInfo to an array that contains it', () => {
        const plateInfo: IPlateInfo = { id: 123 };
        const plateInfoCollection: IPlateInfo[] = [
          {
            ...plateInfo,
          },
          { id: 456 },
        ];
        expectedResult = service.addPlateInfoToCollectionIfMissing(plateInfoCollection, plateInfo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PlateInfo to an array that doesn't contain it", () => {
        const plateInfo: IPlateInfo = { id: 123 };
        const plateInfoCollection: IPlateInfo[] = [{ id: 456 }];
        expectedResult = service.addPlateInfoToCollectionIfMissing(plateInfoCollection, plateInfo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(plateInfo);
      });

      it('should add only unique PlateInfo to an array', () => {
        const plateInfoArray: IPlateInfo[] = [{ id: 123 }, { id: 456 }, { id: 23812 }];
        const plateInfoCollection: IPlateInfo[] = [{ id: 123 }];
        expectedResult = service.addPlateInfoToCollectionIfMissing(plateInfoCollection, ...plateInfoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const plateInfo: IPlateInfo = { id: 123 };
        const plateInfo2: IPlateInfo = { id: 456 };
        expectedResult = service.addPlateInfoToCollectionIfMissing([], plateInfo, plateInfo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(plateInfo);
        expect(expectedResult).toContain(plateInfo2);
      });

      it('should accept null and undefined values', () => {
        const plateInfo: IPlateInfo = { id: 123 };
        expectedResult = service.addPlateInfoToCollectionIfMissing([], null, plateInfo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(plateInfo);
      });

      it('should return initial array if no PlateInfo is added', () => {
        const plateInfoCollection: IPlateInfo[] = [{ id: 123 }];
        expectedResult = service.addPlateInfoToCollectionIfMissing(plateInfoCollection, undefined, null);
        expect(expectedResult).toEqual(plateInfoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
