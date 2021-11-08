jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DemandeCalculPaieService } from '../service/demande-calcul-paie.service';
import { IDemandeCalculPaie, DemandeCalculPaie } from '../demande-calcul-paie.model';

import { DemandeCalculPaieUpdateComponent } from './demande-calcul-paie-update.component';

describe('DemandeCalculPaie Management Update Component', () => {
  let comp: DemandeCalculPaieUpdateComponent;
  let fixture: ComponentFixture<DemandeCalculPaieUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let demandeCalculPaieService: DemandeCalculPaieService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DemandeCalculPaieUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(DemandeCalculPaieUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DemandeCalculPaieUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    demandeCalculPaieService = TestBed.inject(DemandeCalculPaieService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const demandeCalculPaie: IDemandeCalculPaie = { id: 456 };

      activatedRoute.data = of({ demandeCalculPaie });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(demandeCalculPaie));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DemandeCalculPaie>>();
      const demandeCalculPaie = { id: 123 };
      jest.spyOn(demandeCalculPaieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeCalculPaie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demandeCalculPaie }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(demandeCalculPaieService.update).toHaveBeenCalledWith(demandeCalculPaie);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DemandeCalculPaie>>();
      const demandeCalculPaie = new DemandeCalculPaie();
      jest.spyOn(demandeCalculPaieService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeCalculPaie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demandeCalculPaie }));
      saveSubject.complete();

      // THEN
      expect(demandeCalculPaieService.create).toHaveBeenCalledWith(demandeCalculPaie);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DemandeCalculPaie>>();
      const demandeCalculPaie = { id: 123 };
      jest.spyOn(demandeCalculPaieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeCalculPaie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(demandeCalculPaieService.update).toHaveBeenCalledWith(demandeCalculPaie);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
