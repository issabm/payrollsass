jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SexeService } from '../service/sexe.service';
import { ISexe, Sexe } from '../sexe.model';

import { SexeUpdateComponent } from './sexe-update.component';

describe('Sexe Management Update Component', () => {
  let comp: SexeUpdateComponent;
  let fixture: ComponentFixture<SexeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sexeService: SexeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SexeUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(SexeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SexeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sexeService = TestBed.inject(SexeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const sexe: ISexe = { id: 456 };

      activatedRoute.data = of({ sexe });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(sexe));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Sexe>>();
      const sexe = { id: 123 };
      jest.spyOn(sexeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sexe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sexe }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(sexeService.update).toHaveBeenCalledWith(sexe);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Sexe>>();
      const sexe = new Sexe();
      jest.spyOn(sexeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sexe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sexe }));
      saveSubject.complete();

      // THEN
      expect(sexeService.create).toHaveBeenCalledWith(sexe);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Sexe>>();
      const sexe = { id: 123 };
      jest.spyOn(sexeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sexe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sexeService.update).toHaveBeenCalledWith(sexe);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
