jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TargetEligibleService } from '../service/target-eligible.service';
import { ITargetEligible, TargetEligible } from '../target-eligible.model';

import { TargetEligibleUpdateComponent } from './target-eligible-update.component';

describe('TargetEligible Management Update Component', () => {
  let comp: TargetEligibleUpdateComponent;
  let fixture: ComponentFixture<TargetEligibleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let targetEligibleService: TargetEligibleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TargetEligibleUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(TargetEligibleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TargetEligibleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    targetEligibleService = TestBed.inject(TargetEligibleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const targetEligible: ITargetEligible = { id: 456 };

      activatedRoute.data = of({ targetEligible });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(targetEligible));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TargetEligible>>();
      const targetEligible = { id: 123 };
      jest.spyOn(targetEligibleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ targetEligible });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: targetEligible }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(targetEligibleService.update).toHaveBeenCalledWith(targetEligible);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TargetEligible>>();
      const targetEligible = new TargetEligible();
      jest.spyOn(targetEligibleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ targetEligible });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: targetEligible }));
      saveSubject.complete();

      // THEN
      expect(targetEligibleService.create).toHaveBeenCalledWith(targetEligible);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TargetEligible>>();
      const targetEligible = { id: 123 };
      jest.spyOn(targetEligibleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ targetEligible });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(targetEligibleService.update).toHaveBeenCalledWith(targetEligible);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
