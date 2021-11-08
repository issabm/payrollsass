jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { MouvementPaieService } from '../service/mouvement-paie.service';
import { IMouvementPaie, MouvementPaie } from '../mouvement-paie.model';
import { ISituation } from 'app/entities/situation/situation.model';
import { SituationService } from 'app/entities/situation/service/situation.service';
import { IEmploye } from 'app/entities/employe/employe.model';
import { EmployeService } from 'app/entities/employe/service/employe.service';
import { INatureMvtPaie } from 'app/entities/nature-mvt-paie/nature-mvt-paie.model';
import { NatureMvtPaieService } from 'app/entities/nature-mvt-paie/service/nature-mvt-paie.service';
import { IDemandeCalculPaie } from 'app/entities/demande-calcul-paie/demande-calcul-paie.model';
import { DemandeCalculPaieService } from 'app/entities/demande-calcul-paie/service/demande-calcul-paie.service';

import { MouvementPaieUpdateComponent } from './mouvement-paie-update.component';

describe('MouvementPaie Management Update Component', () => {
  let comp: MouvementPaieUpdateComponent;
  let fixture: ComponentFixture<MouvementPaieUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mouvementPaieService: MouvementPaieService;
  let situationService: SituationService;
  let employeService: EmployeService;
  let natureMvtPaieService: NatureMvtPaieService;
  let demandeCalculPaieService: DemandeCalculPaieService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [MouvementPaieUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(MouvementPaieUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MouvementPaieUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mouvementPaieService = TestBed.inject(MouvementPaieService);
    situationService = TestBed.inject(SituationService);
    employeService = TestBed.inject(EmployeService);
    natureMvtPaieService = TestBed.inject(NatureMvtPaieService);
    demandeCalculPaieService = TestBed.inject(DemandeCalculPaieService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Situation query and add missing value', () => {
      const mouvementPaie: IMouvementPaie = { id: 456 };
      const situation: ISituation = { id: 75925 };
      mouvementPaie.situation = situation;

      const situationCollection: ISituation[] = [{ id: 97910 }];
      jest.spyOn(situationService, 'query').mockReturnValue(of(new HttpResponse({ body: situationCollection })));
      const additionalSituations = [situation];
      const expectedCollection: ISituation[] = [...additionalSituations, ...situationCollection];
      jest.spyOn(situationService, 'addSituationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mouvementPaie });
      comp.ngOnInit();

      expect(situationService.query).toHaveBeenCalled();
      expect(situationService.addSituationToCollectionIfMissing).toHaveBeenCalledWith(situationCollection, ...additionalSituations);
      expect(comp.situationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Employe query and add missing value', () => {
      const mouvementPaie: IMouvementPaie = { id: 456 };
      const employe: IEmploye = { id: 50730 };
      mouvementPaie.employe = employe;

      const employeCollection: IEmploye[] = [{ id: 45277 }];
      jest.spyOn(employeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeCollection })));
      const additionalEmployes = [employe];
      const expectedCollection: IEmploye[] = [...additionalEmployes, ...employeCollection];
      jest.spyOn(employeService, 'addEmployeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mouvementPaie });
      comp.ngOnInit();

      expect(employeService.query).toHaveBeenCalled();
      expect(employeService.addEmployeToCollectionIfMissing).toHaveBeenCalledWith(employeCollection, ...additionalEmployes);
      expect(comp.employesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NatureMvtPaie query and add missing value', () => {
      const mouvementPaie: IMouvementPaie = { id: 456 };
      const natureMvtPaie: INatureMvtPaie = { id: 79972 };
      mouvementPaie.natureMvtPaie = natureMvtPaie;

      const natureMvtPaieCollection: INatureMvtPaie[] = [{ id: 16464 }];
      jest.spyOn(natureMvtPaieService, 'query').mockReturnValue(of(new HttpResponse({ body: natureMvtPaieCollection })));
      const additionalNatureMvtPaies = [natureMvtPaie];
      const expectedCollection: INatureMvtPaie[] = [...additionalNatureMvtPaies, ...natureMvtPaieCollection];
      jest.spyOn(natureMvtPaieService, 'addNatureMvtPaieToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mouvementPaie });
      comp.ngOnInit();

      expect(natureMvtPaieService.query).toHaveBeenCalled();
      expect(natureMvtPaieService.addNatureMvtPaieToCollectionIfMissing).toHaveBeenCalledWith(
        natureMvtPaieCollection,
        ...additionalNatureMvtPaies
      );
      expect(comp.natureMvtPaiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DemandeCalculPaie query and add missing value', () => {
      const mouvementPaie: IMouvementPaie = { id: 456 };
      const demandeCalculPaie: IDemandeCalculPaie = { id: 77151 };
      mouvementPaie.demandeCalculPaie = demandeCalculPaie;

      const demandeCalculPaieCollection: IDemandeCalculPaie[] = [{ id: 69496 }];
      jest.spyOn(demandeCalculPaieService, 'query').mockReturnValue(of(new HttpResponse({ body: demandeCalculPaieCollection })));
      const additionalDemandeCalculPaies = [demandeCalculPaie];
      const expectedCollection: IDemandeCalculPaie[] = [...additionalDemandeCalculPaies, ...demandeCalculPaieCollection];
      jest.spyOn(demandeCalculPaieService, 'addDemandeCalculPaieToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mouvementPaie });
      comp.ngOnInit();

      expect(demandeCalculPaieService.query).toHaveBeenCalled();
      expect(demandeCalculPaieService.addDemandeCalculPaieToCollectionIfMissing).toHaveBeenCalledWith(
        demandeCalculPaieCollection,
        ...additionalDemandeCalculPaies
      );
      expect(comp.demandeCalculPaiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const mouvementPaie: IMouvementPaie = { id: 456 };
      const situation: ISituation = { id: 7289 };
      mouvementPaie.situation = situation;
      const employe: IEmploye = { id: 67076 };
      mouvementPaie.employe = employe;
      const natureMvtPaie: INatureMvtPaie = { id: 34672 };
      mouvementPaie.natureMvtPaie = natureMvtPaie;
      const demandeCalculPaie: IDemandeCalculPaie = { id: 66813 };
      mouvementPaie.demandeCalculPaie = demandeCalculPaie;

      activatedRoute.data = of({ mouvementPaie });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(mouvementPaie));
      expect(comp.situationsSharedCollection).toContain(situation);
      expect(comp.employesSharedCollection).toContain(employe);
      expect(comp.natureMvtPaiesSharedCollection).toContain(natureMvtPaie);
      expect(comp.demandeCalculPaiesSharedCollection).toContain(demandeCalculPaie);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MouvementPaie>>();
      const mouvementPaie = { id: 123 };
      jest.spyOn(mouvementPaieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mouvementPaie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mouvementPaie }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(mouvementPaieService.update).toHaveBeenCalledWith(mouvementPaie);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MouvementPaie>>();
      const mouvementPaie = new MouvementPaie();
      jest.spyOn(mouvementPaieService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mouvementPaie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mouvementPaie }));
      saveSubject.complete();

      // THEN
      expect(mouvementPaieService.create).toHaveBeenCalledWith(mouvementPaie);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MouvementPaie>>();
      const mouvementPaie = { id: 123 };
      jest.spyOn(mouvementPaieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mouvementPaie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mouvementPaieService.update).toHaveBeenCalledWith(mouvementPaie);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSituationById', () => {
      it('Should return tracked Situation primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSituationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackEmployeById', () => {
      it('Should return tracked Employe primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEmployeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackNatureMvtPaieById', () => {
      it('Should return tracked NatureMvtPaie primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackNatureMvtPaieById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDemandeCalculPaieById', () => {
      it('Should return tracked DemandeCalculPaie primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDemandeCalculPaieById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
