jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SensService } from '../service/sens.service';
import { ISens, Sens } from '../sens.model';

import { SensUpdateComponent } from './sens-update.component';

describe('Sens Management Update Component', () => {
  let comp: SensUpdateComponent;
  let fixture: ComponentFixture<SensUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sensService: SensService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SensUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(SensUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SensUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sensService = TestBed.inject(SensService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const sens: ISens = { id: 456 };

      activatedRoute.data = of({ sens });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(sens));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Sens>>();
      const sens = { id: 123 };
      jest.spyOn(sensService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sens });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sens }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(sensService.update).toHaveBeenCalledWith(sens);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Sens>>();
      const sens = new Sens();
      jest.spyOn(sensService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sens });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sens }));
      saveSubject.complete();

      // THEN
      expect(sensService.create).toHaveBeenCalledWith(sens);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Sens>>();
      const sens = { id: 123 };
      jest.spyOn(sensService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sens });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sensService.update).toHaveBeenCalledWith(sens);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
