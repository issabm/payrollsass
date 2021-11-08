jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EnfantService } from '../service/enfant.service';
import { IEnfant, Enfant } from '../enfant.model';
import { ITypeHandicap } from 'app/entities/type-handicap/type-handicap.model';
import { TypeHandicapService } from 'app/entities/type-handicap/service/type-handicap.service';
import { ISexe } from 'app/entities/sexe/sexe.model';
import { SexeService } from 'app/entities/sexe/service/sexe.service';
import { INiveauScolaire } from 'app/entities/niveau-scolaire/niveau-scolaire.model';
import { NiveauScolaireService } from 'app/entities/niveau-scolaire/service/niveau-scolaire.service';
import { IFamille } from 'app/entities/famille/famille.model';
import { FamilleService } from 'app/entities/famille/service/famille.service';

import { EnfantUpdateComponent } from './enfant-update.component';

describe('Enfant Management Update Component', () => {
  let comp: EnfantUpdateComponent;
  let fixture: ComponentFixture<EnfantUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let enfantService: EnfantService;
  let typeHandicapService: TypeHandicapService;
  let sexeService: SexeService;
  let niveauScolaireService: NiveauScolaireService;
  let familleService: FamilleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [EnfantUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(EnfantUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EnfantUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    enfantService = TestBed.inject(EnfantService);
    typeHandicapService = TestBed.inject(TypeHandicapService);
    sexeService = TestBed.inject(SexeService);
    niveauScolaireService = TestBed.inject(NiveauScolaireService);
    familleService = TestBed.inject(FamilleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TypeHandicap query and add missing value', () => {
      const enfant: IEnfant = { id: 456 };
      const typeHandicap: ITypeHandicap = { id: 43883 };
      enfant.typeHandicap = typeHandicap;

      const typeHandicapCollection: ITypeHandicap[] = [{ id: 99238 }];
      jest.spyOn(typeHandicapService, 'query').mockReturnValue(of(new HttpResponse({ body: typeHandicapCollection })));
      const additionalTypeHandicaps = [typeHandicap];
      const expectedCollection: ITypeHandicap[] = [...additionalTypeHandicaps, ...typeHandicapCollection];
      jest.spyOn(typeHandicapService, 'addTypeHandicapToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ enfant });
      comp.ngOnInit();

      expect(typeHandicapService.query).toHaveBeenCalled();
      expect(typeHandicapService.addTypeHandicapToCollectionIfMissing).toHaveBeenCalledWith(
        typeHandicapCollection,
        ...additionalTypeHandicaps
      );
      expect(comp.typeHandicapsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Sexe query and add missing value', () => {
      const enfant: IEnfant = { id: 456 };
      const sexe: ISexe = { id: 56474 };
      enfant.sexe = sexe;

      const sexeCollection: ISexe[] = [{ id: 92047 }];
      jest.spyOn(sexeService, 'query').mockReturnValue(of(new HttpResponse({ body: sexeCollection })));
      const additionalSexes = [sexe];
      const expectedCollection: ISexe[] = [...additionalSexes, ...sexeCollection];
      jest.spyOn(sexeService, 'addSexeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ enfant });
      comp.ngOnInit();

      expect(sexeService.query).toHaveBeenCalled();
      expect(sexeService.addSexeToCollectionIfMissing).toHaveBeenCalledWith(sexeCollection, ...additionalSexes);
      expect(comp.sexesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NiveauScolaire query and add missing value', () => {
      const enfant: IEnfant = { id: 456 };
      const nivScolaire: INiveauScolaire = { id: 86038 };
      enfant.nivScolaire = nivScolaire;

      const niveauScolaireCollection: INiveauScolaire[] = [{ id: 65423 }];
      jest.spyOn(niveauScolaireService, 'query').mockReturnValue(of(new HttpResponse({ body: niveauScolaireCollection })));
      const additionalNiveauScolaires = [nivScolaire];
      const expectedCollection: INiveauScolaire[] = [...additionalNiveauScolaires, ...niveauScolaireCollection];
      jest.spyOn(niveauScolaireService, 'addNiveauScolaireToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ enfant });
      comp.ngOnInit();

      expect(niveauScolaireService.query).toHaveBeenCalled();
      expect(niveauScolaireService.addNiveauScolaireToCollectionIfMissing).toHaveBeenCalledWith(
        niveauScolaireCollection,
        ...additionalNiveauScolaires
      );
      expect(comp.niveauScolairesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Famille query and add missing value', () => {
      const enfant: IEnfant = { id: 456 };
      const famille: IFamille = { id: 13866 };
      enfant.famille = famille;

      const familleCollection: IFamille[] = [{ id: 80280 }];
      jest.spyOn(familleService, 'query').mockReturnValue(of(new HttpResponse({ body: familleCollection })));
      const additionalFamilles = [famille];
      const expectedCollection: IFamille[] = [...additionalFamilles, ...familleCollection];
      jest.spyOn(familleService, 'addFamilleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ enfant });
      comp.ngOnInit();

      expect(familleService.query).toHaveBeenCalled();
      expect(familleService.addFamilleToCollectionIfMissing).toHaveBeenCalledWith(familleCollection, ...additionalFamilles);
      expect(comp.famillesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const enfant: IEnfant = { id: 456 };
      const typeHandicap: ITypeHandicap = { id: 99873 };
      enfant.typeHandicap = typeHandicap;
      const sexe: ISexe = { id: 42954 };
      enfant.sexe = sexe;
      const nivScolaire: INiveauScolaire = { id: 88428 };
      enfant.nivScolaire = nivScolaire;
      const famille: IFamille = { id: 44487 };
      enfant.famille = famille;

      activatedRoute.data = of({ enfant });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(enfant));
      expect(comp.typeHandicapsSharedCollection).toContain(typeHandicap);
      expect(comp.sexesSharedCollection).toContain(sexe);
      expect(comp.niveauScolairesSharedCollection).toContain(nivScolaire);
      expect(comp.famillesSharedCollection).toContain(famille);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Enfant>>();
      const enfant = { id: 123 };
      jest.spyOn(enfantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enfant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: enfant }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(enfantService.update).toHaveBeenCalledWith(enfant);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Enfant>>();
      const enfant = new Enfant();
      jest.spyOn(enfantService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enfant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: enfant }));
      saveSubject.complete();

      // THEN
      expect(enfantService.create).toHaveBeenCalledWith(enfant);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Enfant>>();
      const enfant = { id: 123 };
      jest.spyOn(enfantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enfant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(enfantService.update).toHaveBeenCalledWith(enfant);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackTypeHandicapById', () => {
      it('Should return tracked TypeHandicap primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTypeHandicapById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSexeById', () => {
      it('Should return tracked Sexe primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSexeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackNiveauScolaireById', () => {
      it('Should return tracked NiveauScolaire primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackNiveauScolaireById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackFamilleById', () => {
      it('Should return tracked Famille primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackFamilleById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
