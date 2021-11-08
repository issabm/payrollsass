jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { UserLogService } from '../service/user-log.service';
import { IUserLog, UserLog } from '../user-log.model';

import { UserLogUpdateComponent } from './user-log-update.component';

describe('UserLog Management Update Component', () => {
  let comp: UserLogUpdateComponent;
  let fixture: ComponentFixture<UserLogUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let userLogService: UserLogService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [UserLogUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(UserLogUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UserLogUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    userLogService = TestBed.inject(UserLogService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const userLog: IUserLog = { id: 456 };

      activatedRoute.data = of({ userLog });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(userLog));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserLog>>();
      const userLog = { id: 123 };
      jest.spyOn(userLogService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userLog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userLog }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(userLogService.update).toHaveBeenCalledWith(userLog);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserLog>>();
      const userLog = new UserLog();
      jest.spyOn(userLogService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userLog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userLog }));
      saveSubject.complete();

      // THEN
      expect(userLogService.create).toHaveBeenCalledWith(userLog);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserLog>>();
      const userLog = { id: 123 };
      jest.spyOn(userLogService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userLog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(userLogService.update).toHaveBeenCalledWith(userLog);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
