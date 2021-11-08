import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPlate, Plate } from '../plate.model';

import { PlateService } from './plate.service';

describe('Plate Service', () => {
  let service: PlateService;
  let httpMock: HttpTestingController;
  let elemDefault: IPlate;
  let expectedResult: IPlate | IPlate[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PlateService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      priorite: 0,
      valueCalcul: 0,
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

    it('should create a Plate', () => {
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

      service.create(new Plate()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Plate', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          priorite: 1,
          valueCalcul: 1,
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

    it('should partial update a Plate', () => {
      const patchObject = Object.assign(
        {
          priorite: 1,
          code: 'BBBBBB',
          libEn: 'BBBBBB',
          util: 'BBBBBB',
          modifiedBy: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new Plate()
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

    it('should return a list of Plate', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          priorite: 1,
          valueCalcul: 1,
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

    it('should delete a Plate', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPlateToCollectionIfMissing', () => {
      it('should add a Plate to an empty array', () => {
        const plate: IPlate = { id: 123 };
        expectedResult = service.addPlateToCollectionIfMissing([], plate);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(plate);
      });

      it('should not add a Plate to an array that contains it', () => {
        const plate: IPlate = { id: 123 };
        const plateCollection: IPlate[] = [
          {
            ...plate,
          },
          { id: 456 },
        ];
        expectedResult = service.addPlateToCollectionIfMissing(plateCollection, plate);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Plate to an array that doesn't contain it", () => {
        const plate: IPlate = { id: 123 };
        const plateCollection: IPlate[] = [{ id: 456 }];
        expectedResult = service.addPlateToCollectionIfMissing(plateCollection, plate);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(plate);
      });

      it('should add only unique Plate to an array', () => {
        const plateArray: IPlate[] = [{ id: 123 }, { id: 456 }, { id: 37642 }];
        const plateCollection: IPlate[] = [{ id: 123 }];
        expectedResult = service.addPlateToCollectionIfMissing(plateCollection, ...plateArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const plate: IPlate = { id: 123 };
        const plate2: IPlate = { id: 456 };
        expectedResult = service.addPlateToCollectionIfMissing([], plate, plate2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(plate);
        expect(expectedResult).toContain(plate2);
      });

      it('should accept null and undefined values', () => {
        const plate: IPlate = { id: 123 };
        expectedResult = service.addPlateToCollectionIfMissing([], null, plate, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(plate);
      });

      it('should return initial array if no Plate is added', () => {
        const plateCollection: IPlate[] = [{ id: 123 }];
        expectedResult = service.addPlateToCollectionIfMissing(plateCollection, undefined, null);
        expect(expectedResult).toEqual(plateCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
