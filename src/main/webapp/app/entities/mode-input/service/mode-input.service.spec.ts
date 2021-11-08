import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IModeInput, ModeInput } from '../mode-input.model';

import { ModeInputService } from './mode-input.service';

describe('ModeInput Service', () => {
  let service: ModeInputService;
  let httpMock: HttpTestingController;
  let elemDefault: IModeInput;
  let expectedResult: IModeInput | IModeInput[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ModeInputService);
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

    it('should create a ModeInput', () => {
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

      service.create(new ModeInput()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ModeInput', () => {
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

    it('should partial update a ModeInput', () => {
      const patchObject = Object.assign(
        {
          code: 'BBBBBB',
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          op: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new ModeInput()
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

    it('should return a list of ModeInput', () => {
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

    it('should delete a ModeInput', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addModeInputToCollectionIfMissing', () => {
      it('should add a ModeInput to an empty array', () => {
        const modeInput: IModeInput = { id: 123 };
        expectedResult = service.addModeInputToCollectionIfMissing([], modeInput);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(modeInput);
      });

      it('should not add a ModeInput to an array that contains it', () => {
        const modeInput: IModeInput = { id: 123 };
        const modeInputCollection: IModeInput[] = [
          {
            ...modeInput,
          },
          { id: 456 },
        ];
        expectedResult = service.addModeInputToCollectionIfMissing(modeInputCollection, modeInput);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ModeInput to an array that doesn't contain it", () => {
        const modeInput: IModeInput = { id: 123 };
        const modeInputCollection: IModeInput[] = [{ id: 456 }];
        expectedResult = service.addModeInputToCollectionIfMissing(modeInputCollection, modeInput);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(modeInput);
      });

      it('should add only unique ModeInput to an array', () => {
        const modeInputArray: IModeInput[] = [{ id: 123 }, { id: 456 }, { id: 45129 }];
        const modeInputCollection: IModeInput[] = [{ id: 123 }];
        expectedResult = service.addModeInputToCollectionIfMissing(modeInputCollection, ...modeInputArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const modeInput: IModeInput = { id: 123 };
        const modeInput2: IModeInput = { id: 456 };
        expectedResult = service.addModeInputToCollectionIfMissing([], modeInput, modeInput2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(modeInput);
        expect(expectedResult).toContain(modeInput2);
      });

      it('should accept null and undefined values', () => {
        const modeInput: IModeInput = { id: 123 };
        expectedResult = service.addModeInputToCollectionIfMissing([], null, modeInput, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(modeInput);
      });

      it('should return initial array if no ModeInput is added', () => {
        const modeInputCollection: IModeInput[] = [{ id: 123 }];
        expectedResult = service.addModeInputToCollectionIfMissing(modeInputCollection, undefined, null);
        expect(expectedResult).toEqual(modeInputCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
