jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ModeCalculService } from '../service/mode-calcul.service';
import { IModeCalcul, ModeCalcul } from '../mode-calcul.model';

import { ModeCalculUpdateComponent } from './mode-calcul-update.component';

describe('ModeCalcul Management Update Component', () => {
  let comp: ModeCalculUpdateComponent;
  let fixture: ComponentFixture<ModeCalculUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let modeCalculService: ModeCalculService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ModeCalculUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(ModeCalculUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ModeCalculUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    modeCalculService = TestBed.inject(ModeCalculService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const modeCalcul: IModeCalcul = { id: 456 };

      activatedRoute.data = of({ modeCalcul });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(modeCalcul));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ModeCalcul>>();
      const modeCalcul = { id: 123 };
      jest.spyOn(modeCalculService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ modeCalcul });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: modeCalcul }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(modeCalculService.update).toHaveBeenCalledWith(modeCalcul);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ModeCalcul>>();
      const modeCalcul = new ModeCalcul();
      jest.spyOn(modeCalculService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ modeCalcul });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: modeCalcul }));
      saveSubject.complete();

      // THEN
      expect(modeCalculService.create).toHaveBeenCalledWith(modeCalcul);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ModeCalcul>>();
      const modeCalcul = { id: 123 };
      jest.spyOn(modeCalculService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ modeCalcul });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(modeCalculService.update).toHaveBeenCalledWith(modeCalcul);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
