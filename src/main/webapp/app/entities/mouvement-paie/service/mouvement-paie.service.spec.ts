import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMouvementPaie, MouvementPaie } from '../mouvement-paie.model';

import { MouvementPaieService } from './mouvement-paie.service';

describe('MouvementPaie Service', () => {
  let service: MouvementPaieService;
  let httpMock: HttpTestingController;
  let elemDefault: IMouvementPaie;
  let expectedResult: IMouvementPaie | IMouvementPaie[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MouvementPaieService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      code: 'AAAAAAA',
      lib: 'AAAAAAA',
      annee: 0,
      mois: 0,
      dateCalcul: currentDate,
      dateValid: currentDate,
      datePayroll: currentDate,
      totalNet: 0,
      totalNetDevise: 0,
      tauxChange: 0,
      calculBy: 'AAAAAAA',
      effectBy: 'AAAAAAA',
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
          dateCalcul: currentDate.format(DATE_FORMAT),
          dateValid: currentDate.format(DATE_FORMAT),
          datePayroll: currentDate.format(DATE_FORMAT),
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

    it('should create a MouvementPaie', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateCalcul: currentDate.format(DATE_FORMAT),
          dateValid: currentDate.format(DATE_FORMAT),
          datePayroll: currentDate.format(DATE_FORMAT),
          dateSituation: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateCalcul: currentDate,
          dateValid: currentDate,
          datePayroll: currentDate,
          dateSituation: currentDate,
          dateop: currentDate,
          createdDate: currentDate,
          modifiedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new MouvementPaie()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MouvementPaie', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          lib: 'BBBBBB',
          annee: 1,
          mois: 1,
          dateCalcul: currentDate.format(DATE_FORMAT),
          dateValid: currentDate.format(DATE_FORMAT),
          datePayroll: currentDate.format(DATE_FORMAT),
          totalNet: 1,
          totalNetDevise: 1,
          tauxChange: 1,
          calculBy: 'BBBBBB',
          effectBy: 'BBBBBB',
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
          dateCalcul: currentDate,
          dateValid: currentDate,
          datePayroll: currentDate,
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

    it('should partial update a MouvementPaie', () => {
      const patchObject = Object.assign(
        {
          tauxChange: 1,
          calculBy: 'BBBBBB',
          effectBy: 'BBBBBB',
        },
        new MouvementPaie()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateCalcul: currentDate,
          dateValid: currentDate,
          datePayroll: currentDate,
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

    it('should return a list of MouvementPaie', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          lib: 'BBBBBB',
          annee: 1,
          mois: 1,
          dateCalcul: currentDate.format(DATE_FORMAT),
          dateValid: currentDate.format(DATE_FORMAT),
          datePayroll: currentDate.format(DATE_FORMAT),
          totalNet: 1,
          totalNetDevise: 1,
          tauxChange: 1,
          calculBy: 'BBBBBB',
          effectBy: 'BBBBBB',
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
          dateCalcul: currentDate,
          dateValid: currentDate,
          datePayroll: currentDate,
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

    it('should delete a MouvementPaie', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMouvementPaieToCollectionIfMissing', () => {
      it('should add a MouvementPaie to an empty array', () => {
        const mouvementPaie: IMouvementPaie = { id: 123 };
        expectedResult = service.addMouvementPaieToCollectionIfMissing([], mouvementPaie);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mouvementPaie);
      });

      it('should not add a MouvementPaie to an array that contains it', () => {
        const mouvementPaie: IMouvementPaie = { id: 123 };
        const mouvementPaieCollection: IMouvementPaie[] = [
          {
            ...mouvementPaie,
          },
          { id: 456 },
        ];
        expectedResult = service.addMouvementPaieToCollectionIfMissing(mouvementPaieCollection, mouvementPaie);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MouvementPaie to an array that doesn't contain it", () => {
        const mouvementPaie: IMouvementPaie = { id: 123 };
        const mouvementPaieCollection: IMouvementPaie[] = [{ id: 456 }];
        expectedResult = service.addMouvementPaieToCollectionIfMissing(mouvementPaieCollection, mouvementPaie);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mouvementPaie);
      });

      it('should add only unique MouvementPaie to an array', () => {
        const mouvementPaieArray: IMouvementPaie[] = [{ id: 123 }, { id: 456 }, { id: 37961 }];
        const mouvementPaieCollection: IMouvementPaie[] = [{ id: 123 }];
        expectedResult = service.addMouvementPaieToCollectionIfMissing(mouvementPaieCollection, ...mouvementPaieArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const mouvementPaie: IMouvementPaie = { id: 123 };
        const mouvementPaie2: IMouvementPaie = { id: 456 };
        expectedResult = service.addMouvementPaieToCollectionIfMissing([], mouvementPaie, mouvementPaie2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mouvementPaie);
        expect(expectedResult).toContain(mouvementPaie2);
      });

      it('should accept null and undefined values', () => {
        const mouvementPaie: IMouvementPaie = { id: 123 };
        expectedResult = service.addMouvementPaieToCollectionIfMissing([], null, mouvementPaie, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mouvementPaie);
      });

      it('should return initial array if no MouvementPaie is added', () => {
        const mouvementPaieCollection: IMouvementPaie[] = [{ id: 123 }];
        expectedResult = service.addMouvementPaieToCollectionIfMissing(mouvementPaieCollection, undefined, null);
        expect(expectedResult).toEqual(mouvementPaieCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
