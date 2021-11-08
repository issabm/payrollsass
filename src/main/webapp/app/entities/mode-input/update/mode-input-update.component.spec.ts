jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ModeInputService } from '../service/mode-input.service';
import { IModeInput, ModeInput } from '../mode-input.model';

import { ModeInputUpdateComponent } from './mode-input-update.component';

describe('ModeInput Management Update Component', () => {
  let comp: ModeInputUpdateComponent;
  let fixture: ComponentFixture<ModeInputUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let modeInputService: ModeInputService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ModeInputUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(ModeInputUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ModeInputUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    modeInputService = TestBed.inject(ModeInputService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const modeInput: IModeInput = { id: 456 };

      activatedRoute.data = of({ modeInput });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(modeInput));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ModeInput>>();
      const modeInput = { id: 123 };
      jest.spyOn(modeInputService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ modeInput });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: modeInput }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(modeInputService.update).toHaveBeenCalledWith(modeInput);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ModeInput>>();
      const modeInput = new ModeInput();
      jest.spyOn(modeInputService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ modeInput });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: modeInput }));
      saveSubject.complete();

      // THEN
      expect(modeInputService.create).toHaveBeenCalledWith(modeInput);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ModeInput>>();
      const modeInput = { id: 123 };
      jest.spyOn(modeInputService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ modeInput });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(modeInputService.update).toHaveBeenCalledWith(modeInput);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
