jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EligibiliteExcludeService } from '../service/eligibilite-exclude.service';
import { IEligibiliteExclude, EligibiliteExclude } from '../eligibilite-exclude.model';

import { EligibiliteExcludeUpdateComponent } from './eligibilite-exclude-update.component';

describe('EligibiliteExclude Management Update Component', () => {
  let comp: EligibiliteExcludeUpdateComponent;
  let fixture: ComponentFixture<EligibiliteExcludeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eligibiliteExcludeService: EligibiliteExcludeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [EligibiliteExcludeUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(EligibiliteExcludeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EligibiliteExcludeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    eligibiliteExcludeService = TestBed.inject(EligibiliteExcludeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const eligibiliteExclude: IEligibiliteExclude = { id: 456 };

      activatedRoute.data = of({ eligibiliteExclude });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(eligibiliteExclude));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EligibiliteExclude>>();
      const eligibiliteExclude = { id: 123 };
      jest.spyOn(eligibiliteExcludeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eligibiliteExclude });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eligibiliteExclude }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(eligibiliteExcludeService.update).toHaveBeenCalledWith(eligibiliteExclude);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EligibiliteExclude>>();
      const eligibiliteExclude = new EligibiliteExclude();
      jest.spyOn(eligibiliteExcludeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eligibiliteExclude });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eligibiliteExclude }));
      saveSubject.complete();

      // THEN
      expect(eligibiliteExcludeService.create).toHaveBeenCalledWith(eligibiliteExclude);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EligibiliteExclude>>();
      const eligibiliteExclude = { id: 123 };
      jest.spyOn(eligibiliteExcludeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eligibiliteExclude });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(eligibiliteExcludeService.update).toHaveBeenCalledWith(eligibiliteExclude);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
